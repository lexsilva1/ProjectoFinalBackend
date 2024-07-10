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


public void createDefaultSystemVariables() {
    if (systemVariablesDao.findSystemVariableById(1) == null) {
        systemVariablesDao.create(1, 4);
    }
}
public void setSessionTimeout(int timeout) {
    SystemVariablesEntity timeoutVariable = systemVariablesDao.findSystemVariableById(60);
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
public SystemVariablesDto getSystemVariables() {
    SystemVariablesEntity systemVariablesEntity = systemVariablesDao.findSystemVariableById(1);
    SystemVariablesDto systemVariablesDto = new SystemVariablesDto();
    systemVariablesDto.setTimeout(systemVariablesEntity.getTimeout());
    systemVariablesDto.setMaxUsers(systemVariablesEntity.getMaxUsers());
    return systemVariablesDto;
}
}
