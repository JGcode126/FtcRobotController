package org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensors;

import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.yHardware.Controller;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class TouchSensor extends Controller.Button {
    public static DigitalChannel touchSensor;

    public TouchSensor(String hardwareID){
        touchSensor = OpModeUtils.hardwareMap.get(DigitalChannel.class, hardwareID);
        touchSensor.setMode(DigitalChannel.Mode.INPUT);
    }

    public void update(){
        super.update(!touchSensor.getState());
    }
}
