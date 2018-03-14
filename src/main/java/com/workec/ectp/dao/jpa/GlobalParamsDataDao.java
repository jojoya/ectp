package com.workec.ectp.dao.jpa;


import com.workec.ectp.entity.DoBak.GlobalParamsData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GlobalParamsDataDao extends JpaRepository<GlobalParamsData,Integer> {


   List<GlobalParamsData> findByUserIdAndDbEnvIdAndGlobalParamId(int userId, int dbEnvId, int globalParamId);

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
