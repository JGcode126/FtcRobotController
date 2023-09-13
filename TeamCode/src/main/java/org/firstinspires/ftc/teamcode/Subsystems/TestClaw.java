package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class TestClaw {

    int b = 1;
    Servo clawleft;
    Servo clawright;

    public TestClaw(HardwareMap hardwareMap) {
        clawleft = hardwareMap.get(Servo.class, "clawleft");
        clawright = hardwareMap.get(Servo.class, "clawright");
        clawleft.setDirection(Servo.Direction.REVERSE);
    }

    public void open(){
        clawleft.setPosition(0.16);
        clawright.setPosition(0.16);
    }

    public void close(){
        clawleft.setPosition(0);
        clawright.setPosition(0);
    }

    public void openClose(boolean button){
        switch(b) {
            case 1:
                close();
                if(button){
                    b = 2;
                }
                break;
            case 2:
                open();
                if(button){
                    b = 1;
                }
                break;
        }
    }
}
