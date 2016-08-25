package com.discourse.feature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.discourse.data.SentiWordNetDemoCode;
import com.discourse.helper.ApplicationDetails;
import com.discourse.helper.Helper;
import com.discourse.vo.TweetVO;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;
/**
 * Get feature vector for each instance of TweetVO with different experimental setup of feature vectors
 * @author Naveen
 *
 */
public class FeatureExtraction {

	public Set<String> featureNames = new LinkedHashSet<String>();
	Map<String, Integer> unigramMap;
	Map<String, Integer> bigramMap;
	List<String> stopWordList;
	Map<String, List<String>> emotionMap;
	Map<String, String> smileyMap;
	MaxentTagger tagger = null ;
	SentiWordNetDemoCode sentiWordNetObj = null;
	
	Map<String, String> posTagList;

	public List<TweetVO> getFeatureVectors(List<TweetVO> lstTweetVO) throws IOException {
		if(ApplicationDetails.class2){
			unigramMap = Helper.getInstance().getNgramDictionary(ApplicationDetails.unigramDictioanry_2);
			bigramMap = Helper.getInstance().getNgramDictionary(ApplicationDetails.bigramDictioanry_2);	
		}else{
			unigramMap = Helper.getInstance().getNgramDictionary(ApplicationDetails.unigramDictioanry_3);
			bigramMap = Helper.getInstance().getNgramDictionary(ApplicationDetails.bigramDictioanry_3);
		}
		
		if(ApplicationDetails.TAG_FEATURE){
			tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
			posTagList =  Helper.getInstance().posTagList(ApplicationDetails.posTagDisctionary);
		}
		
		if(ApplicationDetails.Senti_Feature){
			tagger = new MaxentTagger("taggers/english-left3words-distsim.tagger");
			sentiWordNetObj = new SentiWordNetDemoCode(ApplicationDetails.sentiList);
		}
			
			
		
		stopWordList = Helper.getInstance().getStopList();
		// negationDictionary =
		// Helper.getInstance().getNegationDict(ApplicationDetails.negationDictionaryPath);
		System.out.println("Size of Uni :" + unigramMap.size());
		System.out.println("Size of Bi :" + bigramMap.size());
		//System.out.println("Size of Emo :" + emotionMap.size());
		smileyMap = Helper.getInstance().getSmileyDict(ApplicationDetails.smileyDictionary);
		List<TweetVO> lstFeatureTweetVO = new ArrayList<TweetVO>();
		int i = 0;
		for (TweetVO tweetVO : lstTweetVO) {
			i++;
			if (i % 100 == 0)
				System.out.println("Feature Ext for Tweet No :" + i);
			Map<String, Double> featureVector = new HashMap<String, Double>();
			String tweet = tweetVO.getPreprocessedTweet().toLowerCase();
			if(ApplicationDetails.TAG_FEATURE){
				getNgramTagFeatures(tagger, tweet, featureVector);
				discourseTagFeature(tweet, featureVector);
			}else{
				getNgramFeatures(tweet, featureVector);
				discourseFeature(tweet, featureVector);
			}
			
			if(ApplicationDetails.Senti_Feature)
				getSentiWordNetFeatures(tweet, featureVector);
			
			
			
			//getSmileyFeature(tweetVO.getTweet(), featureVector, smileyMap);
			/****
			 * Other Features Call Here
			 */

		    tweetVO.setFeatureVector(featureVector);
			lstFeatureTweetVO.add(tweetVO);

		}
		
		
		ApplicationDetails appDetails = ApplicationDetails.getInstance();
		appDetails.setFeatureNames(featureNames);
		return lstFeatureTweetVO;
	}

