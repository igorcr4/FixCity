package com.fixcity.fixcity.subscrption.service;

import com.fixcity.fixcity.municipality.model.Municipality;
import com.fixcity.fixcity.municipality.service.MunicipalityService;
import com.fixcity.fixcity.subscrption.enumeration.PlanType;
import com.fixcity.fixcity.subscrption.enumeration.SubscriptionStatus;
import com.fixcity.fixcity.subscrption.mapper.SubscriptionMapper;
import com.fixcity.fixcity.subscrption.model.Subscription;
import com.fixcity.fixcity.subscrption.repository.SubscriptionRepository;
import com.fixcity.fixcity.subscrption.request.CheckoutRequest;
import com.fixcity.fixcity.subscrption.response.CheckoutResponse;
import com.fixcity.fixcity.subscrption.response.SubscriptionResponse;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.SubscriptionItem;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final SubscriptionRepository repository;
    private final MunicipalityService municipalityService;
    private final SubscriptionMapper subscriptionMapper;

    @Value("${stripe.price.urban}")
    private String priceUrban;

    @Value("${stripe.price.city-pro}")
    private String priceCityPro;

    @Value("${stripe.webhook.secret}")
    private String webhookSecret;

    private Map<PlanType, String> priceMap;

    @PostConstruct
    private void initPriceMap() {
        priceMap = Map.of(
                PlanType.URBAN, priceUrban,
                PlanType.CITY_PRO, priceCityPro
        );
    }

    @Transactional
    public CheckoutResponse createCheckoutSession(CheckoutRequest request) {
        Municipality municipality = municipalityService.findById(request.municipalityId());

        if(municipality.getStripeCustomerId() == null) {
            try {
                CustomerCreateParams params =
                        CustomerCreateParams.builder()
                                .setName(municipality.getName())
                                .putMetadata("municipalityId", String.valueOf(request.municipalityId()))
                                .build();

                Customer customer = Customer.create(params);
                municipality.setStripeCustomerId(customer.getId());
            } catch (StripeException e) {
                throw new RuntimeException(e);//trebuie sa adaug o exceptie personalizata dupa
            }
        }

        try {
            SessionCreateParams params =
                    SessionCreateParams.builder()
                            .setCustomer(municipality.getStripeCustomerId())
                            .addLineItem(
                                    SessionCreateParams.LineItem.builder()
                                            .setPrice(priceMap.get(request.plan()))
                                            .setQuantity(1L)
                                            .build()
                            )
                            .putMetadata("plan", request.plan().name())
                            .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                            .setSuccessUrl("http://localhost:3030/subscription/success")
                            .setCancelUrl("http://localhost:3030/subscription/cancel")
                            .build();
            Session session = Session.create(params);
            return new CheckoutResponse(session.getUrl());

        } catch (StripeException e) {
            throw new RuntimeException(e);//exceptie personalizata
        }
    }

    @Transactional
    public void handleWebhook(String payload, String stripeSignature) {

        try {
            Event event = Webhook.constructEvent(payload, stripeSignature, webhookSecret);

            switch (event.getType()) {
                case "checkout.session.completed" -> {
                    Session session = (Session) event.getDataObjectDeserializer()
                            .getObject()
                            .orElseThrow(); //exceptie personalizata

                    String customerId = session.getCustomer();
                    String stripeSubscriptionId = session.getSubscription();
                    String planName = session.getMetadata().get("plan");

                    Municipality municipality = municipalityService.findByStripeCustomerId(customerId);

                    com.stripe.model.Subscription stripeSubscription = com.stripe.model.Subscription.retrieve(stripeSubscriptionId);

                    SubscriptionItem item = stripeSubscription.getItems().getData().getFirst();

                    Subscription subscription = new Subscription();
                    subscription.setMunicipality(municipality);
                    subscription.setStripeSubscriptionId(stripeSubscriptionId);
                    subscription.setPlan(PlanType.valueOf(planName));
                    subscription.setStatus(SubscriptionStatus.ACTIVE);
                    subscription.setCurrentPeriodStart(
                            LocalDateTime.ofEpochSecond(item.getCurrentPeriodStart(), 0, ZoneOffset.UTC));
                    subscription.setCurrentPeriodEnd(
                            LocalDateTime.ofEpochSecond(item.getCurrentPeriodEnd(), 0, ZoneOffset.UTC));

                    repository.save(subscription);
                }

                case "customer.subscription.updated" -> {
                    com.stripe.model.Subscription stripeSubscription = (com.stripe.model.Subscription) event
                            .getDataObjectDeserializer()
                            .getObject()
                            .orElseThrow();//exceptie personalizata

                    String stripeSubscriptionId = stripeSubscription.getId();
                    boolean cancelAtPeriodEnd = stripeSubscription.getCancelAtPeriodEnd();
                    SubscriptionItem item = stripeSubscription.getItems().getData().getFirst();

                    Subscription subscription = repository.findByStripeSubscriptionId(stripeSubscriptionId).orElseThrow();//personalizata

                    subscription.setStatus(mapStatus(stripeSubscription.getStatus()));
                    subscription.setCancelAtPeriodEnd(cancelAtPeriodEnd);
                    subscription.setCurrentPeriodStart(
                            LocalDateTime.ofEpochSecond(item.getCurrentPeriodStart(), 0 , ZoneOffset.UTC)
                    );
                    subscription.setCurrentPeriodEnd(
                            LocalDateTime.ofEpochSecond(item.getCurrentPeriodEnd(), 0, ZoneOffset.UTC)
                    );
                }

                case "customer.subscription.deleted" -> {
                    com.stripe.model.Subscription stripeSubscription = (com.stripe.model.Subscription) event
                            .getDataObjectDeserializer()
                            .getObject()
                            .orElseThrow();//exceptie personalizata

                    String stripeSubscriptionId = stripeSubscription.getId();
                    Long canceledAt = stripeSubscription.getCanceledAt();

                    Subscription subscription = repository.findByStripeSubscriptionId(stripeSubscriptionId).orElseThrow();//personalizata
                    subscription.setStatus(mapStatus(stripeSubscription.getStatus()));
                    subscription.setCanceledAt(
                            LocalDateTime.ofEpochSecond(canceledAt, 0 ,ZoneOffset.UTC)
                    );
                }
            }
        } catch (SignatureVerificationException e) {
            throw new RuntimeException(e); // exceptie personalizata
        } catch (StripeException e) {
            throw new RuntimeException(e); // exceptie personalizata
        }
    }

    public Optional<SubscriptionResponse> subscriptionResponse(Long municipalityId) {
        return repository.findByMunicipalityId(municipalityId)
                .map(subscriptionMapper::toResponse);
    }

     Optional<Subscription> findByMunicipalityId(Long municipalityId) {
        return repository.findByMunicipalityId(municipalityId);
    }

    private SubscriptionStatus mapStatus(String status) {
        return SubscriptionStatus.valueOf(status.toUpperCase());
    }
}
