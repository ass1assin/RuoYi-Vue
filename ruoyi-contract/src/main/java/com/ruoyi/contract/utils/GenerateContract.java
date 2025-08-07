package com.ruoyi.contract.utils;

import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.Map;

@Component
public class GenerateContract {

//    @Autowired
//    private Configuration freemarkerConfig;

    // 加载模板文件（路径：resources/contract/rentContract.ftl）
    public String generateHtmlTemplate(Map<String,Object> data) throws Exception{
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(new File("ruoyi-contract/src/main/resources/contract/"));
        Template template = cfg.getTemplate("rentContract.ftl");
//        Template template = freemarkerConfig.getTemplate("rentContract.ftl");
//        创建流之后要关闭,这里使用了try-with-resources语法所以不用关闭
//        当try中代码执行结束后（正常结束或者异常结束），都会调用try()括号中对象的close()方法来关闭资源。
        try (StringWriter writer = new StringWriter()) {
            template.process(data, writer);
            return writer.toString();
        }
    }

//    根据HTML生成PDF
    public void generatePdfFromHtml(String html,String pdfFileName) throws Exception{
//        设置字体
        ITextRenderer renderer = new ITextRenderer();
        renderer.getFontResolver().addFont(
                "classpath:contract/SimSun.ttc",
                BaseFont.IDENTITY_H,
                BaseFont.NOT_EMBEDDED
        );
//        转换为PDF
        renderer.setDocumentFromString(html);
        renderer.layout();
        try (FileOutputStream os=new FileOutputStream(pdfFileName)){
            renderer.createPDF(os);
        }
    }
}
