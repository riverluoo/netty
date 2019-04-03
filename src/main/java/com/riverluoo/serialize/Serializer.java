package com.riverluoo.serialize;


import com.riverluoo.serialize.impl.JSONSerializer;

/**
 * @author wangyang
 * @since 2019-03-21 16:46
 */
public interface Serializer {

    Serializer DEFAULT = (Serializer) new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * java 对象转换成二进制
     */
    byte[] serialize(Object object);

    /**
     * 二进制转换成 java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
