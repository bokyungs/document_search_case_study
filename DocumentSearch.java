package document.search;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class DocumentSearch {
	// contains the terms found in the document
	// Given a term, returns how many times the term occurs in the document
	String fileName;

    
    static HashMap<String, HashMap<String, Integer>> indexedDocs = new HashMap<>();

	
	DocumentSearch(String filename){
		this.fileName = filename;
	}
	
	public void indexDocs(String fileList) throws IOException  {
		// build a hashmap for faster searching
		// the hashmap consists of file-name as a key and term-frequency pair hashmap as a value
		// the value for each filename hash key is a hashmap of unique terms in the file/document 
		// and their frequency
		
		BufferedReader in = null;
		File file = new File(fileList);
		Scanner scanner = null;
		HashMap<String, Integer> termFreqs = null;
		
		try {
		    Reader ir = new InputStreamReader(new FileInputStream(file));
		
		    in = new BufferedReader(ir);
		    String fileName;
		              
            while ((fileName = in.readLine()) != null) {
		
            	scanner = new Scanner(new File(fileName)).useDelimiter("\\W");
            	termFreqs = new HashMap<>();
		
            	while (scanner.hasNext()) {
            		String word = scanner.next().toLowerCase();
            		if (termFreqs.containsKey(word))
            			termFreqs.put(word, termFreqs.get(word)+1);
            		else
            			termFreqs.put(word,1);
            	}
            	
            	indexedDocs.put(fileName, termFreqs);
            }
		}
		catch (Exception e) {
			System.out.println("Exception while building a document index: " + e);
		}
		finally {
			if (scanner != null)
				scanner.close();
			if (in != null)
				in.close();
		}
		
	}
	
	public int termFreq(String searchTerm) {
		// process the input file and return how many times the term occurs in the file
		
		int count = 0;
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new File(fileName)).useDelimiter("\\W");
			
			while (scanner.hasNext()) {
				String word = scanner.next();
				//System.out.println("In termFreq1, Word parsed: " + word);
				//String cleanedWord = word.replaceAll("\\W", "");
				
				//if (cleanedWord.equalsIgnoreCase(searchTerm)) {
				//	System.out.println("In termFreq1, Word: " + word + " found");
				//	count++;
				//}
				if (word.equalsIgnoreCase(searchTerm)) {
					//System.out.println("In termFreq1, Word: " + word + " found");
					count++;
				}
			}	
				
		}
		catch (Exception e) {
			System.out.println("Exception while reading file: " + e);
		}
		finally {
			if (scanner != null)
				scanner.close();
		}
		//System.out.println("Count: " + Integer.toString(count));
		return count;
	}
	
	public int termFreq(String searchTerm, boolean regEx) {
		// process the input file, look for the regEx,and return how many times 
		// the reg expression is found in the file. 
		
		int count=0;
		Scanner scanner = null;
		
		if (!regEx) 
			return count;

		try {
			scanner = new Scanner(new File(fileName)).useDelimiter("\\W");
			Pattern pattern = Pattern.compile(searchTerm);
			//System.out.println("Pattern to search " + searchTerm);
			
			while (scanner.hasNext()) {
				String word = scanner.next().toLowerCase();
				//System.out.println("word in file: " + word);
				Matcher matcher = pattern.matcher(word);
				if (matcher.find())
					count++;
			}
		}
		catch (Exception e) {
			System.out.println("Exception while reading file: " + e);
		}
		finally {
			if (scanner != null)
				scanner.close();
		}
		return count;
	}

	public int termFreq(String filename, String searchTerm) {
	// Find a hashmap entry for the given file name, look for the search term in the hashmap of 
	// term-freq pairs, and returns the frequency of the search term.  If the search term is not found,
	// return 0 
	
	    int freq = 0;
	    
	    HashMap<String, Integer> termFreq = new HashMap<>();
	    termFreq = indexedDocs.get(filename);
	    
	    if (termFreq.containsKey(searchTerm)) {
	    	freq = termFreq.get(searchTerm);
	    	//System.out.println("In indexed search, " + "file: " + filename + " term: " + searchTerm + " freq: " + freq);
	    }
	    
	    return freq;
	}
}