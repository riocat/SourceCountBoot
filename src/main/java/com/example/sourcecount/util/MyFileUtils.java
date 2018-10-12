package com.example.sourcecount.util;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Administrator on 2018/10/12.
 */
public class MyFileUtils {

    public static String getWindowsDiskName() throws Exception {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        String realPath = "";
        if (path.getPath().indexOf("file:\\") >= 0) {
            realPath = path.getPath().substring(6,7);
        } else {
            realPath = path.getPath().substring(0, path.getPath().indexOf(":"));
        }

        return realPath;
    }
}
