package com.magcomm.ishare.http;

import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.magcomm.ishare.IShareApp;

import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.magcomm.sharelibrary.utils.Debug;

import java.util.HashMap;
import java.util.Map;

/**
 * 作者:Created by yezesong on 16-3-17:58
 * 邮箱: yezesong@magcomm.cn
 * <p/>
 * 网络请求的封装
 */
public class ShareHttp {

    private static String appendParameter(String url, Map<String, String> params) {
        Uri uri = Uri.parse(url);
        Uri.Builder builder = uri.buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build().getQuery();
    }

    /**
     * @param tag             设置tag，便于cancel请求
     * @param url             请求Url
     * @param param           传递的值
     * @param requestListener 回递接口
     */
    public static void postStringRequest(String tag, String url, final Map<String, String> param, RequestListener<String> requestListener) {
        StringRequest stringRequest = new StringRequest(Method.POST, url, requestListener.getListener(),
                requestListener.getErrorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("appkey", "fbed1d1b4b1449daa4bc49397cbe2350");
                return maps;
            }
        };
        stringRequest.setTag(tag);
        //超时时间10s,最大重连次数2次
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        IShareApp.getHttpQueues().add(stringRequest);
    }

    /**
     * @param tag             设置tag，便于cancel请求
     * @param url             请求Url
     * @param param           传递的值
     * @param requestListener 回递接口
     */
    public static void postJsonRequest(String tag, String url, Map<String, String> param, RequestListener<JSONObject> requestListener) {
        //JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.POST, url, param, requestListener.getListener(),
        //        requestListener.getErrorListener());

        String stringRequest = appendParameter(url, param);
        Debug.log("stringRequest = " + stringRequest);
        ShareJsonObjectRequest jsonRequest = new ShareJsonObjectRequest(url, stringRequest, requestListener.getListener(),
                requestListener.getErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("appkey", "fbed1d1b4b1449daa4bc49397cbe2350");
                return maps;
            }
        };
        jsonRequest.setTag(tag);
        //超时时间10s,最大重连次数2次

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        IShareApp.getHttpQueues().add(jsonRequest);
    }

    public static void doImageRequest(String tag, String url, RequestListener<Bitmap> requestListener) {
        doImageRequest(tag, url, 0, 0, requestListener);
    }

    public static void doImageRequest(String tag, String url, int maxWidth, int maxHeight,
                                      RequestListener<Bitmap> requestListener) {
        doImageRequest(tag, url, maxWidth, maxHeight, ImageView.ScaleType.CENTER, requestListener);
    }

    public static void doImageRequest(String tag, String url, int maxWidth, int maxHeight, ImageView.ScaleType scale,
                                      RequestListener<Bitmap> requestListener) {
        doImageRequest(tag, url, maxWidth, maxHeight, scale, Bitmap.Config.RGB_565, requestListener);
    }

    /**
     * @param tag             设置tag，便于cancel请求
     * @param url             请求Url
     * @param maxWidth        最大宽度设置，0则使用原始宽度
     * @param maxHeight       最大高度设置，0则使用默认高度
     * @param scale           图片缩放样式，默认居中全显
     * @param config          图片使用的Config，默认564
     * @param requestListener 回递接口
     */
    public static void doImageRequest(String tag, String url, int maxWidth, int maxHeight, ImageView.ScaleType scale,
                                      Bitmap.Config config, RequestListener<Bitmap> requestListener) {
        ImageRequest imageRequest = new ImageRequest(url, requestListener.getListener(),
                maxWidth, maxHeight, scale, config, requestListener.getErrorListener());
        imageRequest.setTag(tag);
        //超时时间10s,最大重连次数2次
        imageRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        IShareApp.getHttpQueues().add(imageRequest);
    }

    /**
     * @param tag             设置tag，便于cancel请求
     * @param url             请求Url
     * @param requestListener 回递接口
     */
    public static void getStringRequest(String tag, String url, RequestListener<String> requestListener) {
        StringRequest stringRequest = new StringRequest(Method.GET, url, requestListener.getListener(),
                requestListener.getErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("appkey", "fbed1d1b4b1449daa4bc49397cbe2350");
                return maps;
            }
        };
        stringRequest.setTag(tag);
        //超时时间10s,最大重连次数2次
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        IShareApp.getHttpQueues().add(stringRequest);
    }

    /**
     * @param tag             设置tag，便于cancel请求
     * @param url             请求Url
     * @param requestListener 回递接口
     */
    public static void getJsonRequest(String tag, String url, RequestListener<JSONObject> requestListener) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Method.GET, url, requestListener.getListener(),
                requestListener.getErrorListener()) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> maps = new HashMap<>();
                maps.put("appkey", "fbed1d1b4b1449daa4bc49397cbe2350");
                return maps;
            }
        };
        jsonRequest.setTag(tag);
        //超时时间10s,最大重连次数2次
        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 2, 1.0f));
        IShareApp.getHttpQueues().add(jsonRequest);
    }
}
