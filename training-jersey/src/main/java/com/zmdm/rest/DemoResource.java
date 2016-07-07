package com.zmdm.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xjt520 on 16/7/7.
 */
@Path("/demo")
public class DemoResource {

//    @Inject
//    private ProductRepository productRepository;

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List list(){
        List list = new ArrayList<>();
        list.add("11111");
        list.add("22222");
        return list;
    }
}
