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
/**
 * The bean class for the startup.
 */
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
    @Inject
    SkillBean skillBean;
    @Inject
    ProjectBean projectBean;
    @Inject
    InterestBean interestBean;
    @Inject
    ResourceBean resourceBean;
    @Inject
    MessageBean messageBean;
    @Inject
    ProjectLogBean projectLogBean;
    @Inject
    SystemVariablesBean systemVariablesBean;
    private static final Logger logger = LogManager.getLogger(StartupBean.class);
/**
     * Initialize the startup bean.
     */
    @PostConstruct
    public void init() {
        logger.info("Creating default data");
        systemVariablesBean.createDefaultSystemVariables();
        labBean.createDefaultLocations();
        skillBean.createDefaultSkills();
        interestBean.createDefaultInterests();
        userBean.createDefaultUsers();
        messageBean.createDefaultMessage();
        resourceBean.createDefaultResources();
        projectBean.createDefaultProjects();
        projectLogBean.createDefaultLogs();

    }
}
