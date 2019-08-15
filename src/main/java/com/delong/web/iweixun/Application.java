package com.delong.web.iweixun;

import com.delong.web.iweixun.provider.AuthRequestFilter;
import com.delong.web.iweixun.provider.GsonProvider;
import com.delong.web.iweixun.service.AccountService;
import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.server.ResourceConfig;
import java.util.logging.Logger;

/**
 * Created by Maodelong on 2019/8/14.
 */
@SuppressWarnings("ALL")
public class Application extends ResourceConfig{
    public Application() {
        packages(AccountService.class.getPackage().getName());
        register(GsonProvider.class);
        register(AuthRequestFilter.class);
        register(Logger.class);
       System.out.print("packages>>>>>>"+AccountService.class.getPackage().getName());

    }
}
