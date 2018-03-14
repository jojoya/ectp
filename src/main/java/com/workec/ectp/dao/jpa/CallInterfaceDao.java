package com.workec.ectp.dao.jpa;

import com.workec.ectp.entity.DoBak.CallInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by user on 2018/3/9.
 */
public interface CallInterfaceDao  extends JpaRepository<CallInterface,Integer> {

    List<CallInterface> findByCaseIdAndLocationOrderByStepAsc(Integer interfaceId,Integer location);

//    @Query(value = "SELECT * FROM call_interface WHERE case_id=?1", nativeQuery = true)
    List<CallInterface> getListByCaseIdAndLocation(Integer caseId,Integer location);

    @Query(value = "SELECT id FROM call_interface WHERE case_id=?1", nativeQuery = true)
    List<Integer> getIdByCaseId(int caseId);

    @Query(value = "SELECT interface_id FROM call_interface WHERE id=?1", nativeQuery = true)
    Integer getCallInterfaceIdById(int id);
}
