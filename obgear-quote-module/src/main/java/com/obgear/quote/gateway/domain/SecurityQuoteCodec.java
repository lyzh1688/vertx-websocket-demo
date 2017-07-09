package com.obgear.quote.gateway.domain;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.*;

/**
 * Created by 刘悦之 on 2017/7/9.
 */
public class SecurityQuoteCodec implements MessageCodec<SecurityQuote,SecurityQuote> {
    @Override
    public void encodeToWire(Buffer buffer, SecurityQuote s) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o;
        try {
            o = new ObjectOutputStream(b);
            o.writeObject(s);
            o.close();
            buffer.appendBytes(b.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public SecurityQuote decodeFromWire(int pos, Buffer buffer) {
        final ByteArrayInputStream b = new ByteArrayInputStream(buffer.getBytes());
        ObjectInputStream o = null;
        SecurityQuote msg = null;
        try {
            o = new ObjectInputStream(b);
            msg = (SecurityQuote) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public SecurityQuote transform(SecurityQuote securityQuote) {
        return securityQuote;
    }

    @Override
    public String name() {
        return "SecurityQuoteCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}
