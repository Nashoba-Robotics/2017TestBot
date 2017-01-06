
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

	public static final Drive driveSubsystem = new Drive();

	private static Robot singleton;

	public static Robot getInstance() {
		return singleton;
	}

	public SendableChooser modeChooser, motorLFChooser, motorRFChooser, motorLBChooser, motorRBChooser;

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
		joystick, manualInput
	}

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		initSmartDashboard();
		Drive.init();
	}

	public void initSmartDashboard() {
		motorLFChooser = new SendableChooser();
		motorLFChooser.addDefault("Off", motorLFState.off);
		motorLFChooser.addObject("On", motorLFState.on);
		
		motorRFChooser = new SendableChooser();
		motorRFChooser.addDefault("Off", motorRFState.off);
		motorRFChooser.addObject("On", motorRFState.on);
		
		motorLBChooser = new SendableChooser();
		motorLBChooser.addDefault("Off", motorLBState.off);
		motorLBChooser.addObject("On", motorLBState.on);
		
		motorRBChooser = new SendableChooser();
		motorRBChooser.addDefault("Off", motorRBState.off);
		motorRBChooser.addObject("On", motorRBState.on);

		modeChooser = new SendableChooser();
		modeChooser.addDefault("Joystick-controlled", mode.joystick);
		modeChooser.addObject("Manual input control", mode.manualInput);
		SmartDashboard.putData("Choose control mode", modeChooser);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
		Drive.getInstance().setRawMotorSpeed(0);
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
