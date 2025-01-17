package org.firstinspires.ftc.teamcode.zLibraries.BetterSensors;

import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Utils.GenericDataSupplier;

public class GenericPollingSensor <T> extends Sensor<T>{

    GenericDataSupplier<T> dataSupplier;

    public GenericPollingSensor(int pingFrequency, GenericDataSupplier<T> dataSupplier){
        super(pingFrequency, "");
    }

    @Override
    protected void sensorInit(String hardwareID) {
    }

    @Override
    protected T pingSensor() {
        return dataSupplier.getData();
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public String getDeviceName() {
        return "unknown";
    }
}
