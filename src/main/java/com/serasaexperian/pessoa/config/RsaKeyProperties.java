package com.serasaexperian.pessoa.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class RsaKeyProperties {

    private RSAPublicKey publicKey;

    private RSAPrivateKey privateKey;

    public PrivateKey getPrivateKey() throws Exception {
        Resource resource = new ClassPathResource("certs/private_key.der");
        byte[] keyBytes = Files.readAllBytes(resource.getFile().toPath());

        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = null;

        kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public PublicKey getPublicKey() throws Exception{
        Resource resource = new ClassPathResource("certs/public_key.der");
        byte[] keyBytes = Files.readAllBytes(resource.getFile().toPath());

        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }



}
