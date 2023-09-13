package org.firstinspires.ftc.teamcode.OpModes.CAMPCODE;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.driver;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.setOpMode;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.updateControllers;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.CAMPDuckSpinner;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.CAMPMecanum;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group4Forklift;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group4RingDropper;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

@Disabled
@TeleOp(name="Jack & Sahil", group="Iterative Opmode")
public class Group4Tele extends OpMode {

   /* private ElapsedTime boost = new ElapsedTime();
    private double boostCharge = 0;*/
    private ElapsedTime runtime = new ElapsedTime();
    double offset = 0;
    String offsetTelemetry = "UP";
    double speed = 0.4;

    CAMPMecanum drivetrain;
    CAMPDuckSpinner duckSpinner;
    Group4RingDropper dropper;
    Group4Forklift forklift;


    @Override
    public void init() {
        setOpMode(this);

        drivetrain = new CAMPMecanum();
        duckSpinner = new CAMPDuckSpinner();
        dropper = new Group4RingDropper();
        forklift = new Group4Forklift();

        multTelemetry.addData("Status", "Initialized");
        multTelemetry.update();
    }


    @Override
    public void init_loop() {
        update();

        if(driver.RB.tap()) OpModeUtils.currentAlliance = OpModeUtils.Alliance.BLUE;
        if(driver.LB.tap()) OpModeUtils.currentAlliance = OpModeUtils.Alliance.RED;

        initTelemetry();
    }


    @Override
    public void start() {
        update();
        drivetrain.setRobotNumber(4);
        duckSpinner.setRobotNumber(4);


        //drivetrain.gyro.reset(offset);

        runtime.reset();
        multTelemetry.addData("Status", "Started");
        multTelemetry.update();
    }

    @Override
    public void loop() {

        if(driver.RB.hold()) duckSpinner.spin();
        else duckSpinner.stop();

        drivetrain.driveRobotCentric(driver.rightStick.vec(), driver.leftStick.X(), driver.RT.range(.4, .7));
        dropper.stateMachine(driver.cross.tap(), driver.up.tap(), driver.down.tap());
        forklift.stateMachine(driver.triangle.tap());

        update();
    }

    @Override
    public void stop(){
        //drivetrain.gyro.imuThread.interrupt();
    }




    //PUT ALL UPDATE METHODS HERE

    private void update(){
        updateControllers();
        drivetrain.update();
    }

    //Telemetry to be displayed during init_loop()

    private void initTelemetry(){
        multTelemetry.addData("Status", "InitLoop");
        multTelemetry.addData("ALLIANCE", OpModeUtils.currentAlliance);
        multTelemetry.update();
    }

    //Telemetry to be displayed during loop()

    private void loopTelemetry(){
        multTelemetry.addData("Status", "TeleOp Running");
        multTelemetry.addData("ALLIANCE", OpModeUtils.currentAlliance);
        multTelemetry.update();
    }

    @Disabled
    @TeleOp(name="DEFAULTIterative TeleOp", group="Iterative Opmode")
    public static class Default18438TeleOp extends OpMode {

        private ElapsedTime runtime = new ElapsedTime();

        @Override
        public void init() {
            setOpMode(this);

            multTelemetry.addData("Status", "Initialized");
            multTelemetry.update();
        }


        @Override
        public void init_loop() {
            update();

        }


        @Override
        public void start() {
            update();
            runtime.reset();
            multTelemetry.addData("Status", "Started");
            multTelemetry.update();
        }


        @Override
        public void loop() {
            update();

        }

        @Override
        public void stop(){

        }




        //PUT ALL UPDATE METHODS HERE

        private void update(){
            updateControllers();
        }

        //Telemetry to be displayed during init_loop()

        private void initTelemetry(){
            multTelemetry.addData("Status", "InitLoop");
            multTelemetry.update();
        }

        //Telemetry to be displayed during loop()

        private void loopTelemetry(){
            multTelemetry.addData("Status", "TeleOp Running");
            multTelemetry.update();
        }
    }

    public abstract static class WolfpackOpMode extends LinearOpMode {

        public abstract void externalInit();
        public abstract void externalInitLoop();
        public abstract void externalStart();
        public abstract void externalLoop();
        public abstract void externalStop();

        @Override
        public void runOpMode() throws InterruptedException {
//we stay silly

            externalInit();
            //SetCommandsThreader.startThread();
            while(opModeInInit()){
    //            clearBulkCache();
                externalInitLoop();
                //SetCommandsThreader.commandsThread.notify();
            }
    //        clearBulkCache();
            externalStart();
            while(opModeIsActive()){
    //            clearBulkCache();
                externalLoop();
                //SetCommandsThreader.commandsThread.notify();
            }
            externalStop();
        }

    }
}