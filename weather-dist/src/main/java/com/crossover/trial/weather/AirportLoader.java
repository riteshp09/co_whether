package com.crossover.trial.weather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
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
	
	private static final String BASE_URI = "http://localhost:9090";

	/** end point for read queries */
	private WebTarget query;

	/** end point to supply updates */
	private WebTarget collect;

	public AirportLoader() {
		Client client = ClientBuilder.newClient();
		query = client.target("http://localhost:8080/query");
		collect = client.target("http://localhost:8080/collect");
	}

	public void upload(InputStream airportDataStream) throws IOException{
		
		Client client = ClientBuilder.newClient();
        collect = client.target(BASE_URI + "/collect");
        
		BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream));
		String l = null;
		while ((l = reader.readLine()) != null) {
			String[] data = l.split(",");
			uploadAirportData( data[4], data[6], data[7]);
		}
	}
	
	protected void uploadAirportData(String iata, String latitude, String longitude) {
	        WebTarget path = collect.path("/airport/" + iata + "/" + latitude + "/" + longitude);
	        Response post = path.request().post(Entity.entity("", "application/json"));
	        System.out.print("collect.addairport: " + post.readEntity(String.class) + "\n");
	}

	public static void main(String args[]) throws IOException{
		File airportDataFile = new File("C:\\Users\\Frey\\Documents\\co_whether\\weather-dist\\src\\main\\resources\\airports.dat");
		if (!airportDataFile.exists() || airportDataFile.length() == 0) {
			System.err.println(airportDataFile + " is not a valid input");
			System.exit(1);
		}

		AirportLoader al = new AirportLoader();
		al.upload(new FileInputStream(airportDataFile));
		System.exit(0);
	}
}
