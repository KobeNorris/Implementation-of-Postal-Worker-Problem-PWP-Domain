package com.aim.project.pwp;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import com.aim.project.pwp.instance.Location;

public class SolutionPrinter {

	private String strOutputFilePath;
	
	public SolutionPrinter(String strOutputFilePath) {
		
		this.strOutputFilePath = strOutputFilePath;
	}
	
	/**
	 * 
	 * @param loRouteLocations The array of Locations ordered in route order.
	 */
	public void printSolution(List<Location> loRouteLocations) {
		
		OutputStream os;
		try {
			os = new FileOutputStream(strOutputFilePath);
			PrintStream printStream = new PrintStream(os);
			loRouteLocations.forEach( l -> {
				printStream.println(l.getX() + "," + l.getY());
			});
			printStream.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	}
}
