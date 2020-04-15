package com.github.leeon.view.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.github.annotation.router.IntentParam;
import com.github.annotation.router.RouterActivity;
import com.github.common.base.BaseActivity;
import com.github.leeon.R;

/**
 *
 * @author Lyongwang
 * @date 2020/4/13 17: 21
 * <p>
 * Email: liyongwang@yiche.com
 */
@RouterActivity(path = "app.next")
public class NextActivity extends BaseActivity {

    private static final String TAG = NextActivity.class.getSimpleName();
    @IntentParam
    String apt;

    @IntentParam
    int tab;

    @IntentParam
    boolean isNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_next);

        Log.i(TAG, "onCreate: apt = " + apt + " tab = " + tab + " isNew = " + isNew);
    }
}
