package com.fixcity.fixcity.resend.client;

import com.fixcity.fixcity.resend.exception.ResendClientException;
import com.fixcity.fixcity.resend.request.ResendRequest;
import com.fixcity.fixcity.resend.response.ResendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class ResendClient {
    private final RestClient resendRestClient;

    public ResendResponse sendEmail(ResendRequest request){
        try {
            return resendRestClient.post()
                    .uri("/emails")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request)
                    .retrieve()
                    .body(ResendResponse.class);
        } catch (RestClientException ex) {
            throw new ResendClientException(ex);
        }
    }
}
