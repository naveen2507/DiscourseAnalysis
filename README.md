
-------------------------------------------------------------------------------------------------------
Discourse Analysis Project
-------------------------------------------------------------------------------------------------------

The documentation explains the project work, which is focussed on 'Sentiment Analysis in
Twitter with Lightweight Discourse Analysis' by Subhabrata Mukherjee and Pushpak Bhattacharyya.

Dependencies: (Has to be in classpath)
1.apache-commons-lang.jar
2.libsvm.jar
3.standford-postagger.jar
4.weka.jar

Process to run the code :
1. All the path and configuration settings(if its training procee or testing) is in ApplicationDetails.java. So for path setups, modification 
   in ApplicationDetails.java is required. All the paths ,throughout the project are read from this class.
2. As explained in the project report , this project does preprocessing of data first. The stemming part is also done for one of the  	   	experimentation setup. Refer to class  com.discourse.data.Preprocessing.java
3. Now set up the configuration in ApplicationDetails.java. Extract Ngrams using class com.discourse.data.BOWExtraction.java
4. We have now Ngram dictionary form the above step. Now feature extraction process starts, for which you have to change the configuration in ApplicationDetails.java class for file name path and if you want to include setiwordnet feature,tagging feature or in case you are doing for stemmed data. We also have to specify if the process is for 2-class or 3-class. If the process is for Stemmed data , den you have to comment some line from this class and uncomment some. 
5. Feature vectors are witten in lib svm format.
6. Get cross validation accuracy measure using com.discourse.feature.SVM_Train.java. To save the model remove -v 10 argument from main function.
7. After saving the model, for testing phase refer to com.discourse.feature.svm_predict.java.
8.To get precision and recall measures , refer to com.discourse.feature.Precision.java and com.discourse.feature.Recall.java.

For any other doubts mail me at : nksaxena2507@gmail.com

