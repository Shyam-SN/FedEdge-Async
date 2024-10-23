#pragma once

#include <string>
#include <vector>
#include <openssl/evp.h>

class SecureAggregator {
public:
    SecureAggregator();
    ~SecureAggregator();

    // Initialize the client's DH keypair
    bool initialize();

    // Get the client's public key to send to the server
    std::string getPublicKey() const;

    // Derive the shared secret using the server's public key
    std::vector<uint8_t> deriveSharedSecret(const std::string& serverPublicKeyBytes);

    // Apply the secret mask to the tensor payload
    void applyMask(std::vector<uint8_t>& tensorPayload, const std::vector<uint8_t>& sharedSecret);

private:
    EVP_PKEY* keypair;
};
