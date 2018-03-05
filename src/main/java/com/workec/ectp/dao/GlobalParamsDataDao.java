package com.workec.ectp.dao;


import com.workec.ectp.entity.DO.GlobalParamsData;
import com.workec.ectp.entity.Bo.GlobalParamsDataInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface GlobalParamsDataDao extends JpaRepository<GlobalParamsData,Integer> {

   /*@Query(value = "SELECT " +
           "t.id as id,  " +
           "t.db_env_id as dbEnvId, " +
           "t.global_param_id as globalParamId, " +
           "t.param_value as paramValue, " +
           "t.user_id as userId, " +
           "g.param_name as paramName " +
           "FROM global_params_data t INNER JOIN global_params g ON  g.id = t.global_param_id " +
           "WHERE user_id = ?1 and db_env_id = ?2", nativeQuery = true)
   List<Map<String,Object>> getListByUserIdAndEnvId(int userId, int envId);*/

   /*@Query(value = "SELECT " +
           "t.id,  " +
           "t.db_env_id, " +
           "t.global_param_id, " +
           "t.param_value, " +
           "t.user_id, " +
           "g.param_name " +
           "FROM global_params_data t INNER JOIN global_params g ON  g.id = t.global_param_id " +
           "WHERE user_id = ?1 and db_env_id = ?2", nativeQuery = true)
   List<Map<String,Object>> getListByUserIdAndEnvId(int userId, int envId);*/

}
