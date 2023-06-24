package com.yishak.smm_assessment.network;

import com.yishak.smm_assessment.dtos.Phase;

import retrofit2.Call;
import retrofit2.http.POST;

public interface APIInterface
{
    @POST("getDetail")
    Call<Phase> getTrips();
}
