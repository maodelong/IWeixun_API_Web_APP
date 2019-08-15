package com.delong.web.iweixun.service;

import com.delong.web.iweixun.bean.api.base.ResponseModel;
import com.delong.web.iweixun.bean.api.message.MessageCreateModel;
import com.delong.web.iweixun.bean.card.MessageCard;
import com.delong.web.iweixun.bean.db.Message;
import com.delong.web.iweixun.bean.db.User;
import com.delong.web.iweixun.bean.factory.MessageFactory;
import com.delong.web.iweixun.bean.factory.PushFctory;
import com.delong.web.iweixun.bean.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Maodelong on 2019/8/15.
 */
@SuppressWarnings("ALL")
@Path("/msg")
public class MessageService extends BaseService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<MessageCard> pushMessage(MessageCreateModel model) {
        if (!MessageCreateModel.check(model)){
            return ResponseModel.buildParameterError();
        }
        User self = getSelf();
        Message message = MessageFactory.findById(model.getId());

       if (message!=null){
           return ResponseModel.buildOk(new MessageCard(message));
       }
       if (model.getReceiverType()==Message.RECEIVER_TYPE_GROUP){
          return null;
       }else{
           return  pushToUser(self,model);
        }

    }

    private ResponseModel<MessageCard> pushToUser(User self,MessageCreateModel model) {
        User receiver = UserFactory.findById(model.getReceiverId());
        if (receiver==null){
            return ResponseModel.buildNotFoundUserError("没有找到接收者！");
        }

        if (receiver.getId().equalsIgnoreCase(self.getId())){
           return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
        }

        Message message = MessageFactory.add(self ,receiver,model);

        return buildAndPushResponse(receiver,message);

    }

    private ResponseModel<MessageCard> buildAndPushResponse(User receiver, Message message) {
        if (message==null) {
            return ResponseModel.buildCreateError(ResponseModel.ERROR_CREATE_MESSAGE);
        }

        PushFctory.pushNewMessage(receiver,message);


        return ResponseModel.buildOk(new MessageCard(message));

    }

//    private ResponseModel<MessageCard> pushToGroup() {
//        return null;
//    }


}
