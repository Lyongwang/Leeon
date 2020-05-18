package com.github.annotation.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务调用类
 *
 * @author Lyongwang
 * @date 2020/4/29 16: 34
 * <p>
 * Email: liyongwang@yiche.com
 */
public class Router {
    private Router() {
    }

    public static Router.Builder service(String serviceName) {
        return new Builder(serviceName);
    }

    private static class Builder {
        private final String serviceName;

        private String methodName;

        private Map<String, Object> params = new HashMap<>();

        Builder(String serviceName) {
            this.serviceName = serviceName;
        }

        public Builder callMethod(String methodName) {
            this.methodName = methodName;
            return this;
        }

        public Builder addParam(String paramKey, Object paramValue) {
            params.put(paramKey, paramValue);
            return this;
        }

        public <E> E execute(){
            return ServiceCenter.getInstance().callMethod(serviceName, methodName, params);
        }
    }


    public static void main(String[] args){
        Router.service("leeon://leeon.test/personcenter.personImpl")
                .callMethod("getUserId")
                .addParam("key2", 1)
                .addParam("key1", "value1")
                .execute();
    }

}
