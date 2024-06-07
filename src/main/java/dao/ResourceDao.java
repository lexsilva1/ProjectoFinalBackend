package dao;
import entities.ResourceEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.List;

@Stateless
public class ResourceDao extends AbstractDao<ResourceEntity>{
@PersistenceContext
private EntityManager em;
    private static final long serialVersionUID = 1L;
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

    public List<ResourceEntity> findAllResources(String name, String identifier, String supplier, String type) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ResourceEntity> cq = cb.createQuery(ResourceEntity.class);
        if(name != null && !name.isEmpty())
            cq.where(cb.equal(cq.from(ResourceEntity.class).get("name"), name));
        if(identifier != null && !identifier.isEmpty())
            cq.where(cb.equal(cq.from(ResourceEntity.class).get("identifier"), identifier));
        if(supplier != null && !supplier.isEmpty())
            cq.where(cb.equal(cq.from(ResourceEntity.class).get("supplier"), supplier));
        if(type != null && !type.isEmpty()) {
            ResourceEntity.ResourceType  resourceType = ResourceEntity.ResourceType.valueOf(type);
            cq.where(cb.equal(cq.from(ResourceEntity.class).get("type"), resourceType));
        }
        if(name == null && identifier == null && supplier == null && type == null)
            cq.select(cq.from(ResourceEntity.class));

        return em.createQuery(cq).getResultList();
    }



    }


