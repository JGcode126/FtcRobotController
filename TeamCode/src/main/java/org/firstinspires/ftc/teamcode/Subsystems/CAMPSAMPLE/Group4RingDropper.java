package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group4RingDropper.State.DROP;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group4RingDropper.State.EXTEND;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group4RingDropper.State.HOLD;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class Group4RingDropper {

    Servo dropper, slides;

    private ElapsedTime stateTime = new ElapsedTime();
    private State currentState = HOLD;

    private final double holdPos = .4, dropPos = .95;
    private final double homePosition = .45, extendPosition = .63;

    public Group4RingDropper(){
        dropper = OpModeUtils.hardwareMap.get(Servo.class, "dropper");
        slides = OpModeUtils.hardwareMap.get(Servo.class, "slides");
    }

    public void stateMachine(boolean drop, boolean extend, boolean retract){
        switch (currentState){
            case HOLD:
                setDropperPosition(holdPos);
                slides.setPosition(homePosition);
                if(extend) newState(EXTEND);
                break;

            case EXTEND:
                setDropperPosition(holdPos);
                slides.setPosition(extendPosition);
                if(drop) newState(DROP);
                if(retract) newState(HOLD);
                break;

            case DROP:
                setDropperPosition(dropPos);
                slides.setPosition(extendPosition);
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
        dropper.setPosition(position);
    }
}
