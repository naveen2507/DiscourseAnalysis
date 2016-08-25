package com.discourse.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.discourse.helper.ApplicationDetails;
import com.discourse.vo.TweetVO;

/**
 * Description : Preprocess data and shas function to get stemmed data
 * @author Naveen
 *
 */
public class Preprocessing {

	/**
	 * Read smiley dictionary
	 * @return
	 */
	public Map<String, String> mapSmileyemotion() {
		BufferedReader br;
		String line;
		Map<String, String> smileyMap = new HashMap<String, String>();

		try {
			br = new BufferedReader(
					new FileReader(ApplicationDetails.smileyDictionary));
			while ((line = br.readLine()) != null) {
				String elem[] = line.split("\t", 2);
				// System.out.println(elem[1]);
				// System.out.println(elem[0]);
				smileyMap.put(elem[0], elem[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return smileyMap;

	}

	/**
	 * Get preprocessed tweet from tweet
	 * @param tweet
	 * @return
	 */
	public String getPreprocessedtweet(String tweet) {

		// Map<String, String> smileyMap = mapSmileyemotion();

		// update smiley with emotions
		// preprocessedTweet = replaceSmileyEmotion(smileyMap,
		// preprocessedTweet);

		// remove all user names and RT
		String preprocessedTweet = tweet.replaceAll("@[A-Za-z]+", "@user").replaceAll("RT", "").replaceAll("(\\[#RT\\])", "")

		// remove "[NEWLINE]"
				.replaceAll("\\[NEWLINE\\]", "");

		// remove non English characters
		preprocessedTweet = preprocessedTweet.replaceAll("[^A-Za-z0-9@,\"?#!'::.\\[\\]\\p{M}\\p{Nd}\\s\\\\\\/]", " ");

		// remove extra spaces if any
		preprocessedTweet = preprocessedTweet.trim().replaceAll("\\s+(?=\\s)", "");
				
		//remove n't to not like don't to not
		preprocessedTweet = preprocessedTweet.trim().replaceAll("[a-z]+n't\\s*", "not ")
						
		// replace urls with "[URL]"
				.replaceAll("https?.*$", "[URL]")
				// replace multiple occurrences
				.replaceAll("[.?\\s:!,]{2,}", " ")
				;

		return preprocessedTweet;
	}
	
	/**
	 * Read tweet file and get it in List<TweetVO> object
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public List<TweetVO> getListTweetVO(String fileName) throws IOException{
		List<TweetVO> listTweetVO = new ArrayList<TweetVO>();
		BufferedReader br;
		String line;
		Stemmer stemObj = new Stemmer();
		Map<String,String> rawTweets;
		if(ApplicationDetails.class2){
			rawTweets = getRawTweet(ApplicationDetails.rawTweetFile_2);
		}
		else{
			rawTweets = getRawTweet(ApplicationDetails.rawTweetFile_3);
		}
		
		br = new BufferedReader(new FileReader(fileName));
		int i = 0;
		while ((line = br.readLine()) != null) {
			i++;
			String elem[] = line.split(",");
			if(ApplicationDetails.class2){
				if(elem[1].equalsIgnoreCase("Positive")||elem[1].equalsIgnoreCase("Negative")){	
					String tweet = getPreprocessedtweet(elem[2]);
					TweetVO tweetInstance = new TweetVO();
					String stemmedTweet ; 
					if(ApplicationDetails.stem){
						 stemmedTweet = stemObj.getStemmedSentence(tweet.toLowerCase());
						 tweetInstance.setPreprocessedTweet(" "+stemmedTweet.trim()+" ");
					}
					else{
						tweetInstance.setPreprocessedTweet(" "+tweet.trim()+" ");
					}
					tweetInstance.setTweetId(elem[0]+"");
					tweetInstance.setGoldLabel(elem[1]);
					tweetInstance.setTweet(rawTweets.get(elem[0]));			
					listTweetVO.add(tweetInstance);
					}
			}
			else{
				if(elem[1].equalsIgnoreCase("Positive")||elem[1].equalsIgnoreCase("Negative")||elem[1].equalsIgnoreCase("objnspam")){	
					String tweet = getPreprocessedtweet(elem[2]);
					TweetVO tweetInstance = new TweetVO();
					String stemmedTweet ; 
					if(ApplicationDetails.stem){
						 stemmedTweet = stemObj.getStemmedSentence(tweet.toLowerCase());
						 tweetInstance.setPreprocessedTweet(stemmedTweet);
					}
					else{
						tweetInstance.setPreprocessedTweet(tweet);
					}
					tweetInstance.setTweetId(i+"");
					tweetInstance.setGoldLabel(elem[1]);
					tweetInstance.setTweet(rawTweets.get(elem[0]));			
					listTweetVO.add(tweetInstance);
					}
			}
			
			
		}
		return listTweetVO;
		
	}
	
	/**
	 * Get Stemmed Tweet data in preprocessed file
	 * @param arg
	 * @throws IOException
	 */
	public static void main(String arg[]) throws IOException{
		
		FileWriter writer = new FileWriter("D:\\Uni-MS\\Discourse\\my_paper\\Project\\3Class\\StemmedData\\preprocessed-data.csv");
		FileWriter rawwriter = new FileWriter("D:\\Uni-MS\\Discourse\\my_paper\\Project\\3Class\\StemmedData\\raw-data.csv");
		
		Preprocessing obj = new Preprocessing();
		BufferedReader br;
		String line;
		br = new BufferedReader(new FileReader("D:\\Uni-MS\\Discourse\\my_paper\\Project\\Manually-Annotated-Tweets.tsv"));
		int i =0;
		while ((line = br.readLine()) != null) {
			i++;
			System.out.println("Line Number : "+i);
			String elem[] = line.split("	");
			if(elem[1].equalsIgnoreCase("Positive")||elem[1].equalsIgnoreCase("Negative")||elem[1].equalsIgnoreCase("objnspam")){
			//if(elem[1].equalsIgnoreCase("Positive")||elem[1].equalsIgnoreCase("Negative")){	
			String tweet = obj.getPreprocessedtweet(elem[0]);
			Stemmer stemObj = new Stemmer();
			String stemmedTweet = stemObj.getStemmedSentence(tweet.toLowerCase());
			writer.append(i+"");
			writer.append(",");
			writer.append(elem[1]);
			writer.append(",");
			writer.append(stemmedTweet.toLowerCase());
			writer.append("\n");

			rawwriter.append(i+"");
			rawwriter.append(",");
			rawwriter.append(elem[1]);
			rawwriter.append(",");
			rawwriter.append(elem[0]);
			rawwriter.append("\n");

			
			}
		}
		writer.close();
		
	}
	
	public Map<String,String> getRawTweet(String fileName) throws IOException{
		
		BufferedReader br;
		String line;
		Map<String,String> map = new HashMap<String,String>();
		br = new BufferedReader(new FileReader(fileName));
		
		while ((line = br.readLine()) != null) {
			String elem[] = line.split(",", 3);
			if(map.containsKey(elem[0]))
				continue;
			map.put(elem[0], " "+elem[2].trim()+" ");
		}
		return map;
		
	}

		
}
