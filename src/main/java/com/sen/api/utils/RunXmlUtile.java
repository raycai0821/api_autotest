package com.sen.api.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.TestNG;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * @author leifeng.cai
 * @description
 * @time: 2020/9/23 13:16
 **/
public class RunXmlUtile {

    private static final Logger logger = LoggerFactory.getLogger(RunXmlUtile.class);
    private final String filename = "testng.xml";
//    本地调试使用这个
//    private final String outPutPath = System.getProperty("user.dir") + File.separator +
//            "temp" + File.separator + filename;
//    docker部署使用这个
    private final String outPutPath = "/apiautotest/conf/" + filename;
    /**
     * @param newExcelPath 上传的excel测试用例地址
    */
    public void runTestCaseXml(String newExcelPath) {
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<>();
        //获取testng输出流，copy一份到目录下
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
        logger.info("---" + outPutPath);
        FileUtil.writeFile(is, outPutPath);
        XmlHelperUtil.setXmlValue(newExcelPath,outPutPath);
        suites.add(outPutPath);
        testNG.setTestSuites(suites);
        testNG.run();
    }


//    public static void main(String[] args) {
//        new RunXmlUtile().runTestCaseXml();
//    }
//
}
