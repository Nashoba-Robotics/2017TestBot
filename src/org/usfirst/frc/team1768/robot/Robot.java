
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
	public SendableChooser<motorLBState> motorLBChooser;
	public SendableChooser<motorRBState> motorRBChooser;
	
	public enum motorLFState {
		on, off
	}

	public enum motorRFState {
		on, off
	}

	public enum motorLBState {
		on, off
	}

	public enum motorRBState {
		on, off
	}

	public enum mode {
		joystick, manualInput, tankDrive, arcadeDrive
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		singleton = new Robot();
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

		motorLBChooser = new SendableChooser<motorLBState>();
		motorLBChooser.addDefault("Off", motorLBState.off);
		motorLBChooser.addObject("On", motorLBState.on);
		SmartDashboard.putData("Chose back left motor mode", motorLBChooser);

		motorRBChooser = new SendableChooser<motorRBState>();
		motorRBChooser.addDefault("Off", motorRBState.off);
		motorRBChooser.addObject("On", motorRBState.on);
		SmartDashboard.putData("Choose back right motor mode", motorRBChooser);

		modeChooser = new SendableChooser<mode>();
		modeChooser.addDefault("Joystick-controlled", mode.joystick);
		modeChooser.addObject("Manual input control", mode.manualInput);
		modeChooser.addObject("Tank-drive Input", mode.tankDrive);
		modeChooser.addObject("Arcade-drive Input", mode.arcadeDrive);
		SmartDashboard.putData("Choose control mode", modeChooser);
		
		SmartDashboard.putNumber("talonLF Enc: ", Drive.getInstance().talonLFEncVal);
		SmartDashboard.putNumber("talonRF Enc: ", Drive.getInstance().talonRFENCVal);
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
