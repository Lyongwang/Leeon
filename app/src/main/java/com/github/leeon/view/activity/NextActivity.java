package com.github.leeon.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_next);
    }
}