	/**
	 * Implementing discourse feature vector
	 * @param tweet
	 * @param featureVector
	 * @throws IOException
	 */
	public void discourseFeature(String tweet, Map<String, Double> featureVector) throws IOException {
//		String taggedTweet = tagger.tagString(tweet);	
//		String wordTagList[] = taggedTweet.split(" ");
		String[] wordList = tweet.split(" ");
		
		List<String> conditionals = Arrays.asList(ApplicationDetails.CONDITIONALS.split(","));
		List<String> conj_fol = Arrays.asList(ApplicationDetails.CONJ_FOL.split(","));
		List<String> negation = Arrays.asList(ApplicationDetails.NEGATION.split(","));
		List<String> conj_prev = Arrays.asList(ApplicationDetails.CONJ_PREV.split(","));

		int i = 0;
		for (String word : wordList) {
			if(!(stopWordList.contains(word))){
				featureVector = updateFeatureVector(featureVector, word, 1.0);
			}
			
			if (!(featureVector.containsKey("flip_" + word))) {
				featureVector.put("flip_" + word, 1.0);
				featureNames.add("flip_" + word);
			}

			if (conditionals.contains(word.trim())) {
				featureVector.put("hyp_" + word, 1.0);
				featureNames.add("hyp_" + word);

			} else if (conj_fol.contains(word.trim())) {
				for (int j = i + 1; j < wordList.length; j++) {
					String fol_word = wordList[j];
					if(stopWordList.contains(fol_word)){
						continue;
					}
					if (conj_fol.contains(fol_word.trim()) || conj_prev.contains(fol_word.trim())
							|| conditionals.contains(fol_word.trim()) || negation.contains(fol_word.trim())) {
						break;
					}
					featureVector = updateFeatureVector(featureVector, fol_word, 1.0);
				}

			}

			else if (conj_prev.contains(word.trim())) {
				for (int j = i - 1; j >= 0; j--) {
					String prev_word = wordList[j];
					if(stopWordList.contains(prev_word)){
						continue;
					}
					if (conj_fol.contains(prev_word.trim()) || conj_prev.contains(prev_word.trim())
							|| conditionals.contains(prev_word.trim()) || negation.contains(prev_word.trim())) {
						break;
					}
					featureVector = updateFeatureVector(featureVector, prev_word, 1.0);
				}
			}

			else if (negation.contains(word.trim())) {
				for (int j = i + 1; j < i + 1 + 5; j++) {
					if(j>=wordList.length)
						break;
					String fol_word = wordList[j];
					if(stopWordList.contains(fol_word)){
						continue;
					}
					if (conj_fol.contains(fol_word.trim()) || conj_prev.contains(fol_word.trim())) {
						break;
					}
					featureVector.put("flip_" + fol_word, -1.0);
					featureNames.add("flip_" + fol_word);

				}

			}
			i++;
		}
	}

	
	public void discourseTagFeature(String tweet, Map<String, Double> featureVector) throws IOException {
		String taggedTweet = tagger.tagString(tweet);	
		String wordTagList[] = taggedTweet.split(" ");
		
		List<String> conditionals = Arrays.asList(ApplicationDetails.CONDITIONALS.split(","));
		List<String> conj_fol = Arrays.asList(ApplicationDetails.CONJ_FOL.split(","));
		List<String> negation = Arrays.asList(ApplicationDetails.NEGATION.split(","));
		List<String> conj_prev = Arrays.asList(ApplicationDetails.CONJ_PREV.split(","));

		int i = 0;
		for (String wordTag : wordTagList) {
			String element[] = wordTag.split("_");
			String word = element[0];
			if(!(stopWordList.contains(word))){
				//if(element[1].startsWith("N")||element[1].startsWith("V")||element[1].startsWith("J")||element[1].startsWith("R"))
				if(element[1].startsWith("N")||element[1].startsWith("J")||element[1].startsWith("R"))
					featureVector = updateFeatureVector(featureVector, word, 1.0);
			}
			
			if (!(featureVector.containsKey("flip_" + word))) {
				featureVector.put("flip_" + word, 1.0);
			}

			if (conditionals.contains(word.trim())) {
				featureVector.put("hyp_" + word, 1.0);

			} else if (conj_fol.contains(word.trim())) {
				for (int j = i + 1; j < wordTagList.length; j++) {
					String folTagWord = wordTagList[j];
					String fol_ele[] = folTagWord.split("_");
					
					if(stopWordList.contains(fol_ele[0])){
						continue;
					}
					if (conj_fol.contains(fol_ele[0].trim()) || conj_prev.contains(fol_ele[0].trim())
							|| conditionals.contains(fol_ele[0].trim()) || negation.contains(fol_ele[0].trim())) {
						break;
					}
					//if(fol_ele[1].startsWith("N")||fol_ele[1].startsWith("V")||fol_ele[1].startsWith("J")||fol_ele[1].startsWith("R"))
					if(fol_ele[1].startsWith("N")||fol_ele[1].startsWith("J")||fol_ele[1].startsWith("R"))
						featureVector = updateFeatureVector(featureVector, fol_ele[0], 1.0);
				}

			}

			else if (conj_prev.contains(word.trim())) {
				for (int j = i - 1; j >= 0; j--) {
					String prevTagword = wordTagList[j];
					String prev_ele[] = prevTagword.split("_");
					
					
					if(stopWordList.contains(prev_ele[0])){
						continue;
					}
					if (conj_fol.contains(prev_ele[0].trim()) || conj_prev.contains(prev_ele[0].trim())
							|| conditionals.contains(prev_ele[0].trim()) || negation.contains(prev_ele[0].trim())) {
						break;
					}
					//if(prev_ele[1].startsWith("N")||prev_ele[1].startsWith("V")||prev_ele[1].startsWith("J")||prev_ele[1].startsWith("R"))
					if(prev_ele[1].startsWith("N")||prev_ele[1].startsWith("J")||prev_ele[1].startsWith("R"))
						featureVector = updateFeatureVector(featureVector, prev_ele[0], 1.0);
				}
			}

			else if (negation.contains(word.trim())) {
				for (int j = i + 1; j < i + 1 + 5; j++) {
					if(j>=wordTagList.length)
						break;
					
					String folTagword = wordTagList[j];
					String fol_elem[] = folTagword.split("_");
					if(stopWordList.contains(fol_elem[0])){
						continue;
					}
					if (conj_fol.contains(fol_elem[0].trim()) || conj_prev.contains(fol_elem[0].trim())) {
						break;
					}
					//if(fol_elem[1].startsWith("N")||fol_elem[1].startsWith("V")||fol_elem[1].startsWith("J")||fol_elem[1].startsWith("R"))
					if(fol_elem[1].startsWith("N")||fol_elem[1].startsWith("J")||fol_elem[1].startsWith("R"))
						featureVector.put("flip_" + fol_elem[0], -1.0);
				}

			}
			i++;
		}
	}

	
	
