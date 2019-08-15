package com.delong.web.iweixun.provider;

import com.delong.web.iweixun.bean.api.base.ResponseModel;
import com.delong.web.iweixun.bean.db.User;
import com.delong.web.iweixun.bean.factory.UserFactory;
import com.google.common.base.Strings;
import org.glassfish.jersey.server.ContainerRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
@Provider
public class AuthRequestFilter implements ContainerRequestFilter{

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        //检测是否是登录注册接口
        String relationPath = ((ContainerRequest)requestContext).getPath(false);
        if (relationPath.startsWith("account/login")||relationPath.startsWith("account/register")){
            return;
        }

        String token = requestContext.getHeaders().getFirst("token");
        if (!Strings.isNullOrEmpty(token)){
            final User self = UserFactory.findByToken(token);

            if (self!=null){
                requestContext.setSecurityContext(new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return self;
                    }

                    @Override
                    public boolean isUserInRole(String role) {

                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return null;
                    }
                });
               return;
            }
        }

        ResponseModel model = ResponseModel.buildAccountError();
        requestContext.abortWith(Response.status(Response.Status.OK).entity(model).build());
    }
}



