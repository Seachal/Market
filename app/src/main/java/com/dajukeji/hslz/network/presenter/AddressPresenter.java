package com.dajukeji.hslz.network.presenter;

import com.dajukeji.hslz.domain.javaBean.JsonModel;
import com.dajukeji.hslz.domain.javaBean.UserAddressBean;
import com.dajukeji.hslz.domain.javaBean.UserAddressListBean;
import com.dajukeji.hslz.network.HttpAddress;
import com.dajukeji.hslz.network.IView;
import com.dajukeji.hslz.network.OkGoEngine;
import com.dajukeji.hslz.network.RequestCallBack;
import com.dajukeji.hslz.util.SPUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.model.HttpParams;

/**
 * Created by ${wangjiasheng} on 2017/12/15 0015.
 */

public class AddressPresenter {

    private OkGoEngine okGoEngine;
    private IView iView;
    private Gson gson;

    public AddressPresenter(IView iView) {
        this.iView = iView;
        gson = new Gson();
        okGoEngine = OkGoEngine.getInstance();
    }

    /**
     * 新增地址
     *
     * @param area_info 详细信息
     * @param mobile    手机号码
     * @param trueName  真实姓名
     * @param area_id   所在区的地址编号
     */
    public void addAddress(Object tag, String token, String area_info, String mobile, String trueName, String area_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.addAddress;
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("area_info", area_info);
        map.put("mobile", mobile);
        map.put("trueName", trueName);
        map.put("area_id", area_id);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                JsonModel<JsonModel> obj = gson.fromJson(json, JsonModel.class);
                iView.setResultData(obj, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 查询地址信息,通过id
     */
    public void searchAddressInfo(Object tag, String token, long addressId, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getAddressDetail;
        HttpParams map = new HttpParams();
        map.put("token", token);
        map.put("addressId", addressId);
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    UserAddressBean userAddressBean = gson.fromJson(json, UserAddressBean.class);
                    iView.setResultData(userAddressBean, contentType);
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 获取地址列表
     *
     * @param contentType
     */
    public void getAddressList(Object tag, String token, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getAddressList;
        HttpParams map = new HttpParams();
        map.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                UserAddressListBean userAddressListBean = gson.fromJson(json, UserAddressListBean.class);
                iView.setResultData(userAddressListBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 获取默认地址
     *
     * @param token
     * @param contentType
     */
    public void getDefaultAddress(Object tag, String token, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.getDefaultAddress;
        HttpParams map = new HttpParams();
        map.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                try {
                    UserAddressBean userAddressBean = gson.fromJson(json, UserAddressBean.class);
                    iView.setResultData(userAddressBean, contentType);
                } catch (JsonSyntaxException e) {
                    onfailed("");
                }
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 设置默认地址
     *
     * @param addressId   地址id
     * @param contentType
     */
    public void setDefaultAddress(Object tag, long addressId, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.setDefaultAddress;
        HttpParams map = new HttpParams();
        map.put("addressId", addressId + "");
        map.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                JsonModel<JsonModel> obj = gson.fromJson(json, JsonModel.class);
                iView.setResultData(obj, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 删除用户地址
     *
     * @param addressId
     * @param contentType
     */
    public void deleteAddress(Object tag, Long addressId, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.deleteAddress;
        HttpParams map = new HttpParams();
        map.put("addressId", addressId + "");
        map.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                UserAddressListBean userAddressListBean = gson.fromJson(json, UserAddressListBean.class);
                iView.setResultData(userAddressListBean, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

    /**
     * 更新用户地址
     *
     * @param tag
     * @param id
     * @param area_info
     * @param mobile
     * @param trueName
     * @param area_id
     * @param contentType
     */
    public void updateAddress(Object tag, long id, String area_info, String mobile, String trueName, String area_id, final String contentType) {
        iView.showLoading();
        String url = HttpAddress.mBaseUrl2 + HttpAddress.updateAddress;
        HttpParams map = new HttpParams();
        map.put("id", id);
        map.put("area_info", area_info);
        map.put("mobile", mobile);
        map.put("trueName", trueName);
        map.put("area_id", area_id);
        map.put("token", SPUtil.getPrefString("token", ""));
        okGoEngine.postMap(tag, url, map, new RequestCallBack() {
            @Override
            public void onsuccess(String json) {
                JsonModel<JsonModel> obj = gson.fromJson(json, JsonModel.class);
                iView.setResultData(obj, contentType);
            }

            @Override
            public void onfailed(String exception) {
                iView.showHttpError(exception, contentType);
            }
        });
    }

}
