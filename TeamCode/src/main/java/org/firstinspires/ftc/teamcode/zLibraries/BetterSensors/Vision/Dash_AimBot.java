package org.firstinspires.ftc.teamcode.zLibraries.BetterSensors.Vision;

import com.acmerobotics.dashboard.config.Config;

import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

@Config
public class Dash_AimBot {

    // Thresholding values
    public static Scalar BLUE_MAX_THRESH = new Scalar(170, 250, 180);
    public static Scalar BLUE_MIN_THRESH = new Scalar(50, 200, 130);
    public static Scalar RED_MAX_THRESH = new Scalar(0, 0, 0);
    public static Scalar RED_MIN_THRESH = new Scalar(0, 0, 0);

    // Margins for each value
    public static int YM = 50;
    public static int CrM = 15;
    public static int CbM = 15;     // Lighter conditions
    public static int HM = 30;   // 5
    public static int SM = 30;  // 10
    public static int VM = 30;  // 105

    // Tuning constants to eliminate noise
    public static int blur = 5;
    public static int erode_const = 5;
    public static int dilate_const = 5;
    public static int goalWidth = 100;

    // PowerShots
    public static double PS_BLUE_CLOSE_DIST = 11;
    public static double PS_BLUE_MID_DIST = 34;
    public static double PS_BLUE_FAR_DIST = 50;

    public static double PS_RED_CLOSE_DIST = 40;
    public static double PS_RED_MID_DIST = 58;
    public static double PS_RED_FAR_DIST = 74;

    // Debugging tools
    public static int conversion_factor =  Imgproc.COLOR_RGB2HSV;   // Imgproc.COLOR_RGB2YCrCb
    public static boolean TOWER_DEBUG_MODE_ON = false;
    public static boolean TOWER_AUTO_CALIBRATE_ON = false;
    public static int TOWER_INIT_RECT_SIDELENGTH = 10;
    public static double horizonLowerRatio = 0.7;
    public static boolean DISPLAY_FRONT = true;
}