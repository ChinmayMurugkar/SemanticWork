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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.math.*;

public class DocIndex2 {
	
	int[] anarr;
	
	public static Map<Integer, Double> sortMap(final Map<Integer, Double> map) {
	    Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(new Comparator<Integer>() {
	        public int compare(Integer o1, Integer o2) {
	            return map.get(o1).compareTo(map.get(o2));
	        }
	    });
	    sortedMap.putAll(map);
	    return sortedMap;
	}
	
	int tfreq=0;
public  void indexer(String user_query,String orig_query) throws IOException, ParseException {
	
	int ScoreFlag1=0;
	int ScoreFlag2=0;
	int ScoreFlag3=0;
	int ScoreFlag4=0;
	HashMap hm = new HashMap();
	// 0. Specify the analyzer for tokenizing text.
    //    The same analyzer should be used for indexing and searching
	//String k = str;
    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_34);

    // 1. create the index
    Directory index = new RAMDirectory();

    IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_34, analyzer);
    
    IndexWriter w = new IndexWriter(index, config);
	
	
	try{
	  	  String[] arr,arrt;
	  	  arr = new String[100000]; 
	  	  arrt = new String[100000];
	  	  
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
    
    IndexSearcher searcher = new IndexSearcher(index, true);
    IndexReader reader = IndexReader.open(index);
    int num = reader.numDocs();
    System.out.println(num);
    CountWord wc = new CountWord();
    CheckKey ck = new CheckKey();
    testing cd = new testing();
    StopFilter sf = new StopFilter();
    Boolean b;
   
    WordNetDemo wd = new WordNetDemo();
    double semDist;
   // b = sf.valid("bottle");
   // System.out.println("This is Boolean Answer : "+b);
    String[] user_q = user_query.split(" ");
    String[] freq_words;
    String[] orig_q = orig_query.split(" ");
    
    ArrayList al = new ArrayList();
    ArrayList al2 = new ArrayList();
    HashMap<String, Integer> mp = new HashMap<String, Integer>();
    System.out.println("Initial size of al: " + al.size());
	HelloLucene3 hl3 = new HelloLucene3();
	/*String[] orig_q = orig_query.split(" ");
	System.out.println("Here is the orig_q : "+orig_query);
	for(int r=0;r<orig_q.length; r++){
		al2.add(orig_q[r]);
		System.out.println("The orignal string "+orig_q[r]); 
	}*/

	//   *********COUNTING WORDS IN THE QUERY***************
	System.out.println("user query is "+ user_query);
	float median =0; int ct=0;
	for(int k=0;k<user_q.length;k++){
		int temp = hl3.tfidf(user_q[k]);
		median = median + temp;
		System.out.println("The temp is :"+temp);
		ct++;
		}
	System.out.println("ct is :" + ct);
median = median / ct;
		
	for(int h=0;h<user_q.length;h++){
		int count = hl3.tfidf(user_q[h]);
		if(count > median){
				if( !sf.stopwords.contains(user_q[h]))		 // filtering out stopwords
			al.add(user_q[h]);
		}
		
	
	
	/*	if(count > 0 && count < 20){
			al2.add(user_q[h]);
		} */
	}
	System.out.println("Contents of al: " + al);
	//System.out.println("Contents of al2: " + al2);
	int tempCount;
	
	for (int n=0;n<orig_q.length;n++){
		int flg =0;
		//if(orig_q[n] =="data") System.out.println("data word is here");
		for (Iterator<String> iterer = al.iterator(); iterer.hasNext();  ) {
			String temp = iterer.next() ;
			//if(temp=="data")System.out.println("temp data word is here");
			if(orig_q[n].equals(temp) ){
				flg =1;
			}
		}
			if(flg==0){
				if( !sf.stopwords.contains(orig_q[n])){   // filtering out stopwords
				al2.add(orig_q[n]);
				tempCount = hl3.tfidf(orig_q[n]);
				mp.put(orig_q[n],tempCount);
				}
			}
	}

	System.out.println("Contents of al2: " + al2);
	if(al2.isEmpty()){
		for(int h=0;h<orig_q.length;h++){
			int count = hl3.tfidf(orig_q[h]);
			System.out.println("Count is :" +count +"median is"+median);
			if(count > median ){
				if( !sf.stopwords.contains(orig_q[h])){	 // filtering out stopwords
				al2.add(orig_q[h]);
				mp.put(orig_q[h],count);
				}
			}

		}
	}
	System.out.println("Contents of al2: " + al2);
	//	System.out.println(h+" = "+user_q[h]);


int flag=0;
    for ( int i = 1; i < num; i++)
    {
        if ( !  reader.isDeleted( i))
        {
        	Document d	 = searcher.doc(i);
        	flag =0;
        	//System.out.println(d.get("title"));
        	
   //    if(i==98 || i==1 || i==82  ){
        	 String newst=" ";
        	 
        	 double finalN=0;
        	 String newstr[];
        	String ls = d.get("title").toLowerCase();
        	System.out.println("index is :"+ i);
        	String[] tokens = ls.split("###");
            String lstoken = tokens[0];
            String m = tokens[1];
        	 if(lstoken.equals("adaptive query processing ")){
        	  System.out.println("Got it");
	  
        	 }
        	
        	//---------------------------Iterating through array list for checking inverse frequency---------------------------
        	Double avgC=0D;
        	int r=0;
        		for (Iterator<String> iterer = al2.iterator(); iterer.hasNext();  ) {
        			String temp = iterer.next() ;
        			if(mp.get(temp) != 0){
        			r++;
        			System.out.println(mp.get(temp) +" : " +temp); 
        			FrequecyCalculator fc = new FrequecyCalculator();
        			int tempFreq = fc.frequencyCalculator(ls,temp);
        			System.out.println("Temp is :"+temp+" and its frequency is:"+ tempFreq);
        			if (tempFreq!=0){
        		  avgC = avgC + (tempFreq/num);
        			//	avgC = avgC + (tempFreq/ls.length());
        			}//total number of document by frequency
        			}
        		}
        		//System.out.println("avgC is : "+ avgC);
        		if(r!=0 && avgC != 0){
        			avgC = avgC/r; 
        		}
        	System.out.println("Final avgC is : "+ avgC);

        	
        	//String s = cd.cleanDoc(ls);
        	 String[] st = ls.split(" ");
        		for(int l=0;l<st.length;l++){
           		 b = sf.valid(st[l]);
           		    if(b==true){
           		    	newst = newst +" "+ st[l];
           		    }
        		}
        		newstr = newst.split(" ");
        		String tenmax[];
        		tenmax = wc.frequency(newstr);
        		flag = ck.frequency(newstr,al2);
        		if(flag !=1){
        		int itemCount = al2.size();
        		   System.out.println("The itemsize = "+itemCount+" The flag = "+ flag);
        			if(flag == itemCount){
        				flag = 3;
        				FrequecyCalculator fc1 = new FrequecyCalculator();
        				
        				for (Iterator<String> iterer = al2.iterator(); iterer.hasNext();  ) {
        					System.out.println(" The word is : "+iterer.next());
        					 ScoreFlag3 = ScoreFlag3 + fc1.frequencyCalculator(newst,iterer.next());
            				}
        			}
        			else
        				flag = 1;
        		}
        		else
        			flag =1;
        		
       
        		//----------------------------Check if any orignal query word present in Document----------------------
        		
      /*  		ArrayList orig_qa = new ArrayList();
        		for(int p=0;p < orig_q.length;p++){
        			orig_qa.add(orig_q[p]);
        		}
        		flag = ck.frequency(newstr,orig_qa);
        		if(flag !=1){
        		int itemCount = orig_qa.size();
     		   System.out.println("The itemsize = "+itemCount+" The flag = "+ flag);
     			if(flag == itemCount){
     				flag = 3;
     			}
     			else
     				flag = 1;
     		}
     		else
     			flag =1; */
        		
        		
        		System.out.println("flag is : "+flag);
        		for (int j=0;j<user_q.length;j++){
        			String qw = user_q[j];
        			double N=0;
        			for(int y=0;y<tenmax.length;y++){
        				String dw = tenmax[y];
        				semDist = wd.SemanticDistance(qw, dw);
        			    System.out.println("semDist is : "+semDist);
      /* 		for (Iterator<String> iter = al.iterator(); iter.hasNext();  ) {
        				//    System.out.println("############## here is iter :"+iter.next());
        				    if(iter.next()==qw){
        				    	semDist = semDist * 2;
        				    	System.out.println("Its here at "+qw);
        				    }
        				}
        				
        				if(qw == dw){
        					semDist = semDist * 2;
        				}  
       		if(qw == dw){
 				    	semDist =  semDist/2;
 				    	System.out.println("######################################################"); 
 				    	} */
        			    
        		/*	    FrequecyCalculator fcc = new FrequecyCalculator();
        			    int tt=0;
        			    int ttfrq;
        			    for(int k=0;k<tenmax.length;k++){
        			    	ttfrq = fcc.frequencyCalculator(ls,tenmax[k]);
        			    	tt = tt + ttfrq;
            				}
        			    tt = tt/(tenmax.length-1);   
        			    
        			    FrequecyCalculator fcc = new FrequecyCalculator();
        			    int qwFreq;
        				if(semDist==1.0){
        					  qwFreq = fcc.frequencyCalculator(ls,qw) ;
        					  if(qwFreq!=0 && qwFreq >avgC){
        						  if(flag!=3)
        						  flag = 2;
        						  }
        				}   */
        				
        				int t=0;
        			for (Iterator<String> itere = al2.iterator(); itere.hasNext();  ) {
        				//System.out.println("############## here is iter :"+itere.next());
        				String temp = itere.next() ;
         				    if( temp.equals(dw)){
         				    	t++;
         				    	System.out.println("Atlast they are equal !!!!!");
         				    	tfreq = tfreq + mp.get(dw);
         				    	if(flag!=3){
         				    	flag =2;	
         				    	}
         				    }
         				}
        				if(t!=0){
        				tfreq = tfreq/t; 
        				}
        				//System.out.println("Semantic Distance between "+qw+" and "+dw+"is "+semDist);
        				
        				N = N + semDist;
        			}
        			N = N/tenmax.length;
        		//	System.out.println("N is : "+N);
        			finalN = finalN + N;
        		}
        		finalN = finalN /user_q.length;
        		System.out.println("finalN before flag calc is :" + finalN);
        		System.out.println("At the last the flag is :"+flag);
        		if(flag==1){
        			if(avgC!=0){
        				if(Math.log(avgC)>=0){
        			finalN =Math.log(finalN);
        				}
        			}
        		}
        		if(flag == 3){
        			if(ScoreFlag3!=0)
            			finalN = finalN/ScoreFlag3;
        			System.out.println("Its inside flag 3 and finalN is :"+finalN);
        		}
        		if(flag==2){
        			if(tfreq!=0)
        			finalN = finalN/(tfreq+avgC);
        		}
        		System.out.println("The final Score is : "+ finalN);
        		hm.put(i, new Double(finalN));
        		
        //	}//for FIRST document
        	
        }
    }
    
    try {
		// Create file 
	    FileWriter fstreamw = new FileWriter("/Users/chinmay/Desktop/Results1.txt");
	        BufferedWriter out = new BufferedWriter(fstreamw);
    Map<Integer, Double> sortedMap = sortMap(hm);
    int g =0;
    for (Map.Entry<Integer, Double> entry : sortedMap.entrySet()) {
    	Document finalD = searcher.doc(entry.getKey());
       System.out.println(entry.getKey() + ", " + entry.getValue());       
      String finalDoc = finalD.get("title");
      g++;
    if(g<1) continue;
      String[] tokens = finalDoc.split("###");
        String l = tokens[0];
        String m = tokens[1];
        out.write("\n"+g+". "+l+" "+entry.getKey()+" ,"+ entry.getValue()+"\n");
	       out.write("\n"+m+"\n");
	     //  System.out.println(g+". "+l);
	     //  System.out.println(m); 
     //  out.write("\n \n"+finalDoc);
	       out.flush(); 
	      // if(g==84) break;
      System.out.println(g);
    }
    out.close();
    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   // String[] st = {"bob", "tom","jim", "tom", "tom", "tim"};
//	wc.frequency(st);
    reader.close();	
  }//end of function Stringtfidf
 
}//End of class
