package com.github.leeon;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.annotation.service.Router;
import com.github.common.router.RouterConstans;
import com.github.leeon.widget.SrcScrollFrameLayout;
import com.github.loginlib.view.activity.LoginActivity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.PathClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

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
        tvMyPage.setOnClickListener((view) -> {
            callMyMethod();
        });

        SrcScrollFrameLayout srcLayout = findViewById(R.id.src_layout);
        srcLayout.startScroll();
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

        findViewById(R.id.btn_add_app).setOnClickListener(view -> {
            tvFirstPage.setText("fix");
        });

        findViewById(R.id.btn_add_fix).setOnClickListener(view -> {
            loadClassesFromFix();
        });
    }

    private void loadClassesFromFix() {
        String fileName = "fix.png";
        try(Source source = Okio.source(getAssets().open(fileName));
            Sink sink = Okio.sink(new File(getFilesDir(), fileName));
            BufferedSink buffer = Okio.buffer(sink)){
            buffer.writeAll(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BaseDexClassLoader classLoader = (BaseDexClassLoader) getClassLoader();
        PathClassLoader fixClassLoader = new PathClassLoader(getFilesDir().getPath() + File.separator + fileName, null);
        Class loaderClass = BaseDexClassLoader.class;

        try {
            Field pathListField = loaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            // getClassLoader().pathList
            Object loaderPathList = pathListField.get(classLoader);
            // fixClassLoader.pathList
            final Object fixPathList = pathListField.get(fixClassLoader);

            Class pathListClass = loaderPathList.getClass();

            Field dexElementsField = pathListClass.getField("dexElements");
            dexElementsField.setAccessible(true);
            // fixClassLoader.pathList.dexElements
            Object fixDexElements = dexElementsField.get(fixPathList);
            // classLoader.pathList.dexElements = fixClassLoader.pathList.dexElements;
            dexElementsField.set(loaderPathList, fixDexElements);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }



    }

    private void callMyMethod() {
        int result = Router.service(RouterConstans.Service.personcenter)
                .callMethod("getUserId")
//                .addParam("key", 1)
                //                .addParam("key2", "value1")
//                .addParam("key1", "value1")
                //                .addParam("key3", "value1")
                .execute();
        System.out.println("return ----> " + result);
    }
}
