package com.github.common.mvp;

import com.uber.autodispose.AutoDisposeConverter;

/**
 * Created by Lyongwang on 2020-02-13 18: 04.
 * <p>
 * Email: liyongwang@yiche.com
 */
public interface IView {
    <T> AutoDisposeConverter<T> bindLifecycle();
}
