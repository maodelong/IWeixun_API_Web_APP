package com.delong.web.iweixun.bean.factory;

import com.delong.web.iweixun.bean.api.base.PushModel;
import com.delong.web.iweixun.bean.card.MessageCard;
import com.delong.web.iweixun.bean.db.Message;
import com.delong.web.iweixun.bean.db.PushHistory;
import com.delong.web.iweixun.bean.db.User;
import com.delong.web.iweixun.utils.Hib;
import com.delong.web.iweixun.utils.PushDispatcher;
import com.delong.web.iweixun.utils.TextUtil;

/**
 * Created by Maodelong on 2019/8/15.
 */
@SuppressWarnings("ALL")
public class PushFctory {
    public static void pushNewMessage(User receiver, Message message) {

        if (receiver==null||message==null)
            return;

        MessageCard card = new MessageCard(message);

        String entity = TextUtil.toJson(card);
        PushHistory history = new PushHistory();
        history.setEntityType(PushModel.ENTITY_TYPE_MESSAGE);
        history.setEntity(entity);
        history.setReceiver(receiver);
        history.setId(message.getId());
        history.setReceiverId(receiver.getPushId());

        PushModel model = new PushModel();
        model.add(history.getEntityType(),history.getEntity());
        PushDispatcher.getInstance().addPushMsg(receiver,model,PushDispatcher.TYPE_TRANS_MSG).submit();
        Hib.queryOnly(session -> {
            session.save(history);
        });
    }
}
