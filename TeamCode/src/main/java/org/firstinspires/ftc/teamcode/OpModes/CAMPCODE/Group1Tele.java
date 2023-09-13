package org.firstinspires.ftc.teamcode.OpModes.CAMPCODE;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.driver;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.setOpMode;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.updateControllers;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.CAMPDuckSpinner;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.CAMPMecanum;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group1RingDropper;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

@Disabled
@TeleOp(name="Ari & Harrison", group="Iterative Opmode")
public class Group1Tele extends OpMode {

   /* private ElapsedTime boost = new ElapsedTime();
    private double boostCharge = 0;*/
    private ElapsedTime runtime = new ElapsedTime();
    double offset = 0;
    String offsetTelemetry = "UP";
    double speed = 0.4;

    CAMPMecanum drivetrain;
    CAMPDuckSpinner duckSpinner;
    Group1RingDropper dropper;


    @Override
    public void init() {
        setOpMode(this);

        drivetrain = new CAMPMecanum();
        duckSpinner = new CAMPDuckSpinner();
        dropper = new Group1RingDropper();

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
        drivetrain.setRobotNumber(1);
        duckSpinner.setRobotNumber(1);

        //drivetrain.gyro.reset(offset);

        runtime.reset();
        multTelemetry.addData("Status", "Started");
        multTelemetry.update();
    }

    @Override
    public void loop() {

        if(driver.RB.hold()) duckSpinner.spin();
        else duckSpinner.stop();

        drivetrain.driveFieldCentric(driver.rightStick.vec(), driver.leftStick.X(), driver.RT.range(.4, .7));
        dropper.stateMachine(driver.square.tap(), driver.cross.tap(), driver.up.tap(), driver.down.tap());

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
}