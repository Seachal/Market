package com.dajukeji.hslz.network;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class DataType {

//    public static enum  contentType{
//        getHealthArticleList
//    }

    public static enum myPresenterType {
        getHealthArticleList, getGoodsList, getGoodDetail, getFiveWholesaleOrderList,
        getAddressList, orderPay, createOrder
    }

    //地址接口
    public static enum address {
        addAddress, getDefaultAddress, getAddressList, setDefaultAddress, deleteAddress, updateAddress
    }

    //订单支付接口
    public static enum order {
        orderPay, createWholeBuyOrder, createOrder,createOrderByGc ,createComparePriceOrder ,createIntegralOrder,payForOrder,createNPNOrder,createCreativeOrder,getFeeByGoodsId,payForOder
    }

    //产品接口
    public static enum good {
        getGoodList, getGoodClassList, getGoodDetail, getFiveWholesaleOrderList, GetGoodsClassList, addToCart,goodsEvaluateList,getGoodLis,getCoupon
    }

    //文章接口
    public static enum article {
        getHealthArticleList
    }

    //猫券，京券
    public static enum coupon {
        getTypeList, getCatList , getJDList , getJDGoodLink ,getCatLink
    }

    public static enum collect {
        addCollect, getCollectList, deleteCollect
    }

    //品牌大促
    public static enum brand {
        bigBrandList, storeClassList, brandStoreList, storeBannerList, storeGoodsClassList, storeGoodsList
        , storeIndex, createBrandOrder
    }

    // 免单
    public static enum freeOrder{
        todayFree , meFree ,getGoodDetail ,getDefaultAddress , shareFreeOrder ,createFree ,createInvitee,deleteFreeOrder
    }

    // 商城
    public static enum mall{
        ninepointnine,creative,subsidy,integral,compareprice,index,getProduct
    }

    // 用户登陆
    public static enum userLogin{
        sendBand , sendCode , checkCode ,bandPhone,loginphone
    }

    // 收藏
    public static enum favorite{
        selectFavoriteGoods , selectFavoriteStore ,delete
    }

    // 补贴
    public static enum subsidy{
        mySubsidy,createCutPriceOrder ,shareCutPriceOrder ,payForCutPriceOrder
    }
    //检查更新
    public static enum update{
        checkVersion
    }

    public static enum zone_type{
       free,compareprice,integral,ninepointnine,creative,cutprice,brand
    }

    //购物车
    public static enum cart{
        addToCart,removeGoodsCart, look_goods_cart,goodsCountAdjust,createComparePriceOrder,cancelOrder
        ,createOrderByGc, collectGood, getFeeByGCID
    }

    //我的订单
    public static enum myOrder{
        orderList , orderDetail , orderEvalute ,orderConfirmReceive ,release ,delete , logistics,cancelOrder,getFeeByOrderId,orderStatusCount
    }

    //我的售后
    public static enum myRefund{
        orderList , waitList ,to_apply,refundInit ,saveRefund ,returnDetail,returnWait,returnLogistics,saveWaybill,returnDelete
    }
    //我的中奖
    public static enum award{
        awardGoods,getDefaultAddress,createAwardOrder
    }
    // 意见反馈
    public static enum feedback{
        feedback
    }

    public static enum message{
        systemMessage,notWriteNo
    }
}
