package net.semwebprogramming.chapter2.HelloSemanticWeb;


import java.util.*;

public class CountWord {
       String[] frequency(String st[]){
        	//doc ="Ef   cient Graph Similarity Joins with Edit Distance Constraints ### Xiang Zhao        Chuan Xiao     Xuemin Lin             Wei Wang         The University of New South Wales  Australia  xzhao  chuanx  lxue  weiw  cse unsw edu au     East China Normal University  China    NICTA  Australia Abstract   Graphs are widely used to model complicated data semantics in many applications in bioinformatics  chemistry  social networks  pattern recognition  etc  A recent trend is to tolerate noise arising from various sources  such as erroneous data entry  and    nd similarity matches  In this paper  we study the graph similarity join problem that returns pairs of graphs such that their edit distances are no larger than a threshold  Inspired by the q gram idea for string similarity problem  our solution extracts paths from graphs as features for indexing  We establish a lower bound of common features to generate candidates  An ef   cient algorithm is proposed to exploit both matching and mismatching features to improve the    ltering and veri   cation on candidates  We demonstrate the proposed algorithm signi   cantly outperforms existing approaches with extensive experiments on publicly available datasets";
        int flag =0;
        HashMap<String, Integer> m = new HashMap<String, Integer>();
            String str="";
            for(int i=0;i<st.length;i++){
                str+=st[i]+" ";
            }
            str = str.toLowerCase(); 
                int count = -1;
                for (int i = 0; i < str.length(); i++) { 
                   if ((!Character.isLetter(str.charAt(i))) || (i + 1 == str.length())) { 
                            if (i - count > 1) { 
                            if (Character.isLetter(str.charAt(i))) 
                                i++;
                            String word = str.substring(count + 1, i);
                            if (m.containsKey(word)) { 
                            m.put(word, m.get(word) + 1);
                            }
                            else { 
                            m.put(word, 1);
                            } 
                        } 
                        count = i;
                    } 
                } 
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.addAll(m.values());
        Collections.sort(list, Collections.reverseOrder());
        int last = -1;
        int tenmax = 0;
        String[] retstr= new String[10];
        for (Integer i : list) { 
            if (last == i) 
                continue;
            last = i;
            for (String s : m.keySet()) { 
                if (m.get(s) == i){
                //	 System.out.println(s + ":" + i);
                	if(s.length()>2){
                	 if(tenmax != 10 ) {
                		 retstr[tenmax] = s;
                		 System.out.println("First 10 max words are"+retstr[tenmax]);
                		 tenmax++;
                	 	}
                	}
                } 
                
            }
        } 
        return retstr;
        }
} 