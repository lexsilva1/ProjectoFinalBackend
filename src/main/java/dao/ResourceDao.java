package dao;
import entities.ResourceEntity;
import jakarta.ejb.Stateless;

@Stateless
public class ResourceDao extends AbstractDao<ResourceEntity>{

    public ResourceDao() {
        super(ResourceEntity.class);
    }

public ResourceEntity findResourceByName(String name) {
        try {
            return (ResourceEntity) em.createNamedQuery("Resource.findResourceByName").setParameter("name", name)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
    public ResourceEntity findResourceByType(ResourceEntity.ResourceType type) {
        try {
            return (ResourceEntity) em.createNamedQuery("Resource.findResourceByType").setParameter("type", type)
                    .getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}
