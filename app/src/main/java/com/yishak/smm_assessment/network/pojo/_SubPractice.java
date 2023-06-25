package com.yishak.smm_assessment.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class _SubPractice
{
    @SerializedName("sub_practice_id")
    @Expose
    private Integer sub_practice_id;
    @SerializedName("description")
    @Expose
    private Integer description;
    @SerializedName("evolution_level")
    @Expose
    private Integer evolution_level;

    public Integer getSub_practice_id() {
        return sub_practice_id;
    }

    public void setSub_practice_id(Integer sub_practice_id) {
        this.sub_practice_id = sub_practice_id;
    }

    public Integer getDescription() {
        return description;
    }

    public void setDescription(Integer description) {
        this.description = description;
    }

    public Integer getEvolution_level() {
        return evolution_level;
    }

    public void setEvolution_level(Integer evolution_level) {
        this.evolution_level = evolution_level;
    }
}
