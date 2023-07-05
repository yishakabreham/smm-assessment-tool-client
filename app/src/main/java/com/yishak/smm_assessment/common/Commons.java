package com.yishak.smm_assessment.common;

import com.yishak.smm_assessment.model.BaseTransaction;
import com.yishak.smm_assessment.model.Buffer;
import com.yishak.smm_assessment.model.NewProject;
import com.yishak.smm_assessment.network.pojo._Phase;

import java.util.ArrayList;
import java.util.List;

public class Commons
{
    public static List<Buffer> commonBufferList;
    public static List<NewProject> newProjectList;
    public static BaseTransaction projectUnderConstruction;
    public static ArrayList<_Phase> phaseList;
    public static _Phase requirementEngineeringPhase;
    public static _Phase designPhase;
    public static _Phase implementationPhase;
    public static _Phase deploymentPhase;
    public static _Phase testingPhase;
}
