package org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.MultiThreading;

import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensors.Gyro;

public class GyroUpdater extends Thread{

    private Gyro gyro;

    public GyroUpdater(Gyro gyro){
        this.gyro = gyro;
    }

    @Override
    public void run() {
        while(!super.isInterrupted()){
            gyro.update();
        }
    }
}
