package service;
import bean.EmailBean;
import bean.UserBean;
import dto.UserConfirmation;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import java.lang.String;

@Path("/users")
public class UserService {
    @Context
    private HttpServletRequest request;
    @EJB
    UserBean userBean;

    @EJB
    EmailBean emailBean;


    @POST
    @Path("/login")
    @Produces("application/json")
    public Response login(@HeaderParam("email") String email, @HeaderParam("password") String password) {
        String token = userBean.login(email, password);
        if(token != null) {
            return Response.status(200).entity(token).build();
        } else
            return Response.status(404).entity("user not found").build();

    }
    @POST
    @Path("/logout")
    @Produces("application/json")
    public Response logout(@HeaderParam("token") String token) {
        if(userBean.logout(token)) {
            return Response.status(200).entity("logout successful").build();
        } else
            return Response.status(404).entity("user not found").build();
    }
    @GET
    @Path("")
    @Produces("application/json")
    public Response findAllUsers(@HeaderParam("token") String token){
        if(userBean.findUserByToken(token) == null) {
            return Response.status(404).entity("user not found").build();
        }
        return Response.status(200).entity(userBean.findAllUsers()).build();
    }
    @POST
    @Path("/register")
    @Produces("application/json")
    public Response register(@HeaderParam("email") String email, @HeaderParam("password") String password) {
        if(userBean.findUserByEmail(email) != null) {
            return Response.status(404).entity("email already in use").build();
        }
        if(!userBean.isPasswordValid(password)  || !userBean.isEmailValid(email)) {
            return Response.status(401).entity("invalid credentials").build();
        }
        if(userBean.register(email, password)){
            return Response.status(200).entity("user registered").build();
        }else {
            return Response.status(400).entity("Something went wrong").build();
        }

    }
    @POST
    @Path("/confirm")
    @Produces("application/json")
    public Response confirm(@HeaderParam("token") String token, UserConfirmation userConfirmation) {
        UserEntity user = userBean.confirmUser(token,userConfirmation);
        if(user == null) {
            return Response.status(404).entity("user not found").build();
        }
        String loginToken = userBean.firstLogin(user);
        userBean.setLastActivity(user);
        return Response.status(200).entity(loginToken).build();
    }
}
