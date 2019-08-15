package com.delong.web.iweixun.bean.api.account;

import com.delong.web.iweixun.bean.card.UserCard;
import com.delong.web.iweixun.bean.db.User;
import com.google.gson.annotations.Expose;

/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
public class AccountRspModel {
    @Expose
    private UserCard userCard;
    @Expose
    private String account;
    @Expose
    private String token;
    @Expose
    private boolean isBind;

    public AccountRspModel(User user) {
        this(user,false);
    }

    public AccountRspModel(User user, boolean isBind) {
        this.userCard = new UserCard(user);
        this.account =user.getPhone();
        this.token = user.getToken();
        this.isBind = isBind;
    }

    public UserCard getUserCard() {
        return userCard;
    }

    public void setUserCard(UserCard userCard) {
        this.userCard = userCard;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
