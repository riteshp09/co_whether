package com.crossover.trial.weather;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

/**
 * Create class to provide common utility methods.
 * Singleton class to maintain single client instance.
 * @author riteshp
 *
 */
public class WeatherClientUtil {
	
	public static final String BASE_URI = "http://localhost:9090";
	
	/** end point for read queries */
	private WebTarget query;

	/** end point to supply updates */
	private WebTarget collect;
	
	private static WeatherClientUtil weatherClient;
	
	private WeatherClientUtil() {
		Client client = ClientBuilder.newClient();
		query = client.target(BASE_URI + "/query");
		collect = client.target(BASE_URI + "/collect");
	}
	
	public static WeatherClientUtil getInstance() {
		if(weatherClient == null)
			weatherClient = new WeatherClientUtil();
		return weatherClient;
	}
	
	public WebTarget getQueryWebTarget() {
		return query;
	}
	
	public WebTarget getCollectWebTarget() {
		return collect;
	}
	

}
