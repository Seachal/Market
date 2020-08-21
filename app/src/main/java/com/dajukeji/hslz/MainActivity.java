package com.dajukeji.hslz;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dajukeji.hslz.activity.RedPackedActivity;
import com.dajukeji.hslz.activity.mall.FreeOrderActivity;
import com.dajukeji.hslz.activity.userlogin.MobailePhoneLoginActivity;
import com.dajukeji.hslz.activity.userlogin.WeChatLoginActivity;
import com.dajukeji.hslz.event.ActionBarEvent;
import com.dajukeji.hslz.event.EquipmentEvent;
import com.dajukeji.hslz.event.HavaFreeOrderEvent;
import com.dajukeji.hslz.event.JumpIndexEvent;
import com.dajukeji.hslz.event.RedStartEvent;
import com.dajukeji.hslz.event.WXLoginEvent;
import com.dajukeji.hslz.fragment.ColletFragment;
import com.dajukeji.hslz.fragment.DiscovertwoFragment;
import com.dajukeji.hslz.fragment.HomeClassFragment;
import com.dajukeji.hslz.fragment.ShopCartFragment;
import com.dajukeji.hslz.fragment.ZhuanFragment;
import com.dajukeji.hslz.fragment.mine.MeFragment;
import com.dajukeji.hslz.global.AliSdkApplication;
import com.dajukeji.hslz.im.activity.ChatActivity;
import com.dajukeji.hslz.im.model.UserInfo;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.util.DownloadUtils;
import com.dajukeji.hslz.util.SPUtil;
import com.dajukeji.hslz.util.ToastUtils;
import com.dajukeji.hslz.util.update.UpdateChecker;
import com.dajukeji.hslz.view.NoScrollViewPager;
import com.dajukeji.hslz.view.OrderDialog;
import com.dajukeji.hslz.view.dialog.LoadingDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversationType;
import com.tencent.TIMLogLevel;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.xiaomi.mipush.sdk.MiPushClient;
import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.Screen;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import pub.devrel.easypermissions.EasyPermissions;
/**
 * 作为本软件的主界面
 * 下方有一个导航栏，上部是一个不发生侧滑的ViewPager
 * 下方导航栏分为四个：首页、分类、购物车、我的
 *
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, TIMCallBack {
    private ArrayList<Fragment> fragments = new ArrayList<>();//一个持有者，用来存放被实例化的fragment
    private NoScrollViewPager content;//代表本界面中的主ViewPager
    private LinearLayout home, qianbao, zhuanmaichang, shopping_cart, me;//首页 猫券 京券 购物车 我的（这里是下方导航栏的图标布局）
    public int currentPage = 0;//当前页
    public static boolean isForeground = false;//作为是否前台显示的一个标记，true为正在前台交互
    DownloadUtils downloadUtils;//下载东西用的管理器
    private boolean forceUpdate = false;
    private boolean isCheckLogin = false;
    public boolean isInitRedPackedFloatWindow = false;//供IndexFragment使用，是否以及初始化了红包雨功能
    private long startTime,endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);//检查是否注册过线程广播
        }
        Log.d("tokenaaaa",SPUtil.getPrefString("token", ""));
//        initFloatWindow();//暂时不要红包雨

        init();//登录了腾讯的聊天组件，但是登录用的账号密码还不知道在哪输入（王星2-28）
        final Intent intent = getIntent();
        initView();//完成了页面的初始化，以及单击事件监听

        //这里是用来提示用户登录成功的，应该是从某个页面登录之后跳转至首页
        if (intent != null) {
            if (intent.getStringExtra("flag") != null && intent.getStringExtra("flag").equals("2")) { // 登陆界面跳转
                currentPage = 4;
                switchPage();
                Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            }
        }

        //只要本地存有登录的token并且当前处于非登录状态，就开始检查token是否过期
        if (!isCheckLogin && !SPUtil.getPrefString("token", "").equals("")) {
            String url = HttpAddress.mBaseUrl2 + HttpAddress.checkTokenStatus;
            OkHttpUtils.post()//
                    .url(url)
                    .addParams("token", SPUtil.getPrefString("token", ""))//添加请求参数，将token传给服务器
                    .build()//
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject json = new JSONObject(response);
                                //返回值如果为“1”表示token过期，提示用户重新登录
                                if (json.get("status").toString().equals("1")) {
                                    isCheckLogin = true;
                                    ToastUtils.getInstance().showToast
                                            (AliSdkApplication.getContext(), "由于您长时间未登录，请重新登陆");
                                    Intent intent = new Intent
                                            (AliSdkApplication.getContext(), WeChatLoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    AliSdkApplication.getContext().startActivity(intent);
                                }
                            } catch (JSONException e) {
                                ToastUtils.getInstance().showToast
                                        (AliSdkApplication.getContext(), "服务器异常");
                            }
                        }
                    });
        }

        //如果本地存有token，那么就用这个token向服务器提交设备信息
        if (!SPUtil.getPrefString("token", "").equals("")) {
            String url = HttpAddress.mBaseUrl2 + HttpAddress.getEquipmen;
            OkHttpUtils.post()//
                    .url(url)
                    .addParams("token", SPUtil.getPrefString("token", ""))//提交token
                    .addParams("equipment_info", Build.MODEL)//向服务器提交设备信息
                    .build()//
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                        }
                    });
        }

//        自动更新功能，暂时屏蔽此功能
//        String[] downloadPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
//        if (EasyPermissions.hasPermissions(this, downloadPermissions)) {
//            UpdateChecker.checkForDialog(this);
//        } else {
//            EasyPermissions.requestPermissions(this, "需要读写权限来自动更新", 104, downloadPermissions);
//        }
    }

    /**
     * 获得了权限许可
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
//        downloadUtils.downloadAPK();
        if (requestCode == 104)
            UpdateChecker.checkForDialog(this);
    }

    /**
     * 没有拿到权限
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.getInstance().showToast(MainActivity.this, getResources().getString(R.string.update_failed));
    }

    /**
     * 初始化红包雨
     */
    @SuppressLint({"NewApi", "ClickableViewAccessibility"})
    public void initFloatWindow() {
//        if(!Settings.canDrawOverlays(this)){//适用于6.0以上系统,6.0以下系统崩溃。。。
//            getOverlayPermission();//获取悬浮窗权限
//            return;
//        }
        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setImageResource(R.mipmap.win_bar);

        FloatWindow
                .with(getApplicationContext())
                .setView(imageView)
                .setWidth(Screen.width, 0.2f)
                .setHeight(Screen.width, 0.2f)
                .setX(Screen.width, 0.8f)
                .setY(Screen.height, 0.4f)
                .setMoveType(MoveType.slide)
                .setMoveStyle(500, new BounceInterpolator())
                .setFilter(true, MainActivity.class)
                .setDesktopShow(true)
                .build();
        if(currentPage == 0){
            FloatWindow.get().show();
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SPUtil.getPrefString("token", "").equals("")) {
                    startActivity(new Intent(MainActivity.this, RedPackedActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, WeChatLoginActivity.class));
                }

            }
        });

    }

    public void destroyFloatWindow(){
        if (FloatWindow.get() != null){
            FloatWindow.destroy();
        }
    }



    //请求悬浮窗权限
    private void getOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 123);
    }

    /**
     * 权限请求返回之后
     */
    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123){
            if (!Settings.canDrawOverlays(this)){
                return;
            }else {
                initFloatWindow();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        isForeground = true;//记录当前activity的状态，是否在与用户交互
        super.onResume();
        RefreshEvent.getInstance();//不知道具体是怎么样地刷新方式
        //只有 首页 才需要红包悬浮窗
        if (currentPage == 0 && FloatWindow.get()!=null){
            FloatWindow.get().show();
        }else if (currentPage != 0 && FloatWindow.get()!= null){
            FloatWindow.get().hide();
        }
    }


    @Override
    protected void onPause() {
        isForeground = false;//记录当前activity的状态，是否在与用户交互
        super.onPause();
        if (FloatWindow.get()!=null){
            FloatWindow.get().hide();
        }

    }

    /**
     * 页面初始化 以及单击监听
     */
    private void initView() {
        home = (LinearLayout) findViewById(R.id.home);
        qianbao = (LinearLayout) findViewById(R.id.qianbao);//钱包页面
        zhuanmaichang = (LinearLayout) findViewById(R.id.zhuanmaichang);//转卖场
        shopping_cart = (LinearLayout) findViewById(R.id.shopping_cart);
        me = (LinearLayout) findViewById(R.id.me);
        /*fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);*/
        content = (NoScrollViewPager) findViewById(R.id.content_vp);
//        fragments.add(new IndexPageFragment());
        fragments.add(new DiscovertwoFragment());//首页页面的fragment
//        fragments.add(new DiscoverFragment());

//        fragments.add(new CatGoodFragment());
        fragments.add(new ColletFragment());//钱包的fragment
//        fragments.add(new JDGoodFragment());
        fragments.add(new ZhuanFragment());//转卖场的fragment

        fragments.add(new ShopCartFragment());//购物车的fragment
        fragments.add(new MeFragment());//“我的”页面的fragment
        fragments.add(new HomeClassFragment());//商品分类 页面的fragment（这个名字起得有点战术误导啊） 这是一个kotlin写的
        //设置适配器
        content.setAdapter(adapter);
        content.setOffscreenPageLimit(adapter.getCount());//把adapter里的fragment一次性全部创建出来
        //
        home.setOnClickListener(this);
        qianbao.setOnClickListener(this);
        zhuanmaichang.setOnClickListener(this);
        shopping_cart.setOnClickListener(this);
        me.setOnClickListener(this);
//        findViewById(R.id.good_class).setOnClickListener(this);//隐藏掉分类的页面
    }

    /**
     * viewpager适配器
     */
    private PagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public int getCount() {
            return fragments.size();
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    };

    // 重写Activity中onKeyDown（）方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 当keyCode等于退出事件值时
            ToQuitTheApp();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private boolean isExit = false;

    //封装ToQuitTheApp方法
    private void ToQuitTheApp() {
        if (isExit) {
            // ACTION_MAIN with category CATEGORY_HOME 启动主屏幕
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            SPUtil.removeKey("shareId");
            System.exit(0);// 使虚拟机停止运行并退出程序
        } else {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            mHandlers.sendEmptyMessageDelayed(0, 3000);// 3秒后发送消息
        }
    }

    //试试看能不能拿到红包的通知
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRedStart(RedStartEvent event){
        if (event.Red_tz.equals("hongbaolaile")){
            if (FloatWindow.get()!=null){
                FloatWindow.get().show();
            }else {
                initFloatWindow();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ActionBarEvent event) {
        if (event.actionBar.equals("action")) {
            switchPage();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HavaFreeOrderEvent event) {
        if (event.message.equals("have")) {
            OrderDialog orderDialog = new OrderDialog(getApplicationContext(), "恭喜您成为会员", "您有一个免费的产品待领取", "去看看");
            orderDialog.show();
            orderDialog.setOnDialogListener(new OrderDialog.onDialogListener() {
                @Override
                public void sureClick() {
                    startActivity(new Intent(getApplicationContext(), FreeOrderActivity.class).putExtra("type", "me"));
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EquipmentEvent event) {
        if (event.equipment.equals("login")) {
            String url = HttpAddress.mBaseUrl2 + HttpAddress.getEquipmen;
            OkHttpUtils.post()//
                    .url(url)
                    .addParams("token", SPUtil.getPrefString("token", ""))
                    .addParams("equipment_info", Build.MODEL)
                    .build()//
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(String response, int id) {
                        }
                    });
        }
    }

    //创建Handler对象，用来处理消息
    @SuppressLint("HandlerLeak")
    Handler mHandlers = new Handler() {

        @Override
        public void handleMessage(Message msg) {//处理消息
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public void toCatCoupon() {
        onClick(qianbao);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                if (currentPage == 0) {
                    //如果当前页是首页，我还点击了首页按钮，那么就发送一个线程广播，目前看到是被DaiActivity中的接收到了
                    EventBus.getDefault().post(new JumpIndexEvent("jumpIndex"));
                }
                currentPage = 0;
                break;
            case R.id.qianbao:
                currentPage = 1;
                break;
            case R.id.zhuanmaichang:
                currentPage = 2;
                break;
            case R.id.shopping_cart:
                currentPage = 3;
                break;
            case R.id.me:
                currentPage = 4;
                break;
            case R.id.good_class:
                currentPage = 5;
                break;
        }
        //产品经理说，只有首页才需要红包雨浮窗,否则隐藏起来
        if (currentPage == 0){
            if (FloatWindow.get()!=null){
                FloatWindow.get().show();
            }
        }else {
            if (FloatWindow.get()!=null){
                FloatWindow.get().hide();
            }
        }
        switchPage();
    }

    //每一次单击事件都在重新设置界面
    private void switchPage() {
        ImageView iv_home = (ImageView) findViewById(R.id.iv_home);
        TextView tv_home = (TextView) findViewById(R.id.tv_home);
        ImageView iv_qianbao = (ImageView) findViewById(R.id.iv_qianbao);
        TextView tv_qianbao = (TextView)findViewById(R.id.tv_qianbao);
        ImageView iv_zhuanmaichang = (ImageView) findViewById(R.id.iv_zhuanmaichang);
        TextView tv_zhuanmaichang = (TextView) findViewById(R.id.tv_zhuanmaichang);
        ImageView iv_goods_class = (ImageView) findViewById(R.id.iv_good_class);
        TextView tv_goods_class = (TextView) findViewById(R.id.tv_good_class);
        ImageView iv_shopping_cart = (ImageView) findViewById(R.id.iv_shopping_cart);
        TextView tv_shopping_cart = (TextView) findViewById(R.id.tv_shopping_cart);
        ImageView iv_me = (ImageView) findViewById(R.id.iv_me);
        TextView tv_me = (TextView) findViewById(R.id.tv_me);

        int unselectedColor = Color.rgb(191, 191, 191);
        int selectedColor = Color.rgb(252, 61, 66);
        iv_home.setImageResource(R.drawable.home);
        tv_home.setTextColor(unselectedColor);
        iv_qianbao.setImageResource(R.drawable.qianbao);
        tv_qianbao.setTextColor(unselectedColor);
//        iv_jd_coupon.setImageResource(R.drawable.zhuanmaichang);
        iv_zhuanmaichang.setImageResource(R.drawable.zhuanmaichang);
        tv_zhuanmaichang.setTextColor(unselectedColor);
        iv_goods_class.setImageResource(R.drawable.good_class);
        tv_goods_class.setTextColor(unselectedColor);
        iv_shopping_cart.setImageResource(R.drawable.shopping_cart);
        tv_shopping_cart.setTextColor(unselectedColor);
        iv_me.setImageResource(R.drawable.me);
        tv_me.setTextColor(unselectedColor);

        //首页单击事件处理（针对界面的）
        if (currentPage == 0) {
            content.setCurrentItem(0, false);//如果ViewPager中页面太多就会出问题，不过这里的ViewPager设置了不允许侧滑，应该没啥事
            iv_home.setImageResource(R.drawable.home_select);
            tv_home.setTextColor(selectedColor);
        } else if (currentPage == 1) {
            //钱包，在这里引导用户使用手机号或微信号登录
            if (!SPUtil.getPrefString("token", "").equals("")) {
                if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
                    content.setCurrentItem(1, false);
                    iv_qianbao.setImageResource(R.drawable.qianbao_select);
                    tv_qianbao.setTextColor(selectedColor);
                } else {
                    content.setCurrentItem(0, false);
                    iv_home.setImageResource(R.drawable.home_select);
                    startActivity(new Intent(MainActivity.this, MobailePhoneLoginActivity.class));
                }

            } else {
                content.setCurrentItem(0, false);
                iv_home.setImageResource(R.drawable.home_select);
                startActivity(new Intent(MainActivity.this, WeChatLoginActivity.class));
            }

        } else if (currentPage == 2) {
            //转卖场（这里好像和currentPage为5的情况重复了）
            content.setCurrentItem(2, false);
            iv_zhuanmaichang.setImageResource(R.drawable.zhuanmaichang_select);
            tv_zhuanmaichang.setTextColor(selectedColor);

//            if (!SPUtil.getPrefString("token", "").equals("")) {
//                if (!SPUtil.getPrefString("phoneNumber", "").equals("")) {
//                    content.setCurrentItem(2, false);
//                    iv_jd_coupon.setImageResource(R.drawable.jd_coupon_select);
//                } else {
//                    content.setCurrentItem(0, false);
//                    iv_home.setImageResource(R.drawable.home_select);
//                    startActivity(new Intent(MainActivity.this, MobailePhoneLoginActivity.class));
//                }
//
//            } else {
//                content.setCurrentItem(0, false);
//                iv_home.setImageResource(R.drawable.home_select);
//                startActivity(new Intent(MainActivity.this, WeChatLoginActivity.class));
//            }
        } else if (currentPage == 3) {
            //购物车
            content.setCurrentItem(3, false);
            iv_shopping_cart.setImageResource(R.drawable.shopping_cart_select);
            tv_shopping_cart.setTextColor(selectedColor);
        } else if (currentPage == 4) {
            //“我的”页面
            content.setCurrentItem(4, false);
            iv_me.setImageResource(R.drawable.me_select);
            tv_me.setTextColor(selectedColor);
        } else if (currentPage == 5) {
            //分类
            content.setCurrentItem(5, false);
            iv_goods_class.setImageResource(R.drawable.good_class_select);
            tv_goods_class.setTextColor(selectedColor);
        }
    }


    //权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }



    private int GOOGLE_PLAY_RESULT_CODE = 200;
    private final int REQUEST_PHONE_PERMISSIONS = 0;
    //以下三个变量还不清楚是什么（王星2-28）
    private String id = SPUtil.getPrefString("chatId", "");
    private String userSig = SPUtil.getPrefString("chatSig", "");
    private String friendId = "syb_32817";
    //主要是腾讯IM聊天库的运用
    private void init() {
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());//获取日志等级，如果出现问题，就会返回1~5的数字
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), loglvl);//设置日志等级
        //初始化TLS
        TlsBusiness.init(getApplicationContext());
        //设置刷新监听
        RefreshEvent.getInstance();

        //先从腾讯的服务器上面获取最近是否有登录过IM如果有就从上面获取登录的ID和SIG
        String id = TLSService.getInstance().getLastUserIdentifier();
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));

        //这是判断是否有登录过IM的，如果有
        if (isUserLogin()) {

        } else {
            //如果没有，你自己给他设置用户的id和sig
            if (!this.id.equals("") && !this.userSig.equals("")) {
                setIMUserInfo();
                loginIM();
            }
        }
    }

    /**
     * 登录IM
     */
    private void loginIM() {
        //登录之前要初始化群和好友关系链缓存
        FriendshipEvent.getInstance().init();//好友事件
        GroupEvent.getInstance().init();//组群事件
        LoginBusiness.loginIm(UserInfo.getInstance().getId(), UserInfo.getInstance().getUserSig(), this);
    }

    /**
     * 设置IM登录 id 与 userSig
     */
    private void setIMUserInfo() {
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(userSig);
    }

    /**
     * 开始聊天
     */
    private void startToChat() {
        ChatActivity.navToChat(this, friendId, TIMConversationType.C2C);
    }


    private boolean isUserLogin() {
        return UserInfo.getInstance().getId() != null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()));
    }

    /**
     * 登录 IM 失败回调
     *
     * @param i
     * @param s
     */
    @Override
    public void onError(int i, String s) {
        switch (i) {
//            case 6208:
//                //离线状态下被其他终端踢下线,需要重新登录
////                NotifyDialog dialog = new NotifyDialog();
////                dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialog, int which) {
////                        navToHome();
////                    }
////                });
//                Toast.makeText(this, getString(R.string.kick_logout), Toast.LENGTH_SHORT).show();
//                break;
//            case 6200:
//                Toast.makeText(this, getString(R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
////                navToLogin();
//                break;
//            default:
//                Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
////                navToLogin();
//                break;
        }
    }

    /**
     * 登录 IM 成功回调
     */
    @Override
    public void onSuccess() {

//        Toast.makeText(getApplicationContext(), getString(R.string.login_succ), Toast.LENGTH_SHORT).show();
    }

    /**
     * 清除所有通知栏通知
     */
    private void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
        MiPushClient.clearNotification(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoadingDialog.hideLoadingDialog();
        EventBus.getDefault().unregister(this);
        FloatWindow.destroy();
    }

    /**
     * 也是一个线程广播，微信登录事件
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WXLoginEvent event) {
        this.id = event.chatId;
        this.userSig = event.chatSig;
        init();
    }
}
