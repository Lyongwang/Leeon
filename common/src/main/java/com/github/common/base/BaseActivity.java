package com.github.common.base;

import androidx.appcompat.app.AppCompatActivity;

import com.github.common.mvp.IView;
import com.github.common.rx.RxUtils;
import com.uber.autodispose.AutoDisposeConverter;

/**
 * Created by Lyongwang on 2019-12-13 11: 29.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BaseActivity extends AppCompatActivity implements IView {
    @Override
    public <T> AutoDisposeConverter<T> bindLifecycle(){
        return RxUtils.bindLifecycle(this);
    }

}
