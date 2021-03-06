package com.mike.sim;

/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

import com.mike.util.Log;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * base class for an Agent, agents communicate via Messages which
 * are sent to the receiving agents queue of incoming messages.
 *<p></p>
 * There can be an arbitrary number of agents of a given class,
 * they are given serial numbers when created [0, 1, 2, ...]
 * When messages are sent they are addressed to an Agent-SN pair.
 *<p></p>
 * Agents are constructed and should do initialization prior to
 * registering with the Framework.  Once all created agents are
 * registered normal processing starts, the Framework sends
 * everyone an start message.
 *<p></p>
 * Messages sent by an agent prior to the start do not yet have
 * defined behavior. @TODO fixme
 */
abstract public class Agent extends Thread
{
	abstract protected String getClassName ();

	private static long nextSerialNumber = 0;

	private final long serialNumber;
	private UUID mID;
	private final BlockingQueue<Message> queue;
	protected Framework mFramework;

	protected boolean stopped = true;

	public Agent(Framework f)
	{
		mFramework = f;
		this.serialNumber = nextSerialNumber++;
		mID = UUID.randomUUID();

		queue = new LinkedBlockingQueue<Message>();

		// agents register when they are ready
	}

	public String getID(boolean little)
	{
		if (little)
			return mID.toString().substring(0, 8);

		return mID.toString();
	}
	public String getID()
	{
		return mID.toString();
	}

	public long getSerialNumber () { return serialNumber; }

	public int getQueueLength() { return queue.size(); }

	protected boolean isQueueEmpty()
	{
		return queue.peek() == null;
	}

	protected Message takeFromQueue() throws InterruptedException
	{
		Log.d(getClassName(), "take from queue");
		return queue.take();
	}

	@Override
	public void run()
	{
		stopped = false;

		try
		{
			while ( ! stopped)
			{
				Message m = queue.take();
//		    		Log.d(getID(true), String.format("Process message from %s, %s", m.mSender.getID(true), m.mMessage));

				onMessage(m);
			}
		}
		catch (InterruptedException ex)
		{
		}
	}

	public void incoming (Message msg)
	{
		synchronized (queue)
		{
//			Log.d(getClassName(), "add to queue");
			queue.add(msg);
		}
	}

	public boolean send(Message m)
	{
		return mFramework.send(m);
	}

	abstract void onMessage(Message m);


//	protected boolean send(Class<? extends Agent> class1, int serialNumber, Object msg)
//	{
//		return mFramework.send(new Message(this, class1, serialNumber, msg));
//	}

	public String getTag()
	{
		return this.getClass().getSimpleName();
	}

}
