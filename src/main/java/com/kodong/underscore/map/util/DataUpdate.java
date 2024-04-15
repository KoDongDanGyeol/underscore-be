package com.kodong.underscore.map.util;

import lombok.Data;
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

}
