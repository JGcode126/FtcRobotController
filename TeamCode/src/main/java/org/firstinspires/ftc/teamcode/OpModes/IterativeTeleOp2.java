package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.currentAlliance;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.setOpMode;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.updateControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Subsystems.DT;
import org.firstinspires.ftc.teamcode.Subsystems.TestClaw;

//@Disabled
@TeleOp(name="Beas_TeleOp", group="Iterative Opmode")
public class IterativeTeleOp2 extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    DT dt;
    TestClaw claw;
    /*
     * Code to run ONCE when the driver hits INIT
     */





    @Override
    public void init() {
        setOpMode(this);

        dt = new DT(hardwareMap);
        claw = new TestClaw(hardwareMap);


        claw.close();

        multTelemetry.addData("Status", "Initialized");
        multTelemetry.update();
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
        updateControllers();


        multTelemetry.addData("Status", "InitLoop");
        multTelemetry.addData("Alliance", currentAlliance);
        multTelemetry.update();
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();

        multTelemetry.addData("Status", "Started");
        multTelemetry.update();
    }
  
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        updateControllers();
/*
        if(gamepad1.x){
            dt.A();
        }
        if(gamepad1.a){
            dt.StopMotors();
        }
*/

        dt.Drive(gamepad1.right_stick_y,gamepad1.right_stick_x,gamepad1.left_stick_x);

        if (gamepad2.x) {
            claw.close();
        }
        if (gamepad2.a) {
            claw.open();
        }



        /*
             ----------- L O G G I N G -----------
                                                */
        multTelemetry.addData("Status", "TeleOp Running");
        multTelemetry.update();
    }

    @Override
    public void stop() {

        /*
                    Y O U R   C O D E   H E R E
                                                   */

    }
}