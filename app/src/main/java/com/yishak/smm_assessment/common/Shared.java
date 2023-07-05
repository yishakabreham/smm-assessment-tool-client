package com.yishak.smm_assessment.common;

import android.util.Log;

import com.yishak.smm_assessment.model.Buffer;
import com.yishak.smm_assessment.network.API;
import com.yishak.smm_assessment.network.pojo._Phase;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Shared
{
    public static List<Buffer> bufferList;
    public static void createBuffer(HashMap<Integer, String> map, List<_SubPractice> subPractices, String type)
    {
        bufferList = new ArrayList<>();
        Set<Integer> keys = map.keySet();

        for ( int key : keys)
        {
            Buffer buffer = new Buffer();
            buffer.setType(type);
            buffer.setCode(key);
            buffer.setiL(Integer.parseInt(map.get(key)));

            bufferList.add(buffer);
        }
        Commons.commonBufferList.addAll(bufferList);
    }

    public static void details(){
        Commons.phaseList = new ArrayList<>();
        API.detail().getDetails()
                .enqueue(new Callback<ArrayList<_Phase>>() {
                    @Override
                    public void onResponse(Call<ArrayList<_Phase>> call, Response<ArrayList<_Phase>> response) {
                        if(response.isSuccessful() && response.code() == 200)
                        {
                            Commons.phaseList = response.body();
                            if(Commons.phaseList != null && Commons.phaseList.size() > 0)
                            {
                                for (int i = 0; i < Commons.phaseList.size(); i++)
                                {
                                    String phaseLabel = Commons.phaseList.get(i).getName();
                                    if(phaseLabel != null){
                                        switch (phaseLabel)
                                        {
                                            case "requirement_engineering":
                                                Commons.requirementEngineeringPhase = Commons.phaseList.get(i);
                                                break;
                                            case "design":
                                                Commons.designPhase = Commons.phaseList.get(i);
                                                break;
                                            case "implementation":
                                                Commons.implementationPhase = Commons.phaseList.get(i);
                                                break;
                                            case "deployment":
                                                Commons.deploymentPhase = Commons.phaseList.get(i);
                                                break;
                                            case "testing":
                                                Commons.testingPhase = Commons.phaseList.get(i);
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<_Phase>> call, Throwable t) {
                        Log.i("Failure", t.getMessage());
                    }
                });
    }
}
