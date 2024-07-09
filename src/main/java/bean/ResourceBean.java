package bean;

import dao.ProjectResourceDao;
import dao.ResourceDao;
import dto.ResourceDto;
import dto.ResourceStatisticsDto;
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
    @EJB
    ProjectBean projectBean;

    public ResourceBean() {
    }

    public void createDefaultResources() {
        if(resourceDao.findResourceByIdentifier("CPU-001") == null) {
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
        if(resourceDao.findResourceByIdentifier("RAM-001") == null) {
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
         if(resourceDao.findResourceByIdentifier("O365-001") == null) {
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
        if(resourceDao.findResourceByIdentifier("W10-001") == null) {
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
        if(resourceDao.findResourceByName("MAC-001") == null) {
            ResourceEntity resource5 = new ResourceEntity();
            resource5.setName("MacOS License");
            resource5.setStock(50);
            resource5.setDescription("MacOS License");
            resource5.setIdentifier("MAC-001");
            resource5.setSupplier("Apple");
            resource5.setSupplierContact("21345667788");
            resource5.setType(ResourceEntity.ResourceType.RESOURCE);
            resource5.setBrand("Apple");
            resourceDao.persist(resource5);
        }
        if(resourceDao.findResourceByIdentifier("MM-001") == null) {
            ResourceEntity resource6 = new ResourceEntity();
            resource6.setName("Magic Mouse");
            resource6.setStock(50);
            resource6.setDescription("Magic Mouse");
            resource6.setIdentifier("MM-001");
            resource6.setSupplier("Apple");
            resource6.setSupplierContact("21345667788");
            resource6.setType(ResourceEntity.ResourceType.COMPONENT);
            resource6.setBrand("Apple");
            resourceDao.persist(resource6);
        }
        if(resourceDao.findResourceByIdentifier("MK-001") == null) {
            ResourceEntity resource7 = new ResourceEntity();
            resource7.setName("Magic Keyboard");
            resource7.setStock(50);
            resource7.setDescription("Magic Keyboard");
            resource7.setIdentifier("MK-001");
            resource7.setSupplier("Apple");
            resource7.setSupplierContact("21345667788");
            resource7.setType(ResourceEntity.ResourceType.COMPONENT);
            resource7.setBrand("Apple");
            resourceDao.persist(resource7);
        }
        if(resourceDao.findResourceByIdentifier("RP15-001") == null) {
            ResourceEntity resource8 = new ResourceEntity();
            resource8.setName("Razor Pro 15");
            resource8.setStock(50);
            resource8.setDescription("Razor Pro 15");
            resource8.setIdentifier("RP15-001");
            resource8.setSupplier("Razor");
            resource8.setSupplierContact("21345667788");
            resource8.setType(ResourceEntity.ResourceType.COMPONENT);
            resource8.setBrand("Razor");
            resourceDao.persist(resource8);
        }
        if(resourceDao.findResourceByIdentifier("RP17-001") == null) {
            ResourceEntity resource9 = new ResourceEntity();
            resource9.setName("Razor Pro 17");
            resource9.setStock(50);
            resource9.setDescription("Razor Pro 17");
            resource9.setIdentifier("RP17-001");
            resource9.setSupplier("Razor");
            resource9.setSupplierContact("21345667788");
            resource9.setType(ResourceEntity.ResourceType.COMPONENT);
            resource9.setBrand("Razor");
            resourceDao.persist(resource9);
        }
        if(resourceDao.findResourceByIdentifier("RP13-001") == null) {
            ResourceEntity resource10 = new ResourceEntity();
            resource10.setName("Razor Pro 13");
            resource10.setStock(50);
            resource10.setDescription("Razor Pro 13");
            resource10.setIdentifier("RP13-001");
            resource10.setSupplier("Razor");
            resource10.setSupplierContact("21345667788");
            resource10.setType(ResourceEntity.ResourceType.COMPONENT);
            resource10.setBrand("Razor");
            resourceDao.persist(resource10);
        }
        if(resourceDao.findResourceByIdentifier("A15-001") == null) {
            ResourceEntity resource11 = new ResourceEntity();
            resource11.setName("Asus 15");
            resource11.setStock(50);
            resource11.setDescription("Asus 15");
            resource11.setIdentifier("A15-001");
            resource11.setSupplier("Asus");
            resource11.setSupplierContact("21345667788");
            resource11.setType(ResourceEntity.ResourceType.COMPONENT);
            resource11.setBrand("Asus");
            resourceDao.persist(resource11);
        }
        if(resourceDao.findResourceByIdentifier("A17-001") == null) {
            ResourceEntity resource12 = new ResourceEntity();
            resource12.setName("Asus 17");
            resource12.setStock(50);
            resource12.setDescription("Asus 17");
            resource12.setIdentifier("A17-001");
            resource12.setSupplier("Asus");
            resource12.setSupplierContact("21345667788");
            resource12.setType(ResourceEntity.ResourceType.COMPONENT);
            resource12.setBrand("Asus");
            resourceDao.persist(resource12);
        }
        if(resourceDao.findResourceByIdentifier("A13-001") == null) {
            ResourceEntity resource13 = new ResourceEntity();
            resource13.setName("Asus 13");
            resource13.setStock(50);
            resource13.setDescription("Asus 13");
            resource13.setIdentifier("A13-001");
            resource13.setSupplier("Asus");
            resource13.setSupplierContact("21345667788");
            resource13.setType(ResourceEntity.ResourceType.COMPONENT);
            resource13.setBrand("Asus");
            resourceDao.persist(resource13);
        }
        if(resourceDao.findResourceByIdentifier("D15-001") == null) {
            ResourceEntity resource14 = new ResourceEntity();
            resource14.setName("Dell 15");
            resource14.setStock(50);
            resource14.setDescription("Dell 15");
            resource14.setIdentifier("D15-001");
            resource14.setSupplier("Dell");
            resource14.setSupplierContact("21345667788");
            resource14.setType(ResourceEntity.ResourceType.COMPONENT);
            resource14.setBrand("Dell");
            resourceDao.persist(resource14);
        }
        if(resourceDao.findResourceByIdentifier("D17-001") == null) {
            ResourceEntity resource15 = new ResourceEntity();
            resource15.setName("Deathstar 17");
            resource15.setStock(50);
            resource15.setDescription("Deathstar 17");
            resource15.setIdentifier("D17-001");
            resource15.setSupplier("Empire");
            resource15.setSupplierContact("21345667788");
            resource15.setType(ResourceEntity.ResourceType.COMPONENT);
            resource15.setBrand("Deathstar");
            resourceDao.persist(resource15);
        }
        if(resourceDao.findResourceByIdentifier("L12-001") == null) {
            ResourceEntity resource16 = new ResourceEntity();
            resource16.setName("Lightsaber 12");
            resource16.setStock(50);
            resource16.setDescription("Lightsaber 12");
            resource16.setIdentifier("L12-001");
            resource16.setSupplier("Jedi");
            resource16.setSupplierContact("21345667788");
            resource16.setType(ResourceEntity.ResourceType.COMPONENT);
            resource16.setBrand("Jedi");
            resourceDao.persist(resource16);
        }
        if(resourceDao.findResourceByIdentifier("L15-001") == null) {
            ResourceEntity resource17 = new ResourceEntity();
            resource17.setName("Lightsaber 15 Red");
            resource17.setStock(50);
            resource17.setDescription("Lightsaber 15 Red");
            resource17.setIdentifier("L15-001");
            resource17.setSupplier("Sith");
            resource17.setSupplierContact("21345667788");
            resource17.setType(ResourceEntity.ResourceType.COMPONENT);
            resource17.setBrand("Sith");
            resourceDao.persist(resource17);
        }
        if(resourceDao.findResourceByIdentifier("L15-002") == null) {
            ResourceEntity resource18 = new ResourceEntity();
            resource18.setName("Lightsaber 15 Blue");
            resource18.setStock(50);
            resource18.setDescription("Lightsaber 15 Blue");
            resource18.setIdentifier("L15-002");
            resource18.setSupplier("Sith");
            resource18.setSupplierContact("21345667788");
            resource18.setType(ResourceEntity.ResourceType.COMPONENT);
            resource18.setBrand("Sith");
            resourceDao.persist(resource18);
        }
        if(resourceDao.findResourceByIdentifier("MF-001")== null) {
            ResourceEntity resource19 = new ResourceEntity();
            resource19.setName("Mandalorian Helmet");
            resource19.setStock(50);
            resource19.setDescription("Mandalorian Helmet");
            resource19.setIdentifier("MF-001");
            resource19.setSupplier("Mandalorian");
            resource19.setSupplierContact("21345667788");
            resource19.setType(ResourceEntity.ResourceType.COMPONENT);
            resource19.setBrand("Mandalorian");
            resourceDao.persist(resource19);
        }
        if(resourceDao.findResourceByIdentifier("MF-002")== null) {
            ResourceEntity resource20 = new ResourceEntity();
            resource20.setName("Mandalorian Armor");
            resource20.setStock(50);
            resource20.setDescription("Mandalorian Armor");
            resource20.setIdentifier("MF-002");
            resource20.setSupplier("Mandalorian");
            resource20.setSupplierContact("21345667788");
            resource20.setType(ResourceEntity.ResourceType.COMPONENT);
            resource20.setBrand("Mandalorian");
            resourceDao.persist(resource20);
        }
        if(resourceDao.findResourceByIdentifier("MF-003")== null) {
            ResourceEntity resource21 = new ResourceEntity();
            resource21.setName("Mandalorian Blaster");
            resource21.setStock(50);
            resource21.setDescription("Mandalorian Blaster");
            resource21.setIdentifier("MF-003");
            resource21.setSupplier("Mandalorian");
            resource21.setSupplierContact("21345667788");
            resource21.setType(ResourceEntity.ResourceType.COMPONENT);
            resource21.setBrand("Mandalorian");
            resourceDao.persist(resource21);
        }
        if(resourceDao.findResourceByIdentifier("MF-004")== null) {
            ResourceEntity resource22 = new ResourceEntity();
            resource22.setName("Mandalorian Jetpack");
            resource22.setStock(50);
            resource22.setDescription("Mandalorian Jetpack");
            resource22.setIdentifier("MF-004");
            resource22.setSupplier("Mandalorian");
            resource22.setSupplierContact("21345667788");
            resource22.setType(ResourceEntity.ResourceType.COMPONENT);
            resource22.setBrand("Mandalorian");
            resourceDao.persist(resource22);
        }
        if(resourceDao.findResourceByIdentifier("YT-1700") == null) {
            ResourceEntity resource23 = new ResourceEntity();
            resource23.setName("YT-1700");
            resource23.setStock(50);
            resource23.setDescription("YT-1700");
            resource23.setIdentifier("YT-1700");
            resource23.setSupplier("Millenium Falcon");
            resource23.setSupplierContact("21345667788");
            resource23.setType(ResourceEntity.ResourceType.COMPONENT);
            resource23.setBrand("Millenium Falcon");
            resourceDao.persist(resource23);
        }
        if(resourceDao.findResourceByIdentifier("XW-001") == null) {
            ResourceEntity resource24 = new ResourceEntity();
            resource24.setName("X-Wing");
            resource24.setStock(50);
            resource24.setDescription("X-Wing");
            resource24.setIdentifier("XW-001");
            resource24.setSupplier("Rebel Alliance");
            resource24.setSupplierContact("21345667788");
            resource24.setType(ResourceEntity.ResourceType.COMPONENT);
            resource24.setBrand("Rebel Alliance");
            resourceDao.persist(resource24);
        }
        if(resourceDao.findResourceByIdentifier("TIE-001") == null) {
            ResourceEntity resource25 = new ResourceEntity();
            resource25.setName("TIE Fighter");
            resource25.setStock(50);
            resource25.setDescription("TIE Fighter");
            resource25.setIdentifier("TIE-001");
            resource25.setSupplier("Empire");
            resource25.setSupplierContact("21345667788");
            resource25.setType(ResourceEntity.ResourceType.COMPONENT);
            resource25.setBrand("Empire");
            resourceDao.persist(resource25);
        }
        if(resourceDao.findResourceByIdentifier("DS-001") == null) {
            ResourceEntity resource26 = new ResourceEntity();
            resource26.setName("Death Star");
            resource26.setStock(1);
            resource26.setDescription("Death Star");
            resource26.setIdentifier("DS-001");
            resource26.setSupplier("Empire");
            resource26.setSupplierContact("21345667788");
            resource26.setType(ResourceEntity.ResourceType.COMPONENT);
            resource26.setBrand("Empire");
            resourceDao.persist(resource26);
        }
        if(resourceDao.findResourceByIdentifier("NB-001") == null){
            ResourceEntity resource27 = new ResourceEntity();
            resource27.setName("Naboo Starfighter");
            resource27.setStock(50);
            resource27.setDescription("Naboo Starfighter");
            resource27.setIdentifier("NB-001");
            resource27.setSupplier("Naboo");
            resource27.setSupplierContact("21345667788");
            resource27.setType(ResourceEntity.ResourceType.COMPONENT);
            resource27.setBrand("Naboo");
            resourceDao.persist(resource27);
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
    public ResourceStatisticsDto getResourceStatistics() {
    ResourceStatisticsDto resourceStatisticsDto = new ResourceStatisticsDto();
    resourceStatisticsDto.setResourceQuantityPerLab(projectBean.getResourceQuantitiesByLab());
    resourceStatisticsDto.setResourceQuantityPerProject(projectBean.resourceQuantitiesByProject());
    resourceStatisticsDto.setAllresources(projectBean.findResourceQuantities());
    return resourceStatisticsDto;
    }
}
