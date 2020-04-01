package com.mike.sim;

import com.mike.util.Location;
import com.mike.util.Log;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 * this class decides where a bug wants to move next
 * this may or may not actually happen, that's decided
 * by physics and other issues.
 */
public class Movement extends Agent {
	
	private static final String TAG = Movement.class.getSimpleName();
	private Queue<Location> goals = new ArrayDeque<>();
	
	@Override
    protected String getClassName() {
        return Movement.class.getSimpleName();
    }

    private Bug bug;
    
	private double velocity = 0.3;
	private double heading = 0.0;
	
    public Movement(Bug bug, Location goal) {
        super(bug.mFramework);
        this.bug = bug;
        this.goals.add(goal);
        
        bug.mFramework.registerAsMoving(this);
    }

    @Override
    void onMessage(Message m) {
    }

    private Location moveTowards(Location location) {
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
		
		return loc;
	}
	
    public Location move(Location location) {
		Location loc = moveTowards(location);
		if (loc.equals(goals.peek())) {
			goals.remove();
			
			if (goals.isEmpty())
				goals.add(bug.getNewGoal());
		}
		
		return loc;
    }

    private static double minDistance = 20.0;
    
    public void computeDesiredHeadingVelocity() {
        // compute the desired dx, dy of the next step's movement
		// head towards our goal
		Location goal = goals.peek();
		
//		double dx = goal.x - this.bug.location.x;
//		double dy = goal.y - this.bug.location.y;
		
		this.heading = headingTo(goal);
	
		List<Movement> others = this.bug.mFramework.getMovingThingsLocations(this);
		if (others.size() > 0) {
			Bug other = others.get(0).bug;
			double d = other.location.distance(this.bug.location);
			if (d < minDistance) {
				Log.d(TAG, "contact");
				bug.contact(other);
				other.contact(bug);
			}
		}
	}
	
	private double headingTo(Location goal) {
		double dx = goal.x - this.bug.location.x;
		double dy = goal.y - this.bug.location.y;
		
		return Math.atan2(dy, dx);
	}
	
	public Location getLocation() {
		return this.bug.location;
	}
	
	@Override
	public String toString() {
		return String.format("Move (%.1f, %.1f)", this.heading, this.velocity);
	}
	
	public void avoid(Bug other) {
    	double headingToOther = headingTo(other.location);

		double dt = Math.abs(Math.tan(heading) - Math.tan(headingToOther));
		if (dt > (Math.PI / 2.0)) {
			// if we are already heading away do nothing
			return;
		}
		
		// ok, move away from the other bug's location
		double theta = headingToOther + (Math.PI * 0.9);
		
		Location loc = new Location(
				bug.location.x + (Math.cos(theta) * (velocity * 50.0)),
				bug.location.y + (Math.sin(theta) * (velocity * 50.0)));
		
		goals.add(loc);
	}
	
	public Queue<Location> getGoals() {
    	return goals;
	}
}
