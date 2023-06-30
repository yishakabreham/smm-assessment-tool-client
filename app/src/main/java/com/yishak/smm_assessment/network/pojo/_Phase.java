package com.yishak.smm_assessment.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class _Phase
{
    @SerializedName("phase_id")
    @Expose
    private Integer phase_id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("practice")
    @Expose
    private ArrayList<_Practice> practices;

    public Integer getPhase_id() {
        return phase_id;
    }

    public void setPhase_id(Integer phase_id) {
        this.phase_id = phase_id;
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

    public ArrayList<_Practice> getPractices() {
        return practices;
    }

    public void setPractices(ArrayList<_Practice> practices) {
        this.practices = practices;
    }
}
