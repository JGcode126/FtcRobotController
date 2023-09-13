package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group3RingDropper.State.DROP;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group3RingDropper.State.HOLD;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class Group3RingDropper {
    Servo dropper1, dropper2;

    private ElapsedTime stateTime = new ElapsedTime();
    private State currentState = HOLD;

    private final double holdPos = .6, dropPos = .3;

    public Group3RingDropper(){
        dropper1 = OpModeUtils.hardwareMap.get(Servo.class, "dropper1");
        dropper2 = OpModeUtils.hardwareMap.get(Servo.class, "dropper2");
        dropper2.setDirection(Servo.Direction.REVERSE);
    }

    public void stateMachine(boolean drop){
        switch (currentState){
            case HOLD:
                setDropperPosition(holdPos);
                if(drop) newState(DROP);
                break;

            case DROP:
                setDropperPosition(dropPos);
                if(stateTime.seconds() > 1) newState(HOLD);
                break;
        }
    }

    public enum State{
        DROP, HOLD
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
