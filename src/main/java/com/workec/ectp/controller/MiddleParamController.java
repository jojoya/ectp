package com.workec.ectp.controller;


import com.workec.ectp.entity.Do.Case;
import com.workec.ectp.entity.Do.MiddleParam;
import com.workec.ectp.entity.Dto.Result;
import com.workec.ectp.service.MiddleParamService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController()
public class MiddleParamController {

    @Autowired
    MiddleParamService middleParamService;


    @ApiOperation(value="修改中间变量信息", notes="修改中间变量信息")
    @PostMapping(value = "/case/step/updatemiddleparam")
    public Result<MiddleParam> updateMiddleParam(@RequestBody MiddleParam mp){
        return middleParamService.updateMiddleParam(mp);
    }

    @ApiOperation(value="删除中间变量信息", notes="删除中间变量信息")
    @PostMapping(value = "/case/step/deletemiddleparam/{id}")
    public Result<MiddleParam> deleteMiddleParam(@PathVariable("id") Integer id){
        return middleParamService.deleteMiddleParam(id);
    }


}
