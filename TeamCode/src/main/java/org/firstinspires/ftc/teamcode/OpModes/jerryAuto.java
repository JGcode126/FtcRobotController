package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.multTelemetry;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.DetectBox;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.JerryAnnieDropper;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.JerryAnnieDuck;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensors.ColorSensor;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

@Disabled
@Autonomous(name = "L A N D O  A U T O", group="Autonomous")
public class jerryAuto extends LinearOpMode {

    //public static double gyroOffset = 0;

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    ColorSensor colorSensor;
    Drivetrain drivetrain;

    DetectBox detectBox;

    ElapsedTime timer = new ElapsedTime();
    //OldGyro gyro;
    JerryAnnieDropper jerryAnnieDropper;

    JerryAnnieDuck jerryAnnieDuck;

    public void initialize() {
        OpModeUtils.setOpMode(this);
        drivetrain = new Drivetrain(hardwareMap);
        jerryAnnieDropper = new JerryAnnieDropper(hardwareMap);
        jerryAnnieDuck = new JerryAnnieDuck(hardwareMap);
        detectBox = new DetectBox(hardwareMap);
        colorSensor = new ColorSensor(0, "colorsensor");
        //gyro = new OldGyro(0, "imu");
        //drivetrain.gyro.update();
        //drivetrain.gyro.reset();
        OpModeUtils.currentAlliance = OpModeUtils.Alliance.RED;

        multTelemetry.addData("Status", "Initialized");
        multTelemetry.update();

    }

    public void shutdown() {
        multTelemetry.addData("Status", "Shutting Down");
        multTelemetry.update();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void runOpMode() {

        /*
        initialize();
        waitForStart();
        gyro.update();
        gyro.reset();
        drivetrain.autoStrafeTime(0.4, 0.5, 0);
        String color = detectBox.detectColor();
        drivetrain.autoStrafeTime(0.4, -0.5, 0);
        multTelemetry.addData("Color:", "Red");
        drivetrain.autoDriveTime(1.3, -1, 0);
        drivetrain.autoStrafeTime(0.5, 1, 0);
        drivetrain.autoTurn(90, -1);
        drivetrain.autoStrafeTime(0.85, -1, 90);
        drivetrain.autoDriveTime(0.75, 0.5, 90);
        jerryAnnieDropper.autoDrop();
        drivetrain.autoDriveTime(0.9, -0.5, 90);
        drivetrain.autoTurn(270, -1);
        drivetrain.autoDriveTime(0.3, 0.5, 275);
        drivetrain.autoStrafeTime(0.9, 0.4, 275);
        drivetrain.autoDriveTime(1.5, 0.2, 275);
        timer.reset();
        while (timer.seconds() < 3.5) {
            jerryAnnieDuck.spin(true);
        }
        jerryAnnieDuck.spin(false);
        drivetrain.autoStrafeTime(1.15, -1, 275);
        drivetrain.autoDriveTime(0.4, -1, 275);
        drivetrain.autoTurn(360, -1);
        drivetrain.autoDriveTime(1.6, 1, 360);
        drivetrain.autoDriveTime(2, 0, 360);
        drivetrain.autoDriveTime(1.3, -1, 360);
        drivetrain.autoStrafeTime(0.6, 1, 360);
        drivetrain.autoTurn(450, -1);
        drivetrain.autoStrafeTime(0.8, -1, 450);
        drivetrain.autoDriveTime(0.9, 0.5, 450);
        jerryAnnieDropper.autoDrop();
        drivetrain.autoDriveTime(0.7, -0.5, 450);
        drivetrain.autoStrafeTime(1.12, 1, 450);
        drivetrain.autoStrafeTime(0.1, -1, 450);
        if (color == "Blue") {
            drivetrain.autoDriveTime(1, 0.5, 450);
            drivetrain.autoDriveTime(0.1, -1, 450);
            drivetrain.autoDriveTime(5, 0, 450);
        }


        while (opModeIsActive()) {
            multTelemetry.addData("Status", "TeleOp Running");
            if (detectBox.detectColor() == "Red") { 
                multTelemetry.addData("Color:", "Red");
            } else if (detectBox.detectColor() == "Blue") {
                multTelemetry.addData("Color:", "Blue");
            }
            multTelemetry.addData("Gyro", gyro.angle());
            multTelemetry.update();

        }

        //gyroOffset = drivetrain.gyro.angle();
        */

    }
}

