package net.semwebprogramming.chapter2.HelloSemanticWeb;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

//* This is the code for removing stop words from a text * stop words are saved in a string type array SWList[] and the text is passed as string parameter in the method cleandoc() from the main() */
public class testing2 {
String SWList[] = 
{
		"a","able","about","above","according","accordingly","across","actually","after","afterwards","again","against","all","allow","allows","almost","alone","along","already","also","although","always","am","among","amongst","an","and","another","any","anybody","anyhow","anyone","anything","anyway","anyways","anywhere","apart","appear","appreciate","appropriate","are","around","as","aside","ask","asking","associated","at","available","away","awfully",
       "b","be","became","because","become","becomes","becoming","been","before","beforehand","behind","being","believe","below","beside","besides","best","better","between","beyond","both","brief","but","by",
       "c","came","can","cannot","cant","cause","causes","certain","certainly","changes","clearly","co","com","come","comes","concerning","consequently","consider","considering","contain","containing","contains","corresponding","could","course","currently",
       "d","definitely","described","despite","did","different","do","does","doing","done","down","downwards","during",
       "e","each","edu","eg","eight","either","else","elsewhere","enough","entirely","especially","et","etc","even","ever","every","everybody","everyone","everything","everywhere","ex","exactly","example","except",
       "f","far","few","fifth","first","five","followed","following","follows","for","former","formerly","forth","four","from","further","furthermore",
       "g","get","gets","getting","given","gives","go","goes","going","gone","got","gotten","greetings",
       "h","had","happens","hardly","has","have","having","he","hello","help","hence","her","here","hereafter","hereby","herein","hereupon","hers","herself","hi","him","himself","his","hither","hopefully","how","howbeit","however",
       "i","ie","if","ignored","immediate","in","inasmuch","inc","indeed","indicate","indicated","indicates","inner","insofar","instead","into","inward","is","it","its","itself",
       "j","just","k","keep","keeps","kept","know","knows","known",
       "l","last","lately","later","latter","latterly","least","less","lest","let","like","liked","likely","little","ll","look","looking","looks","ltd",
           "m","mainly","many","may","maybe","me","mean","meanwhile","merely","might","more","moreover","most","mostly","much","must","my","myself",
       "n","name","namely","nd","near","nearly","necessary","need","needs","neither","never","nevertheless","new","next","nine","no","nobody","non","none","noone","nor","normally","not","nothing","novel","now","nowhere",
           "o","obviously","of","off","often","oh","ok","okay","old","on","once","one","ones","only","onto","or","other","others","otherwise","ought","our","ours","ourselves","out","outside","over","overall","own",
           "p","particular","particularly","per","perhaps","placed","please","plus","possible","presumably","probably","provides",
           "q","que","quite","qv",
       "r","rather","rd","re","really","reasonably","regarding","regardless","regards","relatively","respectively","right",
           "s","said","same","saw","say","saying","says","second","secondly","see","seeing","seem","seemed","seeming","seems","seen","self","selves","sensible","sent","serious","seriously","seven","several","shall","she","should","since","six","so","some","somebody","somehow","someone","something","sometime","sometimes","somewhat","somewhere","soon","sorry","specified","specify","specifying","still","sub","such","sup","sure",
           "t","take","taken","tell","tends","th","than","thank","thanks","thanx","that","thats","the","their","theirs","them","themselves","then","thence","there","thereafter","thereby","therefore","therein","theres","thereupon","these","they","think","third","this","thorough","thoroughly","those","though","three","through","throughout","thru","thus","to","together","too","took","toward","towards","tried","tries","truly","try","trying","twice","two",
           "u","un","under","unfortunately","unless","unlikely","until","unto","up","upon","us","use","used","useful","uses","using","usually","uucp",
           "v","value","various","ve","very","via","viz","vs","w","want","wants","was","way","we","welcome","well","went","were","what","whatever","when","whence","whenever","where","whereafter","whereas","whereby","wherein","whereupon","wherever","whether","which","while","whither","who","whoever","whole","whom","whose","why","will","willing","wish","with","within","without","wonder","would","would",
           "x",
       "y","yes","yet","you","your","yours","yourself","yourselves",
           "z","zero","special","characters","?",">","<","'","]","[",":","{","}","+","=","-","_",")","(","*","&","^","%","$","#","@","!","~","`"};

public  String[] cleanDoc(String str)
{  
	
	
	//variable declaration
	String s = str;
int nWordLength = 0; //length of every word in stop words list
int pos=0;           //position of stop word in the main text string
	for(int j=0;j<10;j++)
	{
			for(int i = 0; i< SWList.length; i++) 
			{
				nWordLength = SWList[i].length();
				pos = str.indexOf(" "+SWList[i]+" ");//inserting spaces before and after stop word checking
    
				if(pos!=-1)
				{
					// System.out.println("Stopwrod \t\""+SWList[i]+"\"\t found at position:\t"+ pos);
					str = str.substring(0,pos+1).concat(str.substring(pos+1 +nWordLength));//concatenate string without stop word removal
					//System.out.println("here is the substring"+str.substring(0,pos+1));
				}
			}
	}

	System.out.println("The final String is: " +str);//Display the final String after removing stop words
	String st = str.trim();
	st = st.replaceAll("\\s+", " ");
	System.out.println(st);
	String arr[]=st.split(" ");
	System.out.println("*********************This is from testing2************");
	System.out.println("Number of words are: "+arr.length);
		for(int i=0;i<arr.length;i++)
		{
			System.out.println("array"+i+"  :"+arr[i]);
		}
		System.out.println("*********************Till here***********");
		return arr;
	}

}


