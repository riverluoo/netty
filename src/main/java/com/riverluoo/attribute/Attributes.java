package com.riverluoo.attribute;

import io.netty.util.AttributeKey;

/**
 * @author wangyang
 * @since 2019-03-21 16:42
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN=AttributeKey.newInstance("login");
}
