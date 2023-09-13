package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class BallDropper {

    DcMotor vertSlide;

    int stage = 1;

    public BallDropper(HardwareMap hardwareMap) {
        vertSlide = hardwareMap.get(DcMotor.class, "verticalslides");
        vertSlide.setPower(0.7);
        vertSlide.setTargetPosition(0);
        vertSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vertSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void up() {
        vertSlide.setPower(1);
        vertSlide.setTargetPosition(2850);
    }


    public void down() {
        vertSlide.setPower(0.5);
        vertSlide.setTargetPosition(0);
    }


    public void slide(boolean up, boolean down) {
        switch (stage) {
            case 1:
                if (up == true) {
                    up();
                    stage = 2;
                }
                break;
            case 2:
                if (down == true){
                    down();
                    stage = 1;
                break;
                }
        }

    }
}
