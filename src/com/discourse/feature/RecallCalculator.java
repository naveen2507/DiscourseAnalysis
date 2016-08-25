package com.discourse.feature;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.discourse.helper.ApplicationDetails;
/**
 * Class to calculate recall values from test data predictions and annotated class label for test data
 * @author Naveen
 *
 */
public class RecallCalculator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

		List<String> annotated=getAnnotations(ApplicationDetails.testFeatureWriteFile_2);
		List<String> classified=getListValue(ApplicationDetails.testOutDataFile_2);
		int totalNegative=0;
	
		float wrongPositiveClassified=0;
		float rightPositiveClassified=0;
		float wrongNegativeClassified=0;
		float rightNegativeClassified=0;
		float wrongNeutralClassified=0;
		float rightNeutralClassified=0;
		for(int i=0;i<annotated.size();i++){
			
			String mlClassified[] = classified.get(i).split("\\.");
			String mlClass=mlClassified[0];
			String humanClass = annotated.get(i);
			if(humanClass.equals("0")){
				
				if(humanClass.equals(mlClass))
					rightPositiveClassified++;	
				else
					wrongPositiveClassified++;
				
			}
			else if(humanClass.equals("1")){
				
				if(humanClass.equals(mlClass))
					rightNeutralClassified++;	
				else
					wrongNeutralClassified++;
				
			}
			else if(humanClass.equals("2")){
				totalNegative++;
				if(humanClass.equals(mlClass))
					rightNegativeClassified++;	
				else
					wrongNegativeClassified++;
				
			}
		}
		//System.out.println(":::"+rightValidClassified);
		/*float posRecall= (rightValidClassified/(rightValidClassified+wronginValidClassified));
		float accuracy = ((rightValidClassified+rightinValidClassified)/annotated.size())*100;
		System.out.println("Valid Recall is :"+validRecall*100);
		System.out.println("Accuracy is:"+accuracy);*/
		
		float posRecall= (rightPositiveClassified/(rightPositiveClassified+wrongPositiveClassified));
		float negRecall= (rightNegativeClassified/(rightNegativeClassified+wrongNegativeClassified));
		float neutralRecall= (rightNeutralClassified/(rightNeutralClassified+wrongNeutralClassified));
		float accuracy = ((rightPositiveClassified+rightNeutralClassified+rightNegativeClassified)/annotated.size())*100;
		System.out.println("Pos Recall is :"+posRecall*100);
		System.out.println("neg Recall is :"+negRecall*100);
		System.out.println("neutral Recall is :"+neutralRecall*100);
		System.out.println("Accuracy is:"+accuracy);
		System.out.println("total negative is :::"+totalNegative);
	}
	
	
	
	public static void getRecall(String inputFile,String outFile){

		

		List<String> annotated=getAnnotations(inputFile);
		List<String> classified=getListValue(outFile);
		int totalNegative=0;
	
		float wrongPositiveClassified=0;
		float rightPositiveClassified=0;
		float wrongNegativeClassified=0;
		float rightNegativeClassified=0;
		float wrongNeutralClassified=0;
		float rightNeutralClassified=0;
		for(int i=0;i<annotated.size();i++){
			
			String mlClassified[] = classified.get(i).split("\\.");
			String mlClass=mlClassified[0];
			String humanClass = annotated.get(i);
			if(humanClass.equals("0")){
				
				if(humanClass.equals(mlClass))
					rightPositiveClassified++;	
				else
					wrongPositiveClassified++;
				
			}
			else if(humanClass.equals("1")){
				
				if(humanClass.equals(mlClass))
					rightNeutralClassified++;	
				else
					wrongNeutralClassified++;
				
			}
			else if(humanClass.equals("2")){
				totalNegative++;
				if(humanClass.equals(mlClass))
					rightNegativeClassified++;	
				else
					wrongNegativeClassified++;
				
			}
		}
		//System.out.println(":::"+rightValidClassified);
		/*float posRecall= (rightValidClassified/(rightValidClassified+wronginValidClassified));
		float accuracy = ((rightValidClassified+rightinValidClassified)/annotated.size())*100;
		System.out.println("Valid Recall is :"+validRecall*100);
		System.out.println("Accuracy is:"+accuracy);*/
		
		float posRecall= (rightPositiveClassified/(rightPositiveClassified+wrongPositiveClassified));
		float negRecall= (rightNegativeClassified/(rightNegativeClassified+wrongNegativeClassified));
		float neutralRecall= (rightNeutralClassified/(rightNeutralClassified+wrongNeutralClassified));
		float accuracy = ((rightPositiveClassified+rightNeutralClassified+rightNegativeClassified)/annotated.size())*100;
		System.out.println("Pos Recall is :"+posRecall*100);
		System.out.println("neg Recall is :"+negRecall*100);
		System.out.println("neutral Recall is :"+neutralRecall*100);
		System.out.println("Accuracy is:"+accuracy);
		System.out.println("total negative is :::"+totalNegative);
	
	}
	
	
	
	
	
	
	
	public static List<String> getListValue(String file)
	{
			List<String> predictClassLAbel = new ArrayList<String>();
			BufferedReader br;
			
			String line;
			try{
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				
			if(line.contains(":")){
			String elem[]=line.split(":");
			line = elem[1].trim();
			}
			predictClassLAbel.add(line);
				
			}
			System.out.println("Size of predictClassLAbel in list::"+predictClassLAbel.size());
			
			}
			catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
			
			
			
			return predictClassLAbel;
			
		}
	
	
	public static List<String> getAnnotations(String file)
	{
			List<String> classLabel = new ArrayList<String>();
			BufferedReader br;
			
			String line;
			try{
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				
			
			String elem[]=line.split("	");
			line = elem[0].trim();
			
			classLabel.add(line);
				
			}
			System.out.println("Size of annotated classlabel in list::"+classLabel.size());
			
			}
			catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
			
			
			
			return classLabel;
			
		}
	
	
	
	public static List<String> get(String file)
	{
			List<String> unigrams = new ArrayList<String>();
			BufferedReader br;
			
			String line;
			try{
			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				
			
				unigrams.add(line);
				
			}
			System.out.println("Size of unigram in list::"+unigrams.size());
			
			}
			catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
			
			
			
			return unigrams;
			
		}

}
