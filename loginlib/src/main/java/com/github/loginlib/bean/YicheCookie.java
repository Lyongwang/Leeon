package com.github.loginlib.bean;

import java.io.Serializable;

public class YicheCookie implements Serializable {

    /**
     * @Fields serialVersionUID
     */
    private static final long serialVersionUID = 182822L;

    public String Name;
    public String Value;
    public String Domain;
    public String Path;
    public String Expires;

    @Override
    public String toString() {
        return "YicheCookie{" +
                "Name='" + Name + '\'' +
                ", Value='" + Value + '\'' +
                ", Domain='" + Domain + '\'' +
                ", Path='" + Path + '\'' +
                ", Expires='" + Expires + '\'' +
                '}';
    }
}
