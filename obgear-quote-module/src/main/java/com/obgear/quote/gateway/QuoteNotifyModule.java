package com.obgear.quote.gateway;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Scopes;
import io.vertx.core.Vertx;
import io.vertx.redis.RedisClient;
import io.vertx.redis.RedisOptions;

/**
 * Created by 刘悦之 on 2017/7/9.
 */
public class QuoteNotifyModule extends AbstractModule {

    private Vertx vertx;

    public QuoteNotifyModule(Vertx vertx){
        this.vertx = vertx;
    }

    @Override
    protected void configure() {
        RedisOptions config = new RedisOptions();
        config.setHost("127.0.0.1");
        config.setPort(6379);
//        config.setAuth("admin");
        RedisClient redisClient = RedisClient.create(this.vertx, config);

        final Binder binder = binder();
        binder.bind(RedisClient.class).toInstance(redisClient);
    }
}
