package com.workec.ectp.dao;

import com.workec.ectp.entity.DO.AppEnvAndDomainPK;
import com.workec.ectp.entity.DO.ApplicationEnvironmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by user on 2018/2/2.
 */
public interface ApplicationEnvironmentDetailDao extends JpaRepository<ApplicationEnvironmentDetail,AppEnvAndDomainPK> {
}
