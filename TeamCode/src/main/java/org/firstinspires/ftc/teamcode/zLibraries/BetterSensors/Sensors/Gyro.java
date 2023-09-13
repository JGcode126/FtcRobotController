package org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensors;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.DataTypes.Angle;
import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.DataTypes.AngularPosition;
import org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensor;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.AngleWrapper;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.MathUtils;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.RingBuffer;

public class Gyro extends Sensor<AngularPosition> {

    private final Object imuLock = new Object();

//    @GuardedBy("imuLock")
    public BNO055IMU controlHubIMU;

    public Orientation orientation;
    public Thread imuThread;

    //private BNO055IMU expansionHubIMU;

    public double yawDatum;
    double pitchDatum;
    double rollDatum;

    double yawDelta = 0;

    public static double staticOffset;

    private AngleWrapper yawWrapper;
    private AngleWrapper pitchWrapper;
    private AngleWrapper rollWrapper;

    private AngleInterpreter yawInterpreter;
    private AngleInterpreter pitchInterpreter;
    private AngleInterpreter rollInterpreter;

    private boolean updated = false;

    public Gyro(int pingFrequency, String hardwareID){
        super(pingFrequency, hardwareID);
        readingCache = new AngularPosition(new Angle(0, 0, 0, 0), new Angle(0, 0, 0, 0), new Angle(0, 0, 0, 0));
    }

    @Override
    public void sensorInit(String hardwareID) {
        controlHubIMU = imuConstructor(hardwareID);
        yawWrapper = new AngleWrapper();
        pitchWrapper = new AngleWrapper();
        rollWrapper = new AngleWrapper();

        yawInterpreter = new AngleInterpreter();
        pitchInterpreter = new AngleInterpreter();
        rollInterpreter = new AngleInterpreter();
        orientation = controlHubIMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

    }

    @Override
    public AngularPosition pingSensor(){
        //get recked nerd multithreading go crazy
//        synchronized (imuLock) {
            return pingSensorInternal(controlHubIMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES));
//        }
    }

    //@Override
    public AngularPosition pingSensorInternal(Orientation angles) {

        //Orientation angles = controlHubIMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double rawYaw = angles.firstAngle;
        double rawPitch = angles.secondAngle;
        double rawRoll = angles.thirdAngle;

        double wrappedYaw = yawWrapper.wrapAngle(rawYaw);
        double wrappedPitch = pitchWrapper.wrapAngle(rawPitch);
        double wrappedRoll = rollWrapper.wrapAngle(rawRoll);

        Angle interpretedYaw = yawInterpreter.interpretAngle(wrappedYaw, yawDatum);
        Angle interpretedPitch = pitchInterpreter.interpretAngle(wrappedPitch, pitchDatum);
        Angle interpretedRoll = rollInterpreter.interpretAngle(wrappedRoll, rollDatum);

        yawDelta = interpretedYaw.angle() - angle();

        updated = true;

        return new AngularPosition(
                interpretedYaw,
                interpretedPitch,
                interpretedRoll
        );

    }

    public double getYawDelta(){
        return yawDelta;
    }

    /**
     * IF YOU CALL THIS METHOD, YOU MUST CALL gyro.imuThread.interrupt() in stop() or else I will personally...
     */
    public void startThread(){
        imuThread = new Thread(() -> {
            imuThread.setPriority(Thread.MAX_PRIORITY);
            while (!imuThread.isInterrupted() && OpModeUtils.runThread()) {
//                synchronized (imuLock) {
//                    imuThreadTimer.update();
                    update();
                    pingSensorInternal(controlHubIMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES));
//                }
            }
            imuThread.interrupt();
        });
        imuThread.start();
    }

    public boolean updated(){
        boolean upd = updated;
        updated = false;
        return  upd;
    }

    public static void setStaticOffset(double staticOffset){
        Gyro.staticOffset = staticOffset;
    }

    public static double getStaticOffset(){
        return staticOffset;
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public String getDeviceName() {
        return "Gyroscope";
    }

    //the following are all shortcut/shorthand methods... you probably don't want to do this in any other sensor class, it just makes sense here.
    public double angle() {
        return readingCache.yaw().angle();
    }

    public double modAngle() {
        return readingCache.yaw().modAngle();
    }

    public double rawAngle() {
        return readingCache.yaw().rawAngle();
    }
    public double angularVelocity() {
        return readingCache.yaw().rateOfChange();
    }

    public double yaw(){
        return readingCache.yaw().angle();
    }

    public double pitch(){
        return readingCache.pitch().angle();
    }

    public double roll(){
        return readingCache.roll().angle();
    }

    public void setYawDatum(double yawDatum) {
        this.yawDatum = yawDatum;
    }

    public void setPitchDatum(double pitchDatum) {
        this.pitchDatum = pitchDatum;
    }

    public void setRollDatum(double rollDatum) {
        this.rollDatum = rollDatum;
    }

    public void reset() {
        this.yawDatum = readingCache.yaw().angle();
    }

    public void reset(double offset) {
        this.yawDatum = readingCache.yaw().angle() + offset;
    }

    public void resetYaw() {
        this.yawDatum = readingCache.yaw().angle();
    }

    public void resetPitch() {
        this.pitchDatum = readingCache.pitch().angle();
    }

    public void resetRoll() {
        this.rollDatum = readingCache.roll().angle();
    }

    public void resetAll() {
        this.yawDatum = readingCache.yaw().angle();
        this.pitchDatum = readingCache.pitch().angle();
        this.rollDatum = readingCache.roll().angle();
    }

    private class AngleInterpreter{

        private final RingBuffer<Double> angleRing = new RingBuffer<>(4,0.0);
        private final RingBuffer<Long> angleTimeRing = new RingBuffer<>(4, (long)0);

        public AngleInterpreter() {
        }

        private Angle interpretAngle(double currentAngle, double datum){

            double rawAngle = currentAngle;

            double angle = currentAngle - datum;

            double modAngle = MathUtils.mod(angle, 360);

            long currentTime = System.currentTimeMillis();
            long deltaMili = currentTime - angleTimeRing.getValue(currentTime);
            double deltaSeconds = deltaMili / 1000.0;
            double deltaAngle = angle - angleRing.getValue(angle);
            double rateOfChange = deltaAngle/deltaSeconds;

            return new Angle(angle, modAngle, rawAngle, rateOfChange);
        }

    }

    private BNO055IMU imuConstructor(String deviceName){
        BNO055IMU imu;
        imu = OpModeUtils.hardwareMap.get(BNO055IMU.class, deviceName);
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        imu.initialize(parameters);
        return imu;
    }


}