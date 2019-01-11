package com.mike.sim;

import com.mike.util.Location;
import com.mike.util.Log;

import java.awt.*;

public class Bug extends LocatedAgent {

    private static final String TAG = Bug.class.getSimpleName();

    private final double AtRestEnergyDrainPerTick = 0.010;

    private double energy = 100;

    public Bug(Framework f) {
        super(f);

        f.registerForClock(this);
    }

    @Override
    void paint(Graphics2D g2) {
        if (energy > 50.0)
            g2.setColor(Color.GREEN);
        else if (energy > 10.)
            g2.setColor(Color.RED);

        g2.drawRect(
                (int) Location.meter2PixelX(location.x),
                (int) Location.meter2PixelY(location.y),
                10, 10);

        g2.drawString(
                String.format("%.0f", energy),
                (int) Location.meter2PixelX(location.x),
                (int) Location.meter2PixelY(location.y));
    }

    @Override
    protected void onMessage(Message msg) {
        Log.d(TAG, String.format("Msg from %s", msg.mSender.getClass().getSimpleName()));

        if (msg.mSender instanceof Clock) {
            doTick();
        }
    }

    private void doTick() {

        energy -= AtRestEnergyDrainPerTick;

//        this.location.x += 1.0;
//        this.location.y += 1.0;
        Main.repaint();
    }
}
