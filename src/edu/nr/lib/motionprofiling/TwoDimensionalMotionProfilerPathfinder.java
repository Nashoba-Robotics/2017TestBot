package edu.nr.lib.motionprofiling;

import java.util.Timer;
import java.util.TimerTask;

import edu.nr.lib.AngleGyroCorrection;
import edu.nr.lib.interfaces.DoublePIDOutput;
import edu.nr.lib.interfaces.DoublePIDSource;
import edu.nr.lib.interfaces.GyroCorrection;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class TwoDimensionalMotionProfilerPathfinder extends TimerTask  {

	private final Timer timer;
	
	//In milliseconds
	private final long period;
	private static final long defaultPeriod = 5; //200 Hz 
		
	private boolean enabled = false;
	private DoublePIDOutput out;
	private DoublePIDSource source;
	
	private double ka, kp, kd, kv, kp_theta;
	
	private int initialPositionLeft;
	private int initialPositionRight;
			
	private Trajectory trajectory;
	private Trajectory.Config trajectoryConfig;
	private TankModifier modifier;
	
	GyroCorrection gyroCorrection;
	
	EncoderFollower left;
	EncoderFollower right;
	
	Waypoint[] points;
	
	int encoderTicksPerRevolution;
	double wheelDiameter;

		
	public TwoDimensionalMotionProfilerPathfinder(DoublePIDOutput out, DoublePIDSource source, double kv, double ka, double kp, double kd, double kp_theta, double max_velocity, double max_acceleration, double max_jerk, int encoderTicksPerRevolution, double wheelDiameter, long period) {
		this.out = out;
		this.source = source;
		this.period = period;
		this.trajectoryConfig = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, this.period, max_velocity, max_acceleration, max_jerk);
		this.points = new Waypoint[] {};
		this.trajectory = Pathfinder.generate(points, trajectoryConfig);
		this.modifier = new TankModifier(trajectory).modify(0.5);
		this.left = new EncoderFollower(modifier.getLeftTrajectory());
		this.right = new EncoderFollower(modifier.getRightTrajectory());
		timer = new Timer();
		timer.schedule(this, 0, this.period);
		this.source.setPIDSourceType(PIDSourceType.kDisplacement);
		this.ka = ka;
		this.kp = kp;
		this.kd = kd;
		this.kv = kv;
		this.kp_theta = kp_theta;
		this.encoderTicksPerRevolution = encoderTicksPerRevolution;
		this.initialPositionLeft = (int) (source.pidGetLeft() * encoderTicksPerRevolution);
		this.initialPositionRight = (int) (source.pidGetRight() * encoderTicksPerRevolution);
		this.gyroCorrection = new AngleGyroCorrection();
		this.wheelDiameter = wheelDiameter;
		reset();
	}
	
	public TwoDimensionalMotionProfilerPathfinder(DoublePIDOutput out, DoublePIDSource source, double kv, double ka, double kp, double kd, double kp_theta, double max_velocity, double max_acceleration, double max_jerk, int encoderTicksPerRevolution, double wheelDiameter) {
		this(out, source, kv, ka, kp, kd, kp_theta, max_velocity, max_acceleration, max_jerk, encoderTicksPerRevolution, wheelDiameter, defaultPeriod);
	}
	
	double timeOfVChange = 0;
	double prevV;
	
	@Override
	public void run() {
		if(enabled) {
			double outputLeft = left.calculate((int) (source.pidGetLeft() * encoderTicksPerRevolution));
			double outputRight = right.calculate((int) (source.pidGetRight() * encoderTicksPerRevolution));

			double currentHeading = gyroCorrection.getAngleErrorDegrees();
			double desiredHeading = Pathfinder.r2d(left.getHeading());
			
			double angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - currentHeading);
			double turn = kp_theta * angleDifference;
						
			out.pidWrite(outputLeft - turn, outputRight + turn);
			
			source.setPIDSourceType(PIDSourceType.kRate);
			SmartDashboard.putNumber("Motion Profiler V Left", source.pidGetLeft());
			SmartDashboard.putNumber("Motion Profiler V Right", source.pidGetRight());
			source.setPIDSourceType(PIDSourceType.kDisplacement);
			SmartDashboard.putNumber("Motion Profiler X Left", source.pidGetLeft());
			SmartDashboard.putNumber("Motion Profiler X Right", source.pidGetRight());
		}
	}
		
	/**
	 * Stop the profiler from running and resets it
	 */
	public void disable() {
		enabled = false;
		reset();
	}
	
	/**
	 * Reset the profiler and start it running
	 */
	public void enable() {
		enabled = true;
		reset();
	}
	
	/**
	 * Reset the previous time to the current time.
	 * Doesn't disable the controller
	 */
	public void reset() {
		PIDSourceType type = source.getPIDSourceType();
		source.setPIDSourceType(PIDSourceType.kDisplacement);
		initialPositionLeft = (int) source.pidGetLeft() * encoderTicksPerRevolution;
		initialPositionRight = (int) source.pidGetRight() * encoderTicksPerRevolution;
		left.configureEncoder(initialPositionLeft, encoderTicksPerRevolution, wheelDiameter);
		right.configureEncoder(initialPositionRight, encoderTicksPerRevolution, wheelDiameter);
		left.configurePIDVA(kp, 0.0, kd, kv, ka);
		right.configurePIDVA(kp, 0.0, kd, kv, ka);
		source.setPIDSourceType(type);
		gyroCorrection.clearInitialValue();
	}
	
	/**
	 * Sets the trajectory for the profiler
	 * @param trajectory
	 */
	public void setTrajectory(Waypoint[] points) {
		this.points = points;
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	public void setKA(double ka) {
		this.ka = ka;
	}
	
	public void setKP(double kp) {
		this.kp = kp;
	}
	
	public void setKD(double kd) {
		this.kd = kd;
	}

	public void setKV(double kv) {
		this.kv = kv;
	}

	public void setKP_theta(double kp_theta) {
		this.kp_theta = kp_theta;
	}
	
}
