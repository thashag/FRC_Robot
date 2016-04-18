package org.usfirst.frc.team2557.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2557.robot.commands.arm.MoveArmToAngleCommand;
import org.usfirst.frc.team2557.robot.commands.catapult.CatapultRetractCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.EncoderPosDriveCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.TimeDriveCommand;
import org.usfirst.frc.team2557.robot.subsystems.Arm;

public class Auto_ChivalDeFrise extends CommandGroup {

    public Auto_ChivalDeFrise() {

        this.addParallel(new MoveArmToAngleCommand(Arm.ARM_LOADBALL)); // Move the arm to a position taller than the ramps (vv)
        this.addParallel(new CatapultRetractCommand());

        this.addSequential(new EncoderPosDriveCommand(5185, 0.5));

        this.addSequential(new MoveArmToAngleCommand(Arm.ARM_LOWBAR));

        this.addSequential(new EncoderPosDriveCommand(3802, 0.4));

        this.addParallel(new MoveArmToAngleCommand(Arm.ARM_ROUGH_DEFENSE));
        this.addSequential(new EncoderPosDriveCommand(9930, 0.4));

    }

}
