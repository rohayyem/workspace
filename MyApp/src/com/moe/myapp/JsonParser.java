package com.moe.myapp;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

public class JsonParser extends XMLParser {
	/** Distance covered. **/
	private double distance;

	public JsonParser(String feedUrl) {
		super(feedUrl);
	}

	/**
	 * Parses a url pointing to a Google JSON object to a Route object.
	 * 
	 * @return a Route object based on the JSON object.
	 */

	public Route parse(String r) {
		// turn the stream into a string
		final String result = r;
		// Create an empty route
		final Route route = new Route();
		// Create an empty segment
		Segment segment = new Segment();
		try {
			// Tranform the string into a json object
			 JSONObject json = new JSONObject(result);
			// Get the route object
			 JSONObject jsonRoute = json.getJSONArray("routes")
					.getJSONObject(0);
			// Get the leg, only one leg as we don't support waypoints


			JSONArray legs = jsonRoute.getJSONArray("legs");
			int numlegs = legs.length();

			route.setStart(legs.getJSONObject(0).getString("start_address"));
			route.setDestination(legs.getJSONObject(numlegs-1).getString("end_address"));
			// Set the name of this route using the start & end addresses
			route.setName("From: "+route.getStart() + " To: " + route.getDestination());
			// Get google's copyright notice (tos requirement)
			route.setCopyright(jsonRoute.getString("copyrights"));

			// Get the total length of the route.
			int totalDistance = 0;

			LatLng[] wayPointsLocation = new LatLng[numlegs - 1];
			String[] wayPointsTitles = new String[numlegs - 1];

			for (int j = 0; j < numlegs; j++) {
				// Get the steps for this leg
				JSONObject leg = legs.getJSONObject(j);
				// Number of steps for use in for loop
				JSONArray steps = leg.getJSONArray("steps");
				int numSteps = steps.length();

				if (!(j == 0) && !(j == numlegs)) {

					JSONObject start = leg.getJSONObject("start_location");

					LatLng point = new LatLng(start.getDouble("lat"),
							start.getDouble("lng"));
					wayPointsLocation[j - 1] = point;
					wayPointsTitles[j - 1] = legs.getJSONObject(j).getString(
							"start_address");
					legs.getJSONObject(j).getString("start_address");
					Log.i(getClass().getSimpleName(),
							"***************************WAYPOINT"
									+ legs.getJSONObject(j).getString(
											"start_address"));

				}

				totalDistance += leg.getJSONObject("distance").getInt("value");
				Log.i(getClass().getSimpleName(),
						"#################calculating Distance"
								+ leg.getJSONObject("distance").getInt("value"));

				if (!jsonRoute.getJSONArray("warnings").isNull(0)) {
					route.setWarning(jsonRoute.getJSONArray("warnings")
							.getString(0));
				}
				/*
				 * Loop through the steps, creating a segment for each one and
				 * decoding any polylines found as we go to add to the route
				 * object's map array. Using an explicit for loop because it is
				 * faster!
				 */

				for (int i = 0; i < numSteps; i++) {
					// Get the individual step
					JSONObject step = steps.getJSONObject(i);
					// Get the start position for this step and set it on the
					// segment
					JSONObject start = step.getJSONObject("start_location");
					LatLng position = new LatLng(start.getDouble("lat"),
							start.getDouble("lng"));
					segment.setPoint(position);
					// Set the length of this segment in metres
					int length = step.getJSONObject("distance").getInt("value");
					distance += length;
					segment.setLength(length);
					segment.setDistance(distance / 1000);
					// Strip html from google directions and set as turn
					// instruction
					segment.setInstruction(step.getString("html_instructions")
							.replaceAll("<(.*?)*>", ""));
					// Retrieve & decode this segment's polyline and add it to
					// the route.
					route.addPoints(decodePolyLine(step.getJSONObject(
							"polyline").getString("points")));
					// Push a copy of the segment to the route
					route.addSegment(segment.copy());
				}

			}
			route.setLength((distance * 0.621371) / 1000);
			route.setWayPointsLocation(wayPointsLocation);
			route.setWayPointsTitles(wayPointsTitles);

			Log.i(getClass().getSimpleName(), "#################DISTANCE"
					+ totalDistance);

		} catch (JSONException e) {
			Log.e(e.getMessage(), "Google JSON Parser - " + feedUrl);
		}
		return route;
	}

	/**
	 * Decode a polyline string into a list of LatLng.
	 * 
	 * @param poly
	 *            polyline encoded string to decode.
	 * @return the list of GeoPoints represented by this polystring.
	 */

	private List<LatLng> decodePolyLine(final String poly) {
		int len = poly.length();
		int index = 0;
		List<LatLng> decoded = new LinkedList<LatLng>();
		int lat = 0;
		int lng = 0;

		while (index < len) {
			int b;
			int shift = 0;
			int result = 0;
			do {
				b = poly.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = poly.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			decoded.add(new LatLng((lat / 1E5), (lng / 1E5)));
		}

		return decoded;
	}
}