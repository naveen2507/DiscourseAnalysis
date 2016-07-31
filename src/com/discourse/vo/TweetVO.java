package com.discourse.vo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 
 * @author Naveen
 * Class Description : DataObject definition for data. It reads the file and stores the value as an object for each row/instance.
 * 					   It includes tweetID,tweet,goldLabel,predictedLabel,userName,profileName,language .
 * 
 */
public class TweetVO {

	private String tweetId;
	private String tweet;
	private String goldLabel;
	private String predictedLabel;
	private String preprocessedTweet;
	private Map<String, Double> featureVector;
	
	
	public Map<String, Double> getFeatureVector() {
		return featureVector;
	}
	public void setFeatureVector(Map<String, Double> featureVector) {
		this.featureVector = featureVector;
	}
	public String getTweetId() {
		return tweetId;
	}
	public void setTweetId(String tweetId) {
		this.tweetId = tweetId;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public String getGoldLabel() {
		return goldLabel;
	}
	public void setGoldLabel(String goldLabel) {
		this.goldLabel = goldLabel;
	}
	public String getPredictedLabel() {
		return predictedLabel;
	}
	public void setPredictedLabel(String predictedLabel) {
		this.predictedLabel = predictedLabel;
	}
	public String getPreprocessedTweet() {
		return preprocessedTweet;
	}
	public void setPreprocessedTweet(String preprocessedTweet) {
		this.preprocessedTweet = preprocessedTweet;
	}
	
	
	
}
