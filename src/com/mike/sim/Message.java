package com.mike.sim;

/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

public class Message
{
	public Agent mSender = null;
	public Agent mRecipient = null;
	public Object mMessage;
	
	public Message(Agent sender, Agent class1, Object msg)
	{
		mSender = sender;
		mRecipient = class1;
		mMessage = msg;
	}

	public Message(Message m) {
		this.mSender = m.mSender;
		this.mRecipient = m.mRecipient;
		this.mMessage = m.mMessage;
	}
}
