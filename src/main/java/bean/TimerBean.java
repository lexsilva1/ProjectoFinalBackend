package bean;

import dao.TimeOutDao;

import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.*;

@Singleton
public class TimerBean {

    @EJB
    //private UserDao userDao;


    @Inject
    TimeOutDao timeOutDao;

   // private UserBean userBean;
    //private Gson gson = new Gson();
    //private static final Logger logger = LogManager.getLogger(TimerBean.class);
   // @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)

   /* public void checkTimeouts() {
        logger.info("Checking for timeouts...");
        HashMap<String, LocalDateTime> tokensAndTimeouts = userDao.findAllTokensAndTimeouts();
        TimeOutEntity timeOutEntity = timeOutDao.findTimeOutById(1);
        int timeoutLimit = timeOutEntity.getTimeout();

        for (String token : tokensAndTimeouts.keySet()) {
            LocalDateTime lastAction = tokensAndTimeouts.get(token);
            if (lastAction == null) {
                continue; // Skip this iteration if lastAction is null
            }
            LocalDateTime now = LocalDateTime.now();
            if (Duration.between(lastAction, now).getSeconds() > timeoutLimit) {
                LogOutNotification notificationDto = new LogOutNotification();
                userBean.forcedLogout(token);
                logger.info("User with token " + token + " has been logged out due to inactivity.");
                notifier.send(token, gson.toJson(notificationDto));
                logger.info("Sent logout notification to user with token " + token);
            }
        }
    }
    public void createTimeout(int timeout) {
        logger.info("Creating timeout...");
        if(timeOutDao.findTimeOutById(1) == null) {
            TimeOutEntity timeOutEntity = new TimeOutEntity();
            timeOutEntity.setTimeout(timeout);
            timeOutDao.createTimeOut(timeOutEntity);
        }

    }*/
}
