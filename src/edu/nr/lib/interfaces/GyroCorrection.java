package edu.nr.lib.interfaces;

public abstract class GyroCorrection
{
	private static final double DEFAULT_KP_THETA = 0.05, MAX_ANGLE_CORRECTION_DEGREES = 3;
	private boolean initialized = false;
	
	public double getTurnValue(double kP_theta)
	{
		if(initialized == false)
		{
			reset();
			initialized = true;
		}
		
		double turn = getAngleErrorDegrees();
    	if(turn<0)
    		turn = -Math.min(MAX_ANGLE_CORRECTION_DEGREES, -turn);
    	else
    		turn = Math.min(MAX_ANGLE_CORRECTION_DEGREES, turn);
    	
    	return turn * kP_theta;
	}
	
	public double getTurnValue()
	{
		return this.getTurnValue(DEFAULT_KP_THETA);
	}
	
	public abstract double getAngleErrorDegrees();
	public abstract void reset();
		
	/**
	 * Causes the initial angle value to be reset the next time getTurnValue() is called. Use this in the end() and interrupted()
	 * functions of commands to make sure when the commands are restarted, the initial angle value is reset.
	 */
	public void clearInitialValue()
	{
		initialized = false;
	}
}
