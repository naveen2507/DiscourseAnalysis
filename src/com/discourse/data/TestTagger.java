package com.discourse.data;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class TestTagger {

	public static void main(String arg[]){
		MaxentTagger tagger = new MaxentTagger(
				 
				"taggers/english-left3words-distsim.tagger");
		
		
		// The sample string
		 
		String sample = "taking my daughte neither to the cinema for the first time     'film' is tinkerbell rather than citizen kane/shawshank redemption but it's a start!";
		 
		// The tagged string
		 System.out.println("::::");
		String tagged = tagger.tagString(sample);
		System.out.println(":::"+tagger.getTagIndex("NNP"));
		 
		// Output the result
		 
		System.out.println(tagged);
	}
	
}
