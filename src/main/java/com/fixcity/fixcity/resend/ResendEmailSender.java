package com.fixcity.fixcity.resend;

import com.fixcity.fixcity.email.EmailMessage;
import com.fixcity.fixcity.email.EmailNotSentException;
import com.fixcity.fixcity.email.EmailSender;
import com.fixcity.fixcity.resend.client.ResendClient;
import com.fixcity.fixcity.resend.exception.ResendClientException;
import com.fixcity.fixcity.resend.properties.ResendProperties;
import com.fixcity.fixcity.resend.request.ResendRequest;
import com.fixcity.fixcity.resend.response.ResendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResendEmailSender implements EmailSender {
    private final ResendClient resendClient;
    private final ResendProperties resendProperties;

    @Override
    public void send(EmailMessage emailMessage) {
        Objects.requireNonNull(emailMessage, "email must not be null");
        ResendRequest request = new ResendRequest(resendProperties.from(), emailMessage.to(), emailMessage.subject(), emailMessage.text());

        try {
            ResendResponse response = resendClient.sendEmail(request);
            log.info("Email sent, resend id={}", response.id());
        } catch (ResendClientException ex) {
            throw new EmailNotSentException(ex);
        }
    }
}
