package com.yishak.smm_assessment.common;

import com.yishak.smm_assessment.model.Buffer;
import com.yishak.smm_assessment.network.pojo._SubPractice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
}
