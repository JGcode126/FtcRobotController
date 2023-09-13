package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.Alliance.BLUE;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

public class CAMPDuckSpinner {

    CRServo spinner;

    public CAMPDuckSpinner(){
        spinner = OpModeUtils.hardwareMap.get(CRServo.class, "duckspinner");
    }

    public void setRobotNumber(int robotNumber){

    }

    public void spin(){
        if(OpModeUtils.currentAlliance == BLUE) spinner.setPower(-1);
        else spinner.setPower(1);
    }

    public void stop(){
        spinner.setPower(0);
    }
}
