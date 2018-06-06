package com.sky_wf.chinachat.net.transformer;

import android.util.Log;

import com.sky_wf.chinachat.net.exception.APIException;
import com.sky_wf.chinachat.net.entity.HttpResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * @Date :  2018/6/1
 * @Author : WF
 * @Description :
 */
public class HandleErrorTransformer implements Observable.Transformer {
    @Override
    public Object call(Object o) {
        Log.d("wftt","HandleErrorTransformer>>>"+o);
        return((Observable)o).map(new HttpResultFunc());
    }

    // 统一对请求的结果做预处理，异常则抛出，成功则返回到Subscriber
    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T>
    {
        @Override
        public T call(HttpResult<T> tHttpResult)
        {
            Log.d("wftt","HttpResultFunc>>>"+tHttpResult.getCode());
            if (!tHttpResult.isSucess())
            {
                throw new APIException(tHttpResult.getCode(), tHttpResult.getDesc());
            }
            return tHttpResult.getData();
        }
    }
}
