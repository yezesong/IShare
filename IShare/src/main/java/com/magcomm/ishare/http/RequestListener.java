package com.magcomm.ishare.http;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.NetworkError;
import com.magcomm.ishare.R;
import com.magcomm.sharelibrary.utils.ToastUtils;

/**
 * 作者:Created by yezesong on 16-3-23:21
 * 邮箱: yezesong@magcomm.cn
 */
public abstract class RequestListener<T> {

    public Response.Listener<T> mListener;
    public Response.ErrorListener mErrorListener;

    public RequestListener() {
        mListener = new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                onSuccess(response);
            }
        };
        mErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NoConnectionError) {
                    ToastUtils.show(R.string.net_poor_connections);
                } else if (error instanceof TimeoutError) {
                    ToastUtils.show(R.string.error_timer_out);
                } else if (error instanceof ServerError) {
                    ToastUtils.show(R.string.server_error);
                } else if (error instanceof NetworkError) {
                    ToastUtils.show(R.string.server_path_error);
                } else {
                    onFailed(error);
                }
            }
        };
    }

    public abstract void onSuccess(T response);

    public abstract void onFailed(VolleyError error);

    public Response.Listener<T> getListener() {
        return mListener;
    }

    public Response.ErrorListener getErrorListener() {
        return mErrorListener;
    }
}
