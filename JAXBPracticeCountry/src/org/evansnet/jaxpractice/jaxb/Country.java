package org.evansnet.jaxpractice.jaxb;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Validator;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.evansnet.jaxpractice.jaxb.err.CountryErrorHandler;
import org.evansnet.jaxpractice.jaxb.err.CountryValidationHandler;
import org.xml.sax.SAXException;

/**
 * This class is for JAXB marshalling practice. It is taken from 
 * https://www.javacodegeeks.com/2014/12/jaxb-tutorial-xml-binding.html
 * @author pmidce0
 *
 */

@XmlType(propOrder = {"name","capital","foundation","continent","population"})
@XmlRootElement( name = "Country")
public class Country {
	
	public static final Logger javalogger = Logger.getLogger("JaxB logger");
	
	int importance;
	String name = "";
	String capital = "";
	LocalDate foundation;
	String continent = "";
	int population;
	String[] fieldNames = {"Name", "Capital", "Founded", "Continent", "Population" };
	Schema countrySchema;
	Validator validator;
	
	public Country() throws SAXException {
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		countrySchema = sf.newSchema(new File("CountryLSchema.xsd"));
		validator = countrySchema.newValidator();
		validator.setErrorHandler(new CountryErrorHandler());
	}
	
	public Country(String n, String cap, LocalDate f, String cn, int p) {
		super();
		name = n;
		capital = cap;
		foundation = f;
		continent = cn;
		population = p;
	}
	
	
	@XmlElement( name = "Country_Population")
	public void setPopulation( int population ) {
		this.population = population;
	}
	
	public int getPopulation() {
		return this.population;
	}
	
	@XmlElement (name = "Country_Name")
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@XmlElement( name = "Country_Capital")
	public void setCapital(String cap) {
		this.capital = cap;
	}

	public String getCapital() {
		return this.capital;
	}
	
	@XmlElement( name = "Country_Founded")
	@XmlJavaTypeAdapter( DateAdapter.class )
	public void setFoundation(LocalDate of) {
		this.foundation = of;
	}

	public LocalDate getFoundation() {
		return this.foundation;
	}
	
	@XmlElement( name = "Country_Continent")
	public void setContinent(String continent) {
		this.continent = continent;
	}
	
	public String getContinent() {
		return this.continent;
	}
	
//	@XmlAttribute( name = "importance", required = true)
//	public void setImportance(int importance) {
//		this.importance = importance;
//	}
//	
//	public int getImportance() {
//		return this.importance;
//	}
	
	/**
	 * Provide the list of fields that describe a country.
	 * @return An array of Strings that contains the field names.
	 */
	public String[] getFieldNames() {
		return fieldNames;
	}
	
	/**
	 * Validates the country source provided against a schema
	 * @return True if the source structure conforms to the schema, false otherwise.
	 * @throws JAXBException 
	 */
	private boolean isValidCountry(JAXBContext context) throws JAXBException {
		JAXBSource source = new JAXBSource(context, this);
		try {
			validator.validate(source);
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Marshals this individual country to the stream provided.
	 */
	public void marshallCountry(OutputStream out) {
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Country.class);
			if (!isValidCountry(jaxbContext)) {
				throw new JAXBException("The definition supplied failed to validate");
			}
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			// Set this flag to true to format the output.
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.setSchema(countrySchema);
			
			//Set the validation event handler
			jaxbMarshaller.setEventHandler(new CountryValidationHandler());
			
			// marshalling of java objects in xml (output to file and stdout)
			jaxbMarshaller.marshal(this, new File("country.xml"));
			jaxbMarshaller.marshal(this, out);
			
		} catch (JAXBException e) {
			javalogger.log(Level.SEVERE, "An exception happened during marshalling " + 
					e.getErrorCode() + " " + e.getMessage(), e);
		}
	}
	
	/**
	 * Un-marshalls the country.xml file.
	 * @param File fileName
	 */
	public Country unMarshallCounty(File fileName) {
		Country country = null;
		try {
			country = new Country();
		} catch (SAXException e) {
		e.printStackTrace();
		}
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Country.class);
			Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
			
			country = (Country) jaxbUnMarshaller.unmarshal(fileName);
		} catch (JAXBException jbx) {
			javalogger.log(Level.SEVERE, "An exception happened while un-marshalling "
					+ jbx.getErrorCode() + " " + jbx.getMessage());
		}
		return country;
	}
	
	/**
	 * The DateAdapter class
	 * Used to convert the LocalDate objects used in Country into a form suitable for 
	 * marshalling and un-marshalling.
	 * 
	 * @param args
	 */
	private static class DateAdapter extends XmlAdapter<String, LocalDate> {

		@Override
		public String marshal(LocalDate v) throws Exception {
			return v.toString();
		}

		@Override
		public LocalDate unmarshal(String v) throws Exception {
			return LocalDate.parse(v);
		}
	}

 public static void main(String[] args) throws SAXException {
	 
	 // Test the class.
		Country spain = new Country();
//		spain.setImportance(1);
		spain.setName("Spain");
		spain.setCapital("Madrid");
		spain.setContinent("Europe");
		spain.setFoundation( LocalDate.of(1469, 10, 19));
		spain.setPopulation(450000000);
		
		spain.marshallCountry(System.out);
		
		Country unMarshalledSpain = new Country();
		File countryFile = new File("country.xml");
		if (countryFile.exists()) {
			unMarshalledSpain = unMarshalledSpain.unMarshallCounty(countryFile);
		}
		
		
		//Show the new un-marshalled content
		System.out.println("\n****** Showing un-marshalled content ******");
//		System.out.println("Importance: " + unMarshalledSpain.importance);
		System.out.println("Name: " + unMarshalledSpain.name);
		System.out.println("Capital: " + unMarshalledSpain.capital);
		System.out.println("Founded: " + unMarshalledSpain.foundation.toString());
		System.out.println("Continent: " + unMarshalledSpain.continent);
		System.out.println("Population: " + unMarshalledSpain.population);
		System.out.println("****** End of unmarshalled input ******");
	}

}
