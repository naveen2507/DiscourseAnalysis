package com.discourse.feature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.discourse.data.Preprocessing;
import com.discourse.helper.ApplicationDetails;
import com.discourse.vo.TweetVO;

public class FeatureVectorWriteCSV {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub


		FeatureExtraction featureObj = new FeatureExtraction();
		Preprocessing preprocessObj = new Preprocessing();
		List<TweetVO> lsttweetVO ;
		if(ApplicationDetails.class2){
			if(ApplicationDetails.train){
				lsttweetVO = preprocessObj.getListTweetVO(ApplicationDetails.TRAIN_DATA_2);
			}
			else{
				lsttweetVO = preprocessObj.getListTweetVO(ApplicationDetails.TEST_DATA_2);	
			}
				
		}
		else{
			if(ApplicationDetails.train){
				lsttweetVO = preprocessObj.getListTweetVO(ApplicationDetails.TRAIN_DATA_3);
			}
			else{
				lsttweetVO = preprocessObj.getListTweetVO(ApplicationDetails.TRAIN_DATA_3);	
			}
		}
		
		List<TweetVO> listFeatureTweets = featureObj.getFeatureVectors(lsttweetVO);
		Set<String> featureNames = ApplicationDetails.getInstance().getFeatureNames();
		System.out.println("Size of Labels: "+featureNames.size());
		FileWriter featureWriter;
		if(ApplicationDetails.class2){
			if(ApplicationDetails.train){
				 featureWriter = new FileWriter(ApplicationDetails.trainFeatureWriteFile_2);
			}
			else{
				 featureWriter = new FileWriter(ApplicationDetails.testFeatureWriteFile_2);	
			}
				
		}
		else{
			if(ApplicationDetails.train){
				 featureWriter = new FileWriter(ApplicationDetails.trainFeatureWriteFile_3);
			}
			else{
				 featureWriter = new FileWriter(ApplicationDetails.testFeatureWriteFile_3);	
			}
			
		}
		
		featureWriter.append("Class_Labels");
		int n = 0;
		for(String featureStr:featureNames){		
			featureWriter.append(",");
			n++;
			featureWriter.append("column"+n);
			
		}
		featureWriter.append("\n");
		
		//Map<String,Integer> featureNamesInt = new HashMap<String,Integer>();
		//int num = 0;
		for(TweetVO tweetInstance : listFeatureTweets){
			/*
			if(ApplicationDetails.class2){
				if(tweetInstance.getGoldLabel().equalsIgnoreCase("positive")){
					featureWriter.append(0+"");			
				}
				else if(tweetInstance.getGoldLabel().equalsIgnoreCase("negative")){
					featureWriter.append(1+"");				
				}
			}else{
				if(tweetInstance.getGoldLabel().equalsIgnoreCase("positive")){
					featureWriter.append(0+"");			
				}
				else if(tweetInstance.getGoldLabel().equalsIgnoreCase("negative")){
					featureWriter.append(1+"");				
				}
				else if(tweetInstance.getGoldLabel().equalsIgnoreCase("objnspam")){
					featureWriter.append(2+"");				
				}
			}*/
			
			featureWriter.append(tweetInstance.getGoldLabel());
			//featureWriter.append(tweetInstance.getGoldLabel());
			//featureWriter.append(",");
			//featureWriter.append(tweetInstance.getTweetId());
			
			Map<String,Double> featureVector = tweetInstance.getFeatureVector(); 
			//for(String featureName : featureVector.keySet()){
			int i =0;
			for(String featureName : featureNames){
				/*if(!(featureVector.containsKey(featureName))){
					featureNamesInt.put(featureName, num++);
				}*/
				//int index = featureNamesInt.get(featureName);
				if(featureVector.containsKey(featureName)){
					featureWriter.append(",");
					featureWriter.append(""+featureVector.get(featureName));
				}else{
					featureWriter.append(",");
					featureWriter.append(""+0.0);
				}
				i++;
				if(i==0){
					//System.out.println(featureName);
				}
				
				//featureWriter.append("\t");
				//featureWriter.append(index+":"+featureVector.get(featureName));
				//featureWriter.append(featureName+":"+featureVector.get(featureName));
			}
			featureWriter.append("\n");
			System.out.println(i);
		}
			System.out.println(featureNames.contains("Class_Label"));
		
		featureWriter.flush();
		featureWriter.close();
	}

}
