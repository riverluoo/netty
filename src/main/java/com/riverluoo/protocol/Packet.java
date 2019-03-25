package com.riverluoo.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author wangyang
 * @since 2019-03-21 16:27
 */
@Data
public abstract class Packet {

    @JSONField(deserialize = false,serialize = false)
    private Byte version =1;

    @JSONField(deserialize = false)
    public abstract Byte getCommand();

}
