package com.mike.sim;

import java.awt.*;
import java.util.*;
import java.util.List;

import com.mike.routing.Metrics;
import com.mike.routing.Route;
import com.mike.util.Log;

/**
 * Created by mike on 6/17/2016.
 */
public class Main {

    private static final String TAG = Main.class.getSimpleName();

    public static Drawing drawing;
    private static Controls mControls;

    private static Framework mFramework;

    public static boolean animation = true;

    // all the agents that do the actual evaluation

    private static List<AgentInfo> mAgents = new ArrayList<>();

    static
    {
        mAgents.add(new AgentInfo(Clock.class, 1));

        mAgents.add(new AgentInfo(Bug.class, 1));
    };

    public static void main(String[] args)
    {
        {
            List<String> v = new ArrayList<String>(Arrays.asList(args));

            if (v.contains("-animation"))
                animation = true;
        }

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // create the controls and drawing windows
                mControls = new Controls();

                drawing = new Drawing(1.0);

                mFramework = new Framework(mAgents);
            }
        });
    }

    public static void paint(final Graphics2D g2) {
        Log.d(TAG, "in paint");
        mFramework.walk(new Framework.agentWalker() {
            @Override
            public void f(Agent a) {
                if (a instanceof PaintableAgent) {
                    ((PaintableAgent) a).paint(g2);
                }
            }
        });
    }

    public static void repaint() {
        if (animation)
            Main.drawing.mFrame.repaint();
    }

    static private Random random = new Random(12739);
    public static Random getRandom() {
        return random;
    }

    public static boolean getRunning() {
        return mControls.running;
    }
}
