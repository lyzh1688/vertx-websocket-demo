package com.obgear.quote.gateway.verticle;

import com.obgear.quote.gateway.domain.SecurityQuote;
import com.obgear.quote.gateway.domain.SecurityQuoteCodec;
import com.obgear.quote.gateway.utils.RandomUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;

import static com.obgear.quote.gateway.utils.Const.SECURITY_CHANGE_TOPIC;

/**
 * Created by 刘悦之 on 2017/7/9.
 */
public class MockKafkaVerticle extends AbstractVerticle {
    private static final String securityCode1 = "000001";
    private static final String securityCode2 = "000002";
    private static final String securityCode3 = "000003";


    @Override
    public void start(){
        final EventBus eventBus = vertx.eventBus();
        vertx.eventBus().registerDefaultCodec(SecurityQuote.class, new SecurityQuoteCodec());
        vertx.setPeriodic(3000,new Handler<Long>(){
            @Override
            public void handle(Long aLong) {
                SecurityQuote securityQuote1 = new SecurityQuote();
                securityQuote1.setSecurityCode(securityCode1);
                SecurityQuote securityQuote2 = new SecurityQuote();
                securityQuote2.setSecurityCode(securityCode2);
                SecurityQuote securityQuote3 = new SecurityQuote();
                securityQuote3.setSecurityCode(securityCode3);
                try {
                    securityQuote1.setPrice(RandomUtil.nextDouble(20.0,30.0));
                    securityQuote1.setBuyLevel1(securityQuote1.getPrice() - 0.1);
                    securityQuote1.setBuyLevel2(securityQuote1.getPrice() - 0.2);
                    securityQuote1.setBuyLevel3(securityQuote1.getPrice() - 0.3);
                    securityQuote1.setBuyLevel4(securityQuote1.getPrice() - 0.4);
                    securityQuote1.setBuyLevel5(securityQuote1.getPrice() - 0.5);
                    securityQuote1.setSaleLevel1(securityQuote1.getPrice() + 0.1);
                    securityQuote1.setSaleLevel2(securityQuote1.getPrice() + 0.2);
                    securityQuote1.setSaleLevel3(securityQuote1.getPrice() + 0.3);
                    securityQuote1.setSaleLevel4(securityQuote1.getPrice() + 0.4);
                    securityQuote1.setSaleLevel5(securityQuote1.getPrice() + 0.5);

                    securityQuote2.setPrice(RandomUtil.nextDouble(20.0,30.0));
                    securityQuote2.setBuyLevel1(securityQuote1.getPrice() - 0.1);
                    securityQuote2.setBuyLevel2(securityQuote1.getPrice() - 0.2);
                    securityQuote2.setBuyLevel3(securityQuote1.getPrice() - 0.3);
                    securityQuote2.setBuyLevel4(securityQuote1.getPrice() - 0.4);
                    securityQuote2.setBuyLevel5(securityQuote1.getPrice() - 0.5);
                    securityQuote2.setSaleLevel1(securityQuote1.getPrice() + 0.1);
                    securityQuote2.setSaleLevel2(securityQuote1.getPrice() + 0.2);
                    securityQuote2.setSaleLevel3(securityQuote1.getPrice() + 0.3);
                    securityQuote2.setSaleLevel4(securityQuote1.getPrice() + 0.4);
                    securityQuote2.setSaleLevel5(securityQuote1.getPrice() + 0.5);

                    securityQuote3.setPrice(RandomUtil.nextDouble(20.0,30.0));
                    securityQuote3.setBuyLevel1(securityQuote1.getPrice() - 0.1);
                    securityQuote3.setBuyLevel2(securityQuote1.getPrice() - 0.2);
                    securityQuote3.setBuyLevel3(securityQuote1.getPrice() - 0.3);
                    securityQuote3.setBuyLevel4(securityQuote1.getPrice() - 0.4);
                    securityQuote3.setBuyLevel5(securityQuote1.getPrice() - 0.5);
                    securityQuote3.setSaleLevel1(securityQuote1.getPrice() + 0.1);
                    securityQuote3.setSaleLevel2(securityQuote1.getPrice() + 0.2);
                    securityQuote3.setSaleLevel3(securityQuote1.getPrice() + 0.3);
                    securityQuote3.setSaleLevel4(securityQuote1.getPrice() + 0.4);
                    securityQuote3.setSaleLevel5(securityQuote1.getPrice() + 0.5);

                    eventBus.send(SECURITY_CHANGE_TOPIC,securityQuote1);
                    eventBus.send(SECURITY_CHANGE_TOPIC,securityQuote2);
                    eventBus.send(SECURITY_CHANGE_TOPIC,securityQuote3);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
