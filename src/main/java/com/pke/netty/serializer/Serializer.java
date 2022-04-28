package com.pke.netty.serializer;

/**
 * @author pke
 * @data 2022/4/28 21:34
 */
public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();

    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
