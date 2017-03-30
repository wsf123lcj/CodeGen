package com.geok.gen.core.vm.assist;

import com.geok.gen.core.vm.model.Model;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;

/**
 * Created by Stephen on 2017/3/27.
 *
 */
public class VMEngine {
    private VelocityEngine ve;

    public VMEngine(String vmDir) {
        init(vmDir);
    }

    /**
     * 初始化并启动Velocity引擎
     * @param vmDir vm文件所在目录
     */
    private void init(String vmDir) {
        ve = new VelocityEngine();
        ve.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH, vmDir);
        ve.setProperty(Velocity.INPUT_ENCODING, "utf-8");
        ve.setProperty(Velocity.OUTPUT_ENCODING, "utf-8");
        ve.setProperty(Velocity.ENCODING_DEFAULT, "utf-8");
        ve.init();
    }

    public StringWriter templateMergeModel(Model model) {
        VelocityContext velocityContext = new VelocityContext();
        Template template = ve.getTemplate(model.getVmName());
        velocityContext.put("model", model);
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);
        return writer;
    }
}
