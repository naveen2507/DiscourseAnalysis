package com.discourse.helper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class SparseFormatConvertor {

	/**
	 * 
	 * This function is to convert feature-extraction file to SVM format.
	 * Note : the input file is in format :<Classlabel,tweetID,feature-Vectors>
	 * @param args
	 * @throws IOException 
	 * 
	 * FeatureExtract File: E:\\InGTest4LackTest\\ING_NINELACK_LemmatizedSVMFormatFeatureExtract.csv
	 * outFile : E:\\InGTest4LackTest\\ING_NINELACK_SVMFormat
	 * E:\\InGTest4LackTest\\ING_NINELACK_LemmatizedSVMFormatFeatureExtract.csv E:\\InGTest4LackTest\\ING_NINELACK_SVMFormat
	 */
	
	
	/*private static final Logger LOG = Logger.getLogger(SparseFormatConvertor.class
			.getName());
	*/
	private String featureInputFile;
	
	private String sparseFormatFile;
	
	
	
	 public String getFeatureInputFile() {
		return featureInputFile;
	}

	public void setFeatureInputFile(final String featureInputFile) {
		this.featureInputFile = featureInputFile;
	}

	public String getSparseFormatFile() {
		return sparseFormatFile;
	}

	public void setSparseFormatFile(final String sparseFormatFile) {
		this.sparseFormatFile = sparseFormatFile;
	}

	public static void getSVMFormat(String inputFile,String outputFile) {
		 
		
		 	String csvFile = inputFile;
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = "\t";
			File f = new File(outputFile);
			if(f.exists()) {
			
				f.delete();
				//LOG.info("Previous output SVM Format file deleted successfully");
			}
			
		
		
			try {
				FileWriter writer = new FileWriter(outputFile);
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					
				     
					String[] features = line.split(cvsSplitBy);
		 
					StringBuffer out = new StringBuffer("");
					out.append(features[0]);
					out.append(" ");
					out.append(features[1]);
					
					for(int i=2;i<features.length;i++)
					{
						
						if(!(features[i].equals("0")))
								{
									out.append(" ");
									String form = ""+(i-1)+":"+features[i];
									out.append(form);	
								}
						
					}
					
					 writer.append(out);
					 writer.append('\n');
				}
				  writer.flush();
				  writer.close();
		 
			} catch (FileNotFoundException e) {
				//LOG.error("In SVM Format"+e);
			} catch (IOException e) {
				//LOG.error("In SVM Format"+e);
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						//LOG.error("In SVM Format"+e);
					}
				}
			}
		 
			//LOG.info("SVMFormat Converted");
		  }
	 
	
	public void getSVMFormat() throws Exception {
		 
		
		//LOG.info("-------- In SparseFormatConvertor(training module) getSVMFormat() method ----- ");
		
		final String csvFile = featureInputFile;
		BufferedReader br = null;
		
		final String cvsSplitBy = ",";
		File f = new File(sparseFormatFile);
		if(f.exists()) {
		
			f.delete();
			//LOG.info("Previous output SVM Format file deleted successfully");
		}
		
	
	
		try {
			String line = "";
			FileWriter writer = new FileWriter(sparseFormatFile);
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				
			     
				String[] features = line.split(cvsSplitBy);
	 
				StringBuilder out = new StringBuilder("");
				out.append(features[0]);
				out.append(" ");
				out.append(features[1]);
				
				for(int i=2;i<features.length;i++)
				{
					
					if(!(features[i].equals("0")))
							{
								out.append(" ");
								String form = ""+(i-1)+":"+features[i];
								out.append(form);	
							}
					
				}
				
				 writer.append(out);
				 writer.append('\n');
			}
			  writer.flush();
			  writer.close();
	 
		} catch (Exception e) {
			//LOG.error("In SVM Format"+e);
			throw new Exception(e);
		}  finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					//LOG.error("In SVM Format"+e);
				}
			}
		}
	 
		//LOG.info("SVMFormat Converted");
	  }
 
	
	
	
	
	public static void main(String[] args) throws IOException {
		 
			
		 //getSVMFormat(args[0],args[1]);
		 //getSVMFormat("E:\\ING-SubClassification\\ml-classification-subcategories\\specific-params\\Prediction\\MLAnalysis-Subcategorization-features.csv","E:\\ING-SubClassification\\ml-classification-subcategories\\specific-params\\Prediction\\MLAnalysis-Subcategorization-svmformat");
		 getSVMFormat("E:\\ISI-DATA-SET\\UnigramExtraction\\FirstDataset-ISI-features-slangs.csv","E:\\ISI-DATA-SET\\UnigramExtraction\\FirstDataset-ISI-features-slangs-svmFormat");
		 
		  }
		

}
