package com.github.annotation.bundle;

import java.io.File;

/**
 * Created by Lyongwang on 2020/4/1 14: 56.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BundleData {
    private BundlePropery propery;
    private BundleCode code;
    private String name;
    private String desc;
    private String version;
    private String bundleClassName;

    public BundleData(BundlePropery propery, BundleCode code, String name, String desc, String version, String bundleClassName) {
        this.propery = propery;
        this.code = code;
        this.name = name;
        this.desc = desc;
        this.version = version;
        this.bundleClassName = bundleClassName;
    }

    public BundlePropery getPropery() {
        return propery;
    }

    public BundleCode getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getVersion() {
        return version;
    }

    public String getBundleClassName() {
        return bundleClassName;
    }
}
