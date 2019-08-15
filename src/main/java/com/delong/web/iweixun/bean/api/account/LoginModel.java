package com.delong.web.iweixun.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
public class LoginModel {
    @Expose
    private String account;
    @Expose
    private String password;
    @Expose
    private String pushId;

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static boolean check(LoginModel model){
        return model!=null
                && !Strings.isNullOrEmpty(model.account)
                &&!Strings.isNullOrEmpty(model.password);
    }

}
