package org.usfirst.frc.team1768.robot.subsystems;

import org.usfirst.frc.team1768.robot.Robot;
import org.usfirst.frc.team1768.robot.RobotMap;
import org.usfirst.frc.team1768.robot.commands.DriveJoystickCommand;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import lib.NRMath;
import lib.NRSubsystem;
import lib.Periodic;
import lib.SmartDashboardSource;

public class Drive extends NRSubsystem implements SmartDashboardSource, Periodic {
	public static boolean driveEnabled = true;

	private static Drive singleton;
	CANTalon talonLF, talonRF, talonLB, talonRB;

	double leftMotorSetPoint = 0;
	double rightMotorSetPoint = 0;
	
	public double talonLFEncVal;
	public double talonRFENCVal;

	private static final double turn_F = 0;
	private static final double turn_P = 0;
	private static final double turn_I = 0;
	private static final double turn_D = 0;

	private static final int ticksPerRev = 0;

	private Drive() {
		if (driveEnabled) {
			talonLF = new CANTalon(RobotMap.talonLF);
			talonLF.enableBrakeMode(true);
			talonLF.changeControlMode(TalonControlMode.PercentVbus);
			talonLF.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talonLF.setF(turn_F);
			talonLF.setP(turn_P);
			talonLF.setI(turn_I);
			talonLF.setD(turn_D);
			talonLF.configEncoderCodesPerRev(ticksPerRev);
			talonLFEncVal = talonLF.getEncPosition();

			talonRF = new CANTalon(RobotMap.talonRF);
			talonRF.enableBrakeMode(true);
			talonRF.changeControlMode(TalonControlMode.PercentVbus);
			talonRF.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talonRF.setF(turn_F);
			talonRF.setP(turn_P);
			talonRF.setI(turn_I);
			talonRF.setD(turn_D);
			talonRF.configEncoderCodesPerRev(ticksPerRev);
			talonRFENCVal = talonRF.getEncPosition();
			

			talonLB = new CANTalon(RobotMap.talonLB);
			talonLB.enableBrakeMode(true);
			talonLB.changeControlMode(TalonControlMode.Follower);
			talonLB.set(talonLF.getDeviceID());
			/*talonLB.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talonLB.setF(turn_F);
			talonLB.setP(turn_P);
			talonLB.setI(turn_I);
			talonLB.setD(turn_D);
			talonLB.configEncoderCodesPerRev(ticksPerRev);*/

			talonRB = new CANTalon(RobotMap.talonRB);
			talonRB.enableBrakeMode(true);
			talonRB.changeControlMode(TalonControlMode.Follower);
			talonRB.set(talonRF.getDeviceID());
			/*talonRB.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talonRB.setF(turn_F);
			talonRB.setP(turn_P);
			talonRB.setI(turn_I);
			talonRB.setD(turn_D);
			talonRB.configEncoderCodesPerRev(ticksPerRev);*/
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
		leftMotorSetPoint = -left;// * rpm;

		System.out.println(Robot.getInstance().modeChooser.getSelected());
		
		switch ((Robot.mode) Robot.getInstance().modeChooser.getSelected()) {
		case tankDrive:
		case arcadeDrive:
			talonLF.set(leftMotorSetPoint);
			talonRF.set(right);
			talonLB.set(leftMotorSetPoint);
			talonRB.set(right);
			break;
		case manualInput:
		case joystick:
			switch ((Robot.motorLFState) Robot.getInstance().motorLFChooser.getSelected()) {
			case on:
				if (talonLF.getControlMode() == TalonControlMode.PercentVbus)
					talonLF.set(leftMotorSetPoint/* \*rpm */);
				else
					talonLF.set(leftMotorSetPoint);
				break;
			case off:
				talonLF.set(0);
				break;
			}
			switch ((Robot.motorLBState) Robot.getInstance().motorLBChooser.getSelected()) {
			case on:
				if (talonLB.getControlMode() == TalonControlMode.PercentVbus)
					talonLB.set(leftMotorSetPoint/* \*rpm */);
				else
					talonLB.set(leftMotorSetPoint);
				break;
			case off:
				talonLB.set(0);
				break;
			}
			switch ((Robot.motorRFState) Robot.getInstance().motorRFChooser.getSelected()) {
			case on:
				if (talonRF.getControlMode() == TalonControlMode.PercentVbus)
					talonRF.set(leftMotorSetPoint/* \*rpm */);
				else
					talonRF.set(leftMotorSetPoint);
				break;
			case off:
				talonRF.set(0);
				break;
			}
			switch ((Robot.motorRBState) Robot.getInstance().motorRBChooser.getSelected()) {
			case on:
				if (talonRB.getControlMode() == TalonControlMode.PercentVbus)
					talonRB.set(leftMotorSetPoint/* \*rpm */);
				else
					talonRB.set(leftMotorSetPoint);
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
		// TODO Auto-generated method stub

	}

	@Override
	public void smartDashboardInfo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void disable() {
		Drive.getInstance().setMotorSpeed(0, 0);

	}
}