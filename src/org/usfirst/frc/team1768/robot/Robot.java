
package org.usfirst.frc.team1768.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team1768.robot.subsystems.Drive;
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

	public SendableChooser<mode> modeChooser;
	public SendableChooser<motorLFState> motorLFChooser;
	public SendableChooser<motorRFState> motorRFChooser;
	
	public enum motorLFState {
		on, off
	}

	public enum motorRFState {
		on, off
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
		Drive.init();
	}

	public void initSmartDashboard() {
		motorLFChooser = new SendableChooser<motorLFState>();
		motorLFChooser.addDefault("Off", motorLFState.off);
		motorLFChooser.addObject("On", motorLFState.on);
		SmartDashboard.putData("Choose front left motor mode", motorLFChooser);

		motorRFChooser = new SendableChooser<motorRFState>();
		motorRFChooser.addDefault("Off", motorRFState.off);
		motorRFChooser.addObject("On", motorRFState.on);
		SmartDashboard.putData("Choose front right motor mode", motorRFChooser);

		modeChooser = new SendableChooser<mode>();
		modeChooser.addObject("Manual input control", mode.manualInput);
		modeChooser.addObject("Tank-drive Input", mode.tankDrive);
		modeChooser.addObject("Arcade-drive Input", mode.arcadeDrive);
		SmartDashboard.putData("Choose control mode", modeChooser);
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
		OI.getInstance().speedMultiplier = SmartDashboard.getNumber("Speed Multiplier", 0);
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
