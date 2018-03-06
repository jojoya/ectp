package com.workec.ectp.components;

import com.workec.ectp.dao.jpa.ApplicationEnvironmentDao;
import com.workec.ectp.dao.jpa.ApplicationEnvironmentDetailDao;
import com.workec.ectp.dao.jpa.DomainDao;
import com.workec.ectp.entity.Do.AppEnvAndDomainPK;
import com.workec.ectp.entity.Do.ApplicationEnvironment;
import com.workec.ectp.entity.Do.ApplicationEnvironmentDetail;
import com.workec.ectp.entity.Do.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 应用环境组件
 */
@Component
public class ApplicationEnvironmentComponent {

    @Autowired
    private ApplicationEnvironmentDao environmentDao;

    @Autowired
    private ApplicationEnvironmentDetailDao environmentDetailDao;

    @Autowired
    private DomainDao domainDao;

    /*添加应用环境明细 domain↔IP*/
    public void saveApplicationEnvironmentDetail(int appEnvId,String ip){
            ApplicationEnvironmentDetail detail = new ApplicationEnvironmentDetail();
            List<Domain> domains = domainDao.findAll();
            for (Domain domain:domains) {
                detail.setDomainId(domain.getId());
                detail.setEvnId(appEnvId);
                detail.setIp(ip);
                environmentDetailDao.save(detail);
            }

    }

    /*更新应用环境IP*/
    public void updateApplicationEnvironmentIp(ApplicationEnvironment environment) {

        int appEnvId = environment.getId();
        String ip = environment.getIp();
        String remark = environment.getRemark();

        ApplicationEnvironment getInfo = environmentDao.getOne(appEnvId);
        ApplicationEnvironment putInfo = new ApplicationEnvironment();
        putInfo.setIp(ip);
        putInfo.setId(appEnvId);
        putInfo.setDbId(getInfo.getDbId());
        putInfo.setName(getInfo.getName());
        putInfo.setRemark(remark);
        environmentDao.save(putInfo);

    }
}
