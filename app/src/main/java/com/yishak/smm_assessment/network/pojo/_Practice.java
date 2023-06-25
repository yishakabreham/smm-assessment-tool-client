package com.yishak.smm_assessment.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class _Practice
{
    @SerializedName("practice_id")
    @Expose
    private Integer practice_id;
    @SerializedName("description")
    @Expose
    private Integer description;
    @SerializedName("sub")
    @Expose
    private ArrayList<_SubPractice> subPractices;

    public Integer getPractice_id() {
        return practice_id;
    }

    public void setPractice_id(Integer practice_id) {
        this.practice_id = practice_id;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public ArrayList<_SubPractice> getSubPractices() {
        return subPractices;
    }

    public void setSubPractices(ArrayList<_SubPractice> subPractices) {
        this.subPractices = subPractices;
    }
}
