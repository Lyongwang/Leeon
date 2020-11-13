package com.github.personalcenter;

import android.widget.Toast;

import com.github.annotation.service.ServiceMethod;
import com.github.annotation.service.ServiceParam;
import com.github.annotation.service.ServiceRouter;
import com.github.common.router.RouterConstans;
import com.github.common.tools.ToolBoxKt;

/**
 * Created by Lyongwang on 2020/9/4 14: 47.
 * <p>
 * Email: liyongwang@yiche.com
 */
@ServiceRouter(RouterConstans.Service.personcenter)
public class PersonImpl {
    @ServiceMethod
    public int getUserId(){
        ToolBoxKt.toast("call method getUserId", Toast.LENGTH_SHORT);
        return 1222;
    }

    @ServiceMethod
    public int getUserId(@ServiceParam("key") int key1){
        ToolBoxKt.toast("call method getUserId params key: " + key1, Toast.LENGTH_SHORT);
        return 122234354;
    }

    @ServiceMethod
    public int getUserId(@ServiceParam("key1") String key, @ServiceParam("key") int key1){
        ToolBoxKt.toast("call method getUserId params key1: " + key + " key: " + key1, Toast.LENGTH_SHORT);
        return 1225542;
    }

    @ServiceMethod
    public int getUserId(@ServiceParam("key") int key, @ServiceParam("key1")String key1){
        ToolBoxKt.toast("call method getUserId params key: " + key + " key1: " + key1, Toast.LENGTH_SHORT);
        return 122243232;
    }
}
