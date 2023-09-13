package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.yHardware.MotorExCustom;

public class Group2BallScorer {

    public MotorExCustom slides;

    private final int homePosition = 0, scorePosition = 2850;

    public State currentState = State.HOME;
    private ElapsedTime stateTime = new ElapsedTime();

    public Group2BallScorer(){
        slides = new MotorExCustom("verticalslides", false);
        slides.setPosition(0);
        slides.runToPosition();
    }

    public void resetEncoder(){
        slides.stopAndResetEncoders();
        slides.runToPosition();
    }

    public void stateMachine(boolean up, boolean down){
        switch (currentState){
            case HOME:
                slides.setPower(.4);
                slides.setPosition(homePosition);
                if(up) newState(State.SCORE);
                break;

            case SCORE:
                slides.setPower(1);
                slides.setPosition(scorePosition);
                if(down) newState(State.HOME);
                break;
        }
    }

    public enum State{
        HOME, SCORE
    }

    public void newState(State newState){
        if(newState != currentState){
            currentState = newState;
            stateTime.reset();
        }
    }

}
