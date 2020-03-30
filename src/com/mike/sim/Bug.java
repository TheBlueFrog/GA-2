package com.mike.sim;

import com.mike.routing.Item;
import com.mike.util.Location;
import com.mike.util.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bug extends LocatedAgent {

    private static final String TAG = Bug.class.getSimpleName();
    private final Energy energy;
    private final Movement movement;


    private List<Item> items = new ArrayList<>();

    public Bug(Framework f) {
        super(f);

        // we have sub-agents that manage our various behaviors
        // get them started

        energy = new Energy(mFramework);
        energy.start();

        location = new Location(Location.getRandomLoc(5.0, Constants.random));
        movement = new Movement(this);
        movement.start();

        f.registerForClock(this);
    }

    @Override
    void paint(Graphics2D g2) {
        // have to arbitrate between our internal agents
        g2.setColor(energy.getColor());

        g2.drawRect(
                (int) Location.meter2PixelX(location.x),
                (int) Location.meter2PixelY(location.y),
                10, 10);

        g2.drawString(
                String.format("%.0f", energy.getEnergyLevel()),
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

        energy.idleDrain();

        movement.computeDesired();

        if (energy.canMove()) {
            energy.moveDrain();
            location = movement.move(location);
        }

//        Main.repaint();
    }
}
