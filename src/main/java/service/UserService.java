package service;

import bean.EmailBean;
import bean.UserBean;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import java.lang.String;

@Path("/users")
public class UserService {
    @Context
    private HttpServletRequest request;
    @Inject
    UserBean userBean;
    @Inject
    EmailBean emailBean;

    @POST
    @Path("/logout")
    @Produces("application/json")
    public Response logout(@HeaderParam("token") String token) {
        userBean.logout(token);
        return Response.status(200).build();
    }
    @POST
    @Path("/login")
    @Produces("application/json")
    public Response login(@HeaderParam("email") String email, @HeaderParam("password") String password) {
        String token = userBean.login(email, password);
        return Response.status(200).entity(token).build();
    }
}
