package com.yishak.smm_assessment.dtos;

import java.util.ArrayList;

public class Practice {
    public int id;
    public String description;
    public ArrayList<SubPractice> subPractices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<SubPractice> getSubPractices() {
        return subPractices;
    }

    public void setSubPractices(ArrayList<SubPractice> subPractices) {
        this.subPractices = subPractices;
    }
}
