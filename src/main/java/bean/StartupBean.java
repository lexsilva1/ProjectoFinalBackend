package bean;
import dao.AbstractDao;
import dao.LabDao;
import entities.LabEntity;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.DependsOn;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Singleton
@Startup
@DependsOn({"UserBean", "TimerBean", "LabBean"})
public class StartupBean {

    @Inject
    UserBean userBean;
    @Inject
    TimerBean timerBean;
    @Inject
    LabBean labBean;


    @PostConstruct
    public void init() {
        labBean.createDefaultLocations();
        userBean.createDefaultUsers();
        timerBean.createTimeout(300);
    }
}
