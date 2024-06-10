package dao;

import entities.ProjectEntity;
import entities.ProjectUserEntity;
import entities.SkillEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProjectDao extends AbstractDao<ProjectEntity> {
    @PersistenceContext
    private EntityManager em;
    private static final long serialVersionUID = 1L;
    ProjectDao() {
        super(ProjectEntity.class);
    }
    public ProjectEntity findProjectByName(String name) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectByName").setParameter("name", name)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public List<ProjectEntity> findProjectsByLab(ProjectEntity lab) {
        try {
            return em.createNamedQuery("ProjectEntity.findProjectsByLab").setParameter("lab", lab)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public ProjectEntity findProjectBySkill(ProjectEntity skills) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectBySkill").setParameter("skills", skills)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public ProjectEntity findProjectByInterest(ProjectEntity interests) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectByInterest").setParameter("interests", interests)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public ProjectEntity findProjectByStatus(ProjectEntity status) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectByStatus").setParameter("status", status)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public List<ProjectEntity> findAllProjects() {
        try {
            return em.createNamedQuery("ProjectEntity.findAllProjects")
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public List<SkillEntity> findProjectSkills(ProjectEntity project) {
        try {
            return em.createNamedQuery("ProjectEntity.findProjectSkills").setParameter("project", project)
                    .getResultList();

        } catch (Exception e) {
            return null;
        }
    }
    public List<ProjectEntity> findProjects(String projectName, String projectLab, String projectSkill, String projectInterest, int projectStatus, int projectUser) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ProjectEntity> cq = cb.createQuery(ProjectEntity.class);
        Root<ProjectEntity> project = cq.from(ProjectEntity.class);
        List<Predicate> predicates = new ArrayList<>();

        if (projectName != null) {
            predicates.add(cb.equal(project.get("name"), projectName));
        }
        if (projectLab != null) {
            predicates.add(cb.equal(project.get("lab"), projectLab));
        }
        if (projectSkill != null) {
            Join<ProjectEntity, SkillEntity> skillJoin = project.join("skills");
            predicates.add(cb.equal(skillJoin.get("name"), projectSkill));
        }
        if (projectInterest != null) {
            Join<ProjectEntity, SkillEntity> interestJoin = project.join("interests");
            predicates.add(cb.equal(interestJoin.get("name"), projectInterest));
        }
        if (projectStatus != 0) {
            predicates.add(cb.equal(project.get("status"), projectStatus));
        }
        if (projectUser != 0) {
            predicates.add(cb.equal(project.get("creator"), projectUser));
        }


        cq.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(cq).getResultList();
    }
    public ProjectEntity findProjectById(int id) {
        try {
            return (ProjectEntity) em.createNamedQuery("ProjectEntity.findProjectById").setParameter("id", id)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
}
