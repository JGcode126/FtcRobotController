package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.Alliance.BLUE;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;


public class JerryAnnieDuck {

    CRServo duck;
    ElapsedTime timer = new ElapsedTime();

    public JerryAnnieDuck(HardwareMap hardwareMap) {
        duck = hardwareMap.get(CRServo.class, "duckspinner");
    }

    public void spin(boolean button) {
        if (button) {
            if (OpModeUtils.currentAlliance == BLUE) duck.setPower(-0.2);
            else duck.setPower(0.2);
        } else {
            duck.setPower(0);
        }
    }


}

