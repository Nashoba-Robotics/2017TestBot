
package org.usfirst.frc.team1768.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team1768.robot.subsystems.PureVoltageControl;
import org.usfirst.frc.team1768.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public Command driveWall;

	private static Robot singleton;

	public static Robot getInstance() {
		return singleton;
	}

	public SendableChooser<controlState> controlChooser;

	public enum controlState {
		shooter, voltage
	}
	
	public enum mode {
		manualInput, tankDrive, arcadeDrive
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		if (singleton == null)
			singleton = this;
		initSmartDashboard();
		Shooter.init();
		PureVoltageControl.init();
	}

	public void initSmartDashboard() {
		controlChooser = new SendableChooser<controlState>();
		controlChooser.addDefault("Shooter mode", controlState.shooter);
		controlChooser.addObject("Voltage mode", controlState.voltage);
		SmartDashboard.putData("Choose control mode", controlChooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
		}
	}
}
