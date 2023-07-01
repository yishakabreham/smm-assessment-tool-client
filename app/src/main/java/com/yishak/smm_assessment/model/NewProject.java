package com.yishak.smm_assessment.model;

public class NewProject
{
    private String projectName;
    private String projectClient;
    private String dateCreated;

    public NewProject(String projectName, String projectClient, String dateCreated) {
        this.projectName = projectName;
        this.projectClient = projectClient;
        this.dateCreated = dateCreated;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectClient() {
        return projectClient;
    }

    public void setProjectClient(String projectClient) {
        this.projectClient = projectClient;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
