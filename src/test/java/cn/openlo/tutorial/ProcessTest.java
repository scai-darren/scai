
package cn.openlo.tutorial;

import javax.annotation.Resource;

import org.junit.Test;

import cn.openlo.gear.test.GearContextConfiguration;
import cn.openlo.gear.test.GearTestBase;
import cn.openlo.process.engine.ProcessActionArgs;
import cn.openlo.process.engine.ProcessContext;
import cn.openlo.process.engine.ProcessEngine;
import cn.openlo.process.engine.Status;
import cn.openlo.process.engine.impl.ExecuteStatus;
import cn.openlo.process.engine.model.Action;

@GearContextConfiguration(boxName = "box01", boxHome = "${user.dir}/src/test/resources/box01/", gearName = "lo-tutorial", gearStartTimeout = 1000000)
public class ProcessTest extends GearTestBase {

    @Resource
    ProcessEngine processEngine;

    @Test
    public void test() throws Exception {
        // processEngine.getManagementService().loadContext();
        Thread.sleep(5000);
    }

    @Test
    public void test1() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610869");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P1");

        processEngine.getRuntimeService().process(context);
    }

    @Test
    public void test2() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610869");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P2");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test3() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610869");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P3");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test4() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610869");
        context.setBizKey(System.currentTimeMillis() + "");
        // // 1467021869995
        // context.setBizKey("1467021869995");

        context.setProcessName("P4");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test5() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610869");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P5");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test6() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610868");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P6");

        processEngine.getRuntimeService().process(context);
        context.setAction(Action.PROCESS);
        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    // @Test
    public void test6Plus() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610868");
        context.setBizKey("1463713372050");
        context.setProcessName("P6");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test7() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610868");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P7");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test8() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610868");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P8");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    // @Test
    public void test8Plus() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610868");
        context.setBizKey("1463716172309");
        context.setProcessName("P8");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test9() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610868");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P9");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test10() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610868");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P10");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test11() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610867");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P11");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test12() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610867");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P12");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(6000);
    }

    @Test
    public void test13() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610867");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P13");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    // @Test
    public void test13Plus() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610867");
        context.setBizKey("1463728433902");
        context.setProcessName("P13");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test14() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610867");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P14");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test15() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610867");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P15");

        Status status = processEngine.getRuntimeService().process(context);
        System.out.println(status);
        Thread.sleep(5000);
    }

    // @Test
    public void test15Plus() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610869");
        context.setBizKey("1463737161990");
        context.setProcessName("P15");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test16() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610866");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P16");

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    // @Test
    public void test16Plus() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610869");
        context.setBizKey("1463966011976");
        context.setProcessName("P16");
        context.setAction(Action.NOTIFY);
        ProcessActionArgs args = new ProcessActionArgs();
        args.setServiceStatus(ExecuteStatus.SUCCESS);
        context.setActionArgs(args);

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    // @Test
    public void test16Plus2() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610869");
        context.setBizKey("1463966392325");
        context.setProcessName("P16");
        context.setAction(Action.NOTIFY);
        ProcessActionArgs args = new ProcessActionArgs();
        args.setServiceStatus(ExecuteStatus.FAILURE);
        context.setActionArgs(args);

        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test17() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610866");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P17");

        Status status = processEngine.getRuntimeService().process(context);
        System.out.println(status);
        Thread.sleep(5000);
    }

    @Test
    public void test18() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610866");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P18");

        Status status = processEngine.getRuntimeService().process(context);
        System.out.println(status);
        Thread.sleep(5000);
    }

    @Test
    public void test19() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610866");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P19");

        Status status = processEngine.getRuntimeService().process(context);
        System.out.println(status);
        Thread.sleep(5000);
    }

    @Test
    public void test20() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610866");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P20");

        Status status = processEngine.getRuntimeService().process(context);
        System.out.println(status);
        Thread.sleep(5000);
    }

    @Test
    public void test21() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610865");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P21");

        Status status = processEngine.getRuntimeService().process(context);
        System.out.println(status);
        Thread.sleep(5000);
    }

    @Test
    public void test22() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610865");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P22");

        Status status = processEngine.getRuntimeService().process(context);
        System.out.println(status);
        Thread.sleep(5000);
    }

    @Test
    public void test23() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610865");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P23");

        processEngine.getRuntimeService().process(context);

        context.setAction(Action.NOTIFY);
        ProcessActionArgs args = new ProcessActionArgs();
        args.setServiceJnlNo(context.getServiceInstance().getChannelJnlNo());
        args.setServiceStatus(ExecuteStatus.FAILURE);
        args.setStatus(ExecuteStatus.FAILURE);
        args.setMessage("失败");
        args.setServiceMsg("失败");
        context.setActionArgs(args);
        processEngine.getRuntimeService().process(context);

        Thread.sleep(5000);
    }

    @Test
    public void test24() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610865");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P24");

        processEngine.getRuntimeService().process(context);
        Thread.sleep(5000);
        processEngine.getRuntimeService().process(context);
        Thread.sleep(5000);
    }

    @Test
    public void test25() throws Exception {
        ProcessContext context = new ProcessContext();
        context.setCustId("1015080600610865");
        context.setBizKey(System.currentTimeMillis() + "");
        context.setProcessName("P25");
        context.put("test", 1);
        context.addCondition("s1", true);
        processEngine.getRuntimeService().process(context);
        Thread.sleep(5000);
    }

    @Test
    public void test27() throws Exception {
        for (int i = 0; i < 1; i++) {
            ProcessContext context = new ProcessContext();
            context.setCustId("1015080600610865");
            context.setBizKey(System.currentTimeMillis() + "");
            context.setProcessName("P27");
            processEngine.getRuntimeService().process(context);
            Thread.sleep(5000);
        }
    }
}
