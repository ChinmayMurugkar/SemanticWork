package net.semwebprogramming.chapter2.HelloSemanticWeb;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.lucene.queryParser.ParseException;
import org.mindswap.pellet.jena.PelletReasonerFactory;

//import pdmiofirst.testing;

import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.ontology.impl.OntResourceImpl;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;

import rita.wordnet.RiWordnet;

public class HelloSemanticWeb {
	
	static String defaultNameSpace1 = "http://www.semanticweb.org/ontologies/2011/3/Ontology1303310384456.owl#";
	static String defaultNameSpace2 = "http://www.semanticweb.org/ontologies/2011/3/medicalpaper.owl#";
	
	static int  arrlen;
	static String st;
	Model _friends = null;
	Model schema = null;
	InfModel inferredFriends = null;
	
	int rank;
	static String ht ;
	static String d="";
//Declaring the map
	
	public static Map<Integer, Double> sortMap(final Map<Integer, Double> map) {
	    Map<Integer, Double> sortedMap = new TreeMap<Integer, Double>(new Comparator<Integer>() {
	        public int compare(Integer o1, Integer o2) {
	            return map.get(o2).compareTo(map.get(o1));
	        }
	    });
	    sortedMap.putAll(map);
	    return sortedMap;
	}
	
public static void main(String[] args) throws IOException,ParseException  {
	
	//calling RitaWordnet Library
	
	 RiWordnet wordnet = new RiWordnet(null);
	 
// Converting the query into vector
	
	testing cd = new testing();
	Scanner input=new Scanner(System.in);
    System.out.print("Enter string: ");
     String s1=input.nextLine();
     
     HelloLucene hl = new HelloLucene();
     long start = System.currentTimeMillis();
     hl.tfidf(s1);
     long end = System.currentTimeMillis();
     long FinalTime = end-start;
     System.out.println("Time for TFIDF is : "+ FinalTime);
//String s1 = "what is the of significance of statue of liberty" ;
     
     //converting string to lower case
	s1 = s1.toLowerCase();
	String orig_s1 = s1;
	s1 = " "+s1;
	System.out.println(s1);
	
	
	// removing stop words from the query
	st = cd.cleanDoc(s1 );
	
	//Finding the match through tfidf
	//LuceneOp lp = new LuceneOp();
	//lp.tfidf(st);
	
	// FINDING SynSets 
	
	String arr[]=st.split(" ");
	System.out.println("*********************From split************");
	System.out.println("Number of words are: "+arr.length);
	String pos;
		for(int i=0;i<arr.length;i++)
		{
			System.out.println("array"+i+"  :"+arr[i]);
			 pos = wordnet.getBestPos(arr[i]);
			 String[] ss;
			 if(pos != null){
            	 ss = wordnet.getAllSynsets(arr[i],pos);
            	 if(ss != null){
                 int l = ss.length;
                 d = d +" "+arr[i];
            	for(int y=0;y<l;y++){
            		System.out.println("Synset :" +ss[y]);
            		d = d +" "+ ss[y];
            		}
            	 }
            	 else{d =d +" "+ arr[i];}
             }
			 else{d =d +" "+ arr[i];}
		}
		System.out.println("*********************Till here************");
		st = d;
		st = st.trim();
		
		//Got the string with word1 synonynyms word2 synonyms so on...
		System.out.println("Final String : "+st);
//	for(int i=0;i<arr.length;i++)
//	{
//	    System.out.println("array in hellosem"+i+"  :"+arr[i]);
//	}
//	arrlen = arr.length;
	

		HelloSemanticWeb hello = new HelloSemanticWeb();
		System.out.println("************PROGRAM STARTED*************");
	
		System.out.println("\nPopulating Patients medical Information Gaphical Strucutre");
		hello.populateMedicalinfo();
		
		// Say Hello to Authors
		System.out.println("\nThese are all Names of Patients");
		hello.mySelf(hello._friends);  
		
		System.out.println("\nThese are all Diseases :");
		hello.ccmySelf(hello._friends); 
			
		System.out.println("\nAdding Medical paper Graphical structure elements");
		hello.populatemedicalpaper(); 
		
		
		// Add the ontologies
		System.out.println("\nAdding the Ontologies");
		hello.populateFOAFSchema();
		hello.populatemedicalpaperSchema();
		
		
		//Align the ontologies to bind together
		System.out.println("\nWe have started to add Allignments to ontologies");
		hello.addAlignment();
		
		//System.out.println("\n\n******************\nThese are the common keys :");
		hello.cmySelf(hello._friends); 
		
		System.out.println("\n\n*******************\nHere are the URLs for each of the corresponding Diseases :\n");
		hello.cAuthors(hello._friends);
		
		//Run reasoner to  align the instances
		System.out.println("\nRunnig a Reasoner and binding");
		hello.bindReasoner();
		//String g = "</body></html>";
		//ht = new StringBuffer().append(g).toString();
	//	ht = ht +"</body></html>";
		
		//Create a file to write all documents in it.
		try {
			// Create file 
		    FileWriter fstreamw = new FileWriter("/Users/chinmay/Desktop/lucene2.txt");
		        BufferedWriter out = new BufferedWriter(fstreamw);
		    out.write(ht);
		    out.flush();
		    //Close the output stream
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		DocIndex docid = new DocIndex();
		//DocIndex2 docid1 = new DocIndex2();
		long startTime = System.nanoTime();
		docid.indexer(st,orig_s1);
		//docid1.indexer(st,orig_s1);
		long endTime = System.nanoTime();
		long duration = endTime-startTime;
		System.out.println("Total Time time required is : "+duration);

/* THIS WHOLE THING IS CLOSED FOR A WHILE		
		
		//Now call Tfidf lucene funtciton to see htis
		HelloLucene lucene = new HelloLucene();
		Map<Integer,Double>hm = lucene.tfidf(st);

		// Get a set of the entries 
		Set set = hm.entrySet(); 
		// Get an iterator 
		Iterator i = set.iterator(); 
		// Display elements 
	
		while(i.hasNext()) { 
		Map.Entry me = (Map.Entry)i.next(); 
		System.out.print(me.getKey() + ": "); 
		System.out.println(me.getValue()); 
		} 
		System.out.println(); 
		
//Part 1	
		
	
		System.out.println("^^^^^^^^^^^^^^^  Final Hashmap ^^^^^^^^^^^^^^^");
		
	//From here I try to get Hashmap sort them and then retreive docs related to that particular value and write in Final.txt
	
		/*
		// Get a set of the entries 
				Set st = hm.entrySet(); 
				// Get an iterator 
				Iterator j = st.iterator(); 
				// Display elements 
				while(j.hasNext()) { 
				Map.Entry me = (Map.Entry)j.next(); 
				System.out.print(me.getKey() + ": "); 
				System.out.println(me.getValue()); 
				} 
				System.out.println();  */

		
		
		/*
				HelloLucene3 lucene3 = new HelloLucene3();
				Map<Integer, Double> sortedMap = sortMap(hm);
				try {
					// Create file 
				    FileWriter fstreamw = new FileWriter("/Users/chinmay/Desktop/Final.txt");
				        BufferedWriter out = new BufferedWriter(fstreamw);
               String op;
               int g =0;
			    for (Map.Entry<Integer, Double> entry : sortedMap.entrySet()) {
			        System.out.println(entry.getKey() + ", " + entry.getValue());
			        
			       op =  lucene3.tfidf(s1,entry.getKey());
			       System.out.println(op);
			       String[] tokens = op.split("###");
			       String l = tokens[0];
			      
			       String m = tokens[1];
			       //System.out.println("here is m"+m);
			       g++;
			       out.write("\n"+g+". "+l+"\n");
			       out.write("\n"+m+"\n");
			       
				    out.flush();
			     }
			    out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
*/
		
}
	
	private void populateMedicalinfo(){
		_friends = ModelFactory.createOntologyModel();
		InputStream inFoafInstance = FileManager.get().open("Ontologies/Medicalinfo.owl");
		_friends.read(inFoafInstance,defaultNameSpace1);
		
	}
		
		
	private void mySelf(Model model){
		
		runQuery(" select  ?name where{ ?pub people:doc ?name}", model); 
		
		 
		// runQuery(" select  ?x ?name where{ ?x otherpeople:annotation \"fever\". ?x otherpeople:url ?name } ", model);
	}
	
	private void ccmySelf(Model model){
		 
	//	 runQuery(" select DISTINCT ?name where{ ?pub people:disease ?name}", model);  running query
		 System.out.println("\nThese are all the synonyms:\n");
		  
	//	 runQuery(" select DISTINCT ?name where{ ?pub people:synonyms ?name  }", model);  runnig query
		 //runQuery(" select  ?x ?name where{ ?x otherpeople:annotation \"fever\". ?x otherpeople:url ?name } ", model);
	}
	
	private void cmySelf(Model model){
	//  OntResource ontResource = new OntResourceImpl(null, null);
	  //ontResource.setSameAs();
		
		}
	
	private void Authors(Model model){
		
	}

	private void cAuthors(Model model){
	//	 runQuery(" select DISTINCT ?name where{ ?y people:disease ?name}", model);
		// runQuery(" select  ?name where{ ?pub people:synonyms ?name}", model);
	//	 runQuery(" select  ?name where{ ?pub otherpeople:abstr ?name}", model);
		// runQuery(" select  ?x ?name where{ ?x otherpeople:url \"http://www.eymj.org/DOIx.php?id=10.3349/ymj.2011.52.3.482\". ?x otherpeople:abstract ?name } ", model);
		// runQuery(" select  ?x ?name where{ ?x people:disease \"neoplasm melenoma\". ?x people:name ?name } ", model); //add the query string
		//System.out.println("yaha se chalu");
	}

	private void populatemedicalpaper() throws IOException {		
		InputStream inFoafInstance = FileManager.get().open("Ontologies/medicalpaper.owl");
		_friends.read(inFoafInstance,defaultNameSpace2);
		inFoafInstance.close();
	} 
	

	private void addAlignment(){
		Resource resource = schema.createResource(defaultNameSpace1 + "cancer");
		Property prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentClass");
		Resource obj = schema.createResource(defaultNameSpace2 + "Cancer");
		schema.add(resource,prop,obj);
		
		resource = schema.createResource(defaultNameSpace1 + "cancer");
	    prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentClass");
		 obj = schema.createResource(defaultNameSpace2 + "cancer");
		schema.add(resource,prop,obj);
		
		resource = schema.createResource(defaultNameSpace1 + "Neoplasm melenoma");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentClass");
		 obj = schema.createResource(defaultNameSpace2 + "Neoplasm melenoma");
		schema.add(resource,prop,obj);
		
		 resource = schema.createResource(defaultNameSpace1 + "neoplasm melenoma");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentClass");
		obj = schema.createResource(defaultNameSpace2 + "cancer");
		schema.add(resource,prop,obj);
		
		
		resource = schema.createResource(defaultNameSpace1 + "has_a");
		//prop = schema.createProperty("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
		prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		obj = schema.createResource(defaultNameSpace2 + "has_a");
		schema.add(resource,prop,obj);
		
		
		resource = schema.createResource(defaultNameSpace1 + "brain tumor");
		//prop = schema.createProperty("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
		prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		obj = schema.createResource(defaultNameSpace2 + "brain tumor");
		schema.add(resource,prop,obj);
		
		resource = schema.createResource(defaultNameSpace1 + "Brain tumor");
		//prop = schema.createProperty("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
		prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		obj = schema.createResource(defaultNameSpace2 + "Brain tumor");
		schema.add(resource,prop,obj);
		
		
		resource = schema.createResource(defaultNameSpace1 + "Headache");
		//prop = schema.createProperty("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
		prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		obj = schema.createResource(defaultNameSpace2 + "Headache");
		schema.add(resource,prop,obj);
		
		resource = schema.createResource(defaultNameSpace1 + "headache");
		//prop = schema.createProperty("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
		prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		obj = schema.createResource(defaultNameSpace2 + "headache");
		schema.add(resource,prop,obj);
		
		
	
		resource = schema.createResource(defaultNameSpace1 + "title");
		//prop = schema.createProperty("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
		prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		obj = schema.createResource(defaultNameSpace2 + "title");
		schema.add(resource,prop,obj);
		
		
		resource = schema.createResource(defaultNameSpace1 + "publication");
		//prop = schema.createProperty("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
		prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		obj = schema.createResource(defaultNameSpace2 + "publication");
		schema.add(resource,prop,obj);
		
		resource = schema.createResource(defaultNameSpace1 + "publication3");
		prop = schema.createProperty("http://www.w3.org/2002/07/owl#sameAs");
		obj = schema.createResource(defaultNameSpace2 + "publication3");
		schema.add(resource,prop,obj); 
	}
	
	
	private void populateFOAFSchema() throws IOException{
		InputStream inFoaf = FileManager.get().open("Ontologies/Medicalinfo_schema.owl");
		InputStream inFoaf2 = FileManager.get().open("Ontologies/Medicalinfo_schema.owl");
		schema = ModelFactory.createOntologyModel();
	
		
		schema.read(inFoaf, defaultNameSpace1);
		_friends.read(inFoaf2, defaultNameSpace1);	
		inFoaf.close();
		inFoaf2.close();
		}
		
	private void populatemedicalpaperSchema() throws IOException {
		InputStream inFoafInstance = FileManager.get().open("Ontologies/medicalpaper_schema.owl");
		_friends.read(inFoafInstance,defaultNameSpace2);
		inFoafInstance.close();
	}
	
	private void bindReasoner(){
	    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	    reasoner = reasoner.bindSchema(schema);
	    inferredFriends = ModelFactory.createInfModel(reasoner, _friends);
	}
	
	
	//Here each disease is searched for it synonyms
	
	
	private void runQuery(String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
				String ss;
				// System.out.println( name.toString() );
				ss = name.toString();
				ss = ss.replaceAll("\n", " ");
			//	System.out.println("here is ss"+ss);
				ht= ht+"\n"+ss;
			//	System.out.println("this is ss :"+ss );
				rank =0;
			//	runQuerymain11(ss," select ?name where{ ?pub otherpeople:title ?name}", model);
				//System.out.println(rank);
				rank =0;
			//	runQuerymain1(ss," select  ?name where{ ?pub otherpeople:abstr ?name}", model);
				rank =0;
		//		runQuery4(ss," select  ?x ?name where{ ?x people:disease \""+ss+"\". ?x people:synonyms ?name } ", model); //add the query string
		//	System.out.println("this is coming from ss"+ss);
			}
			else
				System.out.println("No Friends found!");
			}
		
		} finally { qexec.close();}	
		
		}

	
	
	private void runQuery4(String smain,String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
				String syn;
			//	System.out.println( name.toString() );
				syn = name.toString();
				runQuery11(smain,syn," select  ?name where{ ?pub otherpeople:title ?name}", model);
				runQuery1(smain,syn," select  ?name where{ ?pub otherpeople:abstr ?name}", model);
		//	System.out.println("this is coming from ss"+ss);
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}				
		}

	//this is for abstract matching for synonyms
	private void runQuery1(String smain,String syn,String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
				String st;
				//System.out.println( name.toString() );
				st = name.toString();
				//System.out.println("this is ss "+ss);
				int index = st.indexOf(syn);
				if(index != -1){
					runQuery2(st,syn," select  ?x ?name where{ ?x otherpeople:abstr \""+st+"\". ?x otherpeople:url ?name } ", model);
				}
			//	runQuery(" select  ?x ?name where{ ?x otherpeople:annotation \""+ss+"\". ?x otherpeople:url ?name } ", model);
			//	System.out.println("this is coming from ss"+ss);
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}				
		}
	
	private void runQuery2(String st,String syn,String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
			String s;
				//System.out.println( name.toString() );
				s = name.toString();
				System.out.println(s);
				rank = rank + 5;
				System.out.println(rank);
		//		String m = "<a href = '"+s+"'>"+st+"</a>";
		//		String ht = new StringBuffer().append(m).toString();
			//	ht= ht+"<center>here is the paper related to "+smain+" please click the link below</center> <br>";
				 ht = ht+"<a href = '"+s+"'><center>"+s+"</center></a>Rank : "+rank+"<br>Abstract: "+st+"<br>"; //**********one of the ht declared here ***************
				//int index = st.indexOf(ss);
				//if(index != -1){
				//	runQuery(" select  ?x ?name where{ ?x otherpeople:abstr \""+s+"\". ?x otherpeople:url ?name } ", model);
			//	}
			//	runQuery(" select  ?x ?name where{ ?x otherpeople:annotation \""+ss+"\". ?x otherpeople:url ?name } ", model);
			//	System.out.println("this is coming from ss"+ss);
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}				
		}
	
	// this is for title matching for synonyms
	private void runQuery11(String smain,String syn,String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
				String st;
				//System.out.println( name.toString() );
				st = name.toString();
				//System.out.println("this is ss "+ss);
				int index = st.indexOf(syn);
				if(index != -1){
					runQuery22(st,syn," select  ?x ?name where{ ?x otherpeople:title \""+st+"\". ?x otherpeople:url ?name } ", model);
				}
			//	runQuery(" select  ?x ?name where{ ?x otherpeople:annotation \""+ss+"\". ?x otherpeople:url ?name } ", model);
			//	System.out.println("this is coming from ss"+ss);
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}				
		}
	
	private void runQuery22(String st,String syn,String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		System.out.println("its here");
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		System.out.println(st);
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
			String s;
				//System.out.println( name.toString() );
				s = name.toString();
				rank = rank + 5;
				System.out.println(rank);
				//System.out.println(s);
				//*******************here s is the url********************
				// *******************here st is title******************
		//		String m = "<a href = '"+s+"'>"+st+"</a>";
		//		String ht = new StringBuffer().append(m).toString();
			//	ht= ht+"<center>here is the paper related to "+smain+" please click the link below</center> <br>";
				 ht = ht+"<a href = '"+s+"'><center>"+s+"</center></a> Rank :"+rank+"<br>Abstract: "+st+"<br>"; //**********one of the ht declared here ***************
				//int index = st.indexOf(ss);
				//if(index != -1){
				//	runQuery(" select  ?x ?name where{ ?x otherpeople:abstr \""+s+"\". ?x otherpeople:url ?name } ", model);
			//	}
			//	runQuery(" select  ?x ?name where{ ?x otherpeople:annotation \""+ss+"\". ?x otherpeople:url ?name } ", model);
			//	System.out.println("this is coming from ss"+ss);
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}				
		}
	
	
	//this is for abstract matching of main disease word
	private void runQuerymain1(String ss,String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
				String st;
				
				//System.out.println( name.toString() );
				st = name.toString();
				System.out.println("here is st"+st);
				int index = st.indexOf(ss);
				if(index != -1){
				
			//		runQuerymain2(st," select  ?x ?name where{ ?x otherpeople:abstr \""+st+"\". ?x otherpeople:url ?name } ", model);
				}
			//	runQuery(" select  ?x ?name where{ ?x otherpeople:annotation \""+ss+"\". ?x otherpeople:url ?name } ", model);
			//	System.out.println("this is coming from ss"+ss);
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}				
		}
	private void runQuerymain2(String st,String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
			String s;
				//System.out.println( name.toString() );
				s = name.toString();
				System.out.println(s);
				rank = rank +7;
				System.out.println(rank);
			//	String m = "<a href = '"+s+"'>"+st+"</a>";
			//	String ht = new StringBuffer().append(m).toString();  //**********string append here*******
			//	ht= ht+"<center>here is the paper related to "+st+" please click the link below</center><br>";
				ht = ht+"<a href = '"+s+"'><center>"+s+"</center></a> Rank : "+rank+"<br>Abstratc: "+st+"<br>";
			/*	try {
				// Create file 
    		    FileWriter fstreamw = new FileWriter("C:\\Users\\chinmay\\Documents\\semhealth.html");
    		        BufferedWriter out = new BufferedWriter(fstreamw);
    		    out.write(htm);
    		    //Close the output stream
    		    
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}				
		}
	
	//This is for title
private void runQuerymain11(String ss,String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
		queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
		
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
				String st;
				//System.out.println( name.toString() );
				st = name.toString();
			//	System.out.println(st);
				int index = st.indexOf(ss);
				if(index != -1){
					runQuerymain22(st," select  ?x ?name where{ ?x otherpeople:title \""+st+"\". ?x otherpeople:url ?name } ", model);
				}
			//	runQuery(" select  ?x ?name where{ ?x otherpeople:annotation \""+ss+"\". ?x otherpeople:url ?name } ", model);
			//	System.out.println("this is coming from ss"+ss);
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}				
		}
	
//this is for title second recursion
private void runQuerymain22(String st,String queryRequest, Model model){
	
	StringBuffer queryStr = new StringBuffer();
	// Establish Prefixes
	//Set default Name space first
	queryStr.append("PREFIX people" + ": <" + defaultNameSpace1 + "> ");
	queryStr.append("PREFIX otherpeople" + ": <" + defaultNameSpace2 + ">");
	queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
	queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
	queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
	
	//Now add query
	queryStr.append(queryRequest);
	Query query = QueryFactory.create(queryStr.toString());
	QueryExecution qexec = QueryExecutionFactory.create(query, model);
	try {
	ResultSet response = qexec.execSelect();
	
	while( response.hasNext()){
		QuerySolution soln = response.nextSolution();
		RDFNode name = soln.get("?name");
		if( name != null ){
		String s;
			//System.out.println( name.toString() );
			s = name.toString();
			System.out.println(s);
			rank = rank+9;
			System.out.println(rank);
		//	String m = "<a href = '"+s+"'>"+st+"</a>";
		//	String ht = new StringBuffer().append(m).toString();  //**********string append here*******
		//	ht= ht+"<center>here is the paper related to "+st+" please click the link below</center><br>";
			ht = ht+"<a href = '"+s+"'><center>"+st+"</center></a> Rank : "+rank+"<br>Abstratct: "+st+"<br>";
		/*	try {
			// Create file 
		    FileWriter fstreamw = new FileWriter("C:\\Users\\chinmay\\Documents\\semhealth.html");
		        BufferedWriter out = new BufferedWriter(fstreamw);
		    out.write(htm);
		    //Close the output stream
		    
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
		else
			System.out.println("No Friends found!");
		}
	} finally { qexec.close();}				
	}

	
	private void runJenaRule(Model model){
		String rules = "[emailChange: (?person <http://xmlns.com/foaf/0.1/mbox> ?email), strConcat(?email, ?lit), regex( ?lit, '(.*@gmail.com)') -> (?person <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://org.semwebprogramming/chapter2/people#GmailPerson>)]";

		Reasoner ruleReasoner = new GenericRuleReasoner(Rule.parseRules(rules));
		ruleReasoner = ruleReasoner.bindSchema(schema);
	    inferredFriends = ModelFactory.createInfModel(ruleReasoner, model);		
	}
	
	private void runPellet( ){
		Reasoner reasoner = PelletReasonerFactory.theInstance().create();
	    reasoner = reasoner.bindSchema(schema);
	    inferredFriends = ModelFactory.createInfModel(reasoner, _friends);
	    
	    ValidityReport report = inferredFriends.validate();
	}
	
    public static void printIterator(Iterator i, String header) {

        System.out.println(header);

        for(int c = 0; c < header.length(); c++)

            System.out.print("=");

        System.out.println();
       

        if(i.hasNext()) {

	        while (i.hasNext()) 

	            System.out.println( i.next() );

        }       

        else

            System.out.println("<EMPTY>");

        System.out.println();

    }

    public void setRestriction(Model model) throws IOException{
    	
		InputStream inResInstance = FileManager.get().open("Ontologies/restriction.owl");
		model.read(inResInstance,defaultNameSpace1);
		inResInstance.close();
			
    }
}
   