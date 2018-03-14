package com.workec.ectp.entity.Bo;

import com.workec.ectp.entity.DoBak.CallInterface;
import lombok.Data;

import java.util.List;

@Data
public class CallInterfaceDataSave {

    private CallInterface callInterface;

    private  List<CallInterfaceParamData> paramData;
}
