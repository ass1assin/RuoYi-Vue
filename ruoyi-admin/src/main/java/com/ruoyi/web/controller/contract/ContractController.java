package com.ruoyi.web.controller.contract;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.contract.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/contract")
public class ContractController extends BaseController
{
    @Autowired
    private ContractService contractService;

    @PostMapping("/genRentContract")
    public AjaxResult genRentContract(@RequestBody HashMap<String,Object> formData) throws Exception {
        System.out.println("formData=="+formData);
        HashMap<String, Object> data = new HashMap<>();
        String s= contractService.getContract(formData);
        data.put("path",s);
        return new AjaxResult(200,"预览成功",data);
    }
}
