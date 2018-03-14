package com.workec.ectp.enums;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by user on 2018/3/12.
 */
public class ReqDataMethodTest {

    @Test
    public void testReqDataMethod(){
        System.out.println(ReqDataMethod.REQUEST_GET.getCode());
        System.out.println(ReqDataMethod.POST_FORM.getCode());
        System.out.println(ReqDataMethod.POST_JSON.getCode());
    }

}