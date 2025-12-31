package com.mindteck.common.service;

public interface WebFluxService<T> {

    void post(T payload, String uri);
}
