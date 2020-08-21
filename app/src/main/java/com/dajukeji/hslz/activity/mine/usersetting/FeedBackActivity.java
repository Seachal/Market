package com.dajukeji.hslz.activity.mine.usersetting;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.dajukeji.hslz.R;
import com.dajukeji.hslz.adapter.pictureselect.FullyGridLayoutManager;
import com.dajukeji.hslz.adapter.pictureselect.GridImageAdapter;
import com.dajukeji.hslz.base.HttpBaseActivity;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.presenter.FeedBackPresenter;
import com.dajukeji.hslz.util.PhoneFormatCheckUtils;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.view.SelectedButton;
import com.dajukeji.hslz.view.dialog.LoadingDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;

/**
 * 意见反馈
 */

public class FeedBackActivity extends HttpBaseActivity {

    private FeedBackPresenter feedBackPresenter;
    private EditText mEdtContent;
    private EditText mEdtPhone;
    private RecyclerView mRecyclerView;
    private SelectedButton feed_back_submit;

    private GridImageAdapter mImgAdapter;
    private List<LocalMedia> mSelectList;
    private int mMaxSelectNum = 3;
    private List<String> filePaths = new ArrayList<>();

    @Override
    protected void loadLayout(Bundle savedInstanceState) {
        setContentView(R.layout.activity_feed_back);
        setTitleBar(R.string.text_feedback, true);
        feedBackPresenter = new FeedBackPresenter(this);
    }

