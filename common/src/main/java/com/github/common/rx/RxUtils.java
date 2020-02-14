package com.github.common.rx;




import androidx.lifecycle.LifecycleOwner;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * Created by Lyongwang on 2020-02-13 17: 48.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class RxUtils {
    public static <T> AutoDisposeConverter<T> bindLifecycle(LifecycleOwner lifecycleOwner) {
        return AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(lifecycleOwner)
        );
    }
}
