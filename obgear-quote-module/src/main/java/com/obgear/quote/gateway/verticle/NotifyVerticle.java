package com.obgear.quote.gateway.verticle;

import com.google.inject.Inject;
import com.obgear.quote.gateway.domain.SecurityQuote;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.redis.RedisClient;

import static com.obgear.quote.gateway.utils.Const.SECURITY_CHANGE_TOPIC;
import static com.obgear.quote.gateway.utils.Const.TOPIC_SUBCRIBER;

/**
 * Created by 刘悦之 on 2017/7/8.
 */
public class NotifyVerticle extends AbstractVerticle{

    @Inject
    private RedisClient redisClient;

    @Override
    public void start(){
        final EventBus eventBus = vertx.eventBus();
        vertx.eventBus().consumer(SECURITY_CHANGE_TOPIC, handler->{
            SecurityQuote msg =  (SecurityQuote) handler.body();
//            LocalMap<String, JsonArray> sendMap = vertx.sharedData().getLocalMap(TOPIC_SUBCRIBER);
//            JsonArray  subscribers = sendMap.get(msg.getSecurityCode());
//            if(subscribers == null){
//                return;
//            }
//            System.out.println("subscriber size : " + subscribers.getList().size());
            redisClient.smembers(TOPIC_SUBCRIBER + msg.getSecurityCode(),result->{
                if(result.failed()){
                    System.out.println("redisClient get member failed");
                }
                else {
                    JsonArray subscribers = result.result();
                    for(Object subscriber:subscribers.getList()){
                        System.out.println("subscriber : " + subscriber);
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.put("securityCode",msg.getSecurityCode())
                                .put("price",msg.getPrice())
                                .put("buyLevel1",msg.getBuyLevel1())
                                .put("buyLevel2",msg.getBuyLevel2())
                                .put("buyLevel3",msg.getBuyLevel3())
                                .put("buyLevel4",msg.getBuyLevel4())
                                .put("buyLevel5",msg.getBuyLevel5())
                                .put("saleLevel1",msg.getSaleLevel1())
                                .put("saleLevel2",msg.getSaleLevel2())
                                .put("saleLevel3",msg.getSaleLevel3())
                                .put("saleLevel4",msg.getSaleLevel4())
                                .put("saleLevel5",msg.getSaleLevel5());

                        eventBus.send((String) subscriber,jsonObject.toString());
                    }
                }
            });

//            for(Object subscriber:subscribers.getList()){
//                System.out.println("subscriber : " + subscriber);
//                JsonObject jsonObject = new JsonObject();
//                jsonObject.put("securityCode",msg.getSecurityCode())
//                        .put("price",msg.getPrice())
//                        .put("buyLevel1",msg.getBuyLevel1())
//                        .put("buyLevel2",msg.getBuyLevel2())
//                        .put("buyLevel3",msg.getBuyLevel3())
//                        .put("buyLevel4",msg.getBuyLevel4())
//                        .put("buyLevel5",msg.getBuyLevel5())
//                        .put("saleLevel1",msg.getSaleLevel1())
//                        .put("saleLevel2",msg.getSaleLevel2())
//                        .put("saleLevel3",msg.getSaleLevel3())
//                        .put("saleLevel4",msg.getSaleLevel4())
//                        .put("saleLevel5",msg.getSaleLevel5());
//
//                eventBus.send((String) subscriber,jsonObject.toString());
//            }
        });
    }
}
