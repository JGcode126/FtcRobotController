package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group3WobbleArm.ArmState.DOWN;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group3WobbleArm.ArmState.UP;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class Group3WobbleArm {

    Servo grabber, arm;

    private ElapsedTime stateTime = new ElapsedTime();
    private State currentState = State.RETRACT;
    private ArmState currentArmState = DOWN;

    private final double retractPosition = .87, lineUpPosition = .6, grabPosition = .3;
    private final double armDown = .45, armUp= .7;

    public Group3WobbleArm(){
        grabber = OpModeUtils.hardwareMap.get(Servo.class, "grabber");
        grabber.setDirection(Servo.Direction.REVERSE);
        arm = OpModeUtils.hardwareMap.get(Servo.class, "arm");
        arm.setDirection(Servo.Direction.REVERSE);
    }

    public void stateMachine(boolean retract, boolean extend, boolean closeOpen, boolean up, boolean down){
        switch (currentState){
            case RETRACT:
                newState(DOWN);
                grabber.setPosition(retractPosition);
                if(extend) newState(State.LINEUP);
                break;

            case LINEUP:
                grabber.setPosition(lineUpPosition);
                if(retract) newState(State.RETRACT);
                if(closeOpen) newState(State.GRAB);
                break;

            case GRAB:
                grabber.setPosition(grabPosition);
                if(retract) newState(State.RETRACT);
                if(closeOpen) newState(State.LINEUP);
                break;
        }

        switch(currentArmState){
            case UP:
                arm.setPosition(armUp);
                if(down) newState(DOWN);
                break;

            case DOWN:
                arm.setPosition(armDown);
                if(up) newState(UP);
                break;
        }
    }

    public enum State{
        RETRACT, LINEUP, GRAB
    }

    public enum ArmState{
        UP, DOWN
    }

    public void newState(State newState){
        if(newState != currentState){
            currentState = newState;
            stateTime.reset();
        }
    }

    public void newState(ArmState newState){
        if(newState != currentArmState){
            currentArmState = newState;
        }
    }
}
