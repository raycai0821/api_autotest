package com.sen.api.utils;

import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leifeng.cai
 * @description
 * @time: 2020/9/23 13:16
 **/
public class RunXmlUtile {

    public static void runTestCaseXml() {
        TestNG testNG = new TestNG();
        List<String> suites = new ArrayList<>();
        suites.add(".\\testng.xml");
        testNG.setTestSuites(suites);
        testNG.run();
    }
}
