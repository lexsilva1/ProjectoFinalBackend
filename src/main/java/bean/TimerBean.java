package bean;

import dto.ForcedLogoutDto;
import entities.TokenEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.*;
import websocket.Notifications;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class TimerBean {


    @Inject
    private UserBean userBean;
    @Inject
    private Notifications notifier;
    @EJB
    private TokenBean tokenBean;
    @Inject
    SystemVariablesBean systemVariablesBean;

    private Gson gson = new Gson();
    //private static final Logger logger = LogManager.getLogger(TimerBean.class);
    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)

    public void checkTimeouts() {
        //logger.info("Checking for timeouts...");
        System.out.println("Checking for timeouts...");
        List<TokenEntity> timedOutUsers = tokenBean.findTimedOutTokens(LocalDateTime.now().minusMinutes(systemVariablesBean.getSessionTimeout()));
        if(timedOutUsers != null && !timedOutUsers.isEmpty()) {
            for (TokenEntity token : timedOutUsers) {
                UserEntity user = token.getUser();
                userBean.forcedLogout(token);
                ForcedLogoutDto forcedLogoutDto = new ForcedLogoutDto();
                notifier.send(token.getToken(), gson.toJson(forcedLogoutDto));
                System.out.println("User " + user.getEmail() + " has been logged out due to inactivity." + " " + token.getLastActivity() + " at " + LocalDateTime.now());
                user = null;  //nullify user to prevent memory leaks
            }
            timedOutUsers.clear();
        }
    }
}
