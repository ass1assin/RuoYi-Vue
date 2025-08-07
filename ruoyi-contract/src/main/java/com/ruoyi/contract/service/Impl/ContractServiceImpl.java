package com.ruoyi.contract.service.Impl;

import com.ruoyi.contract.service.ContractService;
import com.ruoyi.contract.utils.GenerateContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private GenerateContract generateContract;

    @Override
    public String getContract(Map<String,Object> formData) throws Exception{
        //1.生成HTML内容
            // 加载模板文件（路径：resources/contract/rentContract.ftl）
        String HTMLL=generateContract.generateHtmlTemplate(formData);
        //2.生成PDF文件
            // 配置字体（支持中文）
            // 渲染 HTML 并生成 PDF
        generateContract.generatePdfFromHtml(HTMLL,"");
        //3.设置响应头，返回PDF文件
        //4.将PDF文件写入响应流
        return HTMLL;
    }
}
