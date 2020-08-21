package com.dajukeji.hslz.event;

import com.dajukeji.hslz.domain.javaBean.UserAddressBean;

/**
 * Created by ${wangjiasheng} on 2017/12/14 0014.
 */

public class ChangeCityEvent {
    public final UserAddressBean userAddressBean;

    public ChangeCityEvent(UserAddressBean userAddressBean) {
        this.userAddressBean = userAddressBean;
    }
}
