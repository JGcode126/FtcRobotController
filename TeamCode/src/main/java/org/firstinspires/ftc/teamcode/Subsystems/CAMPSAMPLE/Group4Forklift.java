package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group4Forklift.State.DOWN;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group4Forklift.State.UP;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class Group4Forklift {

    Servo forklift;

    private ElapsedTime stateTime = new ElapsedTime();
    private State currentState = UP;

    private final double downPos = .6, upPos = .45;

    public Group4Forklift(){
        forklift = OpModeUtils.hardwareMap.get(Servo.class, "forklift");
    }

    public void stateMachine(boolean downUp){
        switch (currentState){
            case DOWN:
                setDropperPosition(downPos);
                if(downUp) newState(UP);
                break;

            case UP:
                setDropperPosition(upPos);
                if(downUp) newState(DOWN);
                break;
        }
    }

    public enum State{
        DOWN, UP
    }

    public void newState(State newState){
        if(newState != currentState){
            currentState = newState;
            stateTime.reset();
        }
    }

    public void setDropperPosition(double position){
        forklift.setPosition(position);
    }
}
