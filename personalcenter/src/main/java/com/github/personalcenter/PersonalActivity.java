package com.github.personalcenter;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.github.annotation.router.RouterActivity;
import com.github.common.base.BaseActivity;

/**
 *
 * @author Lyongwang
 * @date 2020/4/13 18: 53
 * <p>
 * Email: liyongwang@yiche.com
 */
@RouterActivity(paths = {"personalOld", "personalNew"}, path = "personal")
public class PersonalActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalcenter_activity_personal);
    }
}
