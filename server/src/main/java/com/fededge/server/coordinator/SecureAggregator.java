package com.fededge.server.coordinator;

import org.springframework.stereotype.Component;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

@Component
public class SecureAggregator {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public SecureAggregator() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("EC");
            keyPairGen.initialize(256);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch (Exception e) {
            System.err.println("Failed to initialize DH Keys: " + e.getMessage());
        }
    }

    public byte[] getPublicKey() {
        return publicKey.getEncoded();
    }

    public byte[] deriveSharedSecret(byte[] clientPublicKeyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("EC");
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(clientPublicKeyBytes);
            PublicKey clientPublicKey = keyFactory.generatePublic(x509KeySpec);

            KeyAgreement keyAgreement = KeyAgreement.getInstance("ECDH");
            keyAgreement.init(privateKey);
            keyAgreement.doPhase(clientPublicKey, true);

            return keyAgreement.generateSecret();
        } catch (Exception e) {
            System.err.println("Failed to derive shared secret: " + e.getMessage());
            return new byte[0];
        }
    }
}
