package net.stickmix.donate.auth;

import io.grpc.Context;
import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

public class Constant {
    static final String BEARER_TYPE = "Bearer";

    static final Metadata.Key<String> AUTHORIZATION_METADATA_KEY = Metadata.Key.of("Authorization", ASCII_STRING_MARSHALLER);
    static final Context.Key<String> CLIENT_ID_CONTEXT_KEY = Context.key("clientId");

    private Constant() {
        throw new AssertionError();
    }
}
