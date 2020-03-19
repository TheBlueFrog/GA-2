package com.mike.sim;

import com.mike.util.Log;
import com.mike.util.LogImp;

/**
 * Created by mike on 6/17/2016.
 *
 * A global clock for simulation time
 */
public class Clock extends Thread {

    static private final String TAG = Clock.class.getSimpleName();

    private long time = 0;
    private boolean stopped = false;
	private final Framework framework;
	
	public long getTime () { return time; }

    public Clock(Framework f) {
		framework = f;
    }

    @Override
	public void run() {
		do {
            time++;

            try {
                sleep(1000);
				Main.repaint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		while ( ! stopped);
    }

}
