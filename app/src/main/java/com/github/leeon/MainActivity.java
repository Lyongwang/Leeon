package com.github.leeon;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.github.common.bundle.BundleCode;
import com.github.common.bundle.BundleInfo;

@BundleInfo(c = BundleCode.LOGIN, v = "1.0", n = "main", d = "test")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
