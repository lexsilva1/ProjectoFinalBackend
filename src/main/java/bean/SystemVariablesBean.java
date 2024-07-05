package bean;

import dao.SystemVariablesDao;
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


public void createDefaultSystemVariables() {
    if (systemVariablesDao.findSystemVariableById(1) == null) {
        systemVariablesDao.create(30, 4);
    }
}
public void setSessionTimeout(int timeout) {
    SystemVariablesEntity timeoutVariable = systemVariablesDao.findSystemVariableById(1);
    timeoutVariable.setTimeout(timeout);
    systemVariablesDao.merge(timeoutVariable);
}

public int getSessionTimeout() {
    return systemVariablesDao.findSystemVariableById(1).getTimeout();
}

public boolean setMaxUsers(int maxUsers) {
    if (userBean.getAllUsers().size() > maxUsers) {
        return false;
    }
    SystemVariablesEntity maxUsersVariable = systemVariablesDao.findSystemVariableById(1);
    maxUsersVariable.setMaxUsers(maxUsers);
    systemVariablesDao.merge(maxUsersVariable);
    return true;
}
public int getMaxUsers() {
    return systemVariablesDao.findSystemVariableById(1).getMaxUsers();
}

}
