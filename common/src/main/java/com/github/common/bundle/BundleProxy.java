package com.github.common.bundle;


import com.github.annotation.bundle.BundleData;
import com.github.common.event.EventCenter;

/**
 * Created by Lyongwang on 2019-11-11 16: 14.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleProxy {
    private BundleData data;

    private IBundle iBundle;

    private BundleState state;

    public BundleProxy(IBundle iBundle, BundleData data) {
        this.data = data;
        this.iBundle = iBundle;
    }

    public boolean isRuning() {
        return state == BundleState.START;
    }

    public BundleData getBundelData() {
        return data;
    }

    public IBundle getiBundle() {
        return iBundle;
    }

    public void onCreate() {
        if (iBundle != null) {
            iBundle.onCreate();
            EventCenter.regist(iBundle);
            state = BundleState.START;
        }
    }

    public void onDestory() {
        if (iBundle != null) {
            iBundle.onDestory();
            EventCenter.unRegist(iBundle);
            state = BundleState.STOP;
        }
    }

    public static class Builder {
        private BundleData info;
        private IBundle    iBundle;

        public Builder setInfo(BundleData info) {
            this.info = info;
            return this;
        }

        public Builder setiBundle(IBundle iBundle) {
            this.iBundle = iBundle;
            return this;
        }

        public BundleProxy build() {
            BundleProxy proxy = new BundleProxy(iBundle, info);
            return proxy;
        }
    }

}
