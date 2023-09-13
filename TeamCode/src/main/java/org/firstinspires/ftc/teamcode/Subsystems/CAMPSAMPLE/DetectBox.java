package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensors.ColorSensor;

public class DetectBox {

    ColorSensor colorSensor;

    public DetectBox(HardwareMap hardwaremap) {
        colorSensor = new ColorSensor(0, "colorsensor");
    }

    public String detectColor() {
        colorSensor.update();
        if (colorSensor.getRed() > colorSensor.getBlue()) {
            return "Red";
        } else {
            return "Blue";
        }

    }
}
