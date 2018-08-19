package com.mastering.spring.springboot.test;

import com.mastering.spring.springboot.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.Base64;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicControllerIT {

    private static final String LOCAL_HOST = "http://localhost:";

    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();
    HttpHeaders headers = createHeaders("user-name", "user-password");

    @Test
    public void welcome() throws Exception {
        ResponseEntity<String> response = template.exchange(createURL("/welcome"), HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        assertThat(response.getBody(), equalTo("Hello World"));
    }

    @Test
    public void welcomeWithObject() throws Exception {
        ResponseEntity<String> response = template.exchange(createURL("/welcome-with-object"), HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        assertThat(response.getBody(), containsString("Hello World"));
    }

    @Test
    public void welcomeWithParameter() throws Exception {
        ResponseEntity<String> response = template.exchange(createURL("/welcome-with-parameter/name/Buddy"), HttpMethod.GET, new HttpEntity<>(null, headers), String.class);
        assertThat(response.getBody(), containsString("Hello World, Buddy"));
    }

    private String createURL(String uri) {
        return LOCAL_HOST + port + uri;
    }

    HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }
}
