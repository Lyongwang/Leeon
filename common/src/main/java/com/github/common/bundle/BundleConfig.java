package com.github.common.bundle;

import com.github.bundleannotation.BundleCode;
import com.github.bundleannotation.BundlePropery;

/**
 * Created by Lyongwang on 2020-02-14 14: 47.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleConfig {
    /**
     * 组件唯一码
     */
    private BundleCode code;
    /**
     * 组件名称
     */
    private String     name;

    /**
     * 组件版本
     */
    private String        version;
    /**
     * 组件优先级
     */
    private BundlePropery propery;

    /**
     * 组件描述
     */
    private String desc;

    public BundleConfig(BundleCode code, String name, String version, BundlePropery propery, String desc) {
        this.code = code;
        this.name = name;
        this.version = version;
        this.propery = propery;
        this.desc = desc;
    }

    public BundleCode getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public BundlePropery getPropery() {
        return propery;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return "BundleConfig{" +
                "code=" + code.intValue() +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", propery=" + propery.intValue() +
                ", desc='" + desc + '\'' +
                '}';
    }

    public static class Builder{
        private BundleCode code;
        private String name;
        private String verison;
        private BundlePropery propery;
        private String desc;

        public Builder code(BundleCode code){
            this.code = code;
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder version(String version){
            this.verison = version;
            return this;
        }
        public Builder desc(String desc){
            this.desc = desc;
            return this;
        }

        public Builder propery(BundlePropery propery){
            this.propery = propery;
            return this;
        }

        public BundleConfig build(){
            return new BundleConfig(code, name, verison, propery, desc);
        }
    }
}
