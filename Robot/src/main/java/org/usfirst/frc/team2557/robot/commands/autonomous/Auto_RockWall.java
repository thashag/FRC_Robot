package org.usfirst.frc.team2557.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2557.robot.commands.arm.MoveArmToAngleCommand;
import org.usfirst.frc.team2557.robot.commands.catapult.CatapultRetractCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.TimeDriveCommand;
import org.usfirst.frc.team2557.robot.subsystems.Arm;

public class Auto_RockWall extends CommandGroup {

    public Auto_RockWall() {

        this.addSequential(new MoveArmToAngleCommand(Arm.ARM_ROUGH_DEFENSE)); // Move the arm to a relatively high angle
        this.addParallel(new CatapultRetractCommand());
        this.addSequential(new Auto_DriveToDefense()); // Drive the the defense
        this.addSequential(new TimeDriveCommand(1.5, 1)); // Drive over the rock wall
//        this.addSequential(new Auto_LoadBall()); // Load ball!
//
//        this.addSequential(new MoveArmToAngleCommand(Arm.ARM_CAMERA)); // Move the arm so the camera can see
//        this.addSequential(new WaitCommand(1.5)); // Wait for the camera to update
//
//        this.addSequential(new Auto_CameraShootSequence());

    }

}
