package com.workec.ectp.entity.Bo;

import com.workec.ectp.entity.Do.CallInterface;
import com.workec.ectp.entity.Do.CallInterfaceData;
import com.workec.ectp.entity.Do.MiddleParam;
import lombok.Data;

import java.util.List;

@Data
public class CallInterfaceDataSave {

    private CallInterface callInterface;

    private  List<CallInterfaceData> paramData;

    private List<MiddleParam> middleParams;

}
