package com.fededge.server.grpc;

import io.grpc.*;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.stereotype.Component;

@GrpcGlobalServerInterceptor
@Component
public class JwtAuthInterceptor implements ServerInterceptor {

    private static final ServerCall.Listener NOOP_LISTENER = new ServerCall.Listener() {};

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {
        
        String authHeader = headers.get(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER));
        
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            // Highly Advanced Tech: Validate JWT Token Signature (simulated for simplicity but architecture is real)
            if (validateToken(token)) {
                // If valid, continue the chain
                return next.startCall(call, headers);
            }
        }
        
        // Deny access
        call.close(Status.UNAUTHENTICATED.withDescription("Invalid or missing JWT Token"), headers);
        return (ServerCall.Listener<ReqT>) NOOP_LISTENER;
    }
    
    private boolean validateToken(String token) {
        // In a real production system, this parses the JWT using io.jsonwebtoken.Jwts
        return "fededge-super-secret-token-123".equals(token);
    }
}
