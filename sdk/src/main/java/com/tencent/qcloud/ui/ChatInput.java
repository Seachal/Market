package com.tencent.qcloud.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.tencent.qcloud.R;
import com.tencent.qcloud.presentation.viewfeatures.ChatView;
import com.tencent.qcloud.ui.adapter.HorizontalRecyclerviewAdapter;
import com.tencent.qcloud.ui.adapter.NoHorizontalScrollerVPAdapter;
import com.tencent.qcloud.ui.emotionkeyboardview.EmotionKeyboard;
import com.tencent.qcloud.ui.fragment.EmotiomComplateFragment;
import com.tencent.qcloud.ui.fragment.FragmentFactory;
import com.tencent.qcloud.ui.model.ImageModel;
import com.tencent.qcloud.ui.utils.EmotionUtils;
import com.tencent.qcloud.ui.utils.GlobalOnItemClickManagerUtils;
import com.tencent.qcloud.ui.utils.SharedPreferencedUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天界面输入控件
 */
public class ChatInput extends RelativeLayout implements View.OnClickListener {

    private static final String TAG = "ChatInput";

    private Button btnSend;
    private ImageButton btnMore, btnKeyboard, btnEmotion;
    private EditText editText;
    private boolean isSendVisible,isEmoticonReady;
    private ChatView chatView;
    private LinearLayout morePanel;
    private final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private EmotionKeyboard mEmotionKeyboard;
    private ViewPager mViewPager;
    private RecyclerView mRecyclerView;
    private HorizontalRecyclerviewAdapter horizontalRecyclerviewAdapter;
    private LinearLayout mLlEmotionPanel;
    private Context mContext;
    private AppCompatActivity mActivity;
    List<Fragment> fragments = new ArrayList<>();
    private View contentView;//需要绑定的内容view

    //当前被选中底部tab
    private static final String CURRENT_POSITION_FLAG = "CURRENT_POSITION_FLAG";
    private int CurrentPosition = 0;

    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private boolean isBindToBarEditText=true;

    public ChatInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        this.mActivity = (AppCompatActivity) context;

