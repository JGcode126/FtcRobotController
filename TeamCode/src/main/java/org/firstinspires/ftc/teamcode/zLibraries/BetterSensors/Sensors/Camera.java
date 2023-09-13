package org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Sensors;

import static org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Vision.Dash_AimBot.BLUE_MAX_THRESH;
import static org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Vision.Dash_AimBot.BLUE_MIN_THRESH;
import static org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Vision.Dash_AimBot.RED_MAX_THRESH;
import static org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Vision.Dash_AimBot.RED_MIN_THRESH;
import static org.firstinspires.ftc.teamcode.zLibraries.Files.Loggers.Dash_Reader.LOG_DIR;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.hardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.zLibraries.Files.Clocks.Clock;
import org.firstinspires.ftc.teamcode.zLibraries.Files.FileUtils.FileLogWriter;
import org.firstinspires.ftc.teamcode.zLibraries.Files.FileUtils.LogWriter;
import org.firstinspires.ftc.teamcode.zLibraries.Files.Loggers.GridLogger;
import org.firstinspires.ftc.teamcode.zLibraries.Files.Testing.TestClock;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Camera {

    private static String FILE_NAME = "cameradebug";
    //public AimBotPipe aimBotPipe = new AimBotPipe();
    private OpenCvCamera webcam;
    private int i = 0;

    // Writers
    public LogWriter writer = new FileLogWriter("log.csv");
    public Clock testClock = new TestClock();
    public GridLogger coneGridLogger = new GridLogger(writer, testClock);

    public Camera(String id, boolean display2Phone) {
        //reloadThresholds();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, id), cameraMonitorViewId);
        // Start streaming
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(432, 240, OpenCvCameraRotation.UPRIGHT);
            }
            @Override
            public void onError(int errorCode) {
            }
        });
    }

    /*public void setAimBotPipeline(){
        webcam.setPipeline(aimBotPipe);
    }*/


    /*public void calibrateConeDetection(boolean count){
        // Hold to autocalibrate on a color
        if(count) i++;
        if (i > 4) i = 0;
        switch (i) {
            case 1:
                curTarget = BLUE_CONE;
                aimBotPipe.switch2AutoCalibrate();
                break;

            case 2:
                aimBotPipe.switch2Regular();
                break;

            case 3:
                curTarget = RED_CONE;
                aimBotPipe.switch2AutoCalibrate();
                break;

            case 4:
                aimBotPipe.switch2Regular();
                break;
        }
        multTelemetry.addData("i", i);
    }*/


    public void writeConeThreshValues(){


        FILE_NAME = "log.csv";

        String[] headers = {"ONE", "TWO", "THREE"};

        // Add blue max thresh values
        for (int i=0; i < BLUE_MAX_THRESH.val.length - 1; i++){
            coneGridLogger.add(headers[i], BLUE_MAX_THRESH.val[i]);
        }
        coneGridLogger.writeRow();


        // Add blue mainthresh values
        for (int i=0; i < BLUE_MIN_THRESH.val.length - 1; i++){
            coneGridLogger.add(headers[i], BLUE_MIN_THRESH.val[i]);
        }
        coneGridLogger.writeRow();


        // Add RED max thresh values
        for (int i=0; i < RED_MAX_THRESH.val.length - 1; i++){
            coneGridLogger.add(headers[i], RED_MAX_THRESH.val[i]);
        }
        coneGridLogger.writeRow();


        // Add RED mainthresh values
        for (int i=0; i < RED_MIN_THRESH.val.length - 1; i++){
            coneGridLogger.add(headers[i], RED_MIN_THRESH.val[i]);
        }
        coneGridLogger.writeRow();

        coneGridLogger.stop();
    }

    public void stopVision(){ webcam.closeCameraDevice(); }

    public void optimizeView(){ webcam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW); }

    public void optimizeEfficiency(){ webcam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.MAXIMIZE_EFFICIENCY); }

    public enum VisionOption {
        DEGREES, DISTANCE
    }

    /*public void setTarget(VisionUtils.Target target){
        curTarget = target;
    }*/

    /*public double coneError() {
        return aimBotPipe.getConeDegreeError();
    }*/

    public static void reloadThresholds(){
        reloadTowerThresholds();
    }

    public static void reloadTowerThresholds(){

        FILE_NAME = "log.csv";

        String splitBy = ",";
        String line = "";
        try
        {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader(LOG_DIR + "log.csv"));

            // header
            // hsv max
            // hsv min
            // ycrcb max
            // ycrcb min
            // each row has four items, last one is time




            // Check if there's 5 lines
            // Check if headers are written
            // Check if each line has four elements

            // Read all available lines
            ArrayList<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null){
                lines.add(line);
            }

            // Check if we have enough lines
            if (lines.size() == 5){
                String headers = lines.get(0);

                // Check if headers are written
                if (headers.equals("ONE,TWO,THREE,Time")){



                    // Check if all values were written (in other words if we have 4)
                    String[] max_blue_thresh = lines.get(1).split(splitBy);
                    if (max_blue_thresh.length == 4){

                        // write values to thresholds
                        for (int i=0; i < max_blue_thresh.length - 1; i++){
                            BLUE_MAX_THRESH.val[i] = Double.parseDouble(max_blue_thresh[i]);
                        }
                    }


                    // CHeck if all values were written (in other words if we have 4)
                    String[] min_blue_thresh = lines.get(2).split(splitBy);
                    if (min_blue_thresh.length == 4){

                        // write values to thresholds
                        for (int i=0; i < min_blue_thresh.length - 1; i++){
                            BLUE_MIN_THRESH.val[i] = Double.parseDouble(min_blue_thresh[i]);
                        }
                    }

                    // Check if all values were written (in other words if we have 4)
                    String[] max_red_thresh = lines.get(3).split(splitBy);
                    if (max_red_thresh.length == 4){

                        // write values to thresholds
                        for (int i=0; i < max_red_thresh.length - 1; i++){
                            RED_MAX_THRESH.val[i] = Double.parseDouble(max_red_thresh[i]);
                        }
                    }


                    // Check if all values were written (in other words if we have 4)
                    String[] min_red_thresh = lines.get(4).split(splitBy);
                    if (min_red_thresh.length == 4){

                        // write values to thresholds
                        for (int i=0; i < min_red_thresh.length - 1; i++){
                            RED_MIN_THRESH.val[i] = Double.parseDouble(min_red_thresh[i]);
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


}

