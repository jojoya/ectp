package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jpa.ApplicationEnvironmentDao;
import com.workec.ectp.dao.jpa.ApplicationEnvironmentDetailDao;
import com.workec.ectp.entity.Do.AppEnvAndDomainPK;
import com.workec.ectp.entity.Do.ApplicationEnvironment;
import com.workec.ectp.entity.Do.ApplicationEnvironmentDetail;
import com.workec.ectp.entity.Bo.AppEnvDetailInfo;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.service.ApplicationEnvironmentService;
import com.workec.ectp.components.ApplicationEnvironmentComponent;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;


@Service
public class ApplicationEnvironmentServiceImpl implements ApplicationEnvironmentService {

    @Autowired
    private ApplicationEnvironmentDao environmentDao;

    @Autowired
    private ApplicationEnvironmentDetailDao environmentDetailDao;

    @Autowired
    private ApplicationEnvironmentComponent applicationEnvironmentComponent;

    @Override
    public Result<ApplicationEnvironment> getList() {

        List<ApplicationEnvironment> list = environmentDao.findAll();

        System.out.println("list:"+list.size());
        if(list.size()>0){
            return ResultUtil.success(list);
        }else {
            return ResultUtil.success();
        }
    }

    @Override
    public Result<ApplicationEnvironmentDetail> initDetail(ApplicationEnvironment environment,BindingResult bindingResult) {

        //检验字段值
        if (bindingResult.hasErrors()) {
            return ResultUtil.error(
                    BaseResultEnum.PARAMETER_INVALID.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        int id = environment.getId();
        String ip = environment.getIp();

        if(id!=0&&environmentDao.exists(id)&&ip.trim()!=null&&ip.trim()!=""){

            /*添加or更新应用环境明细 domain↔IP*/
            applicationEnvironmentComponent.saveApplicationEnvironmentDetail(id,ip);

            /*更新应用环境IP*/
            applicationEnvironmentComponent.updateApplicationEnvironmentIp(environment);

            return ResultUtil.success();
        }else{
            return ResultUtil.error(BaseResultEnum.PARAMETER_INVALID.getCode(),
                    BaseResultEnum.PARAMETER_INVALID.getMessage());
        }

    }


    @Override
    public Result<ApplicationEnvironmentDetail> updateDetail(AppEnvDetailInfo appEnvDetailInfo) {
        ApplicationEnvironmentDetail detail = new ApplicationEnvironmentDetail();

        AppEnvAndDomainPK pk = new AppEnvAndDomainPK();
        pk.setDomainId(appEnvDetailInfo.getDomainId());
        pk.setEvnId(appEnvDetailInfo.getEnvId());
        detail.setPk(pk);
        detail.setIp(appEnvDetailInfo.getIp());

        return ResultUtil.success(environmentDetailDao.save(detail));
    }
}
