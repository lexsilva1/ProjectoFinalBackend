package bean;

import dao.ResourceDao;
import entities.ResourceEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

@Stateless
public class ResourceBean {
    @EJB
    ResourceDao resourceDao;

    public ResourceBean() {
    }

    public void createDefaultResources() {
        if(resourceDao.findResourceByName("CPU") == null) {


            ResourceEntity resource1 = new ResourceEntity();
            resource1.setName("CPU");
            resource1.setStock(100);
            resource1.setDescription("Central Processing Unit");
            resource1.setIdentifier("CPU-001");
            resource1.setSupplier("Intel");
            resource1.setSupplierContact("customersupport@intel.com");
            resource1.setType(ResourceEntity.ResourceType.COMPONENT);
            resourceDao.persist(resource1);
        }
        if(resourceDao.findResourceByName("RAM") == null) {
            ResourceEntity resource2 = new ResourceEntity();
            resource2.setName("RAM");
            resource2.setStock(200);
            resource2.setDescription("Random Access Memory");
            resource2.setIdentifier("RAM-001");
            resource2.setSupplier("Kingston");
            resource2.setSupplierContact("9123455678");
            resource2.setType(ResourceEntity.ResourceType.COMPONENT);
            resourceDao.persist(resource2);
        }
         if(resourceDao.findResourceByName("Office365 License") == null) {

             ResourceEntity resource3 = new ResourceEntity();
             resource3.setName("Office365 License");
             resource3.setStock(50);
             resource3.setDescription("Office365 License");
             resource3.setIdentifier("O365-001");
             resource3.setSupplier("Microsoft");
             resource3.setSupplierContact("21345667788");
             resource3.setType(ResourceEntity.ResourceType.RESOURCE);
             resourceDao.persist(resource3);
         }
        if(resourceDao.findResourceByName("Windows 10 License") == null) {
            ResourceEntity resource4 = new ResourceEntity();
            resource4.setName("Windows 10 License");
            resource4.setStock(50);
            resource4.setDescription("Windows 10 License");
            resource4.setIdentifier("W10-001");
            resource4.setSupplier("Microsoft");
            resource4.setSupplierContact("21345667788");
            resource4.setType(ResourceEntity.ResourceType.RESOURCE);
            resourceDao.persist(resource4);
        }

    }
}
