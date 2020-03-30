package com.mike.sim;

import com.mike.util.Location;

/**
 * this class decides where a bug wants to move next
 * this may or may not actually happen, that's decided
 * by physics and other issues.
 */
public class Movement extends Agent {
    @Override
    protected String getClassName() {
        return Movement.class.getSimpleName();
    }

    private Bug bug;
    private double dx = 0.5;
    private double dy = 0.4;

    public Movement(Bug bug) {
        super(bug.mFramework);
        this.bug = bug;
    }

    @Override
    void onMessage(Message m) {
    }

    public Location move(Location location) {
		Location loc = new Location(location.x + dx, location.y + dy);
		return loc;
    }

    public void computeDesired() {
        // compute the desired dx,dy of the next step's movement
		if (bug.location.x < Location.worldLeft)
			dx *= -1.0;
		if (bug.location.x > Location.worldRight)
			dx *= -1.0;
	
		if (bug.location.y > Location.worldTop)
			dy *= -1.0;
		if (bug.location.y < Location.worldBottom)
			dy *= -1.0;
	
	}
}
