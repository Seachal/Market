package com.dajukeji.hslz.domain;

/**
 * Created by Administrator on 2017/11/28 0028.
 */

public class Constants {
    public static final boolean DEBUG = true;

    public static final String SUCCESS = "success";

    public static final String HTTPERROR = "httperror";

    public static final String STREAM = "stream";

    public static final String OBJECT = "object";

    public static final int RESPONSE = 1;

    public static final int NORESPONSE = 2;

    public static final String FIRSTRUN = "firstrun";

    public static final String contentType = "contentType";

    public static final int fromGroupBuyGoodDetail = 0;

    public static final String alipay = "alipay";

    public static final String wxpay = "wxpay";
    public static final String cash = "cash";


    public static final String sureBuy = "sureBuy"; //普通购买流程

    public static final String APK_DOWNLOAD_URL = "url";

    public static final String collectGoods = "0";  //收藏产品

    public static final String collectStore = "1";  //收藏店铺

    public static final String payback = "payBack"; //支付返回的状态

    public static final String good_price = "good_price";   //产品价格

    public static final String store_id = "store_id";   //门店id

    public static final String gc_id = "gc_id"; //类目id

    public static final String is_brand_good = "is_brand_good"; //是否品牌大促产品详情

    public static final String goods_id = "goods_id";   //产品编号

    public static final String brand_id = "brand_id";   //品牌id

    public static final String brand_name = "brand_name";   //品牌名称

    //活动区产品，free：免单产品，integral：积分产品，award：抽奖产品(都不会出现在产品列表)


    // ninepointnine：9块9产品，creative：创意新品产品，brand：品牌大促产品
    //都跳到普通产品详情
    public static final String ninepointnine = "ninepointnine";

    public static final String creative = "creative";

    public static final String salebyauction = "salebyauction";
    //compareprice：全网比价产品，跳到comparepricegooddetailactivity
    public static final String compareprice = "compareprice";
    //cutprice：砍价产品，跳到subsidygooddetailactivity
    public static final String cutprice = "cutprice";
}
