package com.delong.web.iweixun.bean.api.message;

import com.delong.web.iweixun.bean.db.Message;
import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * Created by Maodelong on 2019/8/15.
 */
@SuppressWarnings("ALL")
public class MessageCreateModel {
    @Expose
    private String id;
    @Expose
    private String content;
    @Expose
    private String attach;
    @Expose
    private int type = Message.TYPE_STR;
    @Expose
    private String receiverId;
    @Expose
    private int receiverType = Message.RECEIVER_TYPE_NONE;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }


    public static boolean check(MessageCreateModel model) {

        return model!=null
                &&
                !(Strings.isNullOrEmpty(model.getId())
                || Strings.isNullOrEmpty(model.receiverId)
                || Strings.isNullOrEmpty(model.content))

                &&(model.receiverType == Message.RECEIVER_TYPE_NONE
                || model.receiverType == Message.RECEIVER_TYPE_GROUP)

                &&(model.type == Message.TYPE_STR
                || model.type == Message.TYPE_AUDIO
                || model.type == Message.TYPE_PIC
                || model.type == Message.TYPE_FILE);

    }


}
