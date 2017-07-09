package com.obgear.quote.gateway.verticle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.ext.web.handler.sockjs.SockJSSocket;
import io.vertx.redis.RedisClient;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.obgear.quote.gateway.utils.Const.TOPIC_SUBCRIBER;

/**
 * Created by liuyuezhi on 2017/7/7.
 */
public class WebSocketVerticle extends AbstractVerticle {

    @Inject
    private RedisClient redisClient;

    @Override
    public void start(){
        final EventBus eventBus = vertx.eventBus();
        final Pattern chatUrlPattern = Pattern.compile("/quote/(\\w+)");

        vertx.createHttpServer().websocketHandler(new Handler<ServerWebSocket>() {
            @Override
            public void handle(final ServerWebSocket ws) {

                final Matcher m = chatUrlPattern.matcher(ws.path());
                if (!m.matches()) {
                    ws.reject();
                    return;
                }
                final String securityCode = m.group(1);
                final String id = ws.textHandlerID();
                //save to redis
                redisClient.sadd(TOPIC_SUBCRIBER + securityCode,id,reply -> {
                    if(!reply.succeeded()){
                        System.out.println("redisClient save failed...");
                    }
                });
//                LocalMap<String, JsonArray> map = vertx.sharedData().getLocalMap(TOPIC_SUBCRIBER);
//                JsonArray ids =  map.get(securityCode);
//                if(map.get(securityCode) == null){
//                    ids = new JsonArray();
//                    map.put(securityCode,ids);
//                    ids.add(id);
//                }
//                else {
//                    map.get(securityCode).add(id);
//                }
//
//                System.out.println("ids num : " + ids.size());
//                System.out.println("id : " + id);
                ws.closeHandler(new Handler<Void>() {
                    @Override
                    public void handle(final Void event) {
                        System.out.println("leave : " + securityCode);
                        redisClient.srem(TOPIC_SUBCRIBER + securityCode,id,result -> {
                            if(result.failed()){
                                System.out.println("redisClient remove id failed");
                            }
                        });
                        //vertx.sharedData().getLocalMap("quote.security").remove(id);
                    }
                });
            }
        }).listen(8090);

    }
}
