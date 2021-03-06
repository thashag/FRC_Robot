package org.usfirst.frc.team2557.robot.commands.autonomous.macro;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import org.usfirst.frc.team2557.robot.Robot;
import org.usfirst.frc.team2557.robot.RobotMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MacroPlayer {

    Scanner scanner;
    long startTime;

    boolean onTime = true;
    double nextDouble;

    private PIDController _controller;

    public MacroPlayer(String filepath) throws FileNotFoundException
    {
        //create a scanner to read the file created during BTMacroRecord
        //scanner is able to read out the doubles recorded into recordedAuto.csv (as of 2015)
        scanner = new Scanner(new File(filepath));

        //let scanner know that the numbers are separated by a comma or a newline, as it is a .csv file
        scanner.useDelimiter(",|\\n");

        //lets set start time to the current time you begin autonomous
        startTime = System.currentTimeMillis();

        // Construct PID controller for the arm
        this._controller = new PIDController(0.3, 0.15, 0, new PIDSource() {
            @Override
            public void setPIDSourceType(PIDSourceType pidSource) {}

            @Override
            public PIDSourceType getPIDSourceType() {
                return PIDSourceType.kDisplacement;
            }

            @Override
            public double pidGet() {
                return Robot.arm.returnPIDInput();
            }
        }, new PIDOutput() {
            @Override
            public void pidWrite(double output) {
                Robot.arm.usePIDOutput(output);
            }
        });

        this._controller.setInputRange(0, 5);
        this._controller.setOutputRange(-1, 1);
        this._controller.setAbsoluteTolerance(0.05);
    }

    public void init() {
        this._controller.reset();
    }

    public void play()
    {
        //if recordedAuto.csv has a double to read next, then read it
        if ((scanner != null) && (scanner.hasNextDouble()))
        {
            double t_delta;

            //if we have waited the recorded amount of time assigned to each respective motor value,
            //then move on to the next double value
            //prevents the macro playback from getting ahead of itself and writing different
            //motor values too quickly
            if(onTime)
            {
                //take next value
                nextDouble = scanner.nextDouble();
            }

            //time recorded for values minus how far into replaying it we are--> if not zero, hold up
            t_delta = nextDouble - (System.currentTimeMillis()-startTime);

            //if we are on time, then set motor values
            if (t_delta <= 0)
            {
                //it is extremely important to set the motors in the SAME ORDER as was recorded in BTMacroRecord
                //otherwise, motor values will be sent to the wrong motors and the robot will be unpredicatable

                // Drive Motors
                RobotMap.driveLeft1.set(scanner.nextDouble());
                RobotMap.driveLeft2.set(scanner.nextDouble());
                RobotMap.driveRight1.set(scanner.nextDouble());
                RobotMap.driveRight2.set(scanner.nextDouble());

                // Catapult motor
                RobotMap.intakeMotor.set(scanner.nextDouble());

                // Arm
                this._controller.setSetpoint(scanner.nextDouble());
                this._controller.enable();

                // Intake motor
                RobotMap.intakeMotor.set(scanner.nextDouble());

                //go to next double
                onTime = true;
            }
            //else don't change the values of the motors until we are "onTime"
            else
            {
                onTime = false;
            }
        }
        //end play, there are no more values to find
        else
        {
            this.end();
            if (scanner != null)
            {
                scanner.close();
                scanner = null;
            }
        }

    }

    public boolean isFinished() {
        return scanner == null;
    }

    //stop motors and end playing the recorded file
    public void end()
    {
        RobotMap.driveLeft1.set(0);
        RobotMap.driveLeft2.set(0);
        RobotMap.driveRight1.set(0);
        RobotMap.driveRight2.set(0);

        RobotMap.catapultMotor.set(0);

        this._controller.disable();
        Robot.arm.set(0);

        RobotMap.intakeMotor.set(0);

        if (scanner != null)
        {
            scanner.close();
        }

    }


}
