package edu.nr.robotics.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.StatusFrameRate;
import com.ctre.CANTalon.TalonControlMode;

import edu.nr.lib.DoNothingCommand;
import edu.nr.lib.NRMath;
import edu.nr.lib.NRSubsystem;
import edu.nr.lib.Periodic;
import edu.nr.lib.SmartDashboardSource;
import edu.nr.lib.interfaces.DoublePIDOutput;
import edu.nr.lib.interfaces.DoublePIDSource;
import edu.nr.lib.motionprofiling.OneDimensionalMotionProfilerTwoMotor;
import edu.nr.lib.motionprofiling.OneDimensionalTrajectorySimple;
import edu.nr.robotics.OI;
import edu.nr.robotics.Robot;
import edu.nr.robotics.RobotMap;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive extends NRSubsystem implements SmartDashboardSource, Periodic, DoublePIDOutput, DoublePIDSource {
	public static boolean driveEnabled = true;

	private static Drive singleton;

	public CANTalon talonLF;
	public CANTalon talonRF;
	public CANTalon talonLB;
	public CANTalon talonRB;

	public double leftMotorSetPoint = 0;
	public double rightMotorSetPoint = 0;

	public final double turn_F_LEFT = 0.873;
	public final double turn_F_RIGHT = 0.892;
	public final double turn_P_LEFT = 0.0;
	public final double turn_I_LEFT = 0;
	public final double turn_D_LEFT = 0;
	public final double turn_P_RIGHT = 0;
	public final double turn_I_RIGHT = 0;
	public final double turn_D_RIGHT = 0;
	
	private static final int ticksPerRev = 1024;
	
	OneDimensionalMotionProfilerTwoMotor profiler;

	PIDSourceType type = PIDSourceType.kRate;
	
	private double ka, kp, kd, kv, kp_theta;
	
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
			talonLB.setStatusFrameRateMs(StatusFrameRate.Feedback, 1);

			talonRB = new CANTalon(RobotMap.talonRB);
			talonRB.enableBrakeMode(true);
			talonRB.changeControlMode(TalonControlMode.Speed);
			talonRB.setFeedbackDevice(FeedbackDevice.QuadEncoder);
			talonRB.setF(turn_F_RIGHT);
			talonRB.setP(turn_P_RIGHT);
			talonRB.setI(turn_I_RIGHT);
			talonRB.setD(turn_D_RIGHT);
			talonRB.configEncoderCodesPerRev(ticksPerRev);
			talonRB.setStatusFrameRateMs(StatusFrameRate.Feedback, 1);

			talonLF = new CANTalon(RobotMap.talonLF);
			talonLF.enableBrakeMode(true);
			talonLF.changeControlMode(TalonControlMode.Follower);
			talonLF.set(talonLB.getDeviceID());

			talonRF = new CANTalon(RobotMap.talonRF);
			talonRF.enableBrakeMode(true);
			talonRF.changeControlMode(TalonControlMode.Follower);
			talonRF.set(talonRB.getDeviceID());
			
			profiler = new OneDimensionalMotionProfilerTwoMotor(this, this, kv, ka, kp, kd, kp_theta);
			
			new Thread( new Runnable() {
				@Override
				public void run() {
					while(true) {
						SmartDashboard.putNumber("rightRPM", talonRB.getSpeed());
						SmartDashboard.putNumber("leftRPM", -talonLB.getSpeed());
						try {
							java.util.concurrent.TimeUnit.MILLISECONDS.sleep(3);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}).start();
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
		pidWrite(leftMotorSpeed, rightMotorSpeed);
	}
		
	@Override
	public void pidWrite(double left, double right) {
		talonLB.set(-left * RobotMap.MAX_RPM);
		talonRB.set(right * RobotMap.MAX_RPM);
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DoNothingCommand());
	}

	@Override
	public void periodic() {
		
	}

	@Override
	public void smartDashboardInfo() {
	}

	@Override
	public void disable() {
		Drive.getInstance().pidWrite(0, 0);
		Command c = getCurrentCommand();
		if(c != null) {
			c.cancel();
		}
		disableProfiler();
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		type = pidSource;
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return type;
	}

	@Override
	public double pidGetLeft() {
		if(type == PIDSourceType.kRate) {
			return talonLB.getSpeed();
		} else {
			return talonLB.getPosition();
		}
	}

	@Override
	public double pidGetRight() {
		if(type == PIDSourceType.kRate) {
			return talonRB.getSpeed();
		} else {
			return talonRB.getPosition();
		}
	}

	public void enableProfiler() {
		profiler.setTrajectory(new OneDimensionalTrajectorySimple(10 /*rotations*/, RobotMap.MAX_RPM, RobotMap.MAX_RPM/2, RobotMap.MAX_ACC));
		profiler.enable();
	}
	
	public void disableProfiler() {
		profiler.disable();
	}
}