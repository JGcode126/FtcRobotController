package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.currentAlliance;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.driver;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.multTelemetry;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.setOpMode;
import static org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils.updateControllers;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.Subsystems.TestClaw;
import org.firstinspires.ftc.teamcode.zLibraries.Utilities.OpModeUtils;

//@Disabled
@TeleOp(name="Test", group="Iterative Opmode")
public class IterativeTeleOp extends OpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    /*
     * Code to run ONCE when the driver hits INIT
     */

    Drivetrain drivetrain;
    TestClaw claw;
    double rotation;

    double setPoint;




    @Override
    public void init() {
        setOpMode(this);

        drivetrain = new Drivetrain(hardwareMap);
        claw = new TestClaw(hardwareMap);
        //gyro = new Gyro(0, "imu");
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

        if(driver.RB.tap()) currentAlliance = OpModeUtils.Alliance.BLUE;
        if(driver.LB.tap()) currentAlliance = OpModeUtils.Alliance.RED;

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

        //drivetrain.update();
        //drivetrain.gyro.reset();
        //drivetrain.gyro.setYawDatum(jerryAuto.gyroOffset);
        drivetrain.update();
        drivetrain.reset();
        multTelemetry.addData("Status", "Started");
        multTelemetry.update();
    }
  
    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        /*
        drivetrain.update();
        drivetrain.angle(AngleUnit.DEGREES);

        if (gamepad1.left_stick_x > 0.1 || gamepad1.left_stick_x < -0.1) {
            drivetrain.drive(-gamepad1.right_stick_y, gamepad1.right_stick_x,gamepad1.left_stick_x, gamepad1.right_trigger);
            setPoint = drivetrain.angle(AngleUnit.DEGREES);
        }

        if (gamepad1.left_stick_x < 0.1 || gamepad1.left_stick_x > -0.1) {
            rotation = drivetrain.PID(drivetrain.angle(AngleUnit.DEGREES) - setPoint, 0.05, 0.00001, 0.001);
            drivetrain.drive(-gamepad1.right_stick_y, gamepad1.right_stick_x,rotation, gamepad1.right_trigger);
        }
        */

        drivetrain.drive(-gamepad1.right_stick_y, gamepad1.right_stick_x,gamepad1.left_stick_x, gamepad1.right_trigger);


        claw.openClose(gamepad2.a);

        updateControllers();


        /*
             ----------- L O G G I N G -----------
                                                */
        multTelemetry.addData("Status", "TeleOp Running");
        multTelemetry.addData("Gyro angle", drivetrain.angle(AngleUnit.DEGREES));
        multTelemetry.update();
    }

    @Override
    public void stop() {

        /*
                    Y O U R   C O D E   H E R E
                                                   */

    }
}