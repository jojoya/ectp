package com.workec.ectp.service.impl;

import com.workec.ectp.components.CaseComponent;
import com.workec.ectp.components.InterfaceComponent;
import com.workec.ectp.dao.jpa.CallInterfaceDao;
import com.workec.ectp.dao.jpa.CaseDao;
import com.workec.ectp.entity.Bo.CallInterfaceDataSave;
import com.workec.ectp.entity.Bo.GroupedCallInterface;
import com.workec.ectp.entity.Bo.InterfaceInitDataFrontEnd;
import com.workec.ectp.entity.Do.CallInterface;
import com.workec.ectp.entity.Do.CallInterfaceData;
import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.enums.BaseResultEnum;
import com.workec.ectp.enums.CallInterfaceLocation;
import com.workec.ectp.service.CallInterfaceService;
import com.workec.ectp.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallIterfaceServiceImpl implements CallInterfaceService {

    @Autowired
    private CallInterfaceDao callInterfaceDao;

    @Autowired
    private CaseComponent caseComponent;

    @Autowired
    private InterfaceComponent interfaceComponent;


    @Override
    public Result<GroupedCallInterface> getListByCaseId(Integer caseId){
        List<CallInterface> pres = callInterfaceDao.getListByCaseIdAndLocation(caseId,CallInterfaceLocation.PREPOSITION.getCode());
        List<CallInterface> test = callInterfaceDao.getListByCaseIdAndLocation(caseId,CallInterfaceLocation.TEST.getCode());
        List<CallInterface> posts = callInterfaceDao.getListByCaseIdAndLocation(caseId,CallInterfaceLocation.POSTPOSITION.getCode());
        InterfaceInitDataFrontEnd testInfo;

        Case cs = caseComponent.getCase(caseId);
        int interfaceId = cs.getInterfaceId();
        int callInterfaceId = 0;
        if(test.size()>0){
            callInterfaceId = test.get(0).getId();
        }
        testInfo = interfaceComponent.getInterfaceStructure(callInterfaceId,interfaceId);

        GroupedCallInterface group = new GroupedCallInterface();
        group.setPres(pres);
        group.setTest(test);
        group.setTestInfo(testInfo);
        group.setPosts(posts);

        return ResultUtil.success(group);
    }


    @Override
    public Result<CallInterfaceData> updateCallInterface(CallInterfaceDataSave cifds) {

        return ResultUtil.success(caseComponent.saveStep(cifds));

    }

    @Override
    public Result deleteCallInterfaceById(Integer id) {

        if(callInterfaceDao.exists(id)){
            caseComponent.deleteStep(id);
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
