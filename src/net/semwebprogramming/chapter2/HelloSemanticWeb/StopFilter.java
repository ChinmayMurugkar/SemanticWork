package net.semwebprogramming.chapter2.HelloSemanticWeb;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StopFilter 
{
        public static final String stoplistFilename = "/Users/chinmay/Desktop/stoplist.txt";
        
        public final Set<String> stopwords;
        
        public StopFilter() throws IOException
        {
                BufferedReader br = new BufferedReader(new FileReader(stoplistFilename));
                stopwords = new HashSet<String>();
                String line;
                while((line = br.readLine()) != null)
                        stopwords.add(line);
        }
        
        public boolean valid(String s)
        {
                return !stopwords.contains(s);
        }
}
