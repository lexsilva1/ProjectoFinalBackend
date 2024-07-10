package service;

import bean.TokenBean;
import bean.UserBean;
import dto.MyDto;
import dto.PasswordDto;
import dto.UserConfirmation;
import dto.UserDto;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    TokenBean tokenBean;

    
    @POST
    @Path("/login")
    @Produces("application/json")
    public Response login(@HeaderParam("email") String email, @HeaderParam("password") String password) {


        MyDto myInfo = userBean.login(email, password);
        if(myInfo.getToken() != null) {
            return Response.status(200).entity(myInfo).build();
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
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
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


        return Response.status(200).entity(userBean.firstLogin(user)).build();
    }
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findUserById(@HeaderParam("token") String token, @PathParam("id") int id) {
        if(userBean.findUserByToken(token) == null && !tokenBean.isTokenValid(token)) {
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        UserDto user = userBean.findUserDtoById(id);
        if(user == null) {
            return Response.status(404).entity("user not found").build();
        }
        return Response.status(200).entity(user).build();
    }
    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public Response updateUser(@HeaderParam("token") String token, @PathParam("id") int id, UserDto userDto) {
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        if(userBean.updateUser(id, userDto)) {
            return Response.status(200).entity("user updated").build();
        } else {
            return Response.status(404).entity("user not found").build();
        }
    }
    @PUT
    @Path("/resetPassword")
    @Produces("application/json")
    public Response resetPassword(@HeaderParam("email") String email) {
        UserEntity user = userBean.findUserByEmail(email);
        if(user == null) {
            return Response.status(404).entity("user not found").build();
        }
        boolean sent = userBean.resetPassword(user);
        if(!sent) {
            return Response.status(404).entity("email not sent").build();
        }
        return Response.status(200).entity("password reset").build();
    }
    @PUT
    @Path("/confirmPasswordReset/{token}")
    @Produces("application/json")
    public Response confirmPasswordReset(@PathParam("token") String token, PasswordDto passwordDto) {
        if(!userBean.isPasswordValid(passwordDto.getPassword())) {
            return Response .status(406).entity("invalid password").build();
        }
        if(!passwordDto.getConfirmPassword().equals(passwordDto.getPassword())) {
            return Response.status(409).entity("passwords do not match").build();
        }
        boolean reset = userBean.confirmPasswordReset(token, passwordDto);
        if(!reset) {
            return Response.status(404).entity("user not found").build();
        }
        return Response.status(200).entity("password reset").build();
    }
    @PUT
    @Path("/setPrivacy")
    @Produces("application/json")
    public Response setPrivacy(@HeaderParam("token") String token) {
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        if(userBean.setPrivate(token)) {
            return Response.status(200).entity("privacy set").build();
        } else {
            return Response.status(404).entity("user not found").build();
        }
    }
    @PUT
    @Path("/adminStatus")
    @Produces("application/json")
    public Response setAdminStatus(@HeaderParam("token") String token, @HeaderParam("userId") int id) {
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            return Response.status(404).entity("user not found").build();
        }
        if(userBean.findUserByToken(token).getRole().getValue() > 2) {
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if(userBean.setAdminStatus(token,id)) {
            return Response.status(200).entity("admin status set").build();
        } else {
            return Response.status(404).entity("user not found").build();
        }
    }
}
