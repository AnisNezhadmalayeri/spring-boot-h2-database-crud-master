package com.bezkoder.spring.jpa.h2.controller;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.net.http.HttpResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTests {

    //Testing the Status Code
    @Test
    public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived()
            throws ClientProtocolException, IOException {
        // Given
        String name = RandomStringUtils.randomAlphabetic( 8 );
        HttpUriRequest request = new HttpGet( "https://api.github.com/users/" + name );
        // When
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute( request );
        // Then
        assertThat(
                httpResponse.getStatusLine().getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND));
    }

    //Testing the Media Type
    @Test
    public void
    givenRequestWithNoAcceptHeader_whenRequestIsExecuted_thenDefaultResponseContentTypeIsJson()
            throws ClientProtocolException, IOException {
        // Given
        String jsonMimeType = "application/json";
        HttpUriRequest request = new HttpGet( "https://api.github.com/users/eugenp" );
        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );
        // Then
        String mimeType = ContentType.getOrDefault(response.getEntity()).getMimeType();
        assertEquals( jsonMimeType, mimeType );
    }

    //Testing the JSON Payload
    @Test
    public void
    givenUserExists_whenUserInformationIsRetrieved_thenRetrievedResourceIsCorrect()
            throws ClientProtocolException, IOException {
        // Given
        HttpUriRequest request = new HttpGet( "https://api.github.com/users/eugenp" );
        // When
        HttpResponse response = HttpClientBuilder.create().build().execute( request );
        // Then
        GitHubUser resource = RetrieveUtil.retrieveResourceFromResponse(
                response, GitHubUser.class);
        assertThat( "eugenp", Matchers.is( resource.getLogin() ) );
    }

    //Utilities for Testing
    public class GitHubUser {

        private String login;

        // standard getters and setters
    }
    public static <T> T retrieveResourceFromResponse(HttpResponse response, Class<T> clazz)
            throws IOException {

        String jsonFromResponse = EntityUtils.toString(response.getEntity());
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper.readValue(jsonFromResponse, clazz);
    }
}
