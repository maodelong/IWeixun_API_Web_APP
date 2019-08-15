package com.delong.web.iweixun.bean.card;

import com.delong.web.iweixun.bean.db.User;
import com.google.gson.annotations.Expose;
import java.time.LocalDateTime;


/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
public class UserCard {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String phone;
    @Expose
    private String portrait;
    @Expose
    private String description;
    @Expose
    private int sex = 0;
    @Expose
    private int following ;
    @Expose
    private int followers ;
    @Expose
    private boolean isFollow;
    @Expose
    private LocalDateTime modify = LocalDateTime.now();

    public UserCard (User user){
      this(user,false);
        //todo
    }


    public UserCard (User user ,boolean isFollow){
        this.id = user.getId();
        this.name=user.getName();
        this.phone = user.getPhone();
        this.portrait = user.getPortrait();
        this.description=user.getDescription();
        this.sex = user.getSex();
        this.modify = user.getUpdateAt();
        this.isFollow = isFollow;
        //todo
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public LocalDateTime getModify() {
        return modify;
    }

    public void setModify(LocalDateTime modify) {
        this.modify = modify;
    }
}
