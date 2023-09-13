package org.firstinspires.ftc.teamcode.zLibraries.Utilities;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.Alliance.BLUE;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.Alliance.RED;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.OpModes.CAMPCODE.Group4Tele;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.SetCommandsThreader;
import org.firstinspires.ftc.teamcode.yHardware.Controller;
import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.SensorFusion.Enums.AutoType;

import java.util.List;

public class OpModeUtils {

    public static HardwareMap hardwareMap;
    public static OpMode opMode;

    public static Telemetry telemetry;
    public static int campRobotNumber;
    public static FtcDashboard dashboard = FtcDashboard.getInstance();
    public static Telemetry dashboardTelemetry = dashboard.getTelemetry();
    public static MultipleTelemetry baseTelemetry;
    public static ThrowbackTelemetry multTelemetry;

    public static Controller driver, operator;

    public static Alliance currentAlliance = BLUE;
    public static AutoType autoType = AutoType.FORWARD;

    public static void setBlueAlliance(){ currentAlliance = BLUE; }

    public static void setRedAlliance(){ currentAlliance = RED; }


    private static boolean isWolfpackOpMode;

    // Only use if it is in fact a LinearOpMode
    public static Group4Tele.WolfpackOpMode wolfpackOpMode = null;

    private static boolean isLinearOpMode;

    // Only use if it is in fact a LinearOpMode
    public static LinearOpMode linearOpMode = null;

    public static List<LynxModule> allHubs;


    /**
     * Sets the OpMode
     * @param opMode
     */
    public static void setOpMode(OpMode opMode) {
        opModeSetup(opMode);
        isWolfpackOpMode = false;
        wolfpackOpMode = null;
    }

    public static void setWolfpackOpMode(Group4Tele.WolfpackOpMode opMode){
        opModeSetup(opMode);
        isWolfpackOpMode = true;
        wolfpackOpMode = opMode;
    }
//    public static void setLinearOpMode(LinearOpMode opMode){
//        opModeSetup(opMode);
//        isLinearOpMode = true;
//        linearOpMode = opMode;
//    }

    private static void opModeSetup(OpMode opMode){
        SetCommandsThreader.resetLists();
        OpModeUtils.opMode = opMode;
        hardwareMap = opMode.hardwareMap;
        telemetry = opMode.telemetry;
        telemetry.setMsTransmissionInterval(5);
        baseTelemetry = new MultipleTelemetry(telemetry, dashboardTelemetry);
        multTelemetry = new ThrowbackTelemetry(new MultipleTelemetry(telemetry, dashboardTelemetry));

        driver = new Controller(opMode.gamepad1);
        operator = new Controller(opMode.gamepad2);

        allHubs = hardwareMap.getAll(LynxModule.class);

//        for (LynxModule hub : allHubs) {
//            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
//        }
    }

    private static void clearBulkCache(){
//        for (LynxModule hub : allHubs) {
//            hub.clearBulkCache();
//        }
    }

    public static void setNoTelemetry(){
        multTelemetry.setTelemetry(new VoidTelemetry());
    }

    public static void setLogTelemetry(){
        multTelemetry.setTelemetry(new RobotLogTelemetry());
    }

    public static void updateControllers(){
        driver.update(); operator.update();
    }


    public static boolean isActive(){
        if(isWolfpackOpMode) {
            return wolfpackOpMode.opModeIsActive();
        }else if(isLinearOpMode){
            return linearOpMode.opModeIsActive();
        }else{
            RobotLog.a("isActive() was attempted in non-Wolfpack opMode (opModeUtils error)");
            return false;
        }
    }

    public static boolean runThread(){
        return isInit() || isActive();
    }

    public static boolean isInit(){
        return wolfpackOpMode.opModeInInit();
    }

    /**
     * I'm lazy
     * @param o
     */
    public static void print(Object o){
        System.out.println(o);
    }


    public enum Alliance{ BLUE, RED }

}