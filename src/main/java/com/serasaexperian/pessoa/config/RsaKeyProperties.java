package com.serasaexperian.pessoa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
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
        Resource resource = new ClassPathResource(this.publicKeyPath);
        byte[] keyBytes = Files.readAllBytes(resource.getFile().toPath());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public PrivateKey getPrivateKey() throws Exception {
        Resource resource = new ClassPathResource(this.privateKeyPath);
        byte[] keyBytes = Files.readAllBytes(resource.getFile().toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

}
