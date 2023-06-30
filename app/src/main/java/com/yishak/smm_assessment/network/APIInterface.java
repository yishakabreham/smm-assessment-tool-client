package com.yishak.smm_assessment.network;

import com.yishak.smm_assessment.network.pojo._Phase;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface
{
    @GET("getAll")
    Call<ArrayList<_Phase>> getDetails();
}
