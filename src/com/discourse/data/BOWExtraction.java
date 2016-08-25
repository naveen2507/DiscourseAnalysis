package com.discourse.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.discourse.helper.Helper;

/**
 * Description : This class is for Bag of word extraction , that is Ngram extraction.
 * @author Naveen
 *
 */

public class BOWExtraction {

	public static void main(String args[]) throws IOException{
		
		Map<String,Integer> uniGram = new HashMap<String,Integer>();
		Map<String,Integer> biGram = new HashMap<String,Integer>();
		Helper objHelper = Helper.getInstance();
		BufferedReader br;
		
		String line;
		br = new BufferedReader(new FileReader("D:\\Uni-MS\\Discourse\\my_paper\\Project\\2Class\\StemmedData\\preprocessed-data.csv"));
		while ((line = br.readLine()) != null) {
			
			String elem[] = line.split(",",3);
			String tweet = elem[2];
			objHelper.getNgrams(tweet.toLowerCase().trim(), 1, uniGram);
			objHelper.getNgrams(tweet.toLowerCase().trim(), 2, biGram);
	 	}
		
		FileWriter writerUni = new FileWriter("D:\\Uni-MS\\Discourse\\my_paper\\Project\\2Class\\StemmedData\\Unigrams.csv");
		FileWriter writerBi = new FileWriter("D:\\Uni-MS\\Discourse\\my_paper\\Project\\2Class\\StemmedData\\Bigrams.csv");
		
		uniGram = objHelper.sortByComparator(uniGram, false);
		biGram = objHelper.sortByComparator(biGram, false);
		
		List<String> stopWordList = objHelper.getStopList();
		for(String word : uniGram.keySet()){
			if(stopWordList.contains(word)){
				continue;
			}
			writerUni.append(word+","+uniGram.get(word));
			writerUni.append("\n");
		}
		for(String word : biGram.keySet()){
			String elem[] = word.split(" ");
			if(stopWordList.contains(elem[0]) && stopWordList.contains(elem[1])){
				continue;
			}
			writerBi.append(word+","+biGram.get(word));
			writerBi.append("\n");
		}
		
		writerUni.close();
		writerBi.close();
	}
	
	
}
