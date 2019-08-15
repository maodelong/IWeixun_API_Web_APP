package com.delong.web.iweixun.bean.factory;

import com.delong.web.iweixun.bean.api.message.MessageCreateModel;
import com.delong.web.iweixun.bean.db.Group;
import com.delong.web.iweixun.bean.db.Message;
import com.delong.web.iweixun.bean.db.User;
import com.delong.web.iweixun.utils.Hib;

/**
 * Created by Maodelong on 2019/8/15.
 */
@SuppressWarnings("ALL")
public class MessageFactory {
    public static Message findById(String id) {
        return Hib.query(session -> session.get(Message.class, id));
    }

    public static Message add(User sender, User receiver, MessageCreateModel model) {
        Message message = new Message(sender, receiver, model);
        return save(message);
    }

    public static Message add(User sender, Group group, MessageCreateModel model) {

        Message message = new Message(sender, group, model);
        return save(message);
    }


    private static Message save(Message message) {
        return Hib.query(session -> {
            session.save(message);
            session.flush();
            session.refresh(message);
            return message;
        });
    }

}
