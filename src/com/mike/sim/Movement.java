package com.mike.sim;

import com.mike.util.Location;

public class Movement extends Agent {
    @Override
    protected String getClassName() {
        return Movement.class.getSimpleName();
    }

    private double dx = 0;
    private double dy = 0;

    public Movement(Bug bug) {
        super(bug.mFramework);
    }

    @Override
    void onMessage(Message m) {
    }

    public Location move(Location location) {
        return new Location(location.x + dx, location.y + dy);
    }

    public void computeDesired() {
        // where do we want to go?
    }
}
