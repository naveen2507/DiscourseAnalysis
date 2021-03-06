package com.discourse.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;


/**
 * Configuration File
 * @author Naveen
 *
 */
public class ApplicationDetails {

	private static ApplicationDetails appDetails;

	public Set<String> featureNames;
	
	public Set<String> getFeatureNames() {
		return featureNames;
	}
	public void setFeatureNames(Set<String> featureNames) {
		this.featureNames = featureNames;
	}
	
	public static synchronized ApplicationDetails getInstance() {
		if (appDetails == null)
			appDetails = new ApplicationDetails();
		return appDetails;
	}

	public static String smileyDictionary = "D:/Uni-MS/NLPTeamLabWorkSpace/EmotionAnalysis/Data/emoticonsWithPolarity2.txt";
	
	public static String posTagDisctionary = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\Pos_Tag_List.txt";


	public static String stop_word_file = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\stopwords-list.txt";
	
	public static String sentiList = "D:/JavaJars/swn/www/admin/dump/SentiWordNet_3.0.0_20130122.txt";
	
	public static String CONJ_FOL = "but,however,nevertheless,otherwise,yet,still,nonetheless,therefore,furthermore,consequently,thus,eventually,hence";
	public static String CONJ_PREV = "till , until , despite , inspite , though , although";
	public static String CONDITIONALS = "if,might,could,can,would,may";
	public static String NEGATION = "not, neither, never, no, nor";
	public static String STOP_WORDS = "for, an, nor, but, or, yet, so ,the, a, an, another";

	
	

	public static boolean class2 = true ;
	public static boolean class3 = false;

	public static boolean train = true;
	public static boolean test = false;

	public static boolean stem = false;
	public static boolean TAG_FEATURE = false;
	public static boolean Senti_Feature = true;
	

	
	/**
	 * Following lines for preprocessed data and not for stemmed data.
	 */
	
	public static String unigramDictioanry_2   = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\Unigrams.csv";
	public static String bigramDictioanry_2   = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\Bigrams.csv";
	public static String unigramDictioanry_3   = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\Unigrams.csv";
	public static String bigramDictioanry_3   = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\Bigrams.csv";

	
	public static String TRAIN_DATA_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\preprocessed-data.csv";
	public static String TRAIN_DATA_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\preprocessed-data.csv";
	
	public static String TEST_DATA_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\preprocessed-data.csv";	
	public static String TEST_DATA_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\preprocessed-data.csv";
	
	public static String rawTweetFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\raw-data.csv";
	public static String rawTweetFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\raw-data.csv";
	
	
	public static String modelFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\model6";
	public static String modelFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\model6";
	
	
	public static String testOutDataFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\out-test-model06";
	public static String testOutDataFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\out-test-model6";
	
	
	public static String trainFeatureWriteFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\feature-train-model06";
	public static String trainFeatureWriteFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\feature-train-model6";
	
	public static String testFeatureWriteFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\Normal\\feature-test-model06";
	public static String testFeatureWriteFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\Normal\\feature-test-model6";
	
	

	/**
	 * Following lines for stemmed data setup .
	 */
	
	
//	public static String unigramDictioanry_2   = "D:\\Uni-MS\\Discourse\\my_paper\\Project\\2Class\\StemmedData\\Unigrams.csv";
//	public static String bigramDictioanry_2   = "D:\\Uni-MS\\Discourse\\my_paper\\Project\\2Class\\StemmedData\\Bigrams.csv";
//	public static String unigramDictioanry_3   = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\Unigrams.csv";
//	public static String bigramDictioanry_3   = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\Bigrams.csv";
//
//	
//	public static String TRAIN_DATA_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\StemmedData\\preprocessed-data.csv";
//	public static String TRAIN_DATA_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\preprocessed-data.csv";
//	
//	public static String TEST_DATA_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\StemmedData\\preprocessed-data.csv";	
//	public static String TEST_DATA_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\preprocessed-data.csv";
//	
//	public static String rawTweetFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\StemmedData\\raw-data.csv";
//	public static String rawTweetFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\raw-data.csv";
//	
//	
//	public static String modelFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\StemmedData\\model1";
//	public static String modelFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\model004";
//	
//	
//	public static String testOutDataFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\StemmedData\\out-test-model1";
//	public static String testOutDataFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\out-test-model004";
//	
//	
//	public static String trainFeatureWriteFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\StemmedData\\feature-train-model2";
//	public static String trainFeatureWriteFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\feature-train-model004";
//	
//	public static String testFeatureWriteFile_2 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\2Class\\StemmedData\\feature-test-model1";
//	public static String testFeatureWriteFile_3 = "D:\\Uni-MS\\Discourse\\my_paper\\Project_Data\\3Class\\StemmedData\\feature-test-model004";

	
}

