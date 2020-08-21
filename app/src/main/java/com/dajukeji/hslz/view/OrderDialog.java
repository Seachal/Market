package com.dajukeji.hslz.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2018/2/5.
 */

public class OrderDialog {
    private Context context;
    private AlertDialog.Builder orderDialog;
    private String title;
    private String message;
    private String okTitle;
    public OrderDialog(Context context,String title,String message,String okTitle) {
        this.context = context;
        orderDialog = new AlertDialog.Builder(context);
        this.title = title;
        this.message = message;
        this.okTitle = okTitle;
        showDialog();
    }

    private void showDialog(){
        if(!title.equals("")){
            orderDialog.setTitle(title);
        }
        if(!message.equals("")){
            orderDialog.setMessage(message);
        }
        orderDialog.setPositiveButton(okTitle,new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onDialogListener.sureClick();
            }
        });

        orderDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public void show(){
        orderDialog.show();
    }

    private onDialogListener onDialogListener;

    public interface onDialogListener{
        void sureClick();
    }

    public void setOnDialogListener(onDialogListener onDialogListener){
        this.onDialogListener = onDialogListener;
    }
}
