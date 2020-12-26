package com.github.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.annotation.router.IntentParam;
import com.github.common.mvp.IView;
import com.github.common.router.ParamInjector;
import com.github.common.rx.RxUtils;
import com.uber.autodispose.AutoDisposeConverter;

/**
 * Created by Lyongwang on 2019-12-13 11: 29.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BaseFragment extends Fragment implements IView {

    @Override
    public <T> AutoDisposeConverter<T> bindLifecycle(){
        return RxUtils.bindLifecycle(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ParamInjector.inject(this);
        super.onCreate(savedInstanceState);
    }
}
