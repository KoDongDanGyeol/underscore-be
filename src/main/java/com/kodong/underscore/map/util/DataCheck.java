package com.kodong.underscore.map.util;


import com.kodong.underscore.map.data.flpop.Flpop;
import com.kodong.underscore.map.data.ixqq.Ixqq;
import com.kodong.underscore.map.data.ncmcnsmp.NcmCnsmp;
import com.kodong.underscore.map.data.repop.Repop;
import com.kodong.underscore.map.data.selng.Selng;
import com.kodong.underscore.map.data.stor.Stor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataCheck {
    private final DataUpdate dataUpdate;

    public Flpop updateFlpop(Flpop flpop) {
        if(dataUpdate.getAdministrativeData().contains(flpop.getAdstrdCode())){
            String[] dataToUpdate = dataUpdate.getUpdateData().get(flpop.getAdstrdCode());
            flpop.setAdstrdCode(dataToUpdate[0]);
            flpop.setAdstrdCodeName(dataToUpdate[1]);
            return flpop;
        }
        return flpop;
    }

    public NcmCnsmp updateNcmCnsmp(NcmCnsmp data){
        if(dataUpdate.getAdministrativeData().contains(data.getAdstrdCode())){
            String[] dataToUpdate = dataUpdate.getUpdateData().get(data.getAdstrdCode());
            data.setAdstrdCode(dataToUpdate[0]);
            data.setAdstrdCodeName(dataToUpdate[1]);
            return data;
        }
        return data;
    }

    public Ixqq updateIxqq(Ixqq data){
        if(dataUpdate.getAdministrativeData().contains(data.getAdstrdCode())){
            String[] dataToUpdate = dataUpdate.getUpdateData().get(data.getAdstrdCode());
            data.setAdstrdCode(dataToUpdate[0]);
            data.setAdstrdCodeName(dataToUpdate[1]);
            return data;
        }
        return data;
    }

    public Repop updateRepop(Repop data){
        if(dataUpdate.getAdministrativeData().contains(data.getAdstrdCode())){
            String[] dataToUpdate = dataUpdate.getUpdateData().get(data.getAdstrdCode());
            data.setAdstrdCode(dataToUpdate[0]);
            data.setAdstrdCodeName(dataToUpdate[1]);
            return data;
        }
        return data;
    }

    public Selng updateSelng(Selng data){
        if(dataUpdate.getAdministrativeData().contains(data.getAdstrdCode())){
            String[] dataToUpdate = dataUpdate.getUpdateData().get(data.getAdstrdCode());
            data.setAdstrdCode(dataToUpdate[0]);
            data.setAdstrdCodeName(dataToUpdate[1]);
            return data;
        }
        return data;
    }

    public Stor updateStor(Stor data){
        if(dataUpdate.getAdministrativeData().contains(data.getAdstrdCode())){
            String[] dataToUpdate = dataUpdate.getUpdateData().get(data.getAdstrdCode());
            data.setAdstrdCode(dataToUpdate[0]);
            data.setAdstrdCodeName(dataToUpdate[1]);
            return data;
        }
        return data;
    }
}
