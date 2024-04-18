package com.kodong.underscore.map.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;

@Component
public class DataUpdate {

    private static final HashMap<String, String[]> updateData =  new HashMap<>();
    static {
        updateData.put("1174052000", new String[]{"1174052500", "상일제1동"});
        updateData.put("1168074000", new String[]{"1168067500","개포3동"});
    }

    private static final HashSet<String> administrativeData = new HashSet<>();
    static{
        administrativeData.add("1174052000");
        administrativeData.add("1168074000");
    }

    public HashMap<String, String[]> getUpdateData() {
        return  updateData;
    }

    public HashSet<String> getAdministrativeData() {
        return  administrativeData;
    }

    // 행정복지 센터 주소를 api로 받았을 때 오류가 생기는 행정구역분류들이 모여있는곳
    private static final HashSet<String> administrativeClassificationToLocation = new HashSet<>();
    static{
        administrativeClassificationToLocation.add("23010660");
        administrativeClassificationToLocation.add("11160640");
        administrativeClassificationToLocation.add("11160720");
        administrativeClassificationToLocation.add("23080530");
        administrativeClassificationToLocation.add("23080540");
        administrativeClassificationToLocation.add("24040520");
        administrativeClassificationToLocation.add("26010540");
    }

    // 오류가 있는 행정구역분류들 업데이트 대상이 모여 있는 곳
    private static final HashMap<String, String> administrativeClassificationUpdateData =  new HashMap<>();
    static {
        administrativeClassificationUpdateData.put("23010660", "23010681");
        administrativeClassificationUpdateData.put("11160640", "11160751");
        administrativeClassificationUpdateData.put("11160720", "11160761");
        administrativeClassificationUpdateData.put("23080530", "23080531");
        administrativeClassificationUpdateData.put("23080540", "23080541");
        administrativeClassificationUpdateData.put("26010540", "26010681");
    }

    public HashSet<String> getAdministrativeClassificationToLocation() {
        return  administrativeClassificationToLocation;
    }

    public HashMap<String, String> getAdministrativeClassificationUpdateData() {
        return  administrativeClassificationUpdateData;
    }

}
