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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.String;

@Path("/users")
public class UserService {
    @Context
    private HttpServletRequest request;
    @EJB
    UserBean userBean;
    @EJB
    TokenBean tokenBean;
    private static final Logger logger = LogManager.getLogger(UserService.class);
    
    @POST
    @Path("/login")
    @Produces("application/json")
    public Response login(@HeaderParam("email") String email, @HeaderParam("password") String password,@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to login " ,ipAddress);

        MyDto myInfo = userBean.login(email, password);
        if(myInfo.getToken() != null) {
            logger.info("User {} {} logged in successfully with token {}", myInfo.getFirstName(), myInfo.getLastName(),myInfo.getToken());
            return Response.status(200).entity(myInfo).build();
        } else {
            logger.error("User with email {} failed to login", email);
            return Response.status(404).entity("user not found").build();
        }
    }
    @POST
    @Path("/logout")
    @Produces("application/json")
    public Response logout(@HeaderParam("token") String token,@Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with token {} and IP {} is trying to logout", token,ipAddress);
        if(userBean.logout(token)) {
            logger.info("User with token {} and IP {} logged out successfully", token,ipAddress);
            return Response.status(200).entity("logout successful").build();
        } else {
            logger.error("User with token {} and IP {} failed to logout", token, ipAddress);
            return Response.status(404).entity("user not found").build();
        }
    }
    @GET
    @Path("")
    @Produces("application/json")
    public Response findAllUsers(@HeaderParam("token") String token, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with token {} and IP {} is trying to get all users", token,ipAddress);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with token {} and IP {} failed to get all users", token, ipAddress);
            return Response.status(404).entity("user not found").build();
        }
        logger.info("User with token {} and IP {} got all users successfully", token, ipAddress);
        return Response.status(200).entity(userBean.findAllUsers()).build();
    }
    @POST
    @Path("/register")
    @Produces("application/json")
    public Response register(@HeaderParam("email") String email, @HeaderParam("password") String password, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to register", ipAddress);
        if(userBean.findUserByEmail(email) != null) {
            logger.error("User with IP address {} failed to register with {} email", ipAddress, email);
            return Response.status(404).entity("email already in use").build();
        }
        if(!userBean.isPasswordValid(password)  || !userBean.isEmailValid(email)) {
            logger.error("User with IP address {} failed to register with invalid password", ipAddress);
            return Response.status(401).entity("invalid credentials").build();
        }
        if(userBean.register(email, password)){
            logger.info("User with IP address {} registered successfully", ipAddress);
            return Response.status(200).entity("user registered").build();
        }else {
            logger.error("User with IP address {} failed to register", ipAddress);
            return Response.status(400).entity("Something went wrong").build();
        }

    }
    @POST
    @Path("/confirm")
    @Produces("application/json")
    public Response confirm(@HeaderParam("token") String token, UserConfirmation userConfirmation, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to confirm", ipAddress, token);
        UserEntity user = userBean.confirmUser(token,userConfirmation);
        if(user == null) {
            logger.error("User with IP address {} and token {} failed to confirm", ipAddress, token);
            return Response.status(404).entity("user not found").build();
        }
        logger.info("User {} {} with IP address {} and token {} confirmed successfully", user.getFirstName(),user.getLastName(),ipAddress, token);
        return Response.status(200).entity(userBean.firstLogin(user)).build();
    }
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response findUserById(@HeaderParam("token") String token, @PathParam("id") int id, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to find user by id {}", ipAddress, token, id);
        if(userBean.findUserByToken(token) == null && !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} failed to find user by id {}", ipAddress, token, id);
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        UserDto user = userBean.findUserDtoById(id);
        if(user == null) {
            logger.error("User with IP address {} and token {} failed to find user by id {}", ipAddress, token, id);
            return Response.status(404).entity("user not found").build();
        }
        logger.info("User with IP address {} and token {} found user by id {}", ipAddress, token, id);
        return Response.status(200).entity(user).build();
    }
    @PUT
    @Path("/{id}")
    @Produces("application/json")
    public Response updateUser(@HeaderParam("token") String token, @PathParam("id") int id, UserDto userDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to update user by id {}", ipAddress, token, id);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} failed to update user by id {}", ipAddress, token, id);
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        if(userBean.updateUser(id, userDto)) {
            logger.info("User with IP address {} and token {} updated user by id {}", ipAddress, token, id);
            return Response.status(200).entity("user updated").build();
        } else {
            logger.error("User with IP address {} and token {} failed to update user by id {}", ipAddress, token, id);
            return Response.status(404).entity("user not found").build();
        }
    }
    @PUT
    @Path("/resetPassword")
    @Produces("application/json")
    public Response resetPassword(@HeaderParam("email") String email, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to reset password", ipAddress);
        UserEntity user = userBean.findUserByEmail(email);
        if(user == null) {
            logger.error("User with IP address {} and {} email failed to reset password because the email was invalid", ipAddress,email);
            return Response.status(404).entity("user not found").build();
        }
        boolean sent = userBean.resetPassword(user);
        if(!sent) {
            logger.error("User with IP address {} failed to send reset password to {}", ipAddress,email);
            return Response.status(404).entity("email not sent").build();
        }
        logger.info("User with IP address {} sent reset password to {}", ipAddress,email);
        return Response.status(200).entity("password reset").build();
    }
    @PUT
    @Path("/confirmPasswordReset/{token}")
    @Produces("application/json")
    public Response confirmPasswordReset(@PathParam("token") String token, PasswordDto passwordDto, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} is trying to confirm password reset", ipAddress);
        if(!userBean.isPasswordValid(passwordDto.getPassword())) {
            logger.error("User with IP address {} failed to confirm password reset because the password was invalid", ipAddress);
            return Response .status(406).entity("invalid password").build();
        }
        if(!passwordDto.getConfirmPassword().equals(passwordDto.getPassword())) {
            logger.error("User with IP address {} failed to confirm password reset because the passwords do not match", ipAddress);
            return Response.status(409).entity("passwords do not match").build();
        }
        boolean reset = userBean.confirmPasswordReset(token, passwordDto);
        if(!reset) {
            logger.error("User with IP address {} failed to confirm password reset", ipAddress);
            return Response.status(404).entity("user not found").build();
        }
        logger.info("User with IP address {} confirmed password reset", ipAddress);
        return Response.status(200).entity("password reset").build();
    }
    @PUT
    @Path("/setPrivacy")
    @Produces("application/json")
    public Response setPrivacy(@HeaderParam("token") String token, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to set privacy", ipAddress,token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} failed to set privacy", ipAddress,token);
            return Response.status(404).entity("user not found").build();
        }
        userBean.setLastActivity(token);
        if(userBean.setPrivate(token)) {
            logger.info("User with IP address {} an token {} set privacy", ipAddress,token);
            return Response.status(200).entity("privacy set").build();
        } else {
            logger.error("User with IP address {} and token {} failed to set privacy", ipAddress,token);
            return Response.status(404).entity("user not found").build();
        }
    }
    @PUT
    @Path("/adminStatus")
    @Produces("application/json")
    public Response setAdminStatus(@HeaderParam("token") String token, @HeaderParam("userId") int id, @Context HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        logger.info("User with IP address {} and token {} is trying to set admin status", ipAddress,token);
        if(userBean.findUserByToken(token) == null || !tokenBean.isTokenValid(token)) {
            logger.error("User with IP address {} and token {} failed to set admin status", ipAddress,token);
            return Response.status(404).entity("user not found").build();
        }
        if(userBean.findUserByToken(token).getRole().getValue() > 2) {
            logger.error("User with IP address {} and token {} failed to set admin status because they are not a Manager", ipAddress,token);
            return Response.status(403).entity("not allowed").build();
        }
        userBean.setLastActivity(token);
        if(userBean.setAdminStatus(token,id)) {
            logger.info("User with IP address {} and token {} set admin status", ipAddress,token);
            return Response.status(200).entity("admin status set").build();
        } else {
            logger.error("User with IP address {} and token {} failed to set admin status", ipAddress,token);
            return Response.status(404).entity("user not found").build();
        }
    }
}
