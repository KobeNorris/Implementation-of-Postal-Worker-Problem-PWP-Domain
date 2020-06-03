package com.aim.project.pwp.interfaces;

import java.nio.file.Path;
import java.util.Random;

/**
 * 
 * @author Warren G. Jackson
 *
 */
public interface PWPInstanceReaderInterface {

	/**
	 * 
	 * @param path The path to the instance file.
	 * @param random The random number generator to use.
	 * @return A new instance of the PWP problem as specified by the instance file.
	 */
	public PWPInstanceInterface readPWPInstance(Path path, Random random);
}
