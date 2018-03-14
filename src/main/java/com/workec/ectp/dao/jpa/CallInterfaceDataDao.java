package com.workec.ectp.dao.jpa;

import com.workec.ectp.entity.DoBak.CallInterfaceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by user on 2018/3/9.
 */
public interface CallInterfaceDataDao extends JpaRepository<CallInterfaceData,Integer> {

    CallInterfaceData findByCallInterfaceIdAndParamKeyId(Integer callInterfaceId, Integer paramKeyId);

    @Query(value = "SELECT * FROM call_interface_data WHERE id=?1", nativeQuery = true)
    List<CallInterfaceData> getListByCaseDataId(int id);

    @Query(value = "SELECT id FROM call_interface_data WHERE call_interface_id=?1", nativeQuery = true)
    List<Integer> getIdByCallInterfaceId(int callInterfaceId);

    @Query(value = "SELECT params_key_id,params_value FROM call_interface_data WHERE id=?1", nativeQuery = true)
    List<CallInterfaceData> getParamsAndParamsValueById(int id);

}
