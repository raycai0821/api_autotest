package com.sen.api.utils;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author leifeng.cai
 * @description
 * @time: 2020/9/23 13:48
 **/
public class XmlHelperUtil {
    public static void main(String[] args) {
        new XmlHelperUtil().setXmlValue("1111211");
    }
    /**
     * @param value 文件存在位置
     */
    public static void setXmlValue(String value) {
        try {
            // 1.得到DOM解析器的工厂实例
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            // 2.从DOM工厂里获取DOM解析器
            DocumentBuilder db = dbf.newDocumentBuilder();
            // 3.解析XML文档，得到document，即DOM树
            Document doc = db.parse("testng.xml");

            NodeList list = doc.getElementsByTagName("parameter");
            for (int i = 0; i < list.getLength(); i++) {
                Element parameterElement = (Element) list.item(i);
                String parameterName = parameterElement.getAttribute("name");
                if (parameterName.equals("excelPath")) {
                    //属性修改
                    parameterElement.setAttribute("value", value);
                }
            }
            //保存xml文件
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);
            //设置编码类型
            transformer.setOutputProperty(OutputKeys.ENCODING, "GB2312");
            StreamResult result = new StreamResult(new FileOutputStream("testng.xml"));
            //把DOM树转换为xml文件
            transformer.transform(domSource, result);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("修改完毕");
    }

}



