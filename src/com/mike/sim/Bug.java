package com.mike.sim;

import com.mike.routing.Item;
import com.mike.util.Location;
import com.mike.util.Log;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bug extends LocatedAgent {

    private static final String TAG = Bug.class.getSimpleName();
    private boolean debug = false;
    private final Energy energy;
    private final Movement movement;


    private List<Item> items = new ArrayList<>();

    /** construct a random bug */
    public Bug(Framework f) {
        super(f);

        // we have sub-agents that manage our various behaviors
        // get them started

        energy = new Energy(mFramework);
        energy.start();

        location = new Location(Location.getRandomLoc(Constants.random));
        Location goal = new Location(Location.getRandomLoc(Constants.random));
        
        movement = new Movement(this, goal);
        movement.start();

        f.registerForClock(this);
    }

	/** construct a bug with params */
	public Bug(Framework f, Location loc, Location goal) {
		super(f);
		this.debug = true;
		
		// we have sub-agents that manage our various behaviors
		// get them started
		
		energy = new Energy(mFramework);
		energy.start();
		
		location = loc;
		
		movement = new Movement(this, goal);
		movement.start();
		
		f.registerForClock(this);
	}

    @Override
    void paint(Graphics2D g2) {
        // have to arbitrate between our internal agents
	
		String label = String.format("%d: %d", getSerialNumber(), movement.getGoals().size());
		
		if (movement.getGoals().size() > 10)
			g2.setColor(Color.RED);
		else if (movement.getGoals().size() > 5)
			g2.setColor(Color.PINK);
		else if (movement.getGoals().size() > 1)
			g2.setColor(Color.YELLOW);
		else
			g2.setColor(Color.GREEN);
	
		g2.drawString(
				label,
				(int) Location.meter2PixelX(location.x),
				(int) Location.meter2PixelY(location.y));

        g2.drawRect(
                (int) Location.meter2PixelX(location.x),
                (int) Location.meter2PixelY(location.y),
                10, 10);
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

        movement.computeDesiredHeadingVelocity();

        if (energy.canMove()) {
            energy.moveDrain();
            location = movement.move(location);
            
        }

    }
	
	public Location getNewGoal() {
		return new Location(Location.getRandomLoc(Constants.random));
	}
	
	// react to making contact with another bug
	public void contact(Bug other) {
		movement.avoid(other);
	}
	
	@Override
	public String toString() {
		return String.format("Bug: %d, %s, %s", getSerialNumber(), location.toString(), movement.toString());
	}
}
