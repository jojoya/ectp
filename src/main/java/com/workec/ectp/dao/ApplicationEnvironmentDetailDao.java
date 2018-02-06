package com.workec.ectp.dao;

import com.workec.ectp.entity.DO.AppEnvAndDomainPK;
import com.workec.ectp.entity.DO.ApplicationEnvironmentDetail;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by user on 2018/2/2.
 */
public interface ApplicationEnvironmentDetailDao extends JpaRepository<ApplicationEnvironmentDetail,AppEnvAndDomainPK> {

    @Query(value = "SELECT * from application_environment_detail  where evn_id=?1", nativeQuery = true)
    List<ApplicationEnvironmentDetail> findByEnvId(Integer EnvId);
}
