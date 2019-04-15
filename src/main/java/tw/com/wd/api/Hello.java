package tw.com.wd.api;


import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


@Path("api")
@Produces("application/json;charset=utf-8")
public interface Hello {
    @POST
    @Path("hello")
    public Response postHello(@Context HttpHeaders httpHeader, String body);

    @GET
    @Path("hello")
    public Response getHello(@Context HttpHeaders httpHeader);
}
