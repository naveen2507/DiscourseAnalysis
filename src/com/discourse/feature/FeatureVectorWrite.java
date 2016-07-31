package com.discourse.feature;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.discourse.data.Preprocessing;
import com.discourse.helper.ApplicationDetails;
import com.discourse.vo.TweetVO;

public class FeatureVectorWrite {

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
		Set<String> featureNamesStr = ApplicationDetails.getInstance().getFeatureNames();
		System.out.println("Size of feature list "+featureNamesStr.size());
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
		
		
		
		Map<String,Integer> featureNames = new HashMap<String,Integer>();
		int num = 0;
		for(TweetVO tweetInstance : listFeatureTweets){
			
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
			}
			
			//featureWriter.append(tweetInstance.getGoldLabel());
			//featureWriter.append("\t");
			//featureWriter.append(tweetInstance.getTweetId());
			
			Map<String,Double> featureVector = tweetInstance.getFeatureVector(); 
			for(String featureName : featureVector.keySet()){
				if(!(featureNames.containsKey(featureName))){
					featureNames.put(featureName, num++);
				}
				int index = featureNames.get(featureName);
				featureWriter.append("\t");
				featureWriter.append(index+":"+featureVector.get(featureName));
				//featureWriter.append(featureName+":"+featureVector.get(featureName));
			}
			featureWriter.append("\n");
			
		}
		
		featureWriter.flush();
		featureWriter.close();
	}

}
