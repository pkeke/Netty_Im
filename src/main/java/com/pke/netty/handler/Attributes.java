package com.pke.netty.handler;

import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;

/**
 * @author pke
 * @data 2022/4/28 15:58
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
