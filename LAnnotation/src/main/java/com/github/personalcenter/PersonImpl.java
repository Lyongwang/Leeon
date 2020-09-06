package com.github.personalcenter;

import com.github.annotation.service.ServiceParam;

/**
 * Created by Lyongwang on 2020/9/4 14: 47.
 * <p>
 * Email: liyongwang@yiche.com
 */
public class PersonImpl {
    public int getUserId(){
        System.out.println("call method getUserId");
        return 1222;
    }

    public int getUserId(@ServiceParam("key") int key1){
        System.out.println("call method getUserId params key: " + key1);
        return 122234354;
    }

    public int getUserId(@ServiceParam("key1") String key, @ServiceParam("key") int key1){
        System.out.println("call method getUserId params key1: " + key + " key: " + key1);
        return 1225542;
    }

    public int getUserId(@ServiceParam("key") int key, @ServiceParam("key1")String key1){
        System.out.println("call method getUserId params key: " + key + " key1: " + key1);
        return 122243232;
    }
}
