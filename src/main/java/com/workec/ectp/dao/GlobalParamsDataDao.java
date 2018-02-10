package com.workec.ectp.dao;


import com.workec.ectp.entity.DO.GlobalParamsData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalParamsDataDao extends JpaRepository<GlobalParamsData,Integer> {

   /* @Modifying
    @Query(value = "UPDATE global_param_data SET db_env_id=?2,param_value=?3 WHERE id=?1", nativeQuery = true)
    Integer saveGlobalParamsData(int id, int db_env_id, String param_value);
*/
}
