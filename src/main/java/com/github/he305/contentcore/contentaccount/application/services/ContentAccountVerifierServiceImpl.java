package com.github.he305.contentcore.contentaccount.application.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.he305.contentcore.contentaccount.application.dto.ContentAccountDto;
import com.github.he305.contentcore.contentaccount.domain.exceptions.ContentAccountVerifierException;
import com.github.he305.contentcore.contentaccount.domain.model.values.ContentAccountDetails;
import com.github.he305.contentcore.contentaccount.domain.service.ContentAccountVerifierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContentAccountVerifierServiceImpl implements ContentAccountVerifierService {
    public static final String API_VERIFY = "http://CONTENT-ACCOUNT-VERIFIER/api/verify";
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Override
    public void verify(ContentAccountDetails details) throws ContentAccountVerifierException {
        ContentAccountDto dto = new ContentAccountDto(details.getName(), details.getPlatform().name());
        try {
            HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(dto));
            restTemplate.exchange(API_VERIFY, HttpMethod.GET, request, Void.class);
        } catch (HttpClientErrorException ex) {
            throw new ContentAccountVerifierException(details);
        } catch (ResourceAccessException | HttpServerErrorException ex) {
            log.info("Error retrieving data for " + details);
        } catch (JsonProcessingException e) {
            log.info("Error serializing dto " + details);
        }
    }
}
