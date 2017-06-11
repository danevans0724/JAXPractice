package org.evansnet.jaxpractice.jaxb.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JTable;
import org.evansnet.jaxpractice.jaxb.Countries;
import org.evansnet.jaxpractice.jaxb.Country;

/**
 * A simple practice class that contains a table and un-marshalls country
 * data from a file. The table is filled with the country data.
 * Buttons allow the user to fill the empty table, and save any new table data
 * entered into the table. 
 * 
 * Practice in marshalling and un-marshalling, and working with JTables.
 * 
 * @author pmidce0
 * @copyright © 2017 Daniel C. Evans. All rights reserved.
 *
 */
public class CountriesWindow {
	// GUI fields
	private JFrame frame;
	private JTable table;
	private CountriesTableModel tableModel;
	
	Countries countries;
	Country country;

	/**
	 * Create the application.
	 */
	public CountriesWindow() {
		countries = new Countries();
		country = new Country();
		tableModel = new CountriesTableModel();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{228, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		// Create the table and connect the model.		
		table = new JTable(tableModel);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		JScrollPane scrollPane = new JScrollPane(table);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.weighty = 0.8;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		frame.getContentPane().add(scrollPane, gbc_scrollPane);

		JPanel Buttonpanel = new JPanel();
		GridBagConstraints gbc_Buttonpanel = new GridBagConstraints();
		gbc_Buttonpanel.anchor = GridBagConstraints.SOUTH;
		gbc_Buttonpanel.weighty = 0.2;
		gbc_Buttonpanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_Buttonpanel.gridx = 0;
		gbc_Buttonpanel.gridy = 1;
		frame.getContentPane().add(Buttonpanel, gbc_Buttonpanel);
		
		JButton btnFill = new JButton("Fill");
		Buttonpanel.add(btnFill);
		
		JButton btnSave = new JButton("Save");
		Buttonpanel.add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		Buttonpanel.add(btnClear);
	}
	
	//TODO: Set up selection listeners for the buttons and the table. Table needs row, column and cell listeners(?).
	
	public void doFill() {
		// Fill the table with countries
	}

	public void doSave() {
		// Save any new rows. Re-marshall to the file.
	}
	
	public void doClear() {
		//Clear the table of values, but don't delete the data.
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CountriesWindow window = new CountriesWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
