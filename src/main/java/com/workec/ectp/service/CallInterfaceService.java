package com.workec.ectp.service;

import com.workec.ectp.entity.Bo.CallInterfaceDataSave;
import com.workec.ectp.entity.Bo.GroupedCallInterface;
import com.workec.ectp.entity.Do.CallInterfaceData;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.entity.Vo.CallInterfaceStepAdjustment;

public interface CallInterfaceService  {

     //查询步骤
     Result<GroupedCallInterface> getListByCaseId(Integer caseId);

     //保存步骤
     Result<CallInterfaceData> updateCallInterface(CallInterfaceDataSave cifds);

     //删除步骤
     Result deleteCallInterfaceById(Integer id);

     //调整步骤顺序
     Result adjustCallInterfaceStep(CallInterfaceStepAdjustment adjustment);

}
