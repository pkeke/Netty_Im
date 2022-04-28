package com.pke.netty.util;


import com.pke.netty.handler.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author pke
 * @data 2022/4/28 16:49
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }
    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
