package com.mike.sim;

import com.mike.util.Location;
import com.mike.util.Log;

import java.awt.*;

public class Bug extends LocatedAgent {

    private static final String TAG = Bug.class.getSimpleName();

    public Bug(Framework f, java.lang.Long serialNumber) {
        super(f, serialNumber);

        register();
    }

    @Override
    void paint(Graphics2D g2) {
        g2.drawRect(
                (int) Location.meter2PixelX(location.x),
                (int) Location.meter2PixelY(location.y),
                10, 10);
    }

    @Override
    protected void onMessage(Message msg) {

        assert msg.serialNumber == this.getSerialNumber();

        if ((msg.mSender == null) && (((Framework.State) msg.mMessage)).equals(Framework.State.AgentsRunning)) {
            // frameworks says everyone is ready
            Log.d(TAG, "Now running");
            return;
        }

        if (msg.mSender instanceof Clock) {
            doStep();
        }

    }

    private void doStep() {
//        Log.d(TAG, "got a clock");
        this.location.x += 1.0;
        this.location.y += 1.0;
        Main.repaint();
    }
}
