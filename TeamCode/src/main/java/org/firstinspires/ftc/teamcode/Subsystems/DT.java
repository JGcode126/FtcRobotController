package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.Vector2d;

public class DT {
    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;

    public IMU gyro;


    public DT(HardwareMap hardwareMap){
        motor1 = hardwareMap.get(DcMotor.class, "drivefl");
        motor2 = hardwareMap.get(DcMotor.class, "drivefr");
        motor3 = hardwareMap.get(DcMotor.class, "drivebl");
        motor4 = hardwareMap.get(DcMotor.class, "drivebr");
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor4.setDirection(DcMotorSimple.Direction.REVERSE);

        gyro = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        gyro.initialize(parameters);
    }

    public void A(){
        motor1.setPower(1);
        motor2.setPower(1);
        motor3.setPower(1);
        motor4.setPower(1);
    }
    public void StopMotors(){
        motor1.setPower(0);
        motor2.setPower(0);
        motor3.setPower(0);
        motor4.setPower(0);
    }

    public double angle(AngleUnit angleUnit){
        return gyro.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }
   public void Drive(double drive, double strafe, double turn){

       Vector2d driveVector = new Vector2d(strafe, drive);
       Vector2d rotatedVector = driveVector.rotateBy(angle(AngleUnit.DEGREES));

       drive = rotatedVector.y*-1;
       strafe = rotatedVector.x;

       motor1.setPower((drive+strafe+turn)*-1);
       motor2.setPower((drive-strafe-turn)*-1);
       motor3.setPower((drive-strafe+turn)*-1);
       motor4.setPower((drive+strafe-turn)*-1);
    }
}