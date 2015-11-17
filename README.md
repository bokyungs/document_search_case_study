This *README.md* contains information about three files used in implementing **Case Study 3 - Document Search**.    

**MatchTerm.java** - a driver that handles prompting and reading in the user input of the search term and the search method.    
It's also responsbile for printing out the search results in a ranked order of relevance where relevance is the determined by the number of times the term occurs in a document.    
It also prints out the elapsed time of running the search against all three input files in milliseconds.    
For indexed search, it calls the document method that creates the index first.      

**DocumentSearch.java** - A class that is responsible for creating an index for faster searching and performing three different searches:  
  - Simple string matching  
  - Text search using regular expressions  
  - Preprocess the document content and search the index    

**inputfile.txt** - a file that contains the list of input files used for searching.  It currently has names of three files:  
  - *french_armed_forces.txt*  
  - *hitchhikers.txt*  
  - *warp_drive.txt*  

In order to run the program in Eclipse, make sure that *inputfile.txt* is placed at the project level. 

The Java programs and its input file can be found in [document_search_case_study](https://github.com/bokyungs/document_search_case_study).   

