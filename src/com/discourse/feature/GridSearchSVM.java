package com.discourse.feature;

import java.io.BufferedReader;
import java.io.FileReader;

import weka.classifiers.functions.SMO;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.Utils;

public class GridSearchSVM {

		   public static void main(String[] args) throws Exception {
		      // load data
		      BufferedReader reader = new BufferedReader(new FileReader("D:\\Uni-MS\\Discourse\\my_paper\\Project\\2Class\\Normal\\feature-train-model6.arff"));
		      Instances data = new Instances(reader);
		      reader.close();
		      data.setClassIndex(data.numAttributes() - 1);

		      // setup classifier
		      
		      CVParameterSelection ps = new CVParameterSelection();
		      ps.setClassifier(new SMO());
		      ps.setNumFolds(5);  // using 5-fold CV
		      ps.addCVParameter("C 0.1 0.5 5");

		      // build and output best options
		      ps.buildClassifier(data);
		      System.out.println(Utils.joinOptions(ps.getBestClassifierOptions()));
		   }
		}