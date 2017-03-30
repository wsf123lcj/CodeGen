package com.geok.gen.util;

import com.geok.gen.core.vm.model.Model;

import java.io.*;

/**
 * Created by Stephen on 2017/3/27.
 * 创建文件工具
 */
public class FileUtil {

    public static void write(Model model, StringWriter writer) {
        File file = FileUtil.createNewFile(model.getFileAbsDir(), model.getFileName());
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(writer.toString());
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void mkDir(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }


    public static File createNewFile(String dirPath, String fileName) {
        if (dirPath.endsWith("\\")) {
            dirPath = dirPath.substring(0, dirPath.lastIndexOf("\\"));
        }
        mkDir(dirPath);
        File file = new File(dirPath + "\\" + fileName);
        System.out.println(file.getAbsolutePath());
        if (!file.exists()) {
            try {
                boolean b = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }
}
