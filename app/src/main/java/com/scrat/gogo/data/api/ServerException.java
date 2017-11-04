package com.scrat.gogo.data.api;

import com.scrat.gogo.framework.common.BaseResponse;

/**
 * Created by scrat on 2017/11/4.
 */

public class ServerException extends Exception {
    public ServerException(BaseResponse response) {
        super(response.getMsg());
    }
}
