package com.workec.ectp.service.impl;

import com.workec.ectp.components.CaseComponent;
import com.workec.ectp.components.InterfaceComponent;
import com.workec.ectp.dao.jpa.CallInterfaceDao;
import com.workec.ectp.entity.Bo.CallInterfaceDataSave;
import com.workec.ectp.entity.Bo.GroupedCallInterface;
import com.workec.ectp.entity.Do.CallInterface;
import com.workec.ectp.entity.Do.CallInterfaceData;
import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Vo.CallInterfaceStepAdjustment;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.enums.CallInterfaceLocation;
import com.workec.ectp.service.CallInterfaceService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CallIterfaceServiceImpl implements CallInterfaceService {

    @Autowired
    private CallInterfaceDao callInterfaceDao;

    @Autowired
    private CaseComponent caseComponent;

    @Autowired
    private InterfaceComponent interfaceComponent;



    /**
     * 根据caseId获取步骤列表
     *
     * */
    @Override
    public Result<GroupedCallInterface> getListByCaseId(Integer caseId){
        List<CallInterface> pres = callInterfaceDao.getListByCaseIdAndLocation(caseId,CallInterfaceLocation.PREPOSITION.getCode());
        List<CallInterface> test = callInterfaceDao.getListByCaseIdAndLocation(caseId,CallInterfaceLocation.TEST.getCode());
        List<CallInterface> posts = callInterfaceDao.getListByCaseIdAndLocation(caseId,CallInterfaceLocation.POSTPOSITION.getCode());
//        InterfaceInitDataFrontEnd testInfo = null;
//        CallInterfaceInfo testInfo = null;
        Object testInfo = null;

        Case cs = caseComponent.getCase(caseId);
        int interfaceId = cs.getInterfaceId();
        int callInterfaceId = 0;
        if(test.size()>0){
            callInterfaceId = test.get(0).getId();
        }

        if(callInterfaceId==0) {    //没有调用数据，读取定义信息
            testInfo = interfaceComponent.getInterfaceStructure(interfaceId);
        }else if(callInterfaceId!=0){   //有调用数据，读取调用数据
            testInfo = caseComponent.getCallInterfaceInfo(callInterfaceId);
        }

        GroupedCallInterface group = new GroupedCallInterface();
        group.setPres(pres);
        group.setTest(test);
        group.setTestInfo(testInfo);
        group.setPosts(posts);

        return ResultUtil.success(group);
    }


    /**
     * 修改步骤配置
     *
     * */
    @Transactional
    @Override
    public Result<CallInterfaceData> updateCallInterface(CallInterfaceDataSave cifds) {
        return ResultUtil.success(caseComponent.saveStep(cifds));
    }


    /**
     * 调整步骤顺序
     *
     * */
    @Transactional
    @Override
    public Result adjustCallInterfaceStep(CallInterfaceStepAdjustment adjustment) {
        int activeCallInterfaceId = adjustment.getActiveCallInterfaceId();    //被操作的步骤id
        int passiveCallInterfaceId = adjustment.getPassiveCallInterfaceId();    //被交换位置的步骤id
        int activeStepNo = adjustment.getActiveStepNo();               //被操作的步骤编号
        int passiveStepNo = adjustment.getPassiveStepNo();               //被交换位置的步骤编号

        //获取两个步骤信息
        CallInterface activeStep = callInterfaceDao.findOne(activeCallInterfaceId);
        CallInterface passiveStep = callInterfaceDao.findOne(passiveCallInterfaceId);

        if(activeStep!=null &&passiveStep!=null) {
            //交换位置并保持
            activeStep.setStep(passiveStepNo);
            passiveStep.setStep(activeStepNo);
            callInterfaceDao.save(activeStep);
            callInterfaceDao.save(passiveStep);

            return ResultUtil.success();
        }else {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }
    }



    /**
     * 根据步骤id删除步骤
     *
     * */
    @Transactional
    @Override
    public Result deleteCallInterfaceById(Integer id) {
        CallInterface stepInfo = callInterfaceDao.findOne(id);
        if(stepInfo!=null){
            //删除当前步骤
            caseComponent.deleteStep(id);

            //前置（或后置）中所有步骤重新排序
            caseComponent.stepsReorder(stepInfo.getCaseId(),stepInfo.getLocation());
        }else {
            return ResultUtil.error(
                    BaseResultEnum.DATA_NOT_EXIST.getCode(),
                    BaseResultEnum.DATA_NOT_EXIST.getMessage());
        }



        if(!callInterfaceDao.exists(id)){
            return ResultUtil.success();
        }else {
            return ResultUtil.error(1, "删除失败");
        }
    }

}
