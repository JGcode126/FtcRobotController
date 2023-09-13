package org.firstinspires.ftc.teamcode.yHardware;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.hardwareMap;
import static java.util.Objects.isNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE.SetCommandsThreader;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.Derivative;

public class MotorExCustom {
    private DcMotor motor1, motor2;
    private DcMotorEx motor1Ex, motor2Ex;
    private int currentPosition = 0;
    private double velocity = 0;
    private Derivative positionDerivative = new Derivative(3);
    private double ticksPerRev = 537.7;
    private Double powerCommanded;

    public MotorExCustom(DcMotor motor1, DcMotor motor2){
        this.motor1 = motor1;
        this.motor2 = motor2;
        this.motor1Ex = (DcMotorEx) motor1;
        this.motor2Ex = (DcMotorEx) motor2;
        SetCommandsThreader.registerMotor(this);
    }

    public MotorExCustom(DcMotor motor){
        motor1 = motor;
        this.motor1Ex = (DcMotorEx) motor1;
        SetCommandsThreader.registerMotor(this);

    }

    public MotorExCustom(String motor1ID, boolean reverse1, String motor2ID, boolean reverse2){
        motor1 = hardwareMap.get(DcMotor.class, motor1ID);
        motor2 = hardwareMap.get(DcMotor.class, motor2ID);
        this.motor1Ex = (DcMotorEx) motor1;
        this.motor2Ex = (DcMotorEx) motor2;

        if(reverse1) motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        else motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        if(reverse2) motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        else motor2.setDirection(DcMotorSimple.Direction.FORWARD);
        SetCommandsThreader.registerMotor(this);

    }

    public MotorExCustom(String motorID, boolean reverse){
        motor1 = hardwareMap.get(DcMotor.class, motorID);
        this.motor1Ex = (DcMotorEx) motor1;
        if(reverse) setDirectionReverse();
        else setDirectionForward();
        SetCommandsThreader.registerMotor(this);

    }

    public MotorExCustom(String motor1ID, String motor2ID, boolean reversePair) {
        motor1 = hardwareMap.get(DcMotor.class, motor1ID);
        motor2 = hardwareMap.get(DcMotor.class, motor2ID);
        this.motor1Ex = (DcMotorEx) motor1;
        this.motor2Ex = (DcMotorEx) motor2;

        if(reversePair) setDirectionReverse();
        else setDirectionForward();
        SetCommandsThreader.registerMotor(this);

    }

    public MotorExCustom(String motor1ID, String motor2ID) {
        motor1 = hardwareMap.get(DcMotor.class, motor1ID);
        motor2 = hardwareMap.get(DcMotor.class, motor2ID);
        this.motor1Ex = (DcMotorEx) motor1;
        this.motor2Ex = (DcMotorEx) motor2;
        setDirectionForward();
        SetCommandsThreader.registerMotor(this);

    }

    public MotorExCustom(String motorID){
        motor1 = hardwareMap.get(DcMotor.class, motorID);
        this.motor1Ex = (DcMotorEx) motor1;
        setDirectionForward();
        SetCommandsThreader.registerMotor(this);

    }

    public void update(){
        if(motor2 != null) currentPosition = (motor1.getCurrentPosition() + motor2.getCurrentPosition()) / (2);
        else currentPosition = motor1.getCurrentPosition();
        //positionDerivative.update(System.currentTimeMillis() / 60000.0, (double)currentPosition / ticksPerRev);
        positionDerivative.update(System.nanoTime(), currentPosition);
        velocity = positionDerivative.getDerivative();
    }

    public void setTicksPerRev(double ticksPerRev){
        this.ticksPerRev = ticksPerRev;
    }



    public DcMotor.RunMode currentRunMode(){
        return motor1.getMode();
    }

    public void setPower(double power){
        powerCommanded = power;
        commandPosition();
    }

    public void setPosition(double position){
        motor1.setTargetPosition((int) position);
        if(motor2 != null) motor2.setTargetPosition((int) position);
    }

    public double getMotor1Power(){
        return motor1.getPower();
    }

    public double getMotor2Power(){
        return motor2.getPower();
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    public double getVelocityNanoseconds(){
        return velocity;
    }

    public double getVelocitySeconds(){
        return velocity * 1E+9;
    }

    public double getMotor1Current(CurrentUnit currentDrawUnits){
        return motor1Ex.getCurrent(currentDrawUnits);
    }

    public double getMotor2Current(CurrentUnit currentDrawUnits){
        return motor2Ex.getCurrent(currentDrawUnits);
    }

    public double getAvgCurrent(CurrentUnit currentDrawUnits){
        return (Math.abs(getMotor1Current(currentDrawUnits)) + Math.abs(getMotor2Current(currentDrawUnits))) / 2;
    }

    // DIRECTION //

    public void setDirection(DcMotorSimple.Direction direction){
        motor1.setDirection(direction);
        if(motor2 != null) motor2.setDirection(direction);
    }

    public void setDirectionForward() { setDirection(DcMotorSimple.Direction.FORWARD); }

    public void setDirectionReverse() { setDirection(DcMotorSimple.Direction.REVERSE); }

    // MODE //

    public void setMode(DcMotor.RunMode mode){
        if(mode != currentRunMode()) {
            motor1.setMode(mode);
            if (motor2 != null) motor2.setMode(mode);
        }
    }

    public void runWithEncoders(){ setMode(DcMotor.RunMode.RUN_USING_ENCODER); }

    public void runWithoutEncoders(){ setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); }

    public void stopAndResetEncoders(){ setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); }

    public void runToPosition(){ setMode(DcMotor.RunMode.RUN_TO_POSITION); }

    public MotorConfigurationType getMotorType(){
        return motor1.getMotorType();
    }

    public void setMotorType(MotorConfigurationType motorConfigurationType){
        motor1.setMotorType(motorConfigurationType);
    }

    // ZERO POWER BEHAVIOR //

    public void setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior zeroPowerBehaviour){
        motor1.setZeroPowerBehavior(zeroPowerBehaviour);
        if(motor2 != null) motor2.setZeroPowerBehavior(zeroPowerBehaviour);
    }

    public void zeroPowerBrake(){ setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.BRAKE); }

    public void zeroPowerFloat(){ setZeroPowerBehaviour(DcMotor.ZeroPowerBehavior.FLOAT); }

    public DcMotor getMotor1() {
        return motor1;
    }

    public DcMotor getMotor2() {
        return motor2;
    }

    public void commandPosition() {
        if(!isNull(powerCommanded)){
            motor1.setPower(powerCommanded);
            if(motor2 != null) motor2.setPower(powerCommanded);
        }
    }
}