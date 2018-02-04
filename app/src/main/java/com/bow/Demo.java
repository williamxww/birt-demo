package com.bow;

import com.bow.entity.Status;
import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author vv
 * @since 2017/8/19.
 */
public class Demo {

    private static final Logger LOGGER = LoggerFactory.getLogger(Demo.class);
    public void runReport(String designName, Object data) throws BirtException {
        // bridge log
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        LOGGER.info("Init Logger");
        // set engine
        EngineConfig config = new EngineConfig();
        config.setEngineHome(".");
//        config.setLogger();
//        config.setResourcePath("");
        Platform.startup(config);

        IReportEngineFactory reportEngineFactory = (IReportEngineFactory) Platform
                .createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        IReportEngine engine = reportEngineFactory.createReportEngine(config);

        IReportRunnable runnable = engine.openReportDesign(designName);
        IRunAndRenderTask task = engine.createRunAndRenderTask(runnable);

        // datasource
        Map context = new HashMap();
        context.put("APP_CONTEXT_KEY_VVDATASET", data);
        task.setAppContext(context);
        task.validateParameters();

        // output
        HTMLRenderOption option = new HTMLRenderOption();
        option.setOutputFileName("status.html");
        option.setOutputFormat("html");

        task.setRenderOption(option);
        task.run();
        task.close();
    }

    public static void main(String[] args) throws BirtException {
        Demo demo = new Demo();
        List<Status> data = new ArrayList<>();
        Status s1 = new Status();
        s1.setServiceName("com.bow.Calculator");
        s1.setDestination("192.168.5.56");
        s1.setSource("192.168.5.1");
        s1.setSuccessCount(5);
        s1.setFailCount(0);
        data.add(s1);
        demo.runReport("report/status2.rptdesign", data);
    }
}
