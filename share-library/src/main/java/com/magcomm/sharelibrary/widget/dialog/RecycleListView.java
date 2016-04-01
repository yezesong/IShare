/**
 * Copyright (C) 2015  Haiyang Yu Android Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.magcomm.sharelibrary.widget.dialog;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.magcomm.sharelibrary.utils.DensityUtils;

public class RecycleListView extends ListView {
    public boolean mRecycleOnMeasure = true;
    public int mMaxHeight;

    public RecycleListView(Context context) {
        super(context);
        init();
    }

    public RecycleListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecycleListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void init() {
        int width = DensityUtils.getScreenW(getContext());
        int height = DensityUtils.getScreenH(getContext());
        if (width > height) {
            mMaxHeight = height / 2;
        } else {
            mMaxHeight = height * 3 / 7;
        }
    }

    protected boolean recycleOnMeasure() {
        return mRecycleOnMeasure;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightSpec = MeasureSpec.makeMeasureSpec(mMaxHeight, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightSpec);

    }
}
