package com.github.common;

import com.github.annotation.service.ServiceMethod;
import com.github.annotation.service.ServiceParam;
import com.github.annotation.service.ServiceRouter;
import com.github.common.router.RouterConstans;

import io.reactivex.rxjava3.core.Observable;


/**
 *
 * @author Lyongwang
 * @date 2020/4/29 16: 20
 * <p>
 * Email: liyongwang@yiche.com
 */

@ServiceRouter(RouterConstans.Service.common)
public class TestServiceRouter {
    @ServiceMethod
    public Observable<String> getUserNameById(@ServiceParam int id, @ServiceParam String id2, @ServiceParam boolean needShow){
        return Observable.just("leeon");
    }

    @ServiceMethod
    public int getUserId(@ServiceParam int id, @ServiceParam String id2, @ServiceParam boolean needShow){
        return 1;
    }
}
