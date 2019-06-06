package com.song.songup.shoppingapp.util;

import android.app.Activity;
import android.widget.Toast;

import com.jpay.JPay;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/5/29 14:23
 * 修改备注：
 */
public class PayMoney {
    public static void weiXinPay(Activity mContext, String payParameters){
        //服务器给
//        String payParameters = "{\n" +
//                "  \"appId\": \"\",\n" +
//                "  \"partnerId\": \"\",\n" +
//                "  \"prepayId\": \"\",\n" +
//                "  \"sign\": \"\",\n" +
//                "  \"nonceStr\" : \"\",\n" +
//                "  \"timeStamp\": \"\"\n" +
//                "}";
        JPay.getIntance(mContext).toPay(JPay.PayMode.WXPAY, payParameters, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>"+error_code+" "+ message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String s, String s1, String s2) {

            }
        });
//或者
//        JPay.getIntance(mContext).toWxPay(appId, partnerId, prepayId, nonceStr, timeStamp, sign, new JPay.JPayListener() {
//            @Override
//            public void onPaySuccess() {
//                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPayError(int error_code, String message) {
//                Toast.makeText(mContext, "支付失败>"+error_code+" "+ message, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPayCancel() {
//                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
//            }
//        });

    }


    public void aliPay(Activity mContext, String orderInfo){
        JPay.getIntance(mContext).toPay(JPay.PayMode.ALIPAY, orderInfo, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>"+error_code+" "+ message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String s, String s1, String s2) {

            }
        });
//或者
//        Alipay.getInstance(mContext).startAliPay(orderInfo, new JPay.JPayListener() {
//            @Override
//            public void onPaySuccess() {
//
//            }
//
//            @Override
//            public void onPayError(int error_code, String message) {
//
//            }
//
//            @Override
//            public void onPayCancel() {
//
//            }
//        });
    }


    public void yinLianPay(Activity mContext,String tn){
//        tn：服务器返回的数据
        JPay.getIntance(mContext).toUUPay("01",tn, new JPay.JPayListener() {
            @Override
            public void onPaySuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayError(int error_code, String message) {
                Toast.makeText(mContext, "支付失败>" + error_code + " " + message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPayCancel() {
                Toast.makeText(mContext, "取消了支付", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUUPay(String dataOrg, String sign, String mode) {
                Toast.makeText(mContext, "支付成功>需要后台查询订单确认>"+dataOrg+" "+mode, Toast.LENGTH_SHORT).show();
            }
        });
    }



}
