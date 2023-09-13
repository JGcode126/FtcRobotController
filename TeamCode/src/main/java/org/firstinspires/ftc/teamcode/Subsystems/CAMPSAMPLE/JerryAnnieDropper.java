package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class JerryAnnieDropper {

    Servo dropperFront;
    Servo dropperBack;
    DcMotor linearSlide;

    int dropperStep = 1;

    ElapsedTime timer = new ElapsedTime();

    public JerryAnnieDropper(HardwareMap hardwareMap) {
        dropperFront = hardwareMap.get(Servo.class, "dropperFront");
        dropperBack = hardwareMap.get(Servo.class, "dropperBack");
        linearSlide = hardwareMap.get(DcMotor.class, "linearSlide");
        dropperBack.setDirection(Servo.Direction.REVERSE);
        linearSlide.setPower(0.7);
        linearSlide.setTargetPosition(0);
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        linearSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    public void drop(){
        dropperFront.setPosition(.15);
        dropperBack.setPosition(.15);
    }

    public void close(){

        dropperFront.setPosition(0);
        dropperBack.setPosition(0);

    }

    public void extend(){
        linearSlide.setTargetPosition(-800);
    }

    public void retract(){
        linearSlide.setTargetPosition(0);
    }

    public void autoDrop(){
        timer.reset();
        while (timer.seconds() < 1) {
            extend();
            if (timer.seconds() < 1.5) {
                drop();
            }
        }
        close();
        retract();
    }

    public void dropper(boolean input, boolean slide, boolean retract) {
        switch (dropperStep) {
            case 1:
                retract();
                close();
                if (input) {
                    dropperStep = 2;
                    timer.reset();
                }
                if (slide){
                    dropperStep = 3;
                }

                break;
            case 2:
                drop();
                if (timer.seconds() > 0.6) {
                    dropperStep = 1;
                }
                break;
            case 3:
                extend();
                if (retract){
                    dropperStep = 1;
                }
                if (input){
                    dropperStep = 2;
                    timer.reset();
                }

                break;



        }
    }



}
