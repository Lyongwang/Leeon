package com.github.leeon;

import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.annotation.TextUtils;
import com.github.annotation.service.Router;
import com.github.common.router.RouterConstans;
import com.github.leeon.plugin.TestPluginUtils;
import com.github.leeon.widget.SrcScrollFrameLayout;
import com.github.loginlib.view.activity.LoginActivity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;
import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * @author lxiansheng
 */
public class MainActivity extends AppCompatActivity {
    private String fixFileName = "fix.png";
    @BindView(R.id.tv_first)
    TextView  tvFirstPage;
    @BindView(R.id.tv_my)
    TextView  tvMyPage;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.btn_show_text)
    Button    buttonShowText;
    @BindView(R.id.btn_show_plugin)
    Button    buttonShowPlugin;
    @BindView(R.id.btn_remove_fix)
    Button    buttonRemoveHotFix;

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
        buttonShowText.setOnClickListener(view -> {
            Log.i("TAG", "initView: " + getClassLoader());
            tvFirstPage.setText("from app");
        });

        buttonShowPlugin.setOnClickListener(view -> {
            tvFirstPage.setText(getTextFromPlugin());
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
            tvFirstPage.setText(TestPluginUtils.getStr());
        });

        findViewById(R.id.btn_add_fix).setOnClickListener(view -> {
            loadClassesFromFix();
        });

        findViewById(R.id.btn_remove_fix).setOnClickListener(view -> {
            removeFixFile();
        });
    }

    private void removeFixFile() {
        final File file = new File(getFilesDir(), fixFileName);
        if (file.exists()){
            file.delete();
        }
        System.exit(0);
    }

    private void loadClassesFromFix() {
        try(Source source = Okio.source(getAssets().open(fixFileName));
            Sink sink = Okio.sink(new File(getFilesDir(), fixFileName));
            BufferedSink buffer = Okio.buffer(sink)){
            buffer.writeAll(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getTextFromPlugin() {
        // 下载文件
        final String pluginFileName = "plugin1.png";
        final File file = new File(getFilesDir() + File.separator + "plg" + File.separator + pluginFileName);
        try(final Source source = Okio.source(getAssets().open(pluginFileName));
            final BufferedSink buffer = Okio.buffer(Okio.sink(file))) {
            buffer.writeAll(source);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DexClassLoader dexClassLoader = new DexClassLoader(file.getPath(), getFilesDir().getPath(), "", null);

        String pluginText = null;
        try {
            final Class<?> aClass = dexClassLoader.loadClass("com.github.plugin.Plugin2");
            final Object plugin2 = aClass.newInstance();
            final Method getPluginTextMethod = aClass.getDeclaredMethod("getPluginText");
            getPluginTextMethod.setAccessible(true);
            pluginText = (String) getPluginTextMethod.invoke(plugin2);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return TextUtils.isEmpty(pluginText) ? "plugin empty" : pluginText;
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


        callPlugin("ldddddsssssddsdd");
    }

    private void callPlugin(String string) {
        DexClassLoader dexClassLoader = PluginManager.INSTANCE.loadAllPlugins(getApplication());
        try {
            Class<?> aClass = dexClassLoader.loadClass("com.github.plugin.PluginUtils");
            Method logMethod = aClass.getDeclaredMethod("log", String.class);
            logMethod.setAccessible(true);
            Object invoke = logMethod.invoke(aClass.newInstance(), string);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }


    }
}
