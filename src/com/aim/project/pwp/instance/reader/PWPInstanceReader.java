package com.aim.project.pwp.instance.reader;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;

import com.aim.project.pwp.instance.Location;
import com.aim.project.pwp.instance.PWPInstance;
import com.aim.project.pwp.interfaces.PWPInstanceInterface;
import com.aim.project.pwp.interfaces.PWPInstanceReaderInterface;


public class PWPInstanceReader implements PWPInstanceReaderInterface {

	@Override
	public PWPInstanceInterface readPWPInstance(Path path, Random random) {

		BufferedReader bfr;
		try {
			bfr = Files.newBufferedReader(path);
			
			int numberOfLocations = 0;
			String lineBuffer = "";
			Location oPostalDepotLocation = null, oHomeAddressLocation = null;
			ArrayList<Location> aoLocations = new ArrayList<Location>();
			
			// Read in the PWP instance and create and return a new 'PWPInstance'
			String param[];
			
			while(!lineBuffer.equals("EOF")) {
				lineBuffer = bfr.readLine();
				
				// Read in "POSTAL_OFFICE"
				if(lineBuffer.equals("POSTAL_OFFICE")) {
					lineBuffer = bfr.readLine();
					
					param = lineBuffer.split("\t| {1,}");
					oPostalDepotLocation = new Location(Double.valueOf(param[0]), Double.valueOf(param[1]));
				}
				// Read in "WORKER_ADDRESS"
				else if(lineBuffer.equals("WORKER_ADDRESS")) {
					lineBuffer = bfr.readLine();
					
					param = lineBuffer.split("\t| {1,}");
					oHomeAddressLocation = new Location(Double.valueOf(param[0]), Double.valueOf(param[1]));
				}
				// Read in "POSTAL_ADDRESSES"
				else if(lineBuffer.equals("POSTAL_ADDRESSES")) {
					lineBuffer = bfr.readLine();
					// Read in all "POSTAL_ADDRESSES", till reaches "EOF"
					while(!lineBuffer.equals("EOF")) {
						
						param = lineBuffer.split("\t| {1,}");
						aoLocations.add(new Location(Double.valueOf(param[0]), Double.valueOf(param[1])));
						numberOfLocations++;
						
						lineBuffer = bfr.readLine();
					}
				}
			}
			
			PWPInstance instance = new PWPInstance(	numberOfLocations + 2, 
													aoLocations.toArray(new Location[aoLocations.size()]), 
													oPostalDepotLocation, 
													oHomeAddressLocation, 
													random);
//			showInstance(instance);
			bfr.close();
			
			return instance;
		} catch (IOException e) {

			e.printStackTrace();
			return null;
		}
	}
	
	// Demonstrate instance locations
	public void showInstance(PWPInstanceInterface oInstance) {
		System.out.println("POSTAL_OFFICE");
		System.out.println("<" + oInstance.getHomeAddress().getX() + ", " + oInstance.getHomeAddress().getY() + ">\n");
		System.out.println("WORKER_ADDRESS");
		System.out.println("<" + oInstance.getPostalDepot().getX() + ", " + oInstance.getPostalDepot().getY() + ">\n");
		System.out.println("POSTAL_ADDRESSES");
		for(int index = 0; index < oInstance.getNumberOfLocations() - 2; index++)
			System.out.println("<" + oInstance.getLocationForDelivery(index).getX() + ", " + oInstance.getLocationForDelivery(index).getY() + ">");

	}
}
