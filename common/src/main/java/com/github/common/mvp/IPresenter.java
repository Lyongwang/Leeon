package com.github.common.mvp;

import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * Created by Lyongwang on 2020-02-13 18: 04.
 * <p>
 * Email: liyongwang@yiche.com
 */
public interface IPresenter {
    <T> AutoDisposeConverter<T> bindLifecycle();
}
