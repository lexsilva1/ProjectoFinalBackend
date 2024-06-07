package service;

import jakarta.ejb.EJB;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;

public class MessageService {
    @Context
    private HttpServletRequest request;


}
