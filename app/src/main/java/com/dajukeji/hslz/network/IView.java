package com.dajukeji.hslz.network;

import android.content.Context;

public interface IView {

    /**
     * 从服务端拿到数据之后返回
     * @param object 任何类型的数据
     * @param dataType 数据具体类型
     */
    void setResultData(Object object, String dataType);

    /**
     * http链接失败的情况
     * @param error 错误的原因
     * @param dataType 数据的具体类型
     */
    void showHttpError(String error, String dataType);

    /**
     * 在与服务端交互时，或者耗时操作时,执行起来提示用户
     */
    void showLoading();

    /**
     * 隐藏showLoading的提示信息
     */
    void hideLoading();

    /**
     * @return 获取context实例
     */
    Context getContext();
}