        LayoutInflater.from(context).inflate(R.layout.chat_input, this);
        findViewById();
//        initView();
    }

    private void findViewById(){
        btnMore = (ImageButton) findViewById(R.id.btn_add);
        btnSend = (Button) findViewById(R.id.btn_send);
        btnEmotion = (ImageButton) findViewById(R.id.btnEmoticon);
        btnKeyboard = (ImageButton) findViewById(R.id.btn_keyboard);
        morePanel = (LinearLayout) findViewById(R.id.morePanel);
        editText = (EditText) findViewById(R.id.input);
        mLlEmotionPanel = (LinearLayout) findViewById(R.id.ll_emotion_layout);
        mViewPager = (ViewPager) findViewById(R.id.vp_emotionview_layout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_horizontal);
//        emoticonPanel = (LinearLayout) findViewById(R.id.emoticonPanel);
        LinearLayout BtnImage = (LinearLayout) findViewById(R.id.btn_photo);
        BtnImage.setOnClickListener(this);

        LinearLayout BtnPhoto = (LinearLayout) findViewById(R.id.btn_image);
        BtnPhoto.setOnClickListener(this);
    }

    public void initView(){
        btnSend.setOnClickListener(this);
        isSendVisible = editText.getText().length() != 0;
        setSendBtn();

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isSendVisible = s!=null&&s.length()>0;
                setSendBtn();
                if (isSendVisible){
                    chatView.sending();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmotionKeyboard = EmotionKeyboard.with(mActivity)
                .setEmotionView(mLlEmotionPanel)//绑定表情面板
                .setMoreView(morePanel)//绑定更多内容面板
                .bindToContent(contentView)//绑定内容view
                .bindToEditText(!isBindToBarEditText ? ((EditText) contentView) : editText)//判断绑定那种EditView
                .bindToEmotionButton(btnEmotion)//绑定表情按钮
                .bindToKeyboardButton(btnKeyboard)//绑定键盘按钮
                .bindToMoreButton(btnMore)//更多内容按钮
                .build();

        initDatas();

        //创建全局监听
        GlobalOnItemClickManagerUtils globalOnItemClickManager= GlobalOnItemClickManagerUtils.getInstance(mActivity);

        if(isBindToBarEditText){
            //绑定当前Bar的编辑框
            globalOnItemClickManager.attachToEditText(editText);

        }else{
            // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
            globalOnItemClickManager.attachToEditText((EditText) contentView);
            mEmotionKeyboard.bindToEditText((EditText)contentView);
        }
    }

    /**
     * 关联聊天界面逻辑
     */
    public void setChatView(ChatView chatView){
        this.chatView = chatView;
    }

    /**
     * 绑定内容view
     * @param contentView
     * @return
     */
    public void bindToContentView(View contentView){
        this.contentView = contentView;
    }

    private void setSendBtn(){
        if (isSendVisible){
            btnMore.setVisibility(GONE);
            btnSend.setVisibility(VISIBLE);
        }else{
            btnMore.setVisibility(VISIBLE);
            btnSend.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View v) {
        Activity activity = (Activity) getContext();
        int id = v.getId();
        if (id == R.id.btn_send){
            chatView.sendText();
        }
        if (id == R.id.btn_photo){
            if(activity!=null && requestCamera(activity)){
                chatView.sendPhoto();
            }
        }
        if (id == R.id.btn_image){
            if(activity!=null && requestStorage(activity)){
                chatView.sendImage();
            }
        }
    }

    /**
     * 获取输入框文字
     */
    public Editable getText(){
        return editText.getText();
    }

    /**
     * 设置输入框文字
     */
    public void setText(String text){
        editText.setText(text);
    }

    /**
     * 数据操作,这里是测试数据，请自行更换数据
     */
    protected void initDatas(){
        replaceFragment();
        List<ImageModel> list = new ArrayList<>();
        for (int i=0 ; i<fragments.size(); i++){
            ImageModel model1=new ImageModel();
            model1.icon= getResources().getDrawable(R.drawable.ic_expression);
            model1.flag="经典笑脸";
            model1.isSelected=true;
            list.add(model1);
        }

        //记录底部默认选中第一个
        CurrentPosition=0;
        SharedPreferencedUtils.setInteger(mActivity, CURRENT_POSITION_FLAG, CurrentPosition);

        //底部tab
        horizontalRecyclerviewAdapter = new HorizontalRecyclerviewAdapter(mActivity,list);
        mRecyclerView.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        mRecyclerView.setAdapter(horizontalRecyclerviewAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 1, GridLayoutManager.HORIZONTAL, false));
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter.setOnClickItemListener(new HorizontalRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View view, int position, List<ImageModel> datas) {
                //获取先前被点击tab
                int oldPosition = SharedPreferencedUtils.getInteger(mActivity, CURRENT_POSITION_FLAG, 0);
                //修改背景颜色的标记
                datas.get(oldPosition).isSelected = false;
                //记录当前被选中tab下标
                CurrentPosition = position;
                datas.get(CurrentPosition).isSelected = true;
                SharedPreferencedUtils.setInteger(mActivity, CURRENT_POSITION_FLAG, CurrentPosition);
                //通知更新，这里我们选择性更新就行了
                horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition);
                horizontalRecyclerviewAdapter.notifyItemChanged(CurrentPosition);
                //viewpager界面切换
                mViewPager.setCurrentItem(position,false);
            }

            @Override
            public void onItemLongClick(View view, int position, List<ImageModel> datas) {
            }
        });

    }

    private void replaceFragment(){
        //创建fragment的工厂类
        FragmentFactory factory=FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        EmotiomComplateFragment f1= (EmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE);
        fragments.add(f1);

        NoHorizontalScrollerVPAdapter adapter = new NoHorizontalScrollerVPAdapter(mActivity.getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(adapter);
    }

    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     * @return true则隐藏表情布局，拦截返回键操作
     *         false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress(){
        return mEmotionKeyboard.interceptBackPress();
    }

    public void hideAllKeyboard(){
        mEmotionKeyboard.hideAllKeyboard();
    }

    private boolean requestVideo(Activity activity){
        if (afterM()){
            final List<String> permissionsList = new ArrayList<>();
            if ((activity.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.CAMERA);
            if ((activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)) permissionsList.add(Manifest.permission.RECORD_AUDIO);
            if (permissionsList.size() != 0){
                activity.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestCamera(Activity activity){
        if (afterM()){
            int hasPermission = activity.checkSelfPermission(Manifest.permission.CAMERA);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.CAMERA},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestAudio(Activity activity){
        if (afterM()){
            int hasPermission = activity.checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean requestStorage(Activity activity){
        if (afterM()){
            int hasPermission = activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (hasPermission != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
                return false;
            }
        }
        return true;
    }

    private boolean afterM(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

}
