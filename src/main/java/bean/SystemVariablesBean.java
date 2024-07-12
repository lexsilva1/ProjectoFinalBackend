package bean;

import dao.SystemVariablesDao;
import dto.SystemVariablesDto;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import entities.SystemVariablesEntity;

@Stateless
public class SystemVariablesBean {
@Inject
private SystemVariablesDao systemVariablesDao;
@EJB
ProjectBean projectBean;
@EJB
UserBean userBean;
private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(SystemVariablesBean.class);

public void createDefaultSystemVariables() {
    logger.info("Creating default system variables");
    if (systemVariablesDao.findSystemVariableById(1) == null) {
        systemVariablesDao.create(30, 4);

    }
}
public void setSessionTimeout(int timeout) {
    logger.info("Setting session timeout to " + timeout);
    SystemVariablesEntity timeoutVariable = systemVariablesDao.findSystemVariableById(1);
    timeoutVariable.setTimeout(timeout);
    logger.info("Timeout set to " + timeoutVariable.getTimeout());
    systemVariablesDao.merge(timeoutVariable);
}

public int getSessionTimeout() {
    return systemVariablesDao.findSystemVariableById(1).getTimeout();
}

public boolean setMaxUsers(int maxUsers) {
    logger.info("Setting max users to " + maxUsers);
    if (maxUsers > userBean.getAllUsers().size()) {
        logger.error("Max users cannot be more than the current number of users");
        return false;
    }
    SystemVariablesEntity maxUsersVariable = systemVariablesDao.findSystemVariableById(1);
    maxUsersVariable.setMaxUsers(maxUsers);
    systemVariablesDao.merge(maxUsersVariable);
    logger.info("Max users set to " + maxUsersVariable.getMaxUsers());
    return true;
}
public int getMaxUsers() {
     return systemVariablesDao.findSystemVariableById(1).getMaxUsers();
}
public SystemVariablesDto getSystemVariables() {

    SystemVariablesEntity systemVariablesEntity = systemVariablesDao.findSystemVariableById(1);
    SystemVariablesDto systemVariablesDto = new SystemVariablesDto();
    systemVariablesDto.setTimeout(systemVariablesEntity.getTimeout());
    systemVariablesDto.setMaxUsers(systemVariablesEntity.getMaxUsers());

    return systemVariablesDto;
}
}
