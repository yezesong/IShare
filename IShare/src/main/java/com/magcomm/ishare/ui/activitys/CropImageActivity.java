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

package com.magcomm.ishare.ui.activitys;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.bumptech.glide.Glide;
import com.magcomm.ishare.R;
import com.magcomm.ishare.widget.CropImageView;
import com.magcomm.sharelibrary.BaseActivity;
import com.magcomm.sharelibrary.utils.FileUtils;
import com.magcomm.sharelibrary.views.TopBar;
import com.magcomm.sharelibrary.widget.dialog.LoadingDialog;

import java.io.File;
import java.io.IOException;

import utils.SelectPhotoUtils;

public class CropImageActivity extends BaseActivity {

    public static final String KEY_PATH = "crop_image_path";
    public static final String KEY_RESULT_PATH = "result_croped_image_path";
    public static final String KEY_RATIO_X = "result_croped_image_ratio_x";
    public static final String KEY_RATIO_Y = "result_croped_image_ratio_y";
    private CropImageView mCropView;
    private String mPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipimage);
        init();
        Intent intent = getIntent();
        mPath = intent.getStringExtra(KEY_PATH);
        Glide.with(this)
                .load(mPath)
                .asBitmap()
                .into(mCropView);

        int x = intent.getIntExtra(KEY_RATIO_X, 1);
        int y = intent.getIntExtra(KEY_RATIO_Y, 1);
        mCropView.setCustomRatio(x, y);
    }

    private void init() {
        //TopBar topbar = bindView(R.id.topbar);
        //topbar.setOnTopBarListener(this);
        mCropView = bindView(R.id.crop);
    }

    public void doClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                /*Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image*//*");
                startActivity(intent);*/
                finish();
                break;
            case R.id.btn_select:
                LoadingDialog dialog = LoadingDialog.show(this);
                Bitmap bitmap = mCropView.getCroppedBitmap();
                String cachePath = FileUtils.compressImageAndSaveCache(this, bitmap, 300);
                Intent intent2 = new Intent();
                intent2.putExtra(KEY_RESULT_PATH, cachePath);
                setResult(SelectPhotoUtils.Request.REQUEST_CROP, intent2);
                dialog.dismiss();
                finish();
                break;
        }
    }

    public static Bitmap loadBitmap(String imgpath) {
        return BitmapFactory.decodeFile(imgpath);
    }


    /**
     * 从给定的路径加载图片，并指定是否自动旋转方向
     */
    public static Bitmap loadBitmap(String imgpath, boolean adjustOritation) {
        if (!adjustOritation) {
            return loadBitmap(imgpath);
        } else {
            Bitmap bm = loadBitmap(imgpath);
            int digree = 0;
            ExifInterface exif = null;
            try {
                exif = new ExifInterface(imgpath);
            } catch (IOException e) {
                e.printStackTrace();
                exif = null;
            }
            if (exif != null) {
                // 读取图片中相机方向信息
                int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);
                // 计算旋转角度
                switch (ori) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        digree = 90;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_180:
                        digree = 180;
                        break;
                    case ExifInterface.ORIENTATION_ROTATE_270:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;
                }
            }
            if (digree != 0) {
                // 旋转图片
                Matrix m = new Matrix();
                m.postRotate(digree);
                bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
            }
            return bm;
        }
    }
}
