package com.github.common;

import com.github.annotation.service.ServiceMethod;
import com.github.annotation.service.ServiceParam;
import com.github.annotation.service.ServiceRouter;
import io.reactivex.rxjava3.core.Observable;


/**
 *
 * @author Lyongwang
 * @date 2020/4/29 16: 20
 * <p>
 * Email: liyongwang@yiche.com
 */

@ServiceRouter
public class TestServiceRouter {
    @ServiceMethod
    public Observable<String> getUserNameById(@ServiceParam int id, @ServiceParam String id2, @ServiceParam boolean needShow){
        return Observable.just("leeon");
    }
}
