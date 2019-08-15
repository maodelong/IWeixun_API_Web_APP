package com.delong.web.iweixun.service;

import com.delong.web.iweixun.bean.api.base.ResponseModel;
import com.delong.web.iweixun.bean.api.user.UpdateInfoModel;
import com.delong.web.iweixun.bean.card.UserCard;
import com.delong.web.iweixun.bean.db.User;
import com.delong.web.iweixun.bean.factory.UserFactory;
import com.google.common.base.Strings;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
@Path("/user")
public class UserService extends BaseService {

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        User user = getSelf();
        user = model.updateToUser(user);
        user = UserFactory.update(user);
        return ResponseModel.buildOk(new UserCard(user, true));
    }


    @GET
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> contacts() {
        User self = getSelf();
        List<User> users = UserFactory.contacts(self);
        List<UserCard> cards = users.stream().map(user -> new UserCard(user, true))
                .collect(Collectors.toList());
        return ResponseModel.buildOk(cards);
    }


    @PUT
    @Path("/follow/{followId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> follow(@PathParam("followId") String followId) {
        User self = getSelf();
        if (self.getId().equalsIgnoreCase(followId) || Strings.isNullOrEmpty(followId)) {
            return ResponseModel.buildParameterError();
        }

        User follow = UserFactory.findById(followId);
        if (follow == null) {
            return ResponseModel.buildNotFoundUserError(null);
        }

        follow = UserFactory.follow(self, follow, null);
        if (follow == null) {
            return ResponseModel.buildServiceError();
        }
        //TODO
        return ResponseModel.buildOk(new UserCard(follow,true));
    }


    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> getUser(@PathParam("id") String id) {
        User self = getSelf();
        if (Strings.isNullOrEmpty(id)) {
            ResponseModel.buildParameterError();
        }

        if (self.getId().equalsIgnoreCase(id)) {
            return ResponseModel.buildOk(new UserCard(self, true));
        }

        User user = UserFactory.findById(id);
        if (user == null) {
            return ResponseModel.buildNotFoundUserError(null);
        }

        boolean isFollow = UserFactory.getUserFollow(self, user) != null;
        return ResponseModel.buildOk(new UserCard(user, isFollow));

    }


    @GET
    @Path("/search/{name:(.*)?}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> contact(@DefaultValue("") @PathParam("name") String name) {
        User self = getSelf();
        List<User> searchUsers = UserFactory.search(name);

        final List<User> contracts = UserFactory.contacts(self);

        List<UserCard> cards = searchUsers.stream()
                .map(user -> {
                    boolean isFollow = user.getId().equalsIgnoreCase(self.getId()) ||
                            contracts.stream().anyMatch(user1 -> user1.getId().equalsIgnoreCase(user.getId()));
                    return new UserCard(user, isFollow);
                }).collect(Collectors.toList());
        return ResponseModel.buildOk(cards);
    }


}
