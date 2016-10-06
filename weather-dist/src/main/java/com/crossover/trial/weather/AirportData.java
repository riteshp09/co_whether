package com.crossover.trial.weather;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Basic airport information.
 *
 * @author code test administrator
 */
public class AirportData {
	
    /** the three letter IATA code */
    String iata;
    
    /** latitude value in degrees */
    double latitude;

    /** longitude value in degrees */
    double longitude;
    
    AtmosphericInformation atmosphericInformation;
    
    public AirportData() { }

    public String getIata() {
        return iata;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
	public AtmosphericInformation getAtmosphericInformation() {
		return (atmosphericInformation == null) ? new AtmosphericInformation() : atmosphericInformation;
	}

	public void setAtmosphericInformation(AtmosphericInformation atmosphericInformation) {
		this.atmosphericInformation = atmosphericInformation;
	}

	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.NO_CLASS_NAME_STYLE);
    }

    public boolean equals(Object other) {
        if (other instanceof AirportData) {
            return ((AirportData)other).getIata().equals(this.getIata());
        }

        return false;
    }
}
