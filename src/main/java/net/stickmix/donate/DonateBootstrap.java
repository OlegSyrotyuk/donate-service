package net.stickmix.donate;

import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.typesafe.config.Config;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.SneakyThrows;
import net.stickmix.donate.auth.JwtServerInterceptor;
import net.stickmix.donate.manager.DonateManager;
import net.stickmix.donate.manager.DonateManagerImpl;
import net.stickmix.donate.service.DonateServiceImpl;
import net.villenium.athena.client.impl.StorageManager;
import net.villenium.commons.ServiceTemplate;
import net.villenium.commons.config.ConfigBuilder;
import org.slf4j.LoggerFactory;

public class DonateBootstrap {

    @Getter
    private static ServiceTemplate service;
    @Getter
    private static StorageManager storageManager;
    @Getter
    private static DonateManager donateManager;

    @SneakyThrows
    public static void main(String[] args) {
        Config config = ConfigBuilder.create()
                .setFileName("config.conf")
                .build().getHandle();
        service = ServiceTemplate.builder()
                .gson(new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create())
                .logger(LoggerFactory.getLogger("Donate"))
                .build();
        service.start();
        storageManager = new StorageManager();
        storageManager.start(config.getString("athena.target"), config.getString("athena.client"), config.getString("athena.key"));
        donateManager = new DonateManagerImpl();
        Server server = ServerBuilder.forPort(config.getInt("service.port"))
                .addService(new DonateServiceImpl())
                .intercept(new JwtServerInterceptor(Jwts.parser().setSigningKey(config.getString("auth.signing-key"))))
                .build();
        server.start();
        service.getLogger().info("donate-service is started!");
        server.awaitTermination();
//        service = ServiceTemplate.builder()
//                .gson(new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create())
//                .logger(LoggerFactory.getLogger("Donate"))
//                .build();
//        service.start();
//        storageManager = new StorageManager();
//        storageManager.start("localhost:2202", "auth", "123456");
//        donateManager = new DonateManagerImpl();
//        Server server = ServerBuilder.forPort(2224)
//                .addService(new DonateServiceImpl())
//                //.intercept(new JwtServerInterceptor(Jwts.parser().setSigningKey(config.getString("auth.signing-key"))))
//                .build();
//        server.start();
//        service.getLogger().info("donate-service is started!");
//        server.awaitTermination();
    }
}
