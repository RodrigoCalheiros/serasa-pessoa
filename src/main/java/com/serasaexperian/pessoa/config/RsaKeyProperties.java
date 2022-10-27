package com.serasaexperian.pessoa.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class RsaKeyProperties {
    @Value("${rsa.public-key}")
    private String publicKeyPath;

    @Value("${rsa.private-key}")
    private String privateKeyPath;

    public PublicKey getPublicKey(){
        PublicKey publicKey = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(this.publicKeyPath);
        byte[] keyBytes = new byte[0];
        try {
            keyBytes = inputStream.readAllBytes();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public PrivateKey getPrivateKey() {
        PrivateKey privateKey = null;
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(this.privateKeyPath);
        byte[] keyBytes = new byte[0];
        try{
            keyBytes = inputStream.readAllBytes();
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

}
