package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import org.firstinspires.ftc.teamcode.yHardware.MotorExCustom;

public class Group3Catapult {

    public MotorExCustom catapult;

    public Group3Catapult(){
        catapult = new MotorExCustom("catapult", false);
    }

    public void stateMachine(boolean shootBall){
        if(shootBall) catapult.setPower(1);
        catapult.setPower(0);
    }

}
