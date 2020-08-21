package com.dajukeji.hslz.event;

import com.dajukeji.hslz.domain.javaBean.UserAddressBean;

/**
 * Created by ${wangjiasheng} on 2017/12/14 0014.
 * 选择收获地址event
 */

public class SelectAddressEvent {
    public final UserAddressBean.ContentBean userAddressBean;

    public SelectAddressEvent(UserAddressBean.ContentBean userAddressBean) {
        this.userAddressBean = userAddressBean;
    }
}
