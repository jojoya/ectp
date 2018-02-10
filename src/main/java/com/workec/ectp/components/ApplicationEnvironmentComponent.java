package com.workec.ectp.components;

import com.workec.ectp.dao.ApplicationEnvironmentDao;
import com.workec.ectp.dao.ApplicationEnvironmentDetailDao;
import com.workec.ectp.dao.DomainDao;
import com.workec.ectp.entity.DO.AppEnvAndDomainPK;
import com.workec.ectp.entity.DO.ApplicationEnvironment;
import com.workec.ectp.entity.DO.ApplicationEnvironmentDetail;
import com.workec.ectp.entity.DO.Domain;
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
                detail.setPk(new AppEnvAndDomainPK(appEnvId,domain.getId()));
                detail.setIp(ip);
                environmentDetailDao.save(detail);
            }

    }

    /*更新应用环境IP*/
    public void updateApplicationEnvironmentIp(ApplicationEnvironment environment) {

//        environmentDao.updateIp(appEnvId,ip);
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
