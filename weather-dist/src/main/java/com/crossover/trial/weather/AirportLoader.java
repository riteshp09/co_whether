package com.crossover.trial.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * A simple airport loader which reads a file from disk and sends entries to the webservice
 *
 * TODO: Implement the Airport Loader
 * 
 * @author code test administrator
 */
public class AirportLoader {
	
	public void upload(InputStream airportDataStream) throws IOException{
		BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream));
		String l = null;
		
		while ((l = reader.readLine()) != null) {
			String[] data = l.split(",");
			uploadAirportData( data[4], data[6], data[7]);
		}
	}
	
	/**
	 * Added method to upolad airport data on server.
	 * @param iata
	 * @param latitude
	 * @param longitude
	 */
	protected void uploadAirportData(String iata, String latitude, String longitude) {
	        WebTarget path = WeatherClientUtil.getInstance().getCollectWebTarget()
	        		.path(new StringBuffer("/airport/").append(iata).append("/").append(latitude).append("/").append(longitude).toString());
	        Response post = path.request().post(Entity.entity("", "application/json"));
	        System.out.print("collect.addairport: " + post.readEntity(String.class) + "\n");
	}

	public static void main(String args[]) throws IOException{
		AirportLoader al = new AirportLoader();
		al.upload(ClassLoader.getSystemResourceAsStream("airports.dat"));
		System.exit(0);
	}
}
