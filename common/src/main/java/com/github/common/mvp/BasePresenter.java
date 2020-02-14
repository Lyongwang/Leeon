package com.github.common.mvp;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * Created by Lyongwang on 2020-02-14 11: 29.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class BasePresenter implements IPresenter {

    private final IView iView;

    public BasePresenter(IView iView) {
        this.iView = iView;
    }

    @Override
    public <T> AutoDisposeConverter<T> bindLifecycle() {
        return iView.bindLifecycle();
    }
}
