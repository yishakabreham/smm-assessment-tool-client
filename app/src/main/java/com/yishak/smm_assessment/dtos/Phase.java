package com.yishak.smm_assessment.dtos;

import java.util.ArrayList;

public class Phase
{
    public int id;
    public String name;
    public String description;
    public ArrayList<Practice> practices;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Practice> getPractices() {
        return practices;
    }

    public void setPractices(ArrayList<Practice> practices) {
        this.practices = practices;
    }
}
