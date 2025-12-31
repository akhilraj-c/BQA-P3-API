package com.mindteck.common.models.rest;

import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;

@Getter
@Setter
public class RSAKeys {

    public PrivateKey privateKey;

    public PublicKey publicKey;
}
