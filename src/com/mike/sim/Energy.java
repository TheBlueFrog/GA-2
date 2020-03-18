package com.mike.sim;

import com.oracle.jrockit.jfr.management.FlightRecorderMBean;
import com.oracle.jrockit.jfr.management.FlightRecordingMBean;
import com.sun.applet2.Applet2;
import com.sun.jmx.snmp.daemon.CommunicatorServerMBean;

import javax.management.monitor.MonitorMBean;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.util.regex.MatchResult;

public class Energy extends Agent {

    @Override
    protected String getClassName() {
        return Energy.class.getSimpleName();
    }

    private final double AtIdleDrainPerTick = 0.010;
    private final double MovingDrainPerTick = 0.10;

    private double energy = 100;

    public Energy(Framework framework) {
        super(framework);
    }

    @Override
    void onMessage(Message m) {

    }

    public Color getColor() {
        if (energy > 50.0)
            return Color.GREEN;
        if (energy > 10.0)
            return Color.YELLOW;

        return Color.RED;
    }

    protected double getEnergyLevel() {
        return energy;
    }

    public void idleDrain() {
        energy = Math.max(0, energy - AtIdleDrainPerTick);
    }

    public boolean canMove() {
        return energy > MovingDrainPerTick;
    }

    public void moveDrain() {
        energy = Math.max(0, energy - MovingDrainPerTick);
    }
}