	public Map<String, Double> updateFeatureVector(Map<String, Double> featureVector, String word, double value) {
		
		
		if (featureVector.containsKey("weight_" + word)) {
			double weight = featureVector.get("weight_" + word);
			featureVector.put("weight_" + word, weight + value);
			featureNames.add("weight_" + word);
		} else {
			featureVector.put("weight_" + word, value);
			featureNames.add("weight_" + word);
		}
		return featureVector;

	}

	public void getNgramFeatures(String tweet, Map<String, Double> featureVector) throws IOException {

	/*	for (String unigram : unigramMap.keySet()) {
			if(stopWordList.contains(unigram)){
				continue;
			}
			if (tweet.contains(" " + unigram + " ")) {
				featureVector.put(unigram, 1.0);
				featureNames.add(unigram);
			}
		}*/
		
		
		for (String bigram : bigramMap.keySet()) {
			if (tweet.contains(" " + bigram + " ")) {
				featureVector.put(bigram, 1.0);
				featureNames.add(bigram);
			}
		}

	}
	
	public void getNgramTagFeatures(MaxentTagger tagger , String tweet, Map<String, Double> featureVector) throws IOException {

		String taggedTweet = tagger.tagString(tweet);
		String elements[] = taggedTweet.split(" ");
		for(String taggedToken : elements){
			String elem[] = taggedToken.split("_");
			if(stopWordList.contains(elem[0])){
				continue;
			}
			
			if(posTagList.containsKey(elem[1])){
				
				if(elem[1].startsWith("N")||elem[1].startsWith("V")||elem[1].startsWith("J")||elem[1].startsWith("R")){
					featureVector.put(elem[0], 1.0);
					featureVector.put(elem[0]+"_tag", Double.parseDouble(posTagList.get(elem[1])));
				}
					
			
			}
			
		}
		/*for (String unigram : unigramMap.keySet()) {
			if(stopWordList.contains(unigram)){
				continue;
			}
			if (tweet.contains(" " + unigram + " ")) {
				featureVector.put(unigram, 1.0);
				featureNames.add(unigram);
			}
		}*/
		for (String bigram : bigramMap.keySet()) {
			if (tweet.contains(" " + bigram + " ")) {
				featureVector.put(bigram, 1.0);
				featureNames.add(bigram);
			}
		}

	}

	
	public void getSentiWordNetFeatures(String tweet, Map<String, Double> featureVector) throws IOException {

		String taggedTweet = tagger.tagString(tweet);
		String elements[] = taggedTweet.split(" ");
		for(String taggedToken : elements){
			String elem[] = taggedToken.split("_");
			double value;
			try{
				if(elem[1].startsWith("N")){
					value = sentiWordNetObj.extract(elem[0], "n");
					
				}else if (elem[1].startsWith("J")) {
					value = sentiWordNetObj.extract(elem[0], "a");
				}else{
					value = sentiWordNetObj.extract(elem[0], "a");
				}
				featureVector.put(elem[0], value);
				featureNames.add(elem[0]);
			}catch(Exception e){
				continue;
			}
			
		}
		

	}


