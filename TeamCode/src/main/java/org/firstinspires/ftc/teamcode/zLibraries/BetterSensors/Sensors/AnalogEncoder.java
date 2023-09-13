package org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensors;


import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensor;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.AngleWrapper;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.MathUtils;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class AnalogEncoder extends Sensor<Double> {

    private AnalogInput encoder;
    private double absoluteAngle, wrappedAngle;
    private AngleWrapper angleWrapper;
    private double offset = 0;
    private boolean reverse = false;

    public AnalogEncoder(int pingFrequency, String hardwareID) {
        this(pingFrequency, hardwareID, 0, false);
    }

    public AnalogEncoder(int pingFrequency, String hardwareID, double offset, boolean reverse) {
        super(pingFrequency, hardwareID);
        this.offset = offset;
        this.reverse = reverse;
    }

    @Override
    protected void sensorInit(String hardwareID) {
        angleWrapper = new AngleWrapper();
        encoder = OpModeUtils.hardwareMap.get(AnalogInput.class, hardwareID);
    }

    @Override
    protected Double pingSensor() {
        if(reverse) absoluteAngle = -(MathUtils.linearConversion(encoder.getVoltage() * 72, 1.3, 348, 0, 360) + offset);
        else absoluteAngle = MathUtils.linearConversion(encoder.getVoltage() * 72, 1.3, 348, 0, 360) + offset;
        wrappedAngle = angleWrapper.wrapAngle(absoluteAngle);
        return encoder.getVoltage();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public String getDeviceName() {
        return null;
    }

    public double getAbsoluteAngle(){
        return absoluteAngle;
    }

    public double getWrappedAngle(){
        return wrappedAngle;
    }
}
