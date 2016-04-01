package com.magcomm.sharelibrary.adapter;

/**
 * 作者:Created by yezesong on 16-3-2:36
 * 邮箱: yezesong@magcomm.cn
 */
public interface MultiItemTypeSupport<T> {
    int getLayoutId(int position, T t);

    int getViewTypeCount();

    int getItemViewType(int postion, T t);
}
