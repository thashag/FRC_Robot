
package org.usfirst.frc.team2557.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team2557.robot.commands.*;
import org.usfirst.frc.team2557.robot.commands.autonomous.Auto_DoNothing;
import org.usfirst.frc.team2557.robot.commands.autonomous.groups.Auto_Lowbar_Group;
import org.usfirst.frc.team2557.robot.subsystems.*;

import java.io.IOException;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	//Subsystem Declarations//
    public static OI 				oi;
    public static Chassis chassis;
    public static ManipulatorSub 	manipulatorSub;
    public static Camera camera;
    public static SmartDashboardSub smartDashboardSub;

    //Command Declarations//
    Command armConfigurationCommand;
    Command autonomousCommand;
    Command catapultCommand;
    Command climbTower;
    Command driveCommand;
    Command intakeCommand;
    Command ledUpdateCommand;
    Command secondArmRelease;
    Command smartDashboardCommand;

    SendableChooser autoChooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // Initialize RobotMap
        RobotMap.init();

        // Change LEDs to Yellow
//        RobotMap.arduinoComm.changeMode(ArduinoComm.LightsMode.SafetyYellow);

        // Run GRIP
        try {
            new ProcessBuilder("/home/lvuser/start_grip").inheritIO().start();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        //Subsystem Connections//
        chassis = new Chassis();
        manipulatorSub 			= new ManipulatorSub();
        camera = new Camera();
        smartDashboardSub 		= new SmartDashboardSub();
        //Command Connections//
        armConfigurationCommand = new ArmConfigurationCommand();
        catapultCommand 		= new CatapultCommand();
        climbTower 				= new ClimbTowerCommand();
        driveCommand 			= new DriveCommand();
        intakeCommand 			= new IntakeCommand();
        ledUpdateCommand        = new LEDUpdateCommand();
        secondArmRelease 		= new SecondArmReleaseCommand();
        smartDashboardCommand 	= new SmartDashboardCommand();
        
        oi 						= new OI();

        // Make a SendableChooser on the SmartDashboard for changing auto programs
        autoChooser 			= new SendableChooser();
        autoChooser.addDefault("Do Nothing", new Auto_DoNothing());
        autoChooser.addDefault("Lowbar", new Auto_Lowbar_Group());
        SmartDashboard.putData("Autonomous Chooser", autoChooser);
    }

    public void disabledPeriodic() {
        Scheduler.getInstance().run();
    }

    public void autonomousInit() {
        autonomousCommand = (Command) autoChooser.getSelected();
        autonomousCommand.start();

        ledUpdateCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        // Update distance estimator
        RobotMap.distanceEstimator.update();

        Scheduler.getInstance().run();

        // Update Lidar NetworkTables
        RobotMap.LidarSensor.updateNetworkTables();
    }

    public void teleopInit() {
        if(autonomousCommand != null)
            autonomousCommand.cancel();

        // Start the drive command
        ledUpdateCommand.start();
        armConfigurationCommand.start();
        catapultCommand.start();
        climbTower.start();
        driveCommand.start();
        intakeCommand.start();
        secondArmRelease.start();
//        if(oi.manipulatorLJB.get()){
//        	RobotMap.leftActuatorMotor.setEncPosition(0);
//        	RobotMap.rightActuatorMotor.setEncPosition(0);
//        }
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
        Scheduler.getInstance().run();
        
        smartDashboardCommand.start();

        // Update Lidar NetworkTables
        RobotMap.LidarSensor.updateNetworkTables();
        
    }

    public void testInit() {

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();

        // Update Lidar NetworkTables
        RobotMap.LidarSensor.updateNetworkTables();
    }
}
