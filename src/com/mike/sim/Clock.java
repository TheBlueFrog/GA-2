package com.mike.sim;

import com.mike.util.Log;
import com.mike.util.LogImp;

/**
 * Created by mike on 6/17/2016.
 *
 * A global clock for simulation time
 */
public class Clock extends Agent {

    static private final String TAG = Clock.class.getSimpleName();

    static private long time = 0;
    static public long getTime () { return time; }

    @Override
    protected String getClassName() {
        return null;
    }


    public Clock(Framework f) {
        super(f);

        LogImp _d = new LogImp() {
            @Override
            public void d(String tag, String msg) {
                System.out.println(String.format("%8d %30s: %s", time, tag, msg));
            }
        };
        LogImp _e = new LogImp() {
            @Override
            public void d(String tag, String msg) {
                System.out.println(String.format("%8d %30s: ERROR %s", time, tag, msg));
            }
        };

        Log.set_d(_d);
        Log.set_e(_e);

        send(new Message(this, this, null));

    }

    @Override
    protected void onMessage(Message msg) {

    	if (msg.mSender.getClass() != Clock.class) {
    		// we shouldn't get messages from anyone except ourself...
			Log.d(TAG, String.format("Msg from %s", msg.mSender.getClass().getSimpleName()));
		}
    	
        if (msg.mSender instanceof Clock) {
            time++;

            doClock(msg);//

            try {
                sleep(Main.animation ? 1 : 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if ( ! stopped) {
                // next tick
                send(new Message(this, this, null));
            }
        }
    }

    private void doClock(Message msg) {
        mFramework.forwardClock(msg);

//        if (time > 1000)
//            stopped = true;
    }

}
