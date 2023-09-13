package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static java.lang.Math.abs;
import static java.lang.Math.max;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.yHardware.MotorExCustom;
import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.SensorInterpreters.GyroExtensions;
import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensors.OldGyro;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.Vector2d;

public class CAMPMecanum {
    private MotorExCustom drivefl, drivefr, drivebl, drivebr;

    public final OldGyro gyro;
    public final GyroExtensions gyroExtensions;

    public CAMPMecanum() {
        drivefl = new MotorExCustom("drivefl", true);
        drivefr = new MotorExCustom("drivefr", false);
        drivebl = new MotorExCustom("drivebl", true);
        drivebr = new MotorExCustom("drivebr", false);

        drivefl.runWithoutEncoders();
        drivefr.runWithoutEncoders();
        drivebl.runWithoutEncoders();
        drivebr.runWithoutEncoders();

        gyro = new OldGyro(0, "imu");
        gyroExtensions = new GyroExtensions(gyro);
    }

    public void setRobotNumber(int robotNumber){
        if (robotNumber == 4) drivefr.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void update(){
        gyro.update();
    }

    public void driveFieldCentric(Vector2d transVec, double rotation, double power) {
        Vector2d transVecFC = transVec.rotateBy(gyro.angle());
        setMotorPower(transVecFC.getY(), transVecFC.getX(), rotation, power);
    }

    public void driveRobotCentric(Vector2d transVec, double rotation, double power) {
        setMotorPower(transVec.getY(), transVec.getX(), rotation, power);
    }

    public void setMotorPower(double drive, double strafe, double turn, double power){
        drive = Range.clip(drive, -1, 1);
        strafe = Range.clip(strafe, -1, 1);
        turn = Range.clip(turn, -1, 1);
        power = Range.clip(power, 0.05 , 1);

        double flPower = (drive - strafe - turn) * power;
        double frPower = (drive + strafe + turn) * power;
        double blPower = (drive + strafe - turn) * power;
        double brPower = (drive - strafe + turn) * power;

        double maxPower = abs(max(max(abs(flPower), abs(frPower)), max(abs(blPower), abs(brPower))));
        if(maxPower > 1) { frPower /= maxPower; flPower /= maxPower; blPower /= maxPower; brPower /= maxPower; }
        else if(maxPower < .05 && maxPower > -.05) { flPower = 0; frPower = 0; blPower = 0; brPower = 0; }

        drivefl.setPower(flPower); drivefr.setPower(frPower); drivebl.setPower(blPower); drivebr.setPower(brPower);
    }
}
