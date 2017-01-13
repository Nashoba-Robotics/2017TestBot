package org.usfirst.frc.team1768.robot.subsystems;

import org.usfirst.frc.team1768.robot.OI;
import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.RobotMap;
import org.usfirst.frc.team1768.robot.commands.DriveJoystickCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import lib.NRMath;
import lib.NRSubsystem;
import lib.Periodic;
import lib.SmartDashboardSource;

public class Drive extends NRSubsystem implements SmartDashboardSource, Periodic {
	public static boolean driveEnabled = true;

	private static Drive singleton;

	public CANTalon talonLF;
	public CANTalon talonRF;
	public CANTalon talonLB;
	public CANTalon talonRB;

	double leftMotorSetPoint = 0;
	double rightMotorSetPoint = 0;

	public double turn_F_LEFT = 0.82;
	public double turn_F_RIGHT = 1.0;
	public double turn_P_LEFT = 0.0;
	public double turn_I_LEFT = 0;
	public double turn_D_LEFT = 0;
	public double turn_P_RIGHT = 0;
	public double turn_I_RIGHT = 0;
	public double turn_D_RIGHT = 0;
	
	private static final int ticksPerRev = 1024;

	private Drive() {
		if (driveEnabled) {
			talonLB = new CANTalon(RobotMap.talonLB);
			talonLB.enableBrakeMode(true);
			talonLB.changeControlMode(TalonControlMode.Speed);
			talonLB.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talonLB.setF(turn_F_LEFT);
			talonLB.setP(turn_P_LEFT);
			talonLB.setI(turn_I_LEFT);
			talonLB.setD(turn_D_LEFT);
			talonLB.configEncoderCodesPerRev(ticksPerRev);

			talonRB = new CANTalon(RobotMap.talonRB);
			talonRB.enableBrakeMode(true);
			talonRB.changeControlMode(TalonControlMode.Speed);
			talonRB.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talonRB.setF(turn_F_RIGHT);
			talonRB.setP(turn_P_RIGHT);
			talonRB.setI(turn_I_RIGHT);
			talonRB.setD(turn_D_RIGHT);
			talonRB.configEncoderCodesPerRev(ticksPerRev);

			talonLF = new CANTalon(RobotMap.talonLF);
			talonLF.enableBrakeMode(true);
			talonLF.changeControlMode(TalonControlMode.Follower);
			talonLF.set(talonLB.getDeviceID());

			talonRF = new CANTalon(RobotMap.talonRF);
			talonRF.enableBrakeMode(true);
			talonRF.changeControlMode(TalonControlMode.Follower);
			talonRF.set(talonRB.getDeviceID());
		
			SmartDashboard.putNumber("P Left", 0);
			SmartDashboard.putNumber("I Left", 0);
			SmartDashboard.putNumber("D Left", 0);
			SmartDashboard.putNumber("P Right", 0);
			SmartDashboard.putNumber("I Right", 0);
			SmartDashboard.putNumber("D Right", 0);
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

	/**
	 * Sets left and right motor speeds to the speeds needed for the given move
	 * and turn values, multiplied by the OI speed multiplier if the speed
	 * multiplier parameter is true. If you don't care about the speed
	 * multiplier parameter, you might want to use {@link arcadeDrive(double
	 * move, double turn)}
	 * 
	 * @param move
	 *            The speed, from -1 to 1 (inclusive), that the robot should go
	 *            at. 1 is max forward, 0 is stopped, -1 is max backward
	 * @param turn
	 *            The speed, from -1 to 1 (inclusive), that the robot should
	 *            turn at. 1 is max right, 0 is stopped, -1 is max left
	 * @param speedMultiplier
	 *            whether or not to use the OI speed multiplier It should really
	 *            only be used for operator driving
	 * 
	 */
	public void arcadeDrive(double move, double turn, boolean speedMultiplier) {
		move = NRMath.limit(move);
		turn = NRMath.limit(turn);
		double leftMotorSpeed, rightMotorSpeed;
		rightMotorSpeed = leftMotorSpeed = move;
		leftMotorSpeed += turn;
		rightMotorSpeed -= turn;

		if (move > 0.0) {
			if (turn > 0.0) {
				leftMotorSpeed = move - turn;
				rightMotorSpeed = Math.max(move, turn);
			} else {
				leftMotorSpeed = Math.max(move, -turn);
				rightMotorSpeed = move + turn;
			}
		} else {
			if (turn > 0.0) {
				leftMotorSpeed = -Math.max(-move, turn);
				rightMotorSpeed = move + turn;
			} else {
				leftMotorSpeed = move - turn;
				rightMotorSpeed = -Math.max(-move, -turn);
			}
		}
		setMotorSpeed(leftMotorSpeed, rightMotorSpeed);
	}

	public void setMotorSpeed(double left, double right) {
		leftMotorSetPoint = -left * OI.getInstance().speedMultiplier;
		rightMotorSetPoint = right * OI.getInstance().speedMultiplier;
		
		SmartDashboard.putString("rightRPM", talonRB.getSpeed() + "  :  " + rightMotorSetPoint * RobotMap.MAX_RPM);
		SmartDashboard.putString("leftRPM", -talonLB.getSpeed() + "  :  " + leftMotorSetPoint * RobotMap.MAX_RPM);
		
		switch ((Robot.mode) Robot.getInstance().modeChooser.getSelected()) {
		case tankDrive:
		case arcadeDrive:
			if (talonLB.getControlMode() == TalonControlMode.Speed)
				talonLB.set(leftMotorSetPoint * RobotMap.MAX_RPM);
			else
				talonLB.set(leftMotorSetPoint);
			if (talonRB.getControlMode() == TalonControlMode.Speed)
				talonRB.set(rightMotorSetPoint * RobotMap.MAX_RPM);
			else
				talonRB.set(rightMotorSetPoint);
			break;
		case manualInput:
			switch ((Robot.motorLFState) Robot.getInstance().motorLFChooser.getSelected()) {
			case on:
				if (talonLB.getControlMode() == TalonControlMode.Speed)
					talonLB.set(leftMotorSetPoint * RobotMap.MAX_RPM);
				else
					talonLB.set(leftMotorSetPoint);
				break;
			case off:
				talonLB.set(0);
				break;
			}
			switch ((Robot.motorRFState) Robot.getInstance().motorRFChooser.getSelected()) {
			case on:
				if (talonRB.getControlMode() == TalonControlMode.Speed)
					talonRB.set(rightMotorSetPoint * RobotMap.MAX_RPM);
				else
					talonRB.set(rightMotorSetPoint);
				break;
			case off:
				talonRB.set(0);
				break;
			}
			break;
		}
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveJoystickCommand());
	}

	@Override
	public void periodic() {

	}

	@Override
	public void smartDashboardInfo() {

	}

	@Override
	public void disable() {
		Drive.getInstance().setMotorSpeed(0, 0);

	}
}