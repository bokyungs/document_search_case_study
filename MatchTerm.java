package document.search;

import java.io.*;
import java.util.*;


public class MatchTerm {

	static HashMap<String, Integer> termFreqs = new HashMap<>();
	
	
	public static void processFile(String fileList, String searchTerm, int searchMethod) throws IOException {
		// given a file of input file names, read in each file name,
		// and gather the frequency of the search term in each file
		// search Method determines what type of match is done: 
		// 1 - simple string matching
		// 2 - regular expression
		// 3 - index search
		
		BufferedReader in = null;
		File file = new File(fileList);
			
		try {
		    Reader ir = new InputStreamReader(new FileInputStream(file));
		
		    in = new BufferedReader(ir);
		    String fileName;
		    int freq = 0;
            
            while ((fileName = in.readLine()) != null) {
                //System.out.println("***" + fileName);
                                
                DocumentSearch aDocument = new DocumentSearch(fileName);
                
                // determine which termFreq method to call
                switch (searchMethod) {
                    case 1:
                	    freq = aDocument.termFreq(searchTerm);
                	    break;
                    case 2:
                    	freq = aDocument.termFreq(searchTerm, true);
                    	break;
                    case 3:
                    	freq = aDocument.termFreq(fileName, searchTerm);
                    	break;
                }
                
                termFreqs.put(fileName, freq);
                
            }
            
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (in != null)
				in.close();
		}
				
	}
	
	public static void printSearchResults() {
		//print the search results
		
		//sort the results by the term frequency in a descending order
	    List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(termFreqs.entrySet());

	    Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {

	            public int compare(Map.Entry<String, Integer> m1, Map.Entry<String, Integer> m2) {
	                return (m2.getValue()).compareTo(m1.getValue());
	            }
	    });

	    System.out.println("Search results:\n");
	    for (Map.Entry<String, Integer> entry : list) {
	        System.out.println("\t" + entry.getKey() + " - " + entry.getValue() + " matches");
	    }
	        
	}
	
	
	public static void main(String[] args) throws Exception {

		if (args.length != 1) {
			System.out.println("Usage: MatchTerm file-containing-list-of-file-names");
			return;
		}

		String fileList = args[0];
		File file = new File(fileList);
		
		// check to see if the file exists and is readable
		if (!file.exists() || !file.canRead()) {
			System.out.println("Can't read " + file);
			return;
		}
		
		System.out.println("Please enter a term to search: ");
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		String searchTerm = reader.next();
		//System.out.println("Term to search: " + searchTerm);
		
		int searchMethod;
		do {
		    System.out.println("Enter search method (1, 2, or 3): 1)String Match; 2)Regular Expression; 3)Indexed");
		    searchMethod = Integer.parseInt(reader.next());
		    if (searchMethod != 1 && searchMethod != 2 && searchMethod != 3)
			    System.out.println("Invalid search method entered");
			
		} while (searchMethod != 1 && searchMethod != 2 && searchMethod !=3);
		reader.close();
		
		// for indexed search, build the indexes first
		if (searchMethod == 3) {
			DocumentSearch doc = new DocumentSearch(fileList);
			doc.indexDocs(fileList);
		}
			
		long startTime = System.currentTimeMillis();
		processFile(fileList, searchTerm, searchMethod);
		String elapsedTime = Long.toString(System.currentTimeMillis() - startTime);
		
		printSearchResults();
		System.out.println("\nElapsed time: " + elapsedTime + " ms");
		
	}
}
