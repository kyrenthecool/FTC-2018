package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.teamcode.AllianceColor;
import org.firstinspires.ftc.teamcode.AutoMode;
import org.firstinspires.ftc.teamcode.HardwareRobot;

/**
 * Created by fibonacci on 1/12/18.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends LinearOpMode {

    HardwareRobot robot = new HardwareRobot();   // Use a Pushbot's hardware
    private ElapsedTime runtime = new ElapsedTime();



    static final float DRIVE_SPEED= 0.6f;
    static final float TURN_SPEED = 0.5f;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        waitForStart();

        if(robot.allianceColor != AllianceColor.NULL){
            switch(robot.autoMode){

                case IDOL_SIDE:

                    //Drive and knock off particle
                    driveForSeconds(2, DRIVE_SPEED, DRIVE_SPEED);

                    //Backup
                    driveForSeconds(1, -DRIVE_SPEED, -DRIVE_SPEED);

                    if(robot.allianceColor == AllianceColor.RED){
                        //Turn left, towards Cryptobox
                        driveForSeconds(1, -TURN_SPEED, TURN_SPEED);
                    }else{
                        //Turn right, towards Cryptobox
                        driveForSeconds(1, TURN_SPEED, -TURN_SPEED);
                    }
                    //Drive into safe zone
                    driveForSeconds(2, DRIVE_SPEED, DRIVE_SPEED);

                    break;

                case CRYPTOBOX_SIDE:
                    //Drive and knock off particle
                    driveForSeconds(2, DRIVE_SPEED, DRIVE_SPEED);

                    //Backup, over balancing stone
                    driveForSeconds(4, -DRIVE_SPEED, -DRIVE_SPEED);
                    if(robot.allianceColor == AllianceColor.RED){
                        //Turn left, towards Cryptobox
                        driveForSeconds(1, -TURN_SPEED, TURN_SPEED);
                    }else{
                        //Turn right, towards Cryptobox
                        driveForSeconds(1, TURN_SPEED, -TURN_SPEED);
                    }

                    //Drive into safe zone
                    driveForSeconds(3, DRIVE_SPEED, DRIVE_SPEED);
                    break;

                default:
                    System.out.println("Autonomous Mode does not exist!");


            }
        }else{
            telemetry.addData("Alliance Color Not Selected!", 0);
            telemetry.update();
        }
    }


    public void driveForSeconds(float seconds, float leftSpeed, float rightSpeed){

        robot.driveAtSpeed(leftSpeed, rightSpeed);

        while (opModeIsActive() && (runtime.seconds() < seconds)){

        }
        runtime.reset();
    }
}
