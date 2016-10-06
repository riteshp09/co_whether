package com.crossover.trial.weather;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

/**
 * A reference implementation for the weather client. Consumers of the REST API can look at WeatherClient
 * to understand API semantics. This existing client populates the REST endpoint with dummy data useful for
 * testing.
 *
 * @author code test administrator
 */
public class WeatherClient {
	
	private static StringBuffer pathStr = new StringBuffer();

    public void addAirport(String iata, double lat, double lon) {
    	pathStr.replace(0, pathStr.length(), "");
    	WebTarget path = WeatherClientUtil.getInstance().getCollectWebTarget()
    			.path(pathStr.append("/airport/").append(iata).append("/").append(lat).append("/").append(lon).toString());
        Response response = path.request().post(Entity.entity("", "application/json"));
        System.out.print("collect.addairport: " + response.readEntity(String.class) + "\n");
    }
    
    public void deleteAirport(String iata) {
    	pathStr.replace(0, pathStr.length(), "");
    	 WebTarget path = WeatherClientUtil.getInstance().getCollectWebTarget()
    			 .path(pathStr.append("/airport/").append(iata).toString());
         Response response = path.request().delete();
         System.out.print("collect.deleteAirport: " + response.readEntity(String.class) + "\n");
    }
    
    public void getAriport() {
    	 WebTarget path = WeatherClientUtil.getInstance().getCollectWebTarget()
    			 .path("/airports");
         Response response = path.request().get();
         System.out.print("collect.getAirport: " + response.readEntity(String.class) + "\n");
    }

    public void pingCollect() {
        WebTarget path = WeatherClientUtil.getInstance().getCollectWebTarget()
        		.path("/ping");
        Response response = path.request().get();
        System.out.print("collect.ping: " + response.readEntity(String.class) + "\n");
    }

    public void query(String iata) {
        WebTarget path = WeatherClientUtil.getInstance().getQueryWebTarget()
        		.path("/weather/" + iata + "/0");
        Response response = path.request().get();
        System.out.println("query." + iata + ".0: " + response.readEntity(String.class));
    }

    public void pingQuery() {
        WebTarget path = WeatherClientUtil.getInstance().getQueryWebTarget()
        		.path("/ping");
        Response response = path.request().get();
        System.out.println("query.ping: " + response.readEntity(String.class));
    }

    public void populate(String pointType, int first, int last, int mean, int median, int count) {
        WebTarget path = WeatherClientUtil.getInstance().getCollectWebTarget()
        		.path("/weather/BOS/" + pointType);
        DataPoint dp = new DataPoint.Builder()
                .withFirst(first).withLast(last).withMean(mean).withMedian(median).withCount(count)
                .build();
        System.out.println(dp);
        Response post = path.request().post(Entity.entity(dp, "application/json"));
    }

    public void exit() {
        try {
        	WeatherClientUtil.getInstance().getCollectWebTarget().path("/exit").request().get();
        } catch (Throwable t) {
            // swallow
        }
    }

    public static void main(String[] args) {
        WeatherClient wc = new WeatherClient();
        wc.pingCollect();
        wc.populate("wind", 0, 10, 6, 4, 20);

        wc.query("BOS");
        wc.query("JFK");
        wc.query("EWR");
        wc.query("LGA");
        wc.query("MMU");

        wc.pingQuery();
        wc.exit();
        System.out.print("complete");
        System.exit(0);
    }
}
