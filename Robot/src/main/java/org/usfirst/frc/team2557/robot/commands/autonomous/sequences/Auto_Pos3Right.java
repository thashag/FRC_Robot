package org.usfirst.frc.team2557.robot.commands.autonomous.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team2557.robot.commands.automation.Auto_LoadBall;
import org.usfirst.frc.team2557.robot.commands.chassis.EncoderPosDriveCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.TurnByAngleCommand;

public class Auto_Pos3Right extends CommandGroup {

    public Auto_Pos3Right() {

        this.addParallel(new Auto_LoadBall());

        this.addSequential(new TurnByAngleCommand(34.2));

        this.addSequential(new EncoderPosDriveCommand(12613, 0.9));

        this.addSequential(new TurnByAngleCommand(-88.7));

        this.addSequential(new Auto_CameraShootSequence());

    }

}
