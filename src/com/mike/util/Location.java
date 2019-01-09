package com.mike.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mike on 6/17/2016.
 * we assume a flat rectangular coodinate system, e.g. not
 * factoring in spherical earth
 *
 * we do work in Lat/Lon however
 */
public class Location {
    public double x;
    public double y;

    static public double worldLeft = -1000;
    static public double worldTop = 1000;
    static public double worldRight = 1000;
    static public double worldBottom = -1000;

    // width/height of world
    static public double WorldWidthMeters = (worldRight - worldLeft);
    static public double WorldHeightMeters = (worldTop - worldBottom);

    static public double WindowWidth = 750;            // window size in pixels
    static public double WindowHeight = 500;

    static public double meter2PixelX(double meterX) {
        return ((meterX - worldLeft) / WorldWidthMeters) * WindowWidth; }
    static public double meter2PixelY(double meterY) {
        return WindowHeight - (((meterY - worldBottom) / WorldHeightMeters) * WindowHeight);
    }

    static double WorldCenterX = ((worldRight - worldLeft) / 2) + worldLeft;
    static double WorldCenterY = ((worldTop - worldBottom) / 2) + worldBottom;

    /**
     *
     * @param lat coordinates of a location
     * @param lon
     */
    public Location(double lon, double lat) {
        this.x = lon;
        this.y = lat;
    }

    public Location(Location location) {
        this.x = location.x;
        this.y = location.y;
    }

    /**
     * @param location
     * @return distance, in meters, between this location and another location
     */
    public double distance(Location location) {
        // location is in lat/lon
//        return (distance(y, x, location.y, location.x));
//    }
        double dx = meter2PixelX(this.x - location.x);
        double dy = meter2PixelY(this.y - location.y);
        return Math.sqrt((dx * dx) + (dy * dy));
    }

    static private double d2r = (Math.PI / 180.0);

//    //calculate haversine distance for linear distance, in meters
//    double distance(double lat1, double long1, double lat2, double long2)
//    {
//        double dlong = (long2 - long1) * d2r;
//        double dlat = (lat2 - lat1) * d2r;
//        double a = Math.pow(Math.sin(dlat/2.0), 2) + Math.cos(lat1*d2r) * Math.cos(lat2*d2r) * Math.pow(Math.sin(dlong/2.0), 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
//        double d = 6367 * c;
//
//        return d * 1000.0;
//    }

//        double haversine_mi(double lat1, double long1, double lat2, double long2)
//        {
//            double dlong = (long2 - long1) * d2r;
//            double dlat = (lat2 - lat1) * d2r;
//            double a = pow(sin(dlat/2.0), 2) + cos(lat1*d2r) * cos(lat2*d2r) * pow(sin(dlong/2.0), 2);
//            double c = 2 * atan2(sqrt(a), sqrt(1-a));
//            double d = 3956 * c;
//
//            return d;
//        }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (! Location.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Location other = (Location) obj;

        if ((this.x != other.x)) {
            return false;
        }
        if ((this.y != other.y)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = (int) (53 * hash + this.x);
        hash = (int) (53 * hash + this.y);
        return hash;
    }

    /**
     * move so many meters
     * @param dx
     * @param dy
     */
    public void moveMeters(double dx, double dy) {
        x += dx;
        y += dy;
    }

    /**
     * @param holeRadiusM the locations have a hole in
     *                  the middle and are randomly distributed in a band around the hole, this is
     *                  the radium of the hole, in meters
     * @param radiusM   radius of populated band, in meters
     * @return          random location
     */
    public static Location getRandomLoc(double holeRadiusM, double radiusM, Random random) {
        double theta = random.nextDouble() * 2 * Math.PI;
        double r = random.nextDouble() * radiusM;
        r += holeRadiusM;
        double x = Math.cos(theta) * r;
        double y = Math.sin(theta) * r;

        Location loc = new Location(
                WorldCenterX + x,
                WorldCenterY + y);
        return loc;
    }

    /**
     * @param radiusM   radius of a circle with 1 std dev of locations
     * @return          random location
     */
    public static Location getRandomLoc(double radiusM, Random random) {
        double theta = random.nextDouble() * 2 * Math.PI;
        double r = random.nextDouble() * radiusM;
        double x = Math.cos(theta) * r;
        double y = Math.sin(theta) * r;

        Location loc = new Location(
                WorldCenterX + x,
                WorldCenterY + y);
        return loc;
    }
}
