package net.semwebprogramming.chapter2.HelloSemanticWeb;

//program to find occurence of a word in a sentence
import java.io.*;
public class FrequecyCalculator
{
public int frequencyCalculator(String ls, String temp)throws IOException
{
int times=0,count=0,x=0,no=0;
InputStreamReader ir=new InputStreamReader(System.in);
BufferedReader br=new BufferedReader(ir);
String s,w;
s= ls;
w=temp;
try{
	for(int i=0;i<s.length();i++)
	{
		if(w.charAt(0)==s.charAt(i))
		{
			for(int j=0;j<w.length();j++,i++)
			{
				if(s.charAt(i)==w.charAt(j))
				{ count=count+1;}
				if(count==w.length())
				{no=no+1;count=0;};
			}
		}
	}
}
	catch(Exception e){}
	if(no==0)
	{
		System.out.println("word is not present");
	}
	else
	{
		System.out.println("word is present "+no+" times");
	}
	return  no;
}
}

