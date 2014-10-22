package net.semwebprogramming.chapter2.HelloSemanticWeb;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.index.IndexReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class HelloLucene3 {
	int[] anarr;
   
public Integer tfidf(String str) throws IOException, ParseException {
	
	Integer dc =9 ;
	// 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
	String k = str;
    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);

    // 1. create the index
    Directory index = new RAMDirectory();

    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_34, analyzer);
    
    IndexWriter w = new IndexWriter(index, config);
	
	
	try{
	  	  char[] arr,arrt;
	  	  arr = new char[100000]; 
	  	  arrt = new char[100000];
	  	  
	  // Open the file that is the first 
	  // command line parameter
	  FileInputStream fstream = new FileInputStream("/Users/chinmay/Desktop/lucene.txt");
	  // Get the object of DataInputStream
	  DataInputStream in = new DataInputStream(fstream);
	      BufferedReader br = new BufferedReader(new InputStreamReader(in));
	  String strLine;
	  
	  //Read File Line By Line
	  while ((strLine = br.readLine()) != null)   {
		  //System.out.println (strLine);
		   Document doc = new Document();
		    doc.add(new Field("title",strLine, Field.Store.YES, Field.Index.ANALYZED));
		    w.addDocument(doc);
	  }
		}catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage()); }
	
    w.close();
    
    
    // 2. query
    String querystr = k;

    // the "title" arg specifies the default field to use
    // when no field is explicitly specified in the query.
    Query q = new QueryParser(Version.LUCENE_34, "title", analyzer).parse(querystr);

    // 3. search
    int hitsPerPage = 100;
    
    IndexSearcher searcher = new IndexSearcher(index, true);
    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
    searcher.search(q, collector);
    ScoreDoc[] hits = collector.topDocs().scoreDocs;
   String op = "<html><title>Hits</titile><body><h1>Searched papers</h1><br><h3>For testing purpose ony abstracts are present</h3>";
    // 4. display results
    System.out.println("Found " + hits.length + " hits.");
 /*   if(hits.length != 0){
    try {
		// Create file 
	    FileWriter fstreamw = new FileWriter("/Users/chinmay/Desktop/lucene.html");
	        BufferedWriter out = new BufferedWriter(fstreamw);
	        System.out.println("*********************This is from Lucene 2************");
	        anarr = new int[hits.length];

      Document d	 = searcher.doc(dc);
  //    System.out.println((i + 1) + ". Abstract : \n" + d.get("title"));
      op = "Abstract : \n" + d.get("title")+"\n";
      //System.out.println(op);
      out.write(op);
	    out.flush();
	    
    out.write("</body></html>");
    out.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    System.out.println("*********************Till here************");
    
    // searcher can only be closed when there
    // is no need to access the documents any more. 
    searcher.close();
    return hits.length;
    }
    else
    {
    	return null;
    	
    } */
    return hits.length;
    
  }//end of function Stringtfidf
 
}//End of class
