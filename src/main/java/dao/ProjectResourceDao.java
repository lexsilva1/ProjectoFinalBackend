package dao;

import jakarta.ejb.Stateless;
import entities.ProjectResourceEntity;

@Stateless
public class ProjectResourceDao extends AbstractDao<ProjectResourceEntity>{
    public ProjectResourceDao() {
        super(ProjectResourceEntity.class);
    }




}
