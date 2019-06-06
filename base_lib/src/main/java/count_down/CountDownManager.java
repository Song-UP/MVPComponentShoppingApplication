package count_down;


import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import utils.Utils;


/**
 * 倒计时管理者  最小单位1秒
 */
public class CountDownManager {
    public static final int WHAT_COUNT_DOWN = 101;
    public static final long MIN_INTERVAL = 1000;
    public static CountDownManager countDownManager;
    public List<OnCountDownListener> list = new ArrayList();

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_COUNT_DOWN:
                    dispatchCoundown();
                    handler.sendEmptyMessageDelayed(WHAT_COUNT_DOWN, MIN_INTERVAL);
                    break;
            }
        }
    };

    /**
     * 分发事件
     */
    private void dispatchCoundown() {
        if (list == null) {
            return;
        }
        Iterator<OnCountDownListener> iterator = list.iterator();
        while (iterator.hasNext()) {
            OnCountDownListener next = iterator.next();
            if (next != null) {
                if (next.isExecute()) {
                    next.onCountDown();
                }
            }
        }
    }

    public static void init() {
    }

    public synchronized static CountDownManager asInstance() {

        if (countDownManager == null) {
            if (!Utils.isMainThread()) {
                throw new RuntimeException("CountDownManager 未初始化时，只能在main线程执行 asInstance方法");
            }
            countDownManager = new CountDownManager();
        }
        return countDownManager;
    }

    /**
     * 开始倒计时
     */
    public void startCountDown() {
        if (handler.hasMessages(WHAT_COUNT_DOWN)) {
            return;
        }
        handler.sendEmptyMessageDelayed(WHAT_COUNT_DOWN, MIN_INTERVAL);
    }

    /**
     * 停止倒计时
     */
    public void stopCountDown() {
        if (handler.hasMessages(WHAT_COUNT_DOWN)) {
            handler.removeMessages(WHAT_COUNT_DOWN);
        }
    }

    /**
     * 注册
     *
     * @param listener
     */
    public CountDownManager register(OnCountDownListener listener) {
        if (!list.contains(listener)) {
            list.add(listener);
        }
        return this;
    }

    /**
     * 注销
     *
     * @param listener
     */
    public CountDownManager unRegister(OnCountDownListener listener) {
        if (list.contains(listener)) {
            list.remove(listener);
        }
        return this;
    }

    /**
     * 释放
     */
    public void release() {
        stopCountDown();
        list.clear();
    }

    interface OnCountDownListener {

        /**
         * 是否更新
         *
         * @return
         */
        boolean isExecute();

        /**
         * 倒计时执行
         */
        void onCountDown();
    }

    /**
     * 接口实现者
     */
    public static abstract class CountDownModel implements OnCountDownListener {
        private long lastExecuteTime = 0;
        private long interval = 0;

        public CountDownModel() {
        }

        public CountDownModel(long interval) {
            this.interval = interval;
        }

        @Override
        public boolean isExecute() {
            long interval = getInterval();
            return System.currentTimeMillis() - lastExecuteTime >= interval;
        }

        public long getInterval() {
            return interval;
        }

        public void setInterval(long interval) {
            this.interval = interval;
        }

        @Override
        public void onCountDown() {
            lastExecuteTime = System.currentTimeMillis();
            onExecute();
        }

        public abstract void onExecute();
    }
}
