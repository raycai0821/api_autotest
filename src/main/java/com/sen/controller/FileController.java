package com.sen.controller;


import com.sen.api.utils.Result;
import com.sen.api.utils.ResultUtil;
import com.sen.api.utils.RunXmlUtile;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.util.Streams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author leifeng.cai
 * @description
 * @time: 2020/9/23 8:55
 **/
@RestController
//requestMapping注释用于定义访问REST端点的Request URI
@RequestMapping("/file")
//定义RESTFUL格式，有了该注解，在响应返回   的是json格式的数据

public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final static String fileDir = "upload";

    private final static String excelTemplateName = "static/api-data.xls";
    /**
    * 测试用例保存地址
    * */
    private final static String rootPath = System.getProperty("user.dir") + File.separator + fileDir + File.separator;


    @RequestMapping("/test")
    public String test(){
        return "ok";
    }

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

    @RequestMapping("/download")
    public Result dowloadExcelTmp(final HttpServletResponse response){
            OutputStream os = null;
            InputStream is= null;
            try {
                // 取得输出流
                os = response.getOutputStream();
                // 清空输出流
                response.reset();
                response.setContentType("application/x-download;charset=GBK");
                response.setHeader("Content-Disposition", "attachment;filename="+ new String(excelTemplateName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
                //读取流

                is = this.getClass().getClassLoader().getResourceAsStream(excelTemplateName);
                //复制
                IOUtils.copy(is, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                return ResultUtil.error("下载附件失败,error:"+e.getMessage());
            }
            //文件的关闭放在finally中
            finally
            {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    logger.error(ExceptionUtils.getFullStackTrace(e));
                }
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    logger.error(ExceptionUtils.getFullStackTrace(e));
                }
            }
            return null;
        }
    }


