package com.yishak.smm_assessment.model;

import java.util.List;

public class BaseTransaction
{
    private String projectName;
    private String projectClient;
    private String projectStartDate;
    private String projectEndDate;
    private String projectRemark;
    private List<Buffer> bufferList;

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

    public String getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    public String getProjectRemark() {
        return projectRemark;
    }

    public void setProjectRemark(String projectRemark) {
        this.projectRemark = projectRemark;
    }

    public List<Buffer> getBufferList() {
        return bufferList;
    }

    public void setBufferList(List<Buffer> bufferList) {
        this.bufferList = bufferList;
    }
}
