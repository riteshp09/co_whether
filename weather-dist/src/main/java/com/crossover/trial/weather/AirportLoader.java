package com.crossover.trial.weather;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import java.io.*;
import java.util.StringTokenizer;

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
			AirportData aData = new AirportData();
			String[] data = l.split(",");
			aData.setId(data[0]);
			aData.setAirtportName(data[1]);
			aData.setCity(data[2]);
			aData.setCountry(data[3]);
			aData.setIata(data[4]);
			aData.setIcao(data[5]);
			aData.setLatitude(Double.valueOf(data[6]));
			aData.setLongitude(Double.valueOf(data[7]));
			aData.setAltitude(Double.valueOf(data[8]));
			aData.setTimezone(Integer.valueOf(data[9]));
			aData.setDst(data[10]);
			
			uploadAirportData(aData);
		}
	}
	
	protected void uploadAirportData(AirportData ad) {
	        WebTarget path = collect.path("/airport/" + ad.getIata() + "/" + ad.getLatitude() + "/" + ad.getLongitude());
	        Response post = path.request().post(Entity.entity("", "application/json"));
	        System.out.print("collect.ping: " + post.readEntity(String.class) + "\n");
	}

	public static void main(String args[]) throws IOException{
		File airportDataFile = new File("D:\\RP00425428\\Projects\\personal_projects\\co_whether\\weather-dist\\src\\main\\resources\\airports.dat");
		if (!airportDataFile.exists() || airportDataFile.length() == 0) {
			System.err.println(airportDataFile + " is not a valid input");
			System.exit(1);
		}

		AirportLoader al = new AirportLoader();
		al.upload(new FileInputStream(airportDataFile));
		System.exit(0);
	}
}
