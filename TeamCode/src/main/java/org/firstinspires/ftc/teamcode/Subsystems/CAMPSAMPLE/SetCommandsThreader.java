package org.firstinspires.ftc.teamcode.Subsystems.CAMPSAMPLE;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.teamcode.yHardware.MotorExCustom;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

import java.util.ArrayList;

public class SetCommandsThreader {

    public static ArrayList<MotorExCustom> motors = new ArrayList<MotorExCustom>();

    public static Thread commandsThread;

    public static void resetLists(){
        motors.clear();
    }


    public static void registerMotor(MotorExCustom motor){
        motors.add(motor);
    }

    public static void startThread(){
         commandsThread = new Thread(() -> {
            while ((OpModeUtils.isActive() || OpModeUtils.isInit())) {
                for (MotorExCustom motor: motors) {
                    motor.commandPosition();
                }
                RobotLog.a("commandsSet!");
//                try {
//                    commandsThread.sleep(20);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                /*
                try {
                    commandsThread.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                 */
            }
        });
        commandsThread.start();
    }

    public static void setCommands(){
        for (MotorExCustom motor: motors) {
            motor.commandPosition();
        }
        RobotLog.a("commandsSet, Single Time!");
    }

}
