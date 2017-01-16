package org.usfirst.frc.team1768.robot.subsystems;

import edu.wpi.first.wpilibj.I2C;

public class RaspberryPi {

	private I2C i2c;
	private byte[] distance;
	private byte[] turnAngle;
	private Thread updater;

	private final int PI_ADDRESS = 0x00;
	//private final int PI_CONFIG_REGISTER = 0x00;
	private final int PI_DISTANCE_REGISTER = 0x5f;
	private final int PI_ANGLE_REGISTER = 0x6f;

	private int distanceValue; // sixteenth of an inch
	private int turnAngleValue; // hundredth of a degree

	public RaspberryPi(I2C.Port port) {
		i2c = new I2C(port, PI_ADDRESS);

		distance = new byte[4];
		turnAngle = new byte[4];
	}

	/**
	 * Gets distance in inches from target
	 **/
	public double getDistance() {
		return (double) (distanceValue) / 16.0;
	}

	/**
	 * Gets angle to turn in degrees
	 * 
	 * Positive angle means we have to turn left
	 */
	public double getAngle() {
		return (double) (turnAngleValue) / 100.0;
	}

	public void start() {
		if (updater == null)
			updater = new Thread(new PiUpdater());

		if (!updater.isAlive() && !updater.isInterrupted())
			updater.start();
	}

	public void start(int period) {
		updater = new Thread(new PiUpdater(period));

		start();
	}

	public void stop() {
		if (updater != null) {
			updater.interrupt();
		}
		updater = null; // Need to make a new thread when we start again since
						// we just interrupted the last one
	}

	private boolean previousWriteSuccess = false;

	public void update() {
		if (previousWriteSuccess) {
			i2c.read(PI_DISTANCE_REGISTER, 4, distance); // Read in measurement
			distanceValue = (int) ((distance[0] << 24) + (distance[1] << 16) + (distance[2] << 8) + distance[3]);

			i2c.read(PI_ANGLE_REGISTER, 4, turnAngle);
			distanceValue = (int) ((turnAngle[0] << 24) + (turnAngle[1] << 16) + (turnAngle[2] << 8) + turnAngle[3]);
		}
		//previousWriteSuccess = !i2c.write(PI_CONFIG_REGISTER, 0xFF);
	}

	// Timer task to keep distance updated
	private class PiUpdater implements Runnable {
		private int period = 10; // Default of 100Hz

		public PiUpdater() {// Provides a default value for period
		}

		public PiUpdater(int period) {
			this.period = period;
		}

		@Override
		public void run() {
			while (true) {
				update();
				try {
					Thread.sleep(period);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
}
