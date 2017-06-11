package org.evansnet.jaxpractice.jaxb.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.evansnet.jaxpractice.jaxb.Country;
import org.evansnet.jaxpractice.jaxb.Countries;


/**
 * Functions as the controller between the Countries class and the CountriesWindow gui.
 * provides TableModel functionality for the Country objects in the model.
 * @author pmidce0
 * @copyright © 2017 Daniel C. Evans. All rights reserved.
 *
 */
public class CountriesTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;
	Countries countries;
	String[] colHeaders;
	List<Country> countryList;
	Vector<Vector<String>> countryData = new Vector<Vector<String>>(); 
	int row;
	int col;
	
	public CountriesTableModel() {
		countries =	new Countries();
		countries.unMarshallCountries(new File("countries.xml"));
		colHeaders = countries.getFields();	// Set the column headers in the table.
		countryList = countries.getCountries();
		setTableModelData();
	}
	
	/**
	 * Set the data into the Vector(Vector())
	 */
	private void setTableModelData() {
		//Spin through the list and add the individual country fields.
		Vector<String> fields;
		for (Country c : countryList) {
			fields = new Vector<String>();
			fields.add(c.getName());
			fields.add(c.getCapital());
			fields.add(c.getContinent());
			fields.add(String.valueOf(c.getPopulation()));
			countryData.add(fields);
		}
	}
	
	/**
	 * When the countryData table model data has been updated, use this
	 * method to send the updates to the Countries class country list.
	 * 
	 */
//	public void refreshCountriesData() {
//		List<Country> tempList = new ArrayList<Country>();
//		Country ctry = new Country();
//		for (Vector<String> s : countryData) {
//			ctry.setName(s.);
//			tempList.add((Country)s);
//			
//		}
//	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

	@Override
	public int getColumnCount() {
		return colHeaders.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return colHeaders[columnIndex];
	}

	@Override
	public int getRowCount() {
		return countryData.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
//		Country c = countries.getCountries().get(rowIndex);
		String s = new String();
		s = countryData.get(rowIndex).get(columnIndex);
		return s;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		String s = (String) aValue;
		countryData.get(rowIndex).setElementAt(s, columnIndex);
		fireTableCellUpdated(rowIndex, columnIndex);	
	}
		

}
