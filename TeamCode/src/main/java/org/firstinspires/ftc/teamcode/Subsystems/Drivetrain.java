package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.Vector2d;


public class Drivetrain {

    DcMotor motor1;
    DcMotor motor2;
    DcMotor motor3;
    DcMotor motor4;

    public IMU gyro;
    double last_error = 0;
    double integral = 0;

    ElapsedTime timer = new ElapsedTime();
    public Drivetrain(HardwareMap hardwareMap){
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
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        gyro.initialize(parameters);

    }

    public double angle(AngleUnit angleUnit){
        return gyro.getRobotYawPitchRollAngles().getYaw(angleUnit);
    }

    public void reset(){
        gyro.resetYaw();
    }

    public void update(){
        gyro.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    public void drive(double drive, double strafe, double turn, double slow){


        Vector2d driveVector = new Vector2d(strafe, drive);
        Vector2d rotatedVector = driveVector.rotateBy(-angle(AngleUnit.DEGREES));

        drive = rotatedVector.y;
        strafe = rotatedVector.x;



        if (slow > 0.05) {
            motor1.setPower((drive+strafe+turn)*-0.25);
            motor2.setPower((drive-strafe-turn)*-0.25);
            motor3.setPower((drive-strafe+turn)*-0.25);
            motor4.setPower((drive+strafe-turn)*-0.25);
        }else{
            motor1.setPower((drive+strafe+turn)*-1);
            motor2.setPower((drive-strafe-turn)*-1);
            motor3.setPower((drive-strafe+turn)*-1);
            motor4.setPower((drive+strafe-turn)*-1);
        }
    }


    public void driveRobot(double drive, double strafe, double turn){

        motor1.setPower((drive+strafe+turn)*-1);
        motor2.setPower((drive-strafe-turn)*-1);
        motor3.setPower((drive-strafe+turn)*-1);
        motor4.setPower((drive+strafe-turn)*-1);
    }

    public double PID (double error, double kp, double ki, double kd){

        integral += error;
        double derivative = error - last_error;
        double preportional = error * kp;
        double integral2 = integral * ki;
        double derivative2 = derivative * kd;

        double correction = preportional + integral2 + derivative2;
        return correction;

    }
    /*
    public void autoDriveTime(double time, double speed, double heading){

        timer.reset();
        timer.seconds();


        while(time > timer.seconds()){
            gyro.update();
            driveRobot(speed,0,PID(gyro.angle() - heading ,0.05,0.0000,0.00));
            multTelemetry.addData("Secdisbdfs", timer.seconds());
            multTelemetry.addData("gyro", gyro.angle());
            multTelemetry.update();
            if(time < timer.seconds()){
                driveRobot(0,0,0);
                return;
            }

        }


        driveRobot(0,0,0);


    }

    public void autoStrafeTime(double time, double speed, double heading){

        timer.reset();
        timer.seconds();

        while(time >= timer.seconds()){
            gyro.update();
            driveRobot(0,speed,PID(gyro.angle() - heading ,0.05,0.0000,0.00));
            if(time < timer.seconds()){
                driveRobot(0,0,0);
                return;
            }
        }
        driveRobot(0,0,0);
    }

    public void autoTurn(double degrees, double speed) {

        gyro.update();
        boolean end = false;
        while (!end) {
            gyro.update();
            driveRobot(0, 0, speed);
            multTelemetry.addData("Secdisbdfs", timer.seconds());
            multTelemetry.addData("gyro", gyro.angle());
            multTelemetry.update();
            if (gyro.angle() >= degrees && speed < 0) {
                driveRobot(0, 0, 0);
                end = true;
                return;
            }
            if (gyro.angle() <= degrees && speed > 0) {
                driveRobot(0, 0, 0);
                end = true;
                return;
            }
            driveRobot(0, 0, 0);
        }
    }

    public void autoDriveTicks(double ticks, double speed, double heading){

        resetEncoder();

        while(ticks > getEncoderTick()){
            gyro.update();
            driveRobot(speed,0,PID(gyro.angle() - heading ,0.05,0.0000,0.00));
            multTelemetry.addData("Secdisbdfs", timer.seconds());
            multTelemetry.addData("gyro", gyro.angle());
            multTelemetry.update();
            if(ticks < getEncoderTick()){
                driveRobot(0,0,0);
                return;
            }

        }


        driveRobot(0,0,0);


    }

    public void autoStrafeTicks(double ticks, double speed, double heading){

        resetEncoder();

        while(ticks >= getEncoderTick()){
            gyro.update();
            driveRobot(0,speed,PID(gyro.angle() - heading ,0.05,0.0000,0.00));
            if(ticks < getEncoderTick()){
                driveRobot(0,0,0);
                return;
            }
        }
        driveRobot(0,0,0);
    }


    public int getEncoderTick(){
       return ((Math.abs(motor1.getCurrentPosition()) + Math.abs(motor2.getCurrentPosition()) + Math.abs(motor3.getCurrentPosition()) + Math.abs(motor4.getCurrentPosition()))/4);
    }

    public void resetEncoder(){
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor4.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    */

}
