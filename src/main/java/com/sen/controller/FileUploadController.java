package com.sen.controller;


import com.sen.api.utils.Result;
import com.sen.api.utils.ResultUtil;
import com.sen.api.utils.RunXmlUtile;
import com.sen.api.utils.XmlHelperUtil;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author leifeng.cai
 * @description
 * @time: 2020/9/23 8:55
 **/
@RestController
//requestMapping注释用于定义访问REST端点的Request URI
@RequestMapping("/file")
//定义RESTFUL格式，有了该注解，在响应返回   的是json格式的数据

public class FileUploadController {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    private final static String fileDir = "upload";
    /**
    * 测试用例保存地址
    * */
    private final static String rootPath = System.getProperty("user.dir") + File.separator + fileDir + File.separator;


    @RequestMapping("/upload")
    public Result fileUpload(@RequestParam("file") MultipartFile[] multipartFiles) {
        String storagePath = null;
        File fileDir = new File(rootPath);
        if (!fileDir.exists() && !fileDir.isDirectory()) {
            fileDir.mkdirs();
        }

        try {
            if (multipartFiles != null && multipartFiles.length > 0) {
                for (int i = 0; i < multipartFiles.length; i++) {
                    try {
                        //以原来的名称命名,覆盖掉旧的
                        storagePath = rootPath + multipartFiles[i].getOriginalFilename();
                        logger.info("上传的文件：" + multipartFiles[i].getName() + "," + multipartFiles[i].getContentType() + "," + multipartFiles[i].getOriginalFilename()
                                + "，保存的路径为：" + storagePath);
                        Streams.copy(multipartFiles[i].getInputStream(), new FileOutputStream(storagePath), true);

                        //或者下面的
                        // Path path = Paths.get(storagePath);
                        //Files.write(path,multipartFiles[i].getBytes());
                    } catch (IOException e) {
                        logger.error(ExceptionUtils.getFullStackTrace(e));
                    }
                }
            }

        } catch (Exception e) {
            return ResultUtil.error(e.getMessage());
        }
        if (storagePath != null) {
            RunXmlUtile runXmlUtile = new RunXmlUtile();
            runXmlUtile.runTestCaseXml(storagePath);
            return ResultUtil.success("上传成功!接口测试完成");
        } else return ResultUtil.error("上传失败");
    }


}
