package com.workec.ectp.dao.jpa;

import com.workec.ectp.entity.Do.AppEnvAndDomainPK;
import com.workec.ectp.entity.Do.ApplicationEnvironmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by user on 2018/2/2.
 */
public interface ApplicationEnvironmentDetailDao extends JpaRepository<ApplicationEnvironmentDetail,AppEnvAndDomainPK> {

    @Query(value = "SELECT * from application_environment_detail  where evn_id=?1", nativeQuery = true)
    List<ApplicationEnvironmentDetail> findByEnvId(Integer EnvId);
}
