package com.github.personalcenter;

import android.util.Log;

import com.github.bundleannotation.BundleCode;
import com.github.bundleannotation.BundleInfo;
import com.github.bundleannotation.BundlePropery;
import com.github.common.bundle.IBundle;

/**
 * Created by Lyongwang on 2020-02-14 12: 07.
 * <p>
 * Email: liyongwang@yiche.com
 */
@BundleInfo(p = BundlePropery.DEFAULT, c = BundleCode.PERSONCENTER, v = "1.0", n = "personcenter", d = "用户中心")
public class PersoncenterBundle implements IBundle {
    private static final String TAG = "IBundle";

    @Override
    public void onCreate() {
        Log.i(TAG, "PersoncenterBundle onCreate: ");
    }

    @Override
    public void onDestory() {
        Log.i(TAG, "PersoncenterBundle onDistory: ");
    }
}
