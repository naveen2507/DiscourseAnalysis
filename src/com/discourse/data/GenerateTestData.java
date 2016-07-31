package com.discourse.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateTestData {

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//String names[] = {"sad", "anger", "fear", "happy", "disgust", "surprise"};
		
		List<String> listTweet = new ArrayList<String>();
		BufferedReader br;
		Map<String,Integer> countMap  = new HashMap<String,Integer>();
		
		/*countMap.put("happy", 28400);
		countMap.put("sad", 11618);
		countMap.put("anger", 5176);
		countMap.put("fear", 8648);
		countMap.put("disgust", 185);
		countMap.put("surprise", 2130);*/
		GenerateTestData obj = new GenerateTestData();
		countMap = obj.getMapCount(listTweet);
		countMap = obj.getTestMapCount(countMap);
		String line;
		try {
			FileWriter writerTest = new FileWriter("D:\\Uni-MS\\Discourse\\my_paper\\Project\\2Class\\test-data.csv");
			FileWriter writerTrain = new FileWriter("D:\\Uni-MS\\Discourse\\my_paper\\Project\\2Class\\train-data.csv");
			
			//br = new BufferedReader(new FileReader("D:\\Uni-MS\\NLPTeamLab\\DataTeamLAb\\gold-data.csv"));
			int i=0;
			
			//while ((line = br.readLine()) != null) {
			for(String tweet : listTweet){
					
				String elem[] = tweet.split("	",2);
			    String category = elem[1];
			    if(countMap.get(category)>0){
			    	writerTest.append(tweet);
			    	writerTest.append("\n");
			    	countMap.put(category, countMap.get(category)-1);
			    }else{
			    	writerTrain.append(tweet);
			    	writerTrain.append("\n");
			    	
			    }
			}
			
			writerTrain.flush();
			writerTrain.close();
			writerTest.flush();
			writerTest.close();
			
		}catch(Exception e){
			
		}
		

	}

	
	public Map<String,Integer> getMapCount(List<String> listTweet){
		BufferedReader br;
		String line;
		Map<String,Integer> countMap  = new HashMap<String,Integer>();
		try {
			
			br = new BufferedReader(new FileReader("D:\\Uni-MS\\Discourse\\my_paper\\Project\\Manually-Annotated-Tweets.tsv"));
			int i=0;
			while ((line = br.readLine()) != null) {
				
				String elem[] = line.split("	",2);
			    String category = elem[1];
			    
			    if(category.equalsIgnoreCase("objspam")||category.equalsIgnoreCase("objnspam")){
			    	continue;
			    }
		    	if(countMap.containsKey(category)){
		    		countMap.put(category, countMap.get(category)+1);
		    	}else{
		    		countMap.put(category, 1);
		    	}
			    listTweet.add(line);
				
			}
			
		}catch(Exception e){
			
		}
		return countMap;
		
	}
	
	public Map<String,Integer> getTestMapCount(Map<String,Integer> mapCount){
		for(String category : mapCount.keySet()){
			int count = mapCount.get(category);
			System.out.println(category+":"+mapCount.get(category));
			int acount = (int) Math.round(count/20);
			System.out.println(category+":"+acount);
			mapCount.put(category, acount);

		}
		
		return mapCount;
		
	}
		
}
