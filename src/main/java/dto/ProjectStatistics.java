package dto;

import java.util.HashMap;

public class ProjectStatistics {
    private int totalProjects;
    private HashMap<String,Integer> totalApprovedProjects;
    private HashMap<String,Integer> totalCompletedProjects;
    private HashMap<String,Integer> totalCancelledProjects;
    private HashMap<String,Integer> totalPlanningProjects;
    private HashMap<String,Integer> totalInProgressProjects;
    private HashMap<String,Integer> totalReadyProjects;
    private HashMap<String,Integer> projectsByLab;

    private double averageMembersPerProject;
    private double averageExecutionTime;
    private String mostUsedResource;
    private int mostUsedResourceCount;
    private String mostUsedResourceType;
    private int mostUsedResourceTypeCount;
    private String mostUsedSkill;
    private String mostUsedInterest;





    public ProjectStatistics() {
    }

    public int getTotalProjects() {
        return totalProjects;
    }

    public void setTotalProjects(int totalProjects) {
        this.totalProjects = totalProjects;
    }

    public HashMap<String, Integer> getTotalApprovedProjects() {
        return totalApprovedProjects;
    }

    public void setTotalApprovedProjects(HashMap<String, Integer> totalApprovedProjects) {
        this.totalApprovedProjects = totalApprovedProjects;
    }

    public HashMap<String, Integer> getTotalCompletedProjects() {
        return totalCompletedProjects;
    }

    public void setTotalCompletedProjects(HashMap<String, Integer> totalCompletedProjects) {
        this.totalCompletedProjects = totalCompletedProjects;
    }

    public HashMap<String, Integer> getTotalCancelledProjects() {
        return totalCancelledProjects;
    }

    public void setTotalCancelledProjects(HashMap<String, Integer> totalCancelledProjects) {
        this.totalCancelledProjects = totalCancelledProjects;
    }

    public HashMap<String, Integer> getProjectsByLab() {
        return projectsByLab;
    }

    public void setProjectsByLab(HashMap<String, Integer> projectsByLab) {
        this.projectsByLab = projectsByLab;
    }

    public double getAverageMembersPerProject() {
        return averageMembersPerProject;
    }

    public void setAverageMembersPerProject(double averageMembersPerProject) {
        this.averageMembersPerProject = averageMembersPerProject;
    }

    public double getAverageExecutionTime() {
        return averageExecutionTime;
    }

    public void setAverageExecutionTime(double averageExecutionTime) {
        this.averageExecutionTime = averageExecutionTime;
    }

    public HashMap<String, Integer> getTotalPlanningProjects() {
        return totalPlanningProjects;
    }

    public void setTotalPlanningProjects(HashMap<String, Integer> totalPlanningProjects) {
        this.totalPlanningProjects = totalPlanningProjects;
    }

    public HashMap<String, Integer> getTotalInProgressProjects() {
        return totalInProgressProjects;
    }

    public void setTotalInProgressProjects(HashMap<String, Integer> totalInProgressProjects) {
        this.totalInProgressProjects = totalInProgressProjects;
    }

    public HashMap<String, Integer> getTotalReadyProjects() {
        return totalReadyProjects;
    }

    public void setTotalReadyProjects(HashMap<String, Integer> totalReadyProjects) {
        this.totalReadyProjects = totalReadyProjects;
    }

    public String getMostUsedResource() {
        return mostUsedResource;
    }

    public void setMostUsedResource(String mostUsedResource) {
        this.mostUsedResource = mostUsedResource;
    }

    public int getMostUsedResourceCount() {
        return mostUsedResourceCount;
    }

    public void setMostUsedResourceCount(int mostUsedResourceCount) {
        this.mostUsedResourceCount = mostUsedResourceCount;
    }

    public String getMostUsedResourceType() {
        return mostUsedResourceType;
    }

    public void setMostUsedResourceType(String mostUsedResourceType) {
        this.mostUsedResourceType = mostUsedResourceType;
    }

    public int getMostUsedResourceTypeCount() {
        return mostUsedResourceTypeCount;
    }

    public void setMostUsedResourceTypeCount(int mostUsedResourceTypeCount) {
        this.mostUsedResourceTypeCount = mostUsedResourceTypeCount;
    }

    public String getMostUsedSkill() {
        return mostUsedSkill;
    }

    public void setMostUsedSkill(String mostUsedSkill) {
        this.mostUsedSkill = mostUsedSkill;
    }

    public String getMostUsedInterest() {
        return mostUsedInterest;
    }

    public void setMostUsedInterest(String mostUsedInterest) {
        this.mostUsedInterest = mostUsedInterest;
    }


}
