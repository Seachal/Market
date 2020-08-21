package com.dajukeji.hslz.domain.advertisement;

import java.util.List;

/**
 * Created by Administrator on 2017/9/19.
 */

public class AdvertisementRoot {
    private List<GoodsTypeList> goodsTypeList ;

    public void setGoodsTypeList(List<GoodsTypeList> goodsTypeList){
        this.goodsTypeList = goodsTypeList;
    }
    public List<GoodsTypeList> getGoodsTypeList(){
        return this.goodsTypeList;
    }
}
