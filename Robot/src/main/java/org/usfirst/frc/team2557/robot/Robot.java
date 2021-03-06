
package org.usfirst.frc.team2557.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2557.robot.commands.arm.MoveArmToAngleCommand;
import org.usfirst.frc.team2557.robot.commands.automation.Auto_LoadBall;
import org.usfirst.frc.team2557.robot.commands.autonomous.*;
import org.usfirst.frc.team2557.robot.commands.autonomous.sequences.*;
import org.usfirst.frc.team2557.robot.commands.camera.CorrectDistanceToTargetCommand;
import org.usfirst.frc.team2557.robot.commands.camera.TurnToTargetCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.EncoderPosDriveCommand;
import org.usfirst.frc.team2557.robot.commands.chassis.TurnByAngleCommand;
import org.usfirst.frc.team2557.robot.subsystems.*;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//Subsystem Declarations//
    public static OI oi;
    public static Chassis chassis;
    public static Arm arm;
    public static Intake intake;
    public static Catapult catapult;
    public static Winch winch;
    public static SecondArm secondArm;
    public static Camera camera;
    public static Lidar lidar;
    public static Dashboard dashboard;

    //Command Declarations//
    Command autonomousCommand;

    SendableChooser autoChooser;
    public SendableChooser posChooser;
    public SendableChooser batterChooser;

    public static Robot instance;

    public Robot() {
        super();

        instance = this;
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize RobotMap
        RobotMap.init();

        //Subsystem Connections//
        chassis = new Chassis();
        arm = new Arm();
        intake = new Intake();
        catapult = new Catapult();
        winch = new Winch();
        secondArm = new SecondArm();
        camera = new Camera();
        lidar = new Lidar();
        dashboard = new Dashboard();

        //OI Connection//
        // NOTE: oi MUST be constructed after subsystems
        oi 						= new OI();

        // Make a SendableChooser on the SmartDashboard for changing auto programs
        autoChooser = new SendableChooser();
        autoChooser.addDefault("Do Nothing (AUTO)", new Auto_DoNothing());
        autoChooser.addObject("Lowbar (AUTO)", new Auto_Lowbar());
        autoChooser.addObject("Lowbar Left Batter (AUTO)", new Auto_LowbarLeft());
        autoChooser.addObject("Lowbar Center Batter (AUTO)", new Auto_LowbarCenter());
        autoChooser.addObject("Chival De Frise (AUTO)", new Auto_ChivalDeFrise());
        autoChooser.addObject("Portcullis (AUTO)", new Auto_Portcullis());
        autoChooser.addObject("Rough Terrain (AUTO)", new Auto_RoughTerrain());
        autoChooser.addObject("Ramparts (AUTO)", new Auto_Rampards());
        autoChooser.addObject("Rock Wall (AUTO)", new Auto_RockWall());
        autoChooser.addObject("Moat (AUTO)", new Auto_Moat());

        posChooser = new SendableChooser();
        posChooser.addDefault("No Shoot", 0);
        posChooser.addObject("Position 1", 1);
        posChooser.addObject("Position 2", 2);
        posChooser.addObject("Position 3", 3);
        posChooser.addObject("Position 4", 4);

        batterChooser = new SendableChooser();
        batterChooser.addDefault("Left", -1);
        batterChooser.addObject("Center", 0);
        batterChooser.addObject("Right", 1);

        SmartDashboard.putData("Autonomous Chooser", autoChooser);
        SmartDashboard.putData("Position Chooser", posChooser);
        SmartDashboard.putData("Batter Chooser", batterChooser);
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        autonomousCommand = (Command) autoChooser.getSelected();
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        // Update the arm subsystem (updates PIDs and such)
        arm.update();

        Scheduler.getInstance().run();
    }

    public void teleopInit() {
        // Cancel the autonomous command (if there was one previously running
        if(autonomousCommand != null)
            autonomousCommand.cancel();
    }

    /**
     * This function is called when the disabled button is hit.
     * You can use it to reset subsystems before shutting down.
     */
    public void disabledInit() {

    }

    /*

     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        // Update the arm
        Robot.arm.update();

        Scheduler.getInstance().run();
    }

    public void testInit() {

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	SmartDashboard.putNumber("The lidar is reading; ", RobotMap.lidarSensor.getData(10).getDistance());

        LiveWindow.run();
    }
}
