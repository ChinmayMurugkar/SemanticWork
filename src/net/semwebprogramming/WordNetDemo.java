package net.semwebprogramming.chapter2.HelloSemanticWeb;

/* Daniel Shiffman               */
/* Programming from A to Z       */
/* Spring 2008                   */
/* http://www.shiffman.net       */
/* daniel.shiffman@nyu.edu       */
/* Just showing some of the very basic things you can do with
 * RiTa wordnet (duplicating the JWNL example)
 */
import java.io.IOException;

import rita.wordnet.RiWordnet;

public class WordNetDemo  {
	double dist;
                public Double SemanticDistance(String s1,String s2) {
                	String pos;
                                // Would pass in a PApplet normally, but we don't need to here
                                RiWordnet wordnet = new RiWordnet(null);
                               
                                String start = s1;
                                String end = s2;
                                
                                pos = wordnet.getBestPos(start);
                                
                                if(pos != null){
                                // Wordnet can find relationships between words
                              //  System.out.println("\n\nRelationship between: " + start + " and " + end);
                                 dist = wordnet.getDistance(start,end,pos);
                                //String[] parents = wordnet.getCommonParents(start, end, pos);
                                System.out.println(start + " and " + end + " are related by a distance of: " + dist);
                               
                                // These words have common parents (hyponyms in this case)
                               /* System.out.println("Common parents: ");
                                if (parents != null) {
                                                for (int i = 0; i < parents.length; i++) {
                                                                System.out.println(parents[i]);
                                                }
                                }  */
                                
                                }
                                else{
                                	System.out.println("Sorry the word provided is not valid");
                                	dist =1.0;
                                }
                                
                                //System.out.println("\n\nHypernym Tree for " + start);
                                //int[] ids = wordnet.getSenseIds(start,wordnet.NOUN);
                                //wordnet.printHypernymTree(ids[0]);
                                return dist;
                             
                }
}