package com.riverluoo.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangyang
 * @since 2019-04-10 16:12
 */
@Data
@NoArgsConstructor
public class Session {

    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId +":"+ userName;
    }
}
