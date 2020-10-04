package com.github.leeon;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.loginlib.view.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lxiansheng
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_first)
    TextView  tvFirstPage;
    @BindView(R.id.tv_my)
    TextView  tvMyPage;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        tvFirstPage.setOnClickListener((view) -> {
            LoginActivity.start(MainActivity.this);
        });

//        vpMain.setAdapter(new FragmentPagerAdapter() {
//            @Override
//            public Fragment getItem(int position) {
//                return null;
//            }
//
//            @Override
//            public int getCount() {
//                return 0;
//            }
//        });
    }
}
