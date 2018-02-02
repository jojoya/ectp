package com.workec.ectp.service.impl;

import com.workec.ectp.dao.ApplicationEnvironmentDao;
import com.workec.ectp.dao.ApplicationEnvironmentDetailDao;
import com.workec.ectp.dao.DomainDao;
import com.workec.ectp.entity.DO.AppEnvAndDomainPK;
import com.workec.ectp.entity.DO.ApplicationEnvironment;
import com.workec.ectp.entity.DO.ApplicationEnvironmentDetail;
import com.workec.ectp.entity.DO.Domain;
import com.workec.ectp.entity.dto.InitAPPEnvDetail;
import com.workec.ectp.entity.dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.ApplicationEnvironmentService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.workec.ectp.enums.BaseResultEnum.PARAMETER_INVALID;


@Service
public class ApplicationEnvironmentServiceImpl implements ApplicationEnvironmentService {

    @Autowired
    private ApplicationEnvironmentDao applicationEnvironmentDao;

    @Autowired
    private ApplicationEnvironmentDetailDao environmentDetailDao;

    @Autowired
    private DomainDao domainDao;

    @Override
    public Result<ApplicationEnvironment> getList() {

        List<ApplicationEnvironment> list = applicationEnvironmentDao.findAll();

        System.out.println("list:"+list.size());
        if(list.size()>0){
            return ResultUtil.success(list);
        }else {
            return ResultUtil.success();
        }
    }

    @Override
    public Result<ApplicationEnvironmentDetail> initDetail(InitAPPEnvDetail initdata) {

        int appEnvId = initdata.getAppEnvId();
        String ip = initdata.getIp();
        ApplicationEnvironmentDetail detail = null;

        if(appEnvId!=0&&ip.trim()!=null&&ip.trim()!=""){
            List<Domain> domains = domainDao.findAll();
            for (Domain domain:domains
                 ) {
                detail.setPk(new AppEnvAndDomainPK(appEnvId,domain.getId()));
                detail.setIp(ip);
                environmentDetailDao.save(detail);
            }

        }else{
            return ResultUtil.error(BaseResultEnum.PARAMETER_INVALID.getCode(),
                    BaseResultEnum.PARAMETER_INVALID.getMessage());
        }

        return null;
    }
}
