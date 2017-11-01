package com.scrat.gogo.data.callback;

import com.scrat.gogo.framework.common.BaseNetCallback;
import com.scrat.gogo.framework.common.BaseResponse;
import com.scrat.gogo.framework.util.L;

/**
 * Created by scrat on 2017/6/23.
 */

public abstract class DefaultLoadObjCallback<T, R extends BaseResponse<T>>
        extends BaseNetCallback<R> {

    protected abstract void onSuccess(T t);

    protected abstract void onFail(String msg);

    public abstract void onError(Exception e);

    @Override
    protected void onRequestSuccess(R res) {
        if (res.isSuccess()) {
            onSuccess(res.getData());
        } else {
            onFail(res.getMsg());
        }
    }

    @Override
    protected void onRequestFailure(Exception e) {
        L.e(e);
        onError(e);
    }

}
