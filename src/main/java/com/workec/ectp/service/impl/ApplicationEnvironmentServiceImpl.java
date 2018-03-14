package com.workec.ectp.service.impl;

import com.workec.ectp.dao.jdbc.Impl.AppEnvDataDaoImpl;
import com.workec.ectp.dao.jpa.ApplicationEnvironmentDao;
import com.workec.ectp.dao.jpa.ApplicationEnvironmentDetailDao;
import com.workec.ectp.entity.DoBak.ApplicationEnvironment;
import com.workec.ectp.entity.DoBak.ApplicationEnvironmentDetail;
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

    @Autowired
    private AppEnvDataDaoImpl appEnvDataDao;



    /*获取应用环境列表*/
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



    /*根据应用环境ID获取配置详情*/
    @Override
    public Result<List<AppEnvDetailInfo>> findByEnvId(Integer envId){
        List<AppEnvDetailInfo> list = appEnvDataDao.findByEnvId(envId);
        System.out.println(list);
        return ResultUtil.success(list);
    }



    @Override
    public Result<ApplicationEnvironmentDetail> updateDetail(AppEnvDetailInfo appEnvDetailInfo) {

        ApplicationEnvironmentDetail detail = new ApplicationEnvironmentDetail();
        detail.setDomainId(appEnvDetailInfo.getDomainId());
        detail.setEvnId(appEnvDetailInfo.getEnvId());
        detail.setIp(appEnvDetailInfo.getIp());

        return ResultUtil.success(environmentDetailDao.save(detail));
    }
}
