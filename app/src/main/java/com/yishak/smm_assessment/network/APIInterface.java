package com.yishak.smm_assessment.network;

import com.yishak.smm_assessment.dtos.Phase;
import com.yishak.smm_assessment.network.pojo._Phase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface
{
    @GET("getAll")
    Call<ArrayList<_Phase>> getDetails();
}
