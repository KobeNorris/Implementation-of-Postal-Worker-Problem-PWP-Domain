package com.aim.project.pwp.util;

import java.util.Random;

public final class ArrayMethods {

	// Prevent user from initialization
	private ArrayMethods() {}

	public static void shuffle(int[] array, Random rng) {
		for(int index = array.length - 1; index > 0; --index) {
			int rngIndex = rng.nextInt(index);
			swap(array, index, rngIndex);
//			System.out.println(index + " -- " + rngIndex);
		}
	}
	
	public static void shuffule(int[] array) {
		shuffle(array, new Random());
	}
	
	
	public static void swap(int[] array, int index1, int index2) {
		int temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}
	
	public static void swapAdjacent(int[] array, int index) {
		int adjacentIndex = (index + 1) % array.length;
		swap(array, index, adjacentIndex);
	}
	
	
	public static void reverse(int[] array, int startIndex, int endIndex) {
		for(int i = startIndex, j = endIndex; i < j; ++i, --j)
			swap(array, i, j);
	}
	
	public static void reverse(int[] array) {
		reverse(array, 0, array.length);
	}
}
