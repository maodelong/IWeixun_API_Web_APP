package com.delong.web.iweixun.bean.db;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
@Entity
@Table(name = "BD_USER_FOLLOW")
public class UserFollow {
    //这是一个主键
    @Id
    @PrimaryKeyJoinColumn
    //主键生成存储的类型为UUID
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义为uuid2,uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //不允许为空，不允许修改
    @Column(updatable = false, nullable = false)
    private  String id;


    //定义一个发起人
    //多对1 ->你可以关住很多人，你的每一条关注都是一条记录
    //你可以创建很多个关注信息，所以是多对一
    //合理的多对一就是：User 对应 多个UserFollow
    @ManyToOne(optional = false)
    @JoinColumn(name = "originId")
    private User origin;

    @Column(nullable = false,updatable = false,insertable = false)
    private String originId;

    //定义关注目标
    @ManyToOne(optional = false)
    @JoinColumn(name = "targetId")
    private User target;
    @Column(nullable = false,updatable = false,insertable = false)
    private String targetId;

    //别名，也就是对target的备注，可以为空
    @Column
    private String alias;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }


}