    @Override
    protected void initView() {
        mEdtContent = (EditText) findViewById(R.id.feed_back_content);
        mEdtPhone = (EditText) findViewById(R.id.feed_back_phone);
        mRecyclerView = (RecyclerView) findViewById(R.id.feed_back_add_photo);
        feed_back_submit = (SelectedButton) findViewById(R.id.feed_back_submit);
        initPictureSelect();
        feed_back_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                if(!mEdtPhone.getText().toString().trim().equals("")){
                    if(PhoneFormatCheckUtils.isPhoneLegal(mEdtPhone.getText().toString().trim())){
                        params.put("phone",mEdtPhone.getText().toString());
                    }else {
                        showToast("请填写正确手机号码");
                        return;
                    }
                }else {
                    showToast("请填写手机号码");
                    return;
                }
                LoadingDialog.showLoadingDialog(getContext(), "请等待。。。");
                List<File> files = new ArrayList<>();
                for(String path:filePaths){
                    File file = new File(path);
                    files.add(file);
                }
                String url = HttpAddress.mBaseUrl2 +HttpAddress.feedback;
                params.put("token", SPUtil.getPrefString("token",""));
                params.put("content",mEdtContent.getText().toString());
                if(files.isEmpty()){ // 是否上传图片 0 有 1 无
                     params.put("haveImage","1");
                }else {
                    params.put("haveImage","0");
                }
                if(files.isEmpty()){
                    OkHttpUtils.post()//
                            .url(url)
                            .params(params)//
                            .build()//
                            .execute(new MyStringCallback());
                }else  if(files.size()==1){
                    OkHttpUtils.post()//
                            .addFile("refund_photo1", "image.jpg", files.size()>=1?files.get(0):new File(""))
                            .url(url)
                            .params(params)//
                            .build()//
                            .execute(new MyStringCallback());
                }else if(files.size()==2){
                    OkHttpUtils.post()//
                            .addFile("refund_photo1", "image.jpg", files.size()>=1?files.get(0):new File(""))
                            .addFile("refund_photo2", "image.jpg", files.size()>=2?files.get(1):new File(""))//
                            .url(url)
                            .params(params)//
                            .build()//
                            .execute(new MyStringCallback());
                }else if(files.size()==3){
                    OkHttpUtils.post()//
                            .addFile("refund_photo1", "image.jpg", files.size()>=1?files.get(0):new File(""))
                            .addFile("refund_photo2", "image.jpg", files.size()>=2?files.get(1):new File(""))//
                            .addFile("refund_photo3", "image.jpg", files.size()>=3?files.get(2):new File(""))//
                            .url(url)
                            .params(params)//
                            .build()//
                            .execute(new MyStringCallback());
                }

            }
        });
        mEdtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    feed_back_submit.setBackgroundResource(R.drawable.btn_phone_sure);
                    feed_back_submit.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    if(filePaths.isEmpty()&&mEdtPhone.getText().toString().equals("")){
                        feed_back_submit.setBackgroundResource(R.drawable.btn_phone_login);
                        feed_back_submit.setTextColor(Color.parseColor("#666666"));
                    }
                }
            }
        });
        mEdtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    feed_back_submit.setBackgroundResource(R.drawable.btn_phone_sure);
                    feed_back_submit.setTextColor(Color.parseColor("#ffffff"));
                }else {
                    if(filePaths.isEmpty()&&mEdtContent.getText().toString().equals("")){
                        feed_back_submit.setBackgroundResource(R.drawable.btn_phone_login);
                        feed_back_submit.setTextColor(Color.parseColor("#666666"));
                    }
                }
            }
        });
    }



    @Override
    public boolean supportX() {
        return true;
    }

    @Override
    public void setResultData(Object object, String contentType) {
        super.setResultData(object, contentType);
    }

    private class MyStringCallback extends StringCallback{
        @Override
        public void onError(Call call, Exception e, int id) {
            hideDialogLoading();
            showToast("反馈失败");
            finish();
        }

        @Override
        public void onResponse(String response, int id) {
            try {
                JSONObject json=new JSONObject(response);
                if(json.get("status").toString().equals("0")) {
                    hideDialogLoading();
                    showToast("反馈成功");
                    finish();
                }else {
                    hideDialogLoading();
                    showToast("反馈失败");
                }
            } catch (JSONException e) {
                hideDialogLoading();
                showToast("反馈失败");
            }
        }
    }

    /**
     * 初始化相册选择
     */
    private void initPictureSelect(){
        if(mSelectList == null){mSelectList = new ArrayList<>();}

        FullyGridLayoutManager manager = new FullyGridLayoutManager(FeedBackActivity.this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mImgAdapter = new GridImageAdapter(FeedBackActivity.this, onAddPicClickListener);
        mImgAdapter.setList(mSelectList);
        mImgAdapter.setSelectMax(mMaxSelectNum);
        mRecyclerView.setAdapter(mImgAdapter);
        mImgAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mSelectList.size() > 0) {
                    LocalMedia media = mSelectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(FeedBackActivity.this).externalPicturePreview(position, mSelectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(FeedBackActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(FeedBackActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(FeedBackActivity.this);
                } else {
                    Toast.makeText(FeedBackActivity.this, getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {

            // 进入相册
            PictureSelector.create(FeedBackActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.picture_default_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(mMaxSelectNum)// 最大图片选择数量
                    .imageSpanCount(3)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .compress(true)// 是否压缩
                    .synOrAsy(false)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .isGif(false)// 是否显示gif图片
                    .selectionMedia(mSelectList)// 是否传入已选图片
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code

        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //图片选择回调
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    mSelectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : mSelectList) {
//                        MLog.INSTANCE.i("图片-----》", media.getPath());
                        filePaths.add(media.getPath());
                    }
                    if(!filePaths.isEmpty()){
                        feed_back_submit.setBackgroundResource(R.drawable.btn_phone_sure);
                        feed_back_submit.setTextColor(Color.parseColor("#ffffff"));
                    }else {
                        if(mEdtContent.getText().toString().equals("")&&mEdtPhone.getText().toString().equals("")){
                            feed_back_submit.setBackgroundResource(R.drawable.btn_phone_login);
                            feed_back_submit.setTextColor(Color.parseColor("#666666"));
                        }
                    }

                    mImgAdapter.setList(mSelectList);
                    mImgAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


}
