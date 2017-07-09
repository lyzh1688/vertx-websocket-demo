package com.obgear.quote.gateway;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.obgear.quote.gateway.verticle.MockKafkaVerticle;
import com.obgear.quote.gateway.verticle.NotifyVerticle;
import com.obgear.quote.gateway.verticle.WebSocketVerticle;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

import java.io.IOException;

import static sun.security.krb5.SCDynamicStoreConfig.getConfig;

/**
 * Created by liuyuezhi on 2017/7/7.
 */
public class Runner {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Injector injector = Guice.createInjector(new QuoteNotifyModule(vertx));
        WebSocketVerticle webSocketVerticle = injector.getInstance(WebSocketVerticle.class);
        NotifyVerticle notifyVerticle = injector.getInstance(NotifyVerticle.class);
        vertx.deployVerticle(webSocketVerticle);
        vertx.deployVerticle(notifyVerticle);
        vertx.deployVerticle(new MockKafkaVerticle());
    }
}
