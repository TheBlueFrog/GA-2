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
    
	private double velocity = 1.0;
	private double heading = 0.0;
	
	private Location goal;

    public Movement(Bug bug, Location goal) {
        super(bug.mFramework);
        this.bug = bug;
        this.goal = goal;
    }

    @Override
    void onMessage(Message m) {
    }

    public Location move(Location location) {
		Location loc = new Location(
				location.x + (Math.cos(heading) * velocity),
				location.y + (Math.sin(heading) * velocity));

		if (loc.x < Location.worldLeft)
			loc.x = Location.worldLeft;
		if (loc.x > Location.worldRight)
			loc.x = Location.worldRight;
	
		if (loc.y > Location.worldTop)
			loc.y = Location.worldTop;
		if (loc.y < Location.worldBottom)
			loc.y = Location.worldBottom;
		
		if (loc.equals(goal)) {
			goal = bug.getNewGoal();
		}
		
		return loc;
    }

    public void computeDesired() {
        // compute the desired dx, dy of the next step's movement
		// head towards our goal
		double dx = this.goal.x - this.bug.location.x;
		double dy = this.goal.y - this.bug.location.y;
		
		this.heading = Math.atan2(dy, dx);
	}
}
