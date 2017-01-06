package org.usfirst.frc.team1768.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team1768.robot.OI;
import org.usfirst.frc.team1768.robot.subsystems.Drive;

public class DriveJoystickCommand extends Command {

	public DriveJoystickCommand() {
		requires(Drive.getInstance());
	}

	@Override
	protected void initialize() {
		// Nothing needs to be done on start
	}

	@Override
	protected void execute() {
		Drive.getInstance().setRawMotorSpeed(OI.getInstance().getMotorSpeedValue());
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

	}

}
