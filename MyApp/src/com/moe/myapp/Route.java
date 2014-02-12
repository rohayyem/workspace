package com.moe.myapp;

import java.util.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;

public class Route {
	
	private int routeid;
	public int getRouteid() {
		return routeid;
	}

	public void setRouteid(int routeid) {
		this.routeid = routeid;
	}

	private String name;
	private String start;
	private String destination;
	private String date;
	private String time;
	private int capacity;
	private int passengers;
	private double length;
	private String[] wayPointsTitles ={"N/A"};
	
	private final List<LatLng> points;
	private List<Segment> segments;
	private String copyright;
	private String warning;
	private String country;
	private String polyline;
	private LatLng[] wayPointsLocation;
	

	
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getPassengers() {
		return passengers;
	}

	public void setPassengers(int passengers) {
		this.passengers = passengers;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String[] getWayPointsTitles() {
		return wayPointsTitles;
	}

	public void setWayPointsTitles(String[] wayPointsTitles) {
		this.wayPointsTitles = wayPointsTitles;
	}

	public LatLng[] getWayPointsLocation() {
		return wayPointsLocation;
	}

	public void setWayPointsLocation(LatLng[] wayPointsLocation) {
		this.wayPointsLocation = wayPointsLocation;
	}

	public Route() {
		points = new LinkedList<LatLng>();
		segments = new LinkedList<Segment>();
	}

	public void addPoint(final LatLng p) {
		points.add(p);
	}

	public void addPoints(final List<LatLng> points) {
		this.points.addAll(points);
	}

	public List<LatLng> getPoints() {
		return points;
	}

	public void addSegment(final Segment s) {
		segments.add(s);
	}

	public List<Segment> getSegments() {
		return segments;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param copyright
	 *            the copyright to set
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * @return the copyright
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * @param warning
	 *            the warning to set
	 */
	public void setWarning(String warning) {
		this.warning = warning;
	}

	/**
	 * @return the warning
	 */
	public String getWarning() {
		return warning;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(double length) {
		this.length = length;
	}

	/**
	 * @return the length
	 */
	public double getLength() {
		return Math.round((length+0.5));
	}

	/**
	 * @param polyline
	 *            the polyline to set
	 */
	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}

	/**
	 * @return the polyline
	 */
	public String getPolyline() {
		return polyline;
	}

	public void drawMarkers(GoogleMap m, Route r) {

		try {

			
			
			
			m.addMarker(new MarkerOptions().position(r.getPoints().get(0))
					.title("Start: "+r.getStart()));
			//destination point marker
			m.addMarker(new MarkerOptions().position(
					r.getPoints().get(r.getPoints().size() - 1)).title("Destination"+r.getDestination()));
			

			
			//wayPoints point marker
			LatLng[] points = r.getWayPointsLocation();
			String[] titles = r.getWayPointsTitles();

			if (titles != null) {
				for (int i = 0; i < titles.length; i++) {
					m.addMarker(new MarkerOptions().position(points[i]).title(
							"Stop(" + (i + 1) + "): " + titles[i]));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void drawRoute(GoogleMap m ,Route r){
		
		try {
			
			Log.i(getClass().getSimpleName(), "Drawing Route:  "+r.getName());
			
			PolylineOptions polylineOptions = new PolylineOptions();
			ArrayList<LatLng> l = new ArrayList<LatLng>();
		    l.addAll(r.getPoints());
			polylineOptions.addAll(l);
			polylineOptions.color(Color.YELLOW);
			polylineOptions.width(10);
			m.addPolyline(polylineOptions);
			m.moveCamera(CameraUpdateFactory.newLatLngZoom(r.getPoints().get(r.getPoints().size()/2), 10));
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}