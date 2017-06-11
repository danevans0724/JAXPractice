package org.evansnet.jaxpractice.jaxb;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;

/**
 * A class for manipulating a collection of countries
 * This is an exercise in un-marshalling
 * @author pmidce0
 */
@XmlRootElement( name = "Countries")
public class Countries {
	
	private static Logger javaLogger = Logger.getLogger("CountriesLogger");
	private List<Country> countries;
	private static String[] fieldNames = {"Name", "Capital", "Continent", "Population"}; 
	
	public Countries() {
		countries = new ArrayList<Country>();
	} 
	
	public Countries(List<Country> c) {
		countries = c;
	}

	@XmlElement( name = "Country")
	public void setCountries(List<Country> c) {
		countries = c;
	}
	
	public List<Country> getCountries() {
		return countries;
	}
	
	public void addCountry(Country c) {
		countries.add(c);
	}
	
	public void removeCountry(int idx) {
		countries.remove(idx);
	}
	
	public void removeCountry(String name) {
		for (Country c : countries) {
			if (c.getName().equals(name)) 
				countries.remove((Country)c);
		}
	}
	
	public String[] getFields() {
		return fieldNames;
	}
	
	public void marshallContries(File file) {
		File countriesFile = file;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Countries.class);
			Marshaller  marshaller = jaxbContext.createMarshaller();
			marshaller.marshal(this, countriesFile);			
		} catch (JAXBException jbx) {
			javaLogger.log(Level.SEVERE, "An error occurred while marshalling " +
					jbx.getErrorCode() + " " + jbx.getMessage());
		}
	}
	
	public List<Country> unMarshallCountries(File file) {
		Countries tmpCountries = new Countries();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Countries.class);
			Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			tmpCountries = (Countries) unMarshaller.unmarshal(file);
			this.setCountries(tmpCountries.getCountries());
		} catch (JAXBException e) {
			javaLogger.log(Level.SEVERE, "An error occurred during unmarshalling " +
					e.getErrorCode() + " " + e.getMessage());
		}
		return countries;
	}
	
	public static void main(String[] args) {
		// Test the Countries class
		List<Country> countryList = new ArrayList<Country>();
		Countries fetchedCountries = new Countries();
		File countryFile = new File("countries.xml");
		if (countryFile.exists()) {
			countryList = fetchedCountries.unMarshallCountries(countryFile);
		}
		
		// Now show the countries
		for (Country c: countryList) {
			System.out.println(c.getName() + " " + c.getCapital() + " " + c.getContinent());
		}
	}
	
}
