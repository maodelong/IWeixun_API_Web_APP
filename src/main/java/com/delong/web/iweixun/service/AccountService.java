package com.delong.web.iweixun.service;


import com.delong.web.iweixun.bean.api.account.AccountRspModel;
import com.delong.web.iweixun.bean.api.account.LoginModel;
import com.delong.web.iweixun.bean.api.account.RegisterModel;
import com.delong.web.iweixun.bean.api.base.ResponseModel;
import com.delong.web.iweixun.bean.db.User;
import com.delong.web.iweixun.bean.factory.UserFactory;
import com.google.common.base.Strings;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Maodelong on 2019/8/14.
 */

@SuppressWarnings("ALL")
@Path("/account")
public class AccountService extends BaseService {


    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> login(LoginModel model) {
        if (!LoginModel.check(model)) {
            return ResponseModel.buildParameterError();
        }
        User user = UserFactory.login(model.getAccount(), model.getPassword());
        if (user != null) {
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                      return bind(user,model.getPushId());
            }

            AccountRspModel rsModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rsModel);
        } else {
            return ResponseModel.buildLoginError();
        }
    }


    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> register(RegisterModel model) {
        if (!RegisterModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.findByPhone(model.getAccount().trim());

        if (user != null) {
            return ResponseModel.buildHaveAccountError();
        }

        user = UserFactory.findByName(model.getName().trim());

        if (user != null) {
            return ResponseModel.buildHaveNameError();
        }

        user = UserFactory.register(model.getAccount()
                , model.getName(), model.getPassword());
        if (user != null) {
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user,model.getPushId());
            }
            return ResponseModel.buildOk(new AccountRspModel(user));
        } else {
            return ResponseModel.buildRegisterError();
        }
    }

    @POST
    @Path("/bind/{pushId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<AccountRspModel> bind(@PathParam("pushId") String pushId) {
        if (Strings.isNullOrEmpty(pushId)) {
            return ResponseModel.buildParameterError();
        }
            User user = getSelf();
            return bind(user,pushId);
    }


    private ResponseModel<AccountRspModel> bind(User self, String pushId) {
        User user = UserFactory.bindPushId(self, pushId);
        if (user == null) {
            return ResponseModel.buildServiceError();
        }
        AccountRspModel model = new AccountRspModel(user, true);
        return ResponseModel.buildOk(model);
    }

}
