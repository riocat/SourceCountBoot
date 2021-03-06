package com.example.sourcecount.util;

import com.example.sourcecount.entity.ProjectSourceAmount;
import com.example.sourcecount.entity.SourceAmountEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/10/10.
 */
public class TxtAnaylsis {

    private static Pattern pattern = Pattern.compile("(.+)\\b\\s*\\b(\\d+)\\b\\s*\\b(\\d+)\\b\\s*\\b(\\d+)\\b\\s*\\b(\\d+)\\b");

    public static List<ProjectSourceAmount> getExcelDataFromTXT(String clocTxt) throws Exception {
        List<ProjectSourceAmount> result = new ArrayList<ProjectSourceAmount>();

        File txtPath = new File(clocTxt);
        List<File> projectTxts = Arrays.asList(txtPath.listFiles());

        for (File projectTxt : projectTxts) {

            String txtName = projectTxt.getName();
            ProjectSourceAmount projectSourceAmount = new ProjectSourceAmount();
            projectSourceAmount.setProjectName(txtName.substring(0, txtName.lastIndexOf(".")));
            projectSourceAmount.setSourceAmountEntityList(new ArrayList<SourceAmountEntity>());

            boolean arriveCoreTxt = false;
            int lineCount = 0;
            List<String> needStrings = new ArrayList<String>();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(projectTxt));
            String tempStr = null;
            while ((tempStr = bufferedReader.readLine()) != null) {
                if (tempStr.length() > 0 && '-' == tempStr.charAt(0)) {
                    lineCount++;
                    if (lineCount == 2) {
                        arriveCoreTxt = true;
                    }
                }

                if (arriveCoreTxt && '-' != tempStr.charAt(0)) {
                    Matcher matcher = pattern.matcher(tempStr);
                    if (matcher.find()) {
                        SourceAmountEntity sourceAmountEntity = new SourceAmountEntity();
                        // Language
                        String language = matcher.group(1).trim();
                        if ("SUM:".equals(language)) {
                            language = language.replace(":", "");
                        }

                        // TODO 添加过滤筛选 剔除不需要的类型 同时SUM要改为计算获得

                        sourceAmountEntity.setLanguage(language);
                        // files
                        sourceAmountEntity.setFiles(Integer.parseInt(matcher.group(2)));
                        // blank
                        sourceAmountEntity.setBlank(Integer.parseInt(matcher.group(3)));
                        // comment
                        sourceAmountEntity.setComment(Integer.parseInt(matcher.group(4)));
                        // code
                        sourceAmountEntity.setCode(Integer.parseInt(matcher.group(5)));

                        projectSourceAmount.getSourceAmountEntityList().add(sourceAmountEntity);
                    }
                }
            }

            // TODO 添加判断如果projectSourceAmount的sourceAmountEntityList为空不再向result里添加
            result.add(projectSourceAmount);
        }

        return result;
    }
}
