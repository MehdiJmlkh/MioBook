package ir.ac.ut.ece.ie.utils;

import org.springframework.http.HttpMethod;

public record EndpointTestCase(String url, HttpMethod method) {
}
