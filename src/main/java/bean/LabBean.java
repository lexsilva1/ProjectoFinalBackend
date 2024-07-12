package bean;

import dao.LabDao;
import dto.LabDto;
import entities.LabEntity;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class LabBean {
@Inject
private LabDao labDao;
private static final org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager.getLogger(LabBean.class);
    public LabBean() {
    }

    public void createDefaultLocations() {
        // Create default locations
        logger.info("Creating default locations");
        if(labDao.findLabByLocation(LabEntity.Lab.Lisboa) == null) {
            LabEntity lab1 = new LabEntity();
            lab1.setLocation(LabEntity.Lab.Lisboa);
            labDao.persist(lab1);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.Coimbra) == null) {

            LabEntity lab2 = new LabEntity();
            lab2.setLocation(LabEntity.Lab.Coimbra);
            labDao.persist(lab2);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.Porto) == null) {
            LabEntity lab3 = new LabEntity();
            lab3.setLocation(LabEntity.Lab.Porto);
            labDao.persist(lab3);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.Tomar) == null) {
            LabEntity lab4 = new LabEntity();
            lab4.setLocation(LabEntity.Lab.Tomar);
            labDao.persist(lab4);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.Viseu) == null) {
            LabEntity lab5 = new LabEntity();
            lab5.setLocation(LabEntity.Lab.Viseu);
            labDao.persist(lab5);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.Vila_Real) == null) {
            LabEntity lab6 = new LabEntity();
            lab6.setLocation(LabEntity.Lab.Vila_Real);
            labDao.persist(lab6);
        }

    }
    public List<LabDto> findAllLabs() {
        logger.info("Finding all labs");
        List<LabEntity> labEntities = labDao.findAllLabs();
        List<LabDto> labDtos = new ArrayList<>();
        for(LabEntity lab : labEntities) {
            labDtos.add(convertToDto(lab.getLocation()));
        }
        logger.info("All labs found successfully");
        return labDtos;
    }
    public LabDto convertToDto(LabEntity.Lab labEntity) {
        logger.info("Converting lab to dto");
        LabDto labDto = new LabDto();
        labDto.setLocation(labEntity.name());
        logger.info("Lab converted to dto successfully");
        return labDto;
    }
}

