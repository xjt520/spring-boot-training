package com.zmdm.config;

import com.zmdm.rest.DemoResource;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by xjt520 on 16/7/7.
 */
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(JacksonFeature.class);
        register(DemoResource.class);
//        register(ProductRepository.class);
    }
}
