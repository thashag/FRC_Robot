package org.usfirst.frc.team2557.robot.commands.intake;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2557.robot.Robot;

public class IntakeStopCommand extends Command {

    public IntakeStopCommand() {
        requires(Robot.intake);
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        if(Robot.oi.manipulator.getRawAxis(3) >= 0.1) {
            Robot.intake.set(Robot.oi.manipulator.getRawAxis(3) / 2);
        }else if(Robot.oi.manipulator.getRawAxis(2) >= 0.1) {
            Robot.intake.set(Robot.oi.manipulator.getRawAxis(2) / -2);
        }else{
            Robot.intake.set(0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
