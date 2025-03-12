#include "SecureAggregator.h"
#include <openssl/ec.h>
#include <openssl/obj_mac.h>
#include <openssl/x509.h>
#include <iostream>

SecureAggregator::SecureAggregator() : keypair(nullptr) {}

SecureAggregator::~SecureAggregator() {
    if (keypair) {
        EVP_PKEY_free(keypair);
    }
}

bool SecureAggregator::initialize() {
    EVP_PKEY_CTX *pctx = EVP_PKEY_CTX_new_id(EVP_PKEY_EC, NULL);
    if (!pctx) return false;

    if (EVP_PKEY_keygen_init(pctx) <= 0) return false;
    if (EVP_PKEY_CTX_set_ec_paramgen_curve_nid(pctx, NID_X9_62_prime256v1) <= 0) return false;

    if (EVP_PKEY_keygen(pctx, &keypair) <= 0) return false;
    
    EVP_PKEY_CTX_free(pctx);
    return true;
}

std::string SecureAggregator::getPublicKey() const {
    if (!keypair) return "";
    
    unsigned char *buf = nullptr;
    int len = i2d_PUBKEY(keypair, &buf);
    if (len <= 0) return "";
    
    std::string pubKey(reinterpret_cast<char*>(buf), len);
    OPENSSL_free(buf);
    return pubKey;
}

std::vector<uint8_t> SecureAggregator::deriveSharedSecret(const std::string& serverPublicKeyBytes) {
    std::vector<uint8_t> secret;
    if (!keypair) return secret;

    const unsigned char* p = reinterpret_cast<const unsigned char*>(serverPublicKeyBytes.data());
    EVP_PKEY* peerKey = d2i_PUBKEY(NULL, &p, serverPublicKeyBytes.size());
    if (!peerKey) return secret;

    EVP_PKEY_CTX* ctx = EVP_PKEY_CTX_new(keypair, NULL);
    if (!ctx || EVP_PKEY_derive_init(ctx) <= 0 || EVP_PKEY_derive_set_peer(ctx, peerKey) <= 0) {
        if(ctx) EVP_PKEY_CTX_free(ctx);
        EVP_PKEY_free(peerKey);
        return secret;
    }

    size_t secretLen;
    if (EVP_PKEY_derive(ctx, NULL, &secretLen) <= 0) {
        EVP_PKEY_CTX_free(ctx);
        EVP_PKEY_free(peerKey);
        return secret;
    }

    secret.resize(secretLen);
    if (EVP_PKEY_derive(ctx, secret.data(), &secretLen) <= 0) {
        secret.clear();
    } else {
        secret.resize(secretLen);
    }

    EVP_PKEY_CTX_free(ctx);
    EVP_PKEY_free(peerKey);
    return secret;
}

void SecureAggregator::applyMask(std::vector<uint8_t>& tensorPayload, const std::vector<uint8_t>& sharedSecret) {
    if (sharedSecret.empty()) return;
    
    std::cout << "[SecureAggregator] Applying DH mask to tensor payload (size: " << tensorPayload.size() << ")" << std::endl;
    // XOR the tensor payload with the shared secret (repeated)
    for (size_t i = 0; i < tensorPayload.size(); ++i) {
        tensorPayload[i] ^= sharedSecret[i % sharedSecret.size()];
    }
}
