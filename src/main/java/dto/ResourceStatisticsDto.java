package dto;

import java.util.HashMap;

public class ResourceStatisticsDto {
    private HashMap<String,HashMap<String,Integer>> resourceQuantityPerLab;
    private HashMap<String,HashMap<String,Integer>>  resourceQuantityPerProject;


    public ResourceStatisticsDto() {
    }

    public HashMap<String, HashMap<String, Integer>> getResourceQuantityPerLab() {
        return resourceQuantityPerLab;
    }

    public void setResourceQuantityPerLab(HashMap<String, HashMap<String, Integer>> resourceQuantityPerLab, HashMap<String, HashMap<String, Integer>> resourceQuantityPerProject) {
        this.resourceQuantityPerLab = resourceQuantityPerLab;
        this.resourceQuantityPerProject = resourceQuantityPerProject;
    }

    public HashMap<String, HashMap<String, Integer>> getResourceQuantityPerProject() {
        return resourceQuantityPerProject;
    }

    public void setResourceQuantityPerProject(HashMap<String, HashMap<String, Integer>> resourceQuantityPerProject) {
        this.resourceQuantityPerProject = resourceQuantityPerProject;
    }

    public void setResourceQuantityPerLab(HashMap<String, HashMap<String, Integer>> resourceQuantityPerLab) {
        this.resourceQuantityPerLab = resourceQuantityPerLab;
    }
}
