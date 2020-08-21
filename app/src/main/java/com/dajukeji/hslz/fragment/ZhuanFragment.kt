package com.dajukeji.hslz.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.dajukeji.hslz.R
import com.dajukeji.hslz.activity.NormalGoodDetailActivity
import com.dajukeji.hslz.activity.WebActivity
import com.dajukeji.hslz.base.HttpBaseFragment
import com.dajukeji.hslz.jpay.JPay
import com.dajukeji.hslz.network.HttpAddress
import com.dajukeji.hslz.util.MD5Utils
import com.dajukeji.hslz.util.SPUtil
import com.dajukeji.hslz.util.ToastUtils
import org.json.JSONObject
import kotlin.random.Random

class ZhuanFragment :HttpBaseFragment() {

    private val url: String by lazy {
        val token = SPUtil.getPrefString("token", "")
        val nonce = StringBuilder()
        for (i in 0..5) {
            nonce.append(Random.nextInt(10))
        }
        val sign = MD5Utils.dataToMD5(nonce.toString() + token + "helloworld123")

        //跳转
        HttpAddress.mBaseUrl2 + "zmc/shopping_list.html?token=" + token + "&sign=" + sign + "&nonce=" + nonce
    }//解析出URL

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.activity_web_test, null)
        }
        initView(rootView)
        return rootView
    }

    private fun initView(v:View){
        var webView = v.findViewById<WebView>(R.id.webView)
        initWebView(webView,url)
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    private fun initWebView(webView: WebView, url: String) {
        webView.clearCache(true)//清空缓存？
        //1、设置WebView支持Javascript
        var settings = webView.settings//得到webView的配置
        settings.javaScriptEnabled = true
        //开启Dom存储,web前端需要如此
        settings.domStorageEnabled = true
        //四个功能:防止自动跳转,可拦截网页,有页面开始加载与加载结束回调
        webView.webViewClient = MyWebViewClient()
        //可以获取网站的title、logo、具体加载进度
        webView.webChromeClient = MyWebChromeClient()
        //编码方式为UTF-8
        settings.defaultTextEncodingName = "utf-8"
        //设置加载本地资
        settings.allowFileAccess = true

        //将一个类的对象传给webView的window容器供内部的html/js调用 //参1:传递的对象;参2:别称
        webView.addJavascriptInterface(DJJsBaseInterface(activity), "DJJsBaseInterface")
        webView.addJavascriptInterface(DJJsPayInterface(activity, webView), "DJJsPayInterface")
        webView.addJavascriptInterface(DJExchangeGoods(activity),"DJExchangeGoods")

        //加载网络页面 (默认会跳转页面)
        webView.loadUrl(url)//本地页面

    }

    internal class MyWebChromeClient : WebChromeClient() {

        override fun onJsPrompt(
                view: WebView?, url: String?, message: String?, defaultValue: String?,
                result: JsPromptResult?
        ): Boolean {
            return super.onJsPrompt(view, url, message, defaultValue, result)
        }

        //进度
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
        }

        //Logo
        override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
            super.onReceivedIcon(view, icon)
        }

        //title
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
        }
    }

    /**
     * 功能: * 1、可以防止URL通过手机自带的浏览器进行加载(自动跳转) * 2、可以根据逻辑拦截网址 * 3、知道页面开始加载 * 4、知道页面加载完成
     */
    internal inner class MyWebViewClient : WebViewClient() {
        /**当加载内部跳转URL时，立刻调用次方法
         * 此时可以拦截要加载的URL
         * 如果想要拦截,return true、 返回false 则不拦截
         * 注意: webView调用loadUrl不会触发此方法
         */
        override fun shouldOverrideUrlLoading(
                view: WebView?, request: WebResourceRequest?
        ): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        /**
         * 当页面开始加载
         * 网页内部跳链接时低版本不走 shouldOverrideUrlLoading 但一定会走 onPageStarted。所以建议统一在
         * onPageStarted 内监听链接跳转
         * @param favicon 返回的图标(几乎为Null)
         */
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            //暂且使用网站作为返回的方式
            if (HttpAddress.mBaseUrl2+"zmc/shopping_listApp.html" == url) {
//                this@WebActivity.finish()
            }
            showDialogLoading()
            Log.d("duihuan","开始加载h5网页---")
        }

        /**页面加载完成回调*/
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            hideLoading()
            Log.d("duihuan","h5网页加载完成---")
        }
    }

    companion object {
        fun getStartIntent(context: Context, url: String): Intent {
            return Intent(context, WebActivity::class.java).putExtra("URL", url)
        }
    }

    /**
     * 从兑换专区 跳到原生商品详情
     */
    class DJExchangeGoods(val activity: Activity?){
        //把h5页面显示的商品，同原生界面展示出来
        @JavascriptInterface
        fun toExchange(goods_id:String){
//			val intent = Intent(activity,DHZQGoodDetailActivity::class.java)
//			intent.putExtra("goods_id",goods_id.toInt())

            val intent = Intent(activity, NormalGoodDetailActivity::class.java)
            intent.putExtra("goods_id_webActivity",goods_id)

            activity!!.startActivity(intent)
        }


    }

    /**供JS基础交互*/
    class DJJsBaseInterface(val activity: Activity?) {

        /**关闭页面*/
        @JavascriptInterface
        fun backView() {
            activity!!.finish()
        }

        //返回给h5页面一个token
        @JavascriptInterface
        fun getUserToken():String{
            Log.d("duihuan","h5网页拿到了token---")
            return SPUtil.getPrefString("token", "")
        }

        @JavascriptInterface
        fun showToast(toast: String) {
            ToastUtils.getInstance().showToast(activity, toast)
        }
    }

    /** 发起支付操作 */
    class DJJsPayInterface(val activity: Activity?, val webView: WebView) {

        /** 发起支付 */
        @JavascriptInterface
        fun startPay(payInfo: String) {
//			MLog.i(payInfo)
            Log.d("weixinzf",payInfo)
            try {
                val json = JSONObject(payInfo)
//				if (json.get("status").toString() == "0") {
//					val payResult = JSONObject(json.get("content").toString())
//					val payType = payResult.get("paytype").toString()

//				var jsonObject:JSONObject = JSONObject()
//
//				jsonObject.put("appid",json.get("appid"))
//				jsonObject.put("partnerid",json.get("partnerid"))
//				jsonObject.put("prepayid",json.get("prepayid"))
//				jsonObject.put("noncestr",json.get("noncestr"))
//				jsonObject.put("timestamp",json.get("timestamp"))
//				jsonObject.put("sign",json.get("sign"))
//				jsonObject.put("package",json.get("package"))


                val payType = json.get("paytype").toString()
                if (payType.equals("wxpay")) { // 微信支付*/
                    Log.d("weixin: ",json.toString())
                    wxPay(json.toString()) // 调用微信支付

                } else { // 支付宝支付
                    Log.d("zhifubao: ",json.toString())
                    val orderInfo = json.get("alipaymessge").toString()
                    aliPay(orderInfo) //调用支付宝支付
                }
//				} else {
//					onPayResult(NO_ERROR, "发起支付失败")
//				}
            } catch (e: Exception) {
                onPayResult(NO_ERROR, "发起支付失败")
            }
        }

        /** 支付宝支付 */
        private fun aliPay(orderInfo: String) {
            JPay.getIntance(activity).toPay(JPay.PayMode.ALIPAY, orderInfo, object :
                    JPay.JPayListener {
                override fun onPaySuccess() {
                    onPayResult()
                }

                override fun onPayError(error_code: Int, message: String) {
                    onPayResult(error_code, message)
                }

                override fun onPayCancel() {
                    onPayResult(CANCELED)
                }
            })
        }

        /** 微信支付 */
        private fun wxPay(payParameters: String) {
            JPay.getIntance(activity).toPay(JPay.PayMode.WXPAY, payParameters, object :
                    JPay.JPayListener {
                override fun onPaySuccess() {
                    onPayResult()
                }

                override fun onPayError(error_code: Int, message: String) {
                    onPayResult(error_code, message)
                }

                override fun onPayCancel() {
                    onPayResult(CANCELED)
                }
            })
        }



        /** 支付结果 */
        private fun onPayResult(errorCode: Int? = NO_ERROR, errorMsg: String? = "支付成功") {
            var msg = when (errorCode) {
                NO_ERROR -> "支付成功"
                CANCELED -> "支付取消"
                else -> null
            }
        }

        companion object {
            private const val NO_ERROR = 0//支付成功
            private const val CANCELED = 1//支付被取消
        }
    }
}