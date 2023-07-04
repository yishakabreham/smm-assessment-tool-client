package com.yishak.smm_assessment.model;

public class NewProject
{
    private String projectName;
    private String projectClient;
    private String dateCreated;
    private String projectEndDate;
    private String remark;

    public NewProject(String projectName, String projectClient, String dateCreated, String end, String remark) {
        this.projectName = projectName;
        this.projectClient = projectClient;
        this.dateCreated = dateCreated;
        this.projectEndDate = end;
        this.remark = remark;
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

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
