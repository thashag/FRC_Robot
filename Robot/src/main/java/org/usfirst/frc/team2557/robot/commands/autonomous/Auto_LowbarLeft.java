package org.usfirst.frc.team2557.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2557.robot.commands.arm.MoveArmToAngleCommand;
import org.usfirst.frc.team2557.robot.commands.automation.Auto_LoadBall;
import org.usfirst.frc.team2557.robot.commands.catapult.CatapultRetractCommand;
import org.usfirst.frc.team2557.robot.commands.catapult.CatapultShootCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.EncoderPosDriveCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.TurnByAngleCommand;
import org.usfirst.frc.team2557.robot.subsystems.Arm;

public class Auto_LowbarLeft extends CommandGroup {

    public Auto_LowbarLeft() {

        // Parallels that run with a sequential
        // are added before that sequential!

        this.addParallel(new CatapultRetractCommand());
        this.addSequential(new MoveArmToAngleCommand(Arm.ARM_LOWBAR));

        this.addSequential(new EncoderPosDriveCommand(18282, 0.75));


        // BATTER
        this.addSequential(new EncoderPosDriveCommand(7349, 0.75));

        this.addParallel(new Auto_LoadBall());

        this.addSequential(new TurnByAngleCommand(55));

        this.addSequential(new EncoderPosDriveCommand(10370, 0.75));

        // TODO: Camera

        // Onto the actual batter
        this.addSequential(new EncoderPosDriveCommand(4058, 0.5));

        this.addSequential(new MoveArmToAngleCommand(Arm.ARM_BOTTOM));

        this.addSequential(new CatapultShootCommand());
    }

}
