package com.discourse.feature;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import com.discourse.helper.ApplicationDetails;

/**
 * 
 * Split data into train and test data.
 * @author Naveen
 *
 */
public class SplitLibSVMData {

	/**
	 * @param args
	 * 
	 * arg 0 : input file name
	 * arg 1 : output train file name
	 * arg 2 : output test file name
	 * arg 3 : Test data split %
	 * 
	 */
	public static void main(String[] args) {
		/*
		String inputFileName = args[0];
        String trainingOutput =args[1];
        String testOutput = args[2];
        */

		String inputFileName = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\feature-train-model6";
        String trainingOutput ="D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\feature-train-model06";
        String testOutput = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\feature-test-model06";
        
		Integer randomSelectionPct = Integer.parseInt("20");;   
        Double threshold = randomSelectionPct * 1.0/100.0;
        Random random = new Random();
        BufferedReader br = null;
		String line = "";
		
        try{
        FileWriter writerTrain = new FileWriter(trainingOutput);
        FileWriter writerTest = new FileWriter(testOutput);
        br = new BufferedReader(new FileReader(inputFileName));
		int lineNumber =0;
        while ((line = br.readLine()) != null) {
        	lineNumber++;
			System.out.println("Processing line number : "+lineNumber);
			if(random.nextDouble() < threshold){
				writerTest.append(line);
				writerTest.append('\n');
			}else{
				writerTrain.append(line);
				writerTrain.append('\n');
			}
			
		}
		writerTest.flush();
		writerTest.close();
		writerTrain.flush();
		writerTrain.close();
        
        
        }
        catch(Exception e)
        {
        	e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}

}
