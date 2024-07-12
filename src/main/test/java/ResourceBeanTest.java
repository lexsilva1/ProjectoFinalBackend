import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import bean.ProjectBean;
import bean.ResourceBean;
import dto.ResourceDto;
import entities.ResourceEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dto.ResourceStatisticsDto;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.Collections;
import dao.ResourceDao;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
public class ResourceBeanTest {

    private ResourceBean resourceBean;
    private ResourceEntity sampleResourceEntity;
    private ResourceDto sampleResourceDto;
    private ResourceDao resourceDao;
    private ProjectBean projectBean;

    @BeforeEach
    void setUp() {
        resourceBean = new ResourceBean();
        sampleResourceEntity = new ResourceEntity();
        sampleResourceEntity.setId(1);
        sampleResourceEntity.setName("Test Resource");
        sampleResourceEntity.setDescription("Test Description");
        sampleResourceEntity.setType(ResourceEntity.ResourceType.COMPONENT);
        sampleResourceEntity.setIdentifier("TR-001");
        sampleResourceEntity.setSupplier("Test Supplier");
        sampleResourceEntity.setSupplierContact("Test Contact");
        sampleResourceEntity.setBrand("Test Brand");
        sampleResourceEntity.setStock(100);
        sampleResourceEntity.setObservations("Test Observations");
    }

    @Test
    void testConvertToDto_AllFields() {
        ResourceDto resultDto = resourceBean.convertToDto(sampleResourceEntity);
        assertEquals(sampleResourceEntity.getId(), resultDto.getId());
        assertEquals(sampleResourceEntity.getName(), resultDto.getName());
        assertEquals(sampleResourceEntity.getDescription(), resultDto.getDescription());
        assertEquals(sampleResourceEntity.getType().toString(), resultDto.getType());
        assertEquals(sampleResourceEntity.getIdentifier(), resultDto.getIdentifier());
        assertEquals(sampleResourceEntity.getSupplier(), resultDto.getSupplier());
        assertEquals(sampleResourceEntity.getSupplierContact(), resultDto.getSupplierContact());
        assertEquals(sampleResourceEntity.getBrand(), resultDto.getBrand());
        assertEquals(sampleResourceEntity.getStock(), resultDto.getStock());
        assertEquals(sampleResourceEntity.getObservations(), resultDto.getObservations());
    }

    @Test
    void testConvertToDto_OptionalFieldsNull() {
        sampleResourceEntity.setSupplier(null);
        sampleResourceEntity.setSupplierContact(null);
        ResourceDto resultDto = resourceBean.convertToDto(sampleResourceEntity);
        assertNull(resultDto.getSupplier());
        assertNull(resultDto.getSupplierContact());
    }

    @Test
    void testConvertToDto_EmptyStrings() {
        sampleResourceEntity.setName("");
        sampleResourceEntity.setDescription("");
        ResourceDto resultDto = resourceBean.convertToDto(sampleResourceEntity);
        assertEquals("", resultDto.getName());
        assertEquals("", resultDto.getDescription());
    }

    @Test
    void testConvertToDto_SpecialCharacters() {
        sampleResourceEntity.setName("Name with special characters: ñ@#");
        ResourceDto resultDto = resourceBean.convertToDto(sampleResourceEntity);
        assertEquals("Name with special characters: ñ@#", resultDto.getName());
    }

    @Test
    void testConvertToDto_MaximumValues() {
        sampleResourceEntity.setStock(Integer.MAX_VALUE);
        ResourceDto resultDto = resourceBean.convertToDto(sampleResourceEntity);
        assertEquals(Integer.MAX_VALUE, resultDto.getStock());
    }

    @Test
    void testConvertToDto_MinimumValues() {
        sampleResourceEntity.setStock(0);
        ResourceDto resultDto = resourceBean.convertToDto(sampleResourceEntity);
        assertEquals(0, resultDto.getStock());
    }

    @Test
    void testConvertToDto_EnumTypes() {
        sampleResourceEntity.setType(ResourceEntity.ResourceType.RESOURCE);
        ResourceDto resultDto = resourceBean.convertToDto(sampleResourceEntity);
        assertEquals("RESOURCE", resultDto.getType());


    }
}

