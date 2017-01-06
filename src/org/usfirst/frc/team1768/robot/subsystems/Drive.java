package org.usfirst.frc.team1768.robot.subsystems;

import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.RobotMap;
import org.usfirst.frc.team1768.robot.commands.DriveJoystickCommand;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Drive extends Subsystem {
	public static boolean driveEnabled = false;

	private static Drive singleton;
	CANTalon talonLF, talonRF, talonLB, talonRB;

	public Drive() {
		if (driveEnabled) {
			talonLF = new CANTalon(RobotMap.talonLF);
			talonRF = new CANTalon(RobotMap.talonRF);
			talonLB = new CANTalon(RobotMap.talonLB);
			talonRB = new CANTalon(RobotMap.talonRB);

			talonLF.enableBrakeMode(true);
			talonRF.enableBrakeMode(true);
			talonLB.enableBrakeMode(true);
			talonRB.enableBrakeMode(true);
		}
	}

	public static Drive getInstance() {
		init();
		return singleton;
	}

	public static void init() {
		if (singleton == null) {
			singleton = new Drive();
		}
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveJoystickCommand());
	}
	
	/**
	 * Sets the motor speed for the left and right motors A raw motor speed is
	 * actually a scaled voltage value
	 * 
	 * @param left
	 *            the left motor speed, from -1 to 1
	 * @param right
	 *            the right motor speed, from -1 to 1
	 */
	public void setRawMotorSpeed(double input) {
		switch ((Robot.motorLFState) Robot.getInstance().motorLFChooser.getSelected()) {
		case on:
			Drive.getInstance().talonLF.set(input);
		case off:
			Drive.getInstance().talonLF.set(0);
		}
		switch ((Robot.motorRFState) Robot.getInstance().motorRFChooser.getSelected()) {
		case on:
			Drive.getInstance().talonRF.set(input);
		case off:
			Drive.getInstance().talonRF.set(0);
		}
		switch ((Robot.motorLBState) Robot.getInstance().motorLBChooser.getSelected()) {
		case on:
			Drive.getInstance().talonLB.set(input);
		case off:
			Drive.getInstance().talonLB.set(0);
		}
		switch ((Robot.motorRBState) Robot.getInstance().motorRBChooser.getSelected()) {
		case on:
			Drive.getInstance().talonRB.set(input);
		case off:
			Drive.getInstance().talonRB.set(0);
		}
	}
}