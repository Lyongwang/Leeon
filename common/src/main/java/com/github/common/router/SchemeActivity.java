package com.github.common.router;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.common.LaunchManager;

/**
 *
 * @author Lyongwang
 * @date 2020/4/13 15: 58
 * <p>
 * Email: liyongwang@yiche.com
 */
public class SchemeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LaunchManager.getInstance().start(this, getIntent().getData());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
