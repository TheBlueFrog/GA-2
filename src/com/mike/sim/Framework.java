package com.mike.sim;

/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

import com.mike.util.Location;
import com.mike.util.Log;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class Framework
{
	private static final String TAG = Framework.class.getSimpleName();

	public Framework() {
		new Clock(this).start();
	}

	public void paint(Graphics2D g2) {

		g2.setColor(Color.RED);
		
		g2.drawRect(
				(int) Location.meter2PixelX(0.0),
				(int) Location.meter2PixelY(0.0),
				10, 10);
		
		g2.drawString(
				String.format("%.0f", 4.0),
				(int) Location.meter2PixelX(0.0),
				(int) Location.meter2PixelY(-10.0));
	}

}

