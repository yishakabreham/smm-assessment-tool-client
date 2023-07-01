package com.yishak.smm_assessment.network.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class _Project
{
    @SerializedName("project_id")
    @Expose
    private Integer project_id;

    @SerializedName("project_name")
    @Expose
    private String project_name;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("project_client")
    @Expose
    private String project_client;

    @SerializedName("project_start_date")
    @Expose
    private String project_start_date;

    @SerializedName("project_end_date")
    @Expose
    private String project_end_date;

    @SerializedName("maturity_level")
    @Expose
    private String maturity_level;

    @SerializedName("phase")
    @Expose
    private ArrayList<_Phase> phases;
}
