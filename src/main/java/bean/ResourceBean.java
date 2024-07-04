package bean;

import dao.ProjectResourceDao;
import dao.ResourceDao;
import dto.ResourceDto;
import entities.ResourceEntity;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
public class ResourceBean {
    @EJB
    ResourceDao resourceDao;
    @EJB
    ProjectResourceDao projectResourcedao;

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
            resource1.setBrand("Intel");
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
            resource2.setBrand("Kingston");
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
             resource3.setBrand("Microsoft");
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
            resource4.setBrand("Microsoft");
            resourceDao.persist(resource4);
        }

    }
    public ResourceDto convertToDto(ResourceEntity resourceEntity) {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(resourceEntity.getId());
        resourceDto.setName(resourceEntity.getName());
        resourceDto.setDescription(resourceEntity.getDescription());
        resourceDto.setType(resourceEntity.getType().toString());
        resourceDto.setIdentifier(resourceEntity.getIdentifier());
        resourceDto.setSupplier(resourceEntity.getSupplier());
        resourceDto.setSupplierContact(resourceEntity.getSupplierContact());
        resourceDto.setBrand(resourceEntity.getBrand());
        resourceDto.setStock(resourceEntity.getStock());
        resourceDto.setObservations(resourceEntity.getObservations());
        return resourceDto;
    }


    public List<ResourceDto> findAllResources(String name, String identifier, String supplier, String type) {

        List<ResourceEntity> entities = resourceDao.findAllResources(name, identifier, supplier, type);
        List <ResourceDto> dtos = new ArrayList<>();
        for(ResourceEntity entity: entities) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }
    public ResourceEntity findResourceByName(String name) {
        return resourceDao.findResourceByName(name);
    }
    public void createResource(ResourceDto resourceDto) {
        ResourceEntity resource = new ResourceEntity();
        resource.setName(resourceDto.getName());
        resource.setDescription(resourceDto.getDescription());
        resource.setIdentifier(generateResourceIdentifier(resourceDto));
        resource.setSupplier(resourceDto.getSupplier());
        resource.setSupplierContact(resourceDto.getSupplierContact());
        resource.setBrand(resourceDto.getBrand());
        resource.setStock(resourceDto.getStock());
        resource.setObservations(resourceDto.getObservations());
        resource.setType(ResourceEntity.ResourceType.valueOf(resourceDto.getType()));
        resourceDao.persist(resource);
    }
    public String generateResourceIdentifier(ResourceDto resourceDto) {
        if (resourceDto.getIdentifier() == null || resourceDto.getIdentifier().isEmpty()) {
            String identifier = resourceDto.getName().substring(0, 3).toUpperCase() + "-";
            if (resourceDto.getType().equals("COMPONENT")) {
                identifier += "C";
            } else if (resourceDto.getType().equals("RESOURCE")) {
                identifier += "R";
            }
            identifier += "-" + resourceDao.countResources();
            return identifier;
        }

        return resourceDto.getIdentifier();
    }
    public ResourceEntity findResourcebyIdentifier(String identifier) {
        return resourceDao.findResourceByIdentifier(identifier);
    }
    public ResourceEntity findResourceByNameAndSupplier(String name, String supplier) {
        return resourceDao.findResourceByNameAndSupplier(name, supplier);
    }

    public ResourceDto findResourceById(int id) {
        return convertToDto(resourceDao.findResourceById(id));
    }
}
