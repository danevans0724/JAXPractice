package org.evansnet.jaxpractice.jaxb.err;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class CountryErrorHandler implements ErrorHandler {
	
	Logger saxErrLogger = Logger.getLogger("CountryErrorHandlerLogger");

	@Override
	public void error(SAXParseException e) throws SAXException {
		saxErrLogger.log(Level.SEVERE, "A sax error has occurred " + 
				e.getMessage() + " at " + e.getLineNumber() + " at " + e.getColumnNumber());
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		saxErrLogger.log(Level.SEVERE, "A sax fatal error has occurred " + 
				e.getMessage() + " at " + e.getLineNumber() + " at " + e.getColumnNumber());
	}

	@Override
	public void warning(SAXParseException w) throws SAXException {
		saxErrLogger.log(Level.WARNING, "A sax warning has occurred " + 
				w.getMessage() + " at " + w.getLineNumber() + " at " + w.getColumnNumber());
	}

}
