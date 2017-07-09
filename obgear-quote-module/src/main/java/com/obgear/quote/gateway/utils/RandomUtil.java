package com.obgear.quote.gateway.utils;

import java.util.Random;

/**
 * Created by 刘悦之 on 2017/7/9.
 */
public class RandomUtil {

    public static double nextDouble(final double min, final double max) throws Exception {
        if (max < min) {
            throw new Exception("min < max");
        }
        if (min == max) {
            return min;
        }
        return min + ((max - min) * new Random().nextDouble());
    }
}
