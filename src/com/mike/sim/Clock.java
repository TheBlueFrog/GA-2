package com.mike.sim;

import com.mike.sim.Agent;
import com.mike.sim.Framework;
import com.mike.sim.Message;
import com.mike.util.Log;
import com.mike.util.LogImp;
import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;

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


    public Clock(Framework f, Long serialNumber) {
        super(f);

        assert serialNumber == 0; // singleton

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

        register();
    }

    @Override
    protected void onMessage(Message msg) {

        assert msg.serialNumber == this.getSerialNumber();

        if (msg.mSender instanceof Clock) {
            time++;

            doClock();//

            try {
                sleep(Main.animation ? 1 : 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if ( ! stopped) {
                // next tick
                send(new Message(this, this, 0, null));
            }
        }
    }

    private void doClock () {
//        Log.d(TAG, String.format("%8d", time));
        if (time > 100)
            stopped = true;
    }

}
