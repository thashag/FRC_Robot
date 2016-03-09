package org.usfirst.frc.team2557.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2557.robot.commands.arm.MoveArmToAngleCommand;
import org.usfirst.frc.team2557.robot.commands.automation.Auto_LoadBall;
import org.usfirst.frc.team2557.robot.commands.camera.CorrectDistanceToTargetCommand;
import org.usfirst.frc.team2557.robot.commands.camera.TurnToTargetCommand;
import org.usfirst.frc.team2557.robot.commands.catapult.CatapultShootCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.DistanceDriveCommand;
import org.usfirst.frc.team2557.robot.subsystems.Arm;

public class Auto_Portcullis extends CommandGroup {

    public Auto_Portcullis() {

        this.addSequential(new Auto_LoadBall()); // Load ball!
        this.addSequential(new MoveArmToAngleCommand(Arm.ARM_LOWBAR)); // Move the arm down
        this.addSequential(new Auto_DriveToDefense()); // Drive to the defense
        this.addSequential(new DistanceDriveCommand(1.5, 0.8)); // Drive through the portcullis (fast enough will bounce the gate up high enough)
        this.addSequential(new TurnToTargetCommand()); // Turn to the target
        this.addSequential(new CorrectDistanceToTargetCommand()); // Correct our distance to the target
        this.addSequential(new TurnToTargetCommand()); // Double check that we are aligned
        this.addSequential(new MoveArmToAngleCommand(Arm.ARM_LOWBAR)); // Lower the arm to the floor
        this.addSequential(new CatapultShootCommand()); // Shoot the ball!

    }
}