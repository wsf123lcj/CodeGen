package com.geok.gen.core.vm.assist;

import java.io.*;

/**
 * Created by Stephen on 2017/3/27.
 * 解析vm文件
 */
public class VmFileResolver {
    private StringBuilder sb = new StringBuilder(1000);
    private String templateId;
    private String suffix;

    public VmFileResolver(String vmAbsPath) {
        file2str(vmAbsPath);
    }

    public boolean contains(String str) {
        return sb.toString().contains(str);
    }

    private void file2str(String vmAbsPath)  {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(new File(vmAbsPath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}