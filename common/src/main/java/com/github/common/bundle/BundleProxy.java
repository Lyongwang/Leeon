package com.github.common.bundle;

/**
 * Created by Lyongwang on 2019-11-11 16: 14.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleProxy {
    private BundleConfig config;

    private IBundle iBundle;

    private BundleState state;

    public BundleProxy(IBundle iBundle, BundleConfig config) {
        this.config = config;
        this.iBundle = iBundle;
    }

    public boolean isRuning() {
        return state == BundleState.START;
    }

    public BundleConfig getConfig() {
        return config;
    }

    public IBundle getiBundle() {
        return iBundle;
    }

    public void onCreate() {
        if (iBundle != null){
            iBundle.onCreate();
            state = BundleState.START;
        }
    }

    public void onDestory(){
        if (iBundle != null){
            iBundle.onDestory();
            state = BundleState.STOP;
        }
    }

    public static class Builder {
        private BundleInfo info;
        private IBundle    iBundle;

        public Builder setInfo(BundleInfo info) {
            this.info = info;
            return this;
        }

        public Builder setiBundle(IBundle iBundle) {
            this.iBundle = iBundle;
            return this;
        }

        public BundleProxy build() {
            BundleProxy proxy = new BundleProxy(iBundle,
                    new BundleConfig.Builder()
                            .name(info.n())
                            .code(info.c())
                            .propery(info.p())
                            .desc(info.d())
                            .version(info.v())
                            .build());
            return proxy;
        }
    }

}
