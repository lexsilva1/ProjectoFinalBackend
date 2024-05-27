package bean;

import dao.TimeOutDao;
import dao.UserDao;
import entities.TimeOutEntity;
import entities.UserEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.*;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class TimerBean {

    @Inject
    private UserDao userDao;


    @Inject
    TimeOutDao timeOutDao;
    @Inject
    private UserBean userBean;

    //private Gson gson = new Gson();
    //private static final Logger logger = LogManager.getLogger(TimerBean.class);
    @Schedule(hour = "*", minute = "*", second = "*/30", persistent = false)

    public void checkTimeouts() {
        //logger.info("Checking for timeouts...");
        System.out.println("Checking for timeouts...");
        List<UserEntity> timedOutUsers = userBean.findTimedOutUsers();
        for (UserEntity user : timedOutUsers) {
            userBean.forcedLogout(user);
            System.out.println("User " + user.getEmail() + " has been logged out due to inactivity.");
        }

    }
}
