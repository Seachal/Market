package com.dajukeji.hslz.im.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.dajukeji.hslz.R;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.im.utils.FileUtil;
import com.tencent.TIMCallBack;
import com.tencent.TIMImage;

import java.io.IOException;

/**
 * 消息图片-原图界面
 */
public class ImageViewActivity extends Activity {

    private ImageView mImageView;
    private boolean mIsDownloading;
    private TIMImage mThumbTIMImage, mOriginalTIMImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_view);

        mImageView = (ImageView) findViewById(R.id.image);
        mThumbTIMImage = (TIMImage)getIntent().getSerializableExtra("thumb");
        mOriginalTIMImage = (TIMImage)getIntent().getSerializableExtra("original");

        if(mThumbTIMImage != null && mOriginalTIMImage != null){
            show(this);
        }

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Bitmap getImage(String path){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int reqWidth, reqHeight, width=options.outWidth, height=options.outHeight;
        if (width > height){
            reqWidth = getWindowManager().getDefaultDisplay().getWidth();
            reqHeight = (reqWidth * height)/width;
        }else{
            reqHeight = getWindowManager().getDefaultDisplay().getHeight();
            reqWidth = (width * reqHeight)/height;
        }
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        try{
            options.inSampleSize = inSampleSize;
            options.inJustDecodeBounds = false;
            Matrix mat = new Matrix();
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            if (bitmap == null) {
                Toast.makeText(this, getString(R.string.file_not_found), Toast.LENGTH_SHORT).show();
                return null;
            }
            ExifInterface ei =  new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    mat.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    mat.postRotate(180);
                    break;
            }
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mat, true);
        }catch (IOException e){
            return null;
        }
    }

    private void showThumb(String filename){
        Bitmap bitmap = BitmapFactory.decodeFile(FileUtil.getCacheFilePath(filename));
        mImageView.setImageBitmap(bitmap);
    }

    private void show(final Context context){

        //判断本地是否存在原图

        //本地存在原图
        if (FileUtil.isCacheFileExist(mOriginalTIMImage.getUuid())){

            //显示原图
            setOriginalBitmap(mOriginalTIMImage.getUuid());

        }else{
            //本地不存在原图，先显示缩略图
            showThumb(mThumbTIMImage.getUuid());

            //showLoading
            buildProgressDialog();

            //下载原图
            if (!mIsDownloading){
                mIsDownloading = true;
                mOriginalTIMImage.getImage(FileUtil.getCacheFilePath(mOriginalTIMImage.getUuid()), new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        //错误码code和错误描述desc，可用于定位请求失败原因
                        //错误码code含义请参见错误码表

                        Toast.makeText(context, AliSdkApplication.getContext().getString(R.string.download_fail), Toast.LENGTH_SHORT).show();
                        mIsDownloading = false;
                        cancelProgressDialog();
                    }

                    @Override
                    public void onSuccess() {
                        setOriginalBitmap(mOriginalTIMImage.getUuid());
                        mIsDownloading = false;
                        cancelProgressDialog();
                    }
                });
            }else{
                Toast.makeText(context, AliSdkApplication.getContext().getString(R.string.downloading), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setOriginalBitmap(String filename){
        Bitmap bitmap = getImage(FileUtil.getCacheFilePath(filename));
        if (bitmap != null){
            mImageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 加载框
     */
    private ProgressDialog progressDialog;
    public void buildProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
//        progressDialog.setMessage(getString(id));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    /**
     * @Description: TODO 取消加载框
     * @author Sunday
     * @date 2015年12月25日
     */
    public void cancelProgressDialog() {
        if (progressDialog != null)
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
    }
}
