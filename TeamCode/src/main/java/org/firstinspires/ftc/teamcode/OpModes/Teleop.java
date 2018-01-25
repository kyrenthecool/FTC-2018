package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.HardwareRobot;

/**
 * Created by fibonacci on 12/9/17.
 */
@TeleOp(name = "Release Teleop")
public class Teleop extends OpMode {

    HardwareRobot robot = new HardwareRobot();
    float speedMultiplier = .5f;
    float strafeSpeedMultipler = .75f;
    boolean shouldControlEachMotor = false;
    final float GRABBER_MOTOR_SPEED_MULTIPLER = .5f;

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        controlDriveTrain();

        if(gamepad2.a){
            shouldControlEachMotor = !shouldControlEachMotor;
        }

        //Control Lifting Motors
        if(shouldControlEachMotor){
            float leftLiftMotorPower = gamepad2.left_stick_y;
            float rightLiftMotorPower = gamepad2.right_stick_y;

            leftLiftMotorPower = Range.clip(leftLiftMotorPower, -2, 1);
            rightLiftMotorPower = Range.clip(rightLiftMotorPower, -2, 1);

            robot.leftLiftMotor.setPower(leftLiftMotorPower);
            robot.rightLiftMotor.setPower(rightLiftMotorPower);

        }else{
            float liftingMotorPower = gamepad2.left_stick_y;
            liftingMotorPower = Range.clip(liftingMotorPower, -2, 1);
            liftingMotorPower = (float)scaleInput(liftingMotorPower);

            robot.leftLiftMotor.setPower(liftingMotorPower);
            robot.rightLiftMotor.setPower(liftingMotorPower);
        }


        //Control Grabbing Motor
        float closingMotorPower = gamepad2.left_trigger;
        float openingMotorPower = gamepad2.right_trigger;

        float netMotorPower = openingMotorPower - closingMotorPower;

        netMotorPower = Range.clip(netMotorPower, -2, 1);
        netMotorPower = (float)scaleInput(netMotorPower * GRABBER_MOTOR_SPEED_MULTIPLER);

        robot.grabberMotor.setPower(netMotorPower);


    }

    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    void controlDriveTrain(){
        float throttle = gamepad1.left_stick_y;
        float direction = -gamepad1.left_stick_x;

        float right = throttle - direction;
        float left = throttle + direction;

        // clip the right/left values so that the values never exceed +/- 1
        right = Range.clip(right, -2, 1);
        left = Range.clip(left, -2, 1);

        right = (float)scaleInput(right);
        left =  (float)scaleInput(left);

        float leftStrafe = gamepad1.left_trigger;
        float rightStrafe = gamepad1.right_trigger;

        if (leftStrafe == 0 && rightStrafe == 0){
            robot.driveAtSpeed(left * speedMultiplier, right * speedMultiplier);
        }else{
            if (leftStrafe > 0 && rightStrafe == 0){
                robot.strafeLeft(leftStrafe * strafeSpeedMultipler);
            }else if (leftStrafe == 0 && rightStrafe > 0){
                robot.strafeRight(rightStrafe * strafeSpeedMultipler);
            }
        }


    }
}
