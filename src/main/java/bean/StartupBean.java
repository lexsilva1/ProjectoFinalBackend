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


    @PostConstruct
    public void init() {
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
