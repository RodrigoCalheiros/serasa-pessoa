package com.serasaexperian.pessoa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class RsaKeyProperties {
    @Value("${rsa.public-key}")
    private String publicKeyPath;

    @Value("${rsa.private-key}")
    private String privateKeyPath;

    public PublicKey getPublicKey() throws Exception{
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(this.publicKeyPath);
        byte[] keyBytes = inputStream.readAllBytes();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public PrivateKey getPrivateKey() throws Exception {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(this.privateKeyPath);
        byte[] keyBytes = inputStream.readAllBytes();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

}
