package org.usfirst.frc.team1768.robot.commands;

import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.RobotMap;
import org.usfirst.frc.team1768.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lib.NRCommand;

public class ShooterConstantSpeedCommand extends NRCommand{

	public ShooterConstantSpeedCommand() {
		requires(Shooter.getInstance());
	}

	@Override
	protected void onExecute() {
		switch (Robot.getInstance().controlChooser.getSelected()) {
		case shooter:
			Shooter.getInstance().setMotorSpeed(SmartDashboard.getNumber("Goal Shooter Speed", RobotMap.SHOOTER_GOAL_SPEED));
			//Shooter.getInstance().setMotorSpeed(RobotMap.SHOOTER_GOAL_SPEED);
			break;
		case voltage:
			break;
		}
	}
}
