package umeng;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.lib.R;


public class BaseDialog02 extends Dialog {
    private boolean iscancelable;
    //控制点击dialog外部是否dismiss
    private boolean isBackCancelable;
    //控制返回键是否dismiss
    private int view;
    private Context context;
    public TextView tvFanYong;

    public BaseDialog02(Context context, int view, boolean isCancelable, boolean isBackCancelable) {
        super(context, R.style.MyDialog);//在这里面设置样式和动画
        this.context = context;
        this.view = view;
        this.iscancelable = isCancelable;
        this.isBackCancelable = isBackCancelable;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        setCancelable(iscancelable);
        setCanceledOnTouchOutside(isBackCancelable);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
//        View view  = findViewById(R.id.tv_close);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                VipManageDialog.this.dismiss();
//            }
//        });
//
//        tvFanYong= findViewById(R.id.tv_fanyong);
    }

}
