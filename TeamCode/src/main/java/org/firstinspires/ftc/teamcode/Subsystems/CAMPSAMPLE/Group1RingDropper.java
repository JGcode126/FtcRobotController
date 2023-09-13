package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group1RingDropper.State.ADJUSTMENT;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group1RingDropper.State.DROP;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group1RingDropper.State.LINEUP;
import static org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.Group1RingDropper.State.LOAD;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class Group1RingDropper {

    Servo dropper1, dropper2, arm1, arm2;

    private ElapsedTime stateTime = new ElapsedTime();
    private State currentState = LOAD;

    private final double closePos = .5, openPos = .75;
    private final double loadPosition = 1, lineUpPosition = .1, lineUpAdjustment = .1;

    public Group1RingDropper(){
        dropper1 = OpModeUtils.hardwareMap.get(Servo.class, "dropper1");
        dropper2 = OpModeUtils.hardwareMap.get(Servo.class, "dropper2");
        dropper2.setDirection(Servo.Direction.REVERSE);
        arm1 = OpModeUtils.hardwareMap.get(Servo.class, "arm1");
        arm2 = OpModeUtils.hardwareMap.get(Servo.class, "arm2");
    }

    public void stateMachine(boolean drop, boolean armUpDown, boolean up, boolean down){
        switch (currentState){
            case LOAD:
                setDropperPosition(openPos);
                setArmPosition(loadPosition);
                if(armUpDown) newState(LINEUP);
                break;

            case LINEUP:
                setDropperPosition(closePos);
                setArmPosition(lineUpPosition);
                if(drop) newState(DROP);
                if(up) newState(ADJUSTMENT);
                if(armUpDown) newState(LOAD);
                break;

            case ADJUSTMENT:
                setDropperPosition(closePos);
                setArmPosition(lineUpPosition + lineUpAdjustment);
                if(drop) newState(DROP);
                if(up) newState(LINEUP);
                if(armUpDown) newState(LOAD);
                break;

            case DROP:
                setDropperPosition(openPos);
                setArmPosition(lineUpPosition);
                if(stateTime.seconds() > 1) newState(LOAD);
                break;
        }
    }

    public enum State{
        DUNK, LOAD, LINEUP, DROP, ADJUSTMENT
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

    public void setArmPosition(double position){
        arm1.setPosition(position);
        arm2.setPosition(position);
    }
}
