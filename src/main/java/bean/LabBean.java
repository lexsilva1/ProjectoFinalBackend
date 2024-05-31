package bean;

import dao.LabDao;
import dto.LabDto;
import entities.LabEntity;
import jakarta.ejb.Singleton;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class LabBean {
@Inject
private LabDao labDao;
    public LabBean() {
    }

    public void createDefaultLocations() {
        // Create default locations
        if(labDao.findLabByLocation(LabEntity.Lab.LISBOA) == null) {
            LabEntity lab1 = new LabEntity();
            lab1.setLocation(LabEntity.Lab.LISBOA);
            labDao.persist(lab1);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.COIMBRA) == null) {

            LabEntity lab2 = new LabEntity();
            lab2.setLocation(LabEntity.Lab.COIMBRA);
            labDao.persist(lab2);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.PORTO) == null) {
            LabEntity lab3 = new LabEntity();
            lab3.setLocation(LabEntity.Lab.PORTO);
            labDao.persist(lab3);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.TOMAR) == null) {
            LabEntity lab4 = new LabEntity();
            lab4.setLocation(LabEntity.Lab.TOMAR);
            labDao.persist(lab4);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.VISEU) == null) {
            LabEntity lab5 = new LabEntity();
            lab5.setLocation(LabEntity.Lab.VISEU);
            labDao.persist(lab5);
        }
        if(labDao.findLabByLocation(LabEntity.Lab.VILA_REAL) == null) {
            LabEntity lab6 = new LabEntity();
            lab6.setLocation(LabEntity.Lab.VILA_REAL);
            labDao.persist(lab6);
        }

    }
    public List<LabDto> findAllLabs() {
        List<LabEntity> labEntities = labDao.findAllLabs();
        List<LabDto> labDtos = new ArrayList<>();
        for(LabEntity lab : labEntities) {
            labDtos.add(convertToDto(lab.getLocation()));
        }
        return labDtos;
    }
    public LabDto convertToDto(LabEntity.Lab labEntity) {
        LabDto labDto = new LabDto();
        labDto.setLocation(labEntity.name());
        return labDto;
    }
}

