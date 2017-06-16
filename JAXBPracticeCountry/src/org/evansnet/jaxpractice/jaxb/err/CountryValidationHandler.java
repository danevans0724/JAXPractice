/**
 * 
 */
package org.evansnet.jaxpractice.jaxb.err;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

/**
 * @author pmidce0
 *
 */
public class CountryValidationHandler implements ValidationEventHandler {
	
	Logger vldLogger = Logger.getLogger("CountryValidationLogger");

	/* (non-Javadoc)
	 * @see javax.xml.bind.ValidationEventHandler#handleEvent(javax.xml.bind.ValidationEvent)
	 */
	@Override
	public boolean handleEvent(ValidationEvent vEvt) {
		vldLogger.log(Level.SEVERE, "A validation error event occurred: " +
				+ vEvt.getSeverity() + vEvt.getMessage() + vEvt.getLinkedException());
		return false;
	}

}
