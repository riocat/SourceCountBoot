package com.example.sourcecount.util;

import com.example.sourcecount.entity.ProjectSourceAmount;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/10/11.
 */
public class ExcelGeneraor {

    private static ExcelGeneraor excelGeneraor;

    private ExcelGeneraor() {
    }

    public synchronized static ExcelGeneraor getExcelGeneraor() {
        if (ExcelGeneraor.excelGeneraor == null) {
            ExcelGeneraor.excelGeneraor = new ExcelGeneraor();
        }
        return ExcelGeneraor.excelGeneraor;
    }

    public String createExcel(List<ProjectSourceAmount> projectSourceAmounts, File reportDirectory, Properties properties, URL classUrl) throws Exception {
        OutputStream tempOS = null;

        String reportPath = reportDirectory + "/" + CommonsDataFormat.SDF_YMD.format(new Date()) + "日代码统计报告.xls";

        XLSTransformer transformer = new XLSTransformer();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("projectSourceAmounts", projectSourceAmounts);
        map.put("dateinfo", "报告生成时间：" + CommonsDataFormat.SDF_YMDHMS.format(new Date()));
        InputStream is = null;
        if (classUrl == null) {
            Resource resource = new ClassPathResource(properties.getProperty("exceltemplet"));
            is = resource.getInputStream();
        } else {
            is = new FileInputStream(classUrl.getPath() + properties.getProperty("exceltemplet"));
        }
        try {
            Workbook workbook = transformer.transformXLS(is, map);
            tempOS = new FileOutputStream(reportPath);
            workbook.write(tempOS);
            tempOS.flush();
            tempOS.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {

            if (is != null) {
                is.close();
            }

            if (tempOS != null) {
                tempOS.close();
            }
        }

        return reportPath;
    }
}