	public void getSmileyFeature(String tweet, Map<String, Double> featureVector, Map<String, String> smileyMap)
			throws IOException {
		//String[] wordList = tweet.split(" ");
		for (String smiley : smileyMap.keySet()) {
			if(tweet==null){
				continue;
			}
			if (tweet.contains(smiley)) {
				String val = smileyMap.get(smiley);
				String feature = smiley;
				if(val.equalsIgnoreCase("Extremely-Positive")){
					featureVector.put(feature, 4.0);	
				}
				else if (val.equalsIgnoreCase("Extremely-Negative")) {
					featureVector.put(feature, -4.0);
				}
				else if (val.equalsIgnoreCase("Positive")) {
					featureVector.put(feature, 2.0);
				}
				else if (val.equalsIgnoreCase("Negative")) {
					featureVector.put(feature, -2.0);
				}
				else if (val.equalsIgnoreCase("Neutral")) {
					featureVector.put(feature, 1.0);
				}
				//featureVector.put(feature, val);
				featureNames.add(feature);

			}
		}
	
//		for(String smiley:smileyMap.keySet()){
//			
//			if(tweet.toLowerCase().contains(smiley)){
//				String feature = smiley.concat("-").concat(smiley);
//				String val = smileyMap.get(smiley);
//				if(val.equalsIgnoreCase("Extremely-Positive")){
//					featureVector.put(feature, 5.0);	
//				}
//				else if (val.equalsIgnoreCase("Extremely-Negative")) {
//					featureVector.put(feature, -5.0);
//				}
//				else if (val.equalsIgnoreCase("Positive")) {
//					featureVector.put(feature, 3.0);
//				}
//				else if (val.equalsIgnoreCase("Negative")) {
//					featureVector.put(feature, -3.0);
//				}
//				else if (val.equalsIgnoreCase("Neutral")) {
//					featureVector.put(feature, 1.0);
//				}
//				featureNames.add(feature);
//
//			}
//		}
	
	}

	
	
}
