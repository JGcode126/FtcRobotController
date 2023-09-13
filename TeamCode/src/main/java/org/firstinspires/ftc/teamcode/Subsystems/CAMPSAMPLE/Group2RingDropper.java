package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group2RingDropper.State.DROP;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group2RingDropper.State.EXTEND;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group2RingDropper.State.HOLD;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.yHardware.MotorExCustom;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class Group2RingDropper {

    Servo dropper1, dropper2;
    public MotorExCustom slides;

    private ElapsedTime stateTime = new ElapsedTime();
    private State currentState = HOLD;

    private final double holdPos = .5, dropPos = .75;
    private final int homePosition = 0, extendPosition = 700;

    public Group2RingDropper(){
        dropper1 = OpModeUtils.hardwareMap.get(Servo.class, "dropper1");
        dropper2 = OpModeUtils.hardwareMap.get(Servo.class, "dropper2");
        dropper2.setDirection(Servo.Direction.REVERSE);
        slides = new MotorExCustom("horizontalslides", false);
        slides.setDirection(DcMotorSimple.Direction.REVERSE);
        slides.setPosition(0);
        slides.runToPosition();
    }

    public void resetEncoder(){
        slides.stopAndResetEncoders();
        slides.runToPosition();
    }

    public void stateMachine(boolean drop, boolean extend, boolean retract){
        switch (currentState){
            case HOLD:
                setDropperPosition(holdPos);
                slides.setPosition(homePosition);
                slides.setPower(.45);
                if(drop) newState(DROP);
                if(extend) newState(EXTEND);
                break;

            case EXTEND:
                setDropperPosition(holdPos);
                slides.setPosition(extendPosition);
                slides.setPower(.4);
                if(drop) newState(DROP);
                if(retract) newState(HOLD);
                break;

            case DROP:
                setDropperPosition(dropPos);
                if(stateTime.seconds() > 1) newState(HOLD);
                break;
        }
    }

    public enum State{
        DROP, HOLD, EXTEND
    }

    public void newState(State newState){
        if(newState != currentState){
            currentState = newState;
            stateTime.reset();
        }
    }

    public void setDropperPosition(double position){
        dropper1.setPosition(position);
        dropper2.setPosition(position);
    }
}
