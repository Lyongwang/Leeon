package com.github.common.router;

/**
 * Created by Lyongwang on 2020/4/8 14: 47.
 * <p>
 * Email: liyongwang@yiche.com
 */
public interface RouterConstans {
    /**
     * 默认scheme
     */
    String def_scheme = "harsh.leeon";
    /**
     * 默认主机名(app)
     */
    String def_host = "leeon.test";
    String separator = "/";


    public interface Service {
        /**
         * 登录模块
         */
        String login = "login";
        /**
         * 个人中心
         */
        String personcenter = "personcenter";
    }

    public interface Paths{
        /**
         * 个人主页
         */
        String personcenter_homepage = "personcenter.homepage";
        /**
         * 个人主页新
         */
        String personcenter_homepage_new = "personcenter.homepage.new";

        /**
         * 登录页
         */
        String login_page = "login.page";
    }

}
