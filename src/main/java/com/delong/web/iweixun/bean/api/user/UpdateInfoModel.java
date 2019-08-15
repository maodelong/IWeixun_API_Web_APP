package com.delong.web.iweixun.bean.api.user;

import com.delong.web.iweixun.bean.db.User;
import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * 用户更新信息，完善信息的Model
 *
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
@SuppressWarnings("ALL")
public class UpdateInfoModel {
    @Expose
    private String name;
    @Expose
    private String portrait;
    @Expose
    private String description;
    @Expose
    private int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    /**
     * 把当前的信息，填充到用户Model中
     * 方便UserModel进行写入
     *
     * @param user User Model
     * @return User Model
     */
    public User updateToUser(User user) {
        if (!Strings.isNullOrEmpty(name))
            user.setName(name);

        if (!Strings.isNullOrEmpty(portrait))
            user.setPortrait(portrait);

        if (!Strings.isNullOrEmpty(description))
            user.setDescription(description);

        if (sex != 0)
            user.setSex(sex);

        return user;
    }

    public static boolean check(UpdateInfoModel model) {
        // Model 不允许为null，
        // 并且只需要具有一个及其以上的参数即可
        boolean b = model != null
                && (!Strings.isNullOrEmpty(model.name) ||
                !Strings.isNullOrEmpty(model.portrait) ||
                !Strings.isNullOrEmpty(model.description) ||
                model.sex != 0);
        return b;
    }
}
