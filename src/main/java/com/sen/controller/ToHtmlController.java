package com.sen.controller;

/**
 * @author leifeng.cai
 * @description
 * @time: 2020/9/24 10:45
 **/

import cn.hutool.core.io.resource.ResourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
public class ToHtmlController {

    @RequestMapping("/fileUpload")
    public String fileUpload(){
        return "fileUpload";
    }

    @GetMapping("/report")
    public void writeHtml(HttpServletResponse response) {
        PrintWriter pw =null;
        response.setHeader("Content-Type","text/html;charset=UTF-8");
        try {
//            本地调试地址
//            String str = ResourceUtil.readUtf8Str("/bea/work/apiauto");
//            docker地址
            String str = ResourceUtil.readUtf8Str("/apiautotest/report");

            pw = response.getWriter();
            pw.write(str);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            pw.close();
        }
    }
}
