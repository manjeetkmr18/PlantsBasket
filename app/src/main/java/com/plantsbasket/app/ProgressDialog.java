package com.plantsbasket.app;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

public class ProgressDialog {
    private Dialog dialog = null;
    private Context context;


    public ProgressDialog(Context context) {
        this.context = context;
        initDialog();
    }

    private void initDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_progress_bar);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setCancelable(false);
    }

    public void show(){
        if(dialog != null) {
            dialog.show();
        }else{
            initDialog();
            dialog.show();
        }
    }

    public void dissmiss(){
        if(dialog != null) {
            dialog.dismiss();
        }
    }
}
