package net.luminis.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Words {
	private String word_sequence;
	private Type type;
	
	/*
	 * "np"
	 * "np\s" -> links een np geeft s
	 * "np/n -> rechts een n geeft np
	 */
	public Words(String word_sequence, Type type){
		setWordSequence(word_sequence);
		setType(type);
	}
	

	
	public void setWordSequence(String word_sequence){
		this.word_sequence = word_sequence;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	/*
	 * "np/n -> rechts een n geeft np
	 */
	public void mergeRight(Words wordright){
		type.typeComplete = type.typeLeft;
		type.findArguments();
		word_sequence = word_sequence + " " + wordright.word_sequence;

	}
	
	
	/*
	 * "np\s" -> links een np geeft s
	 */
	public void mergeLeft(Words wordleft){
		type.typeComplete = type.typeRight;
		type.findArguments();
		
		word_sequence = wordleft.word_sequence + " " + word_sequence;

	}

	
	/*
	 * loop through the word_array, 
	 * if two words next to each other match
	 * merge them and insert in the word_arrayList,
	 * delete the old two words
	 * and call the deduction function again
	 * 
	 * if there is only one Words left in the word_array
	 * Or if there are no more possible matches
	 * return the word_array 
	 */
	public static ArrayList<Words> deduction(ArrayList<Words> word_arrayList){
		if(word_arrayList.size() > 1){
			Words wordleft;
			Words wordright;
			
			for(int i=0; i<word_arrayList.size()-1; i++){
				wordleft = word_arrayList.get(i);
				wordright = word_arrayList.get(i+1);

				if(wordleft.type.needsRightArgument && (wordleft.type.typeRight.equals(wordright.type.typeComplete))){
					wordleft.mergeRight(wordright);
					word_arrayList.remove(i+1);
					deduction(word_arrayList);
				}
				if(wordright.type.needsLeftArgument && (wordright.type.typeLeft.equals(wordleft.type.typeComplete))){
					wordright.mergeLeft(wordleft);
					word_arrayList.remove(i);
					deduction(word_arrayList);
				}
			}
			return word_arrayList;	
		}else {
			return word_arrayList;	
		}		
	}
	
	
	public String toString(){
		return word_sequence + "\n" ;//+  type;
	}
	
	public static boolean isSentence(ArrayList<Words> word_arrayList){
		ArrayList<Words> sentence_arrayList = deduction(word_arrayList);
		if(sentence_arrayList.size() == 1 && sentence_arrayList.get(0).type.typeComplete.equals("s")){
			return true;
		}
		else return false;
	}
	
	public static void main(String[] args){
		Type t = new Type("n");
		Words w = new Words("chat", t);
		
		Type t1 = new Type("np\\s");
		Words w1 = new Words("dort", t1);
		
		Type t2 = new Type("np/n");
		Words w2 = new Words("le", t2);
		
		Type t3 = new Type("np");
		Words w3 = new Words("Jean", t3);
		
		ArrayList<Words> sentence1 = new ArrayList<>();
		sentence1.add(w2);
		sentence1.add(w);
		sentence1.add(w1);
		
		ArrayList<Words> sentence2 = new ArrayList<>();
		sentence2.add(w3);
		sentence2.add(w1);
		sentence2.add(w2);
		
		
		//System.out.println(deduction(word_arrayList1));
		
		System.out.println(isSentence(sentence1));
		System.out.println(isSentence(sentence2));
		
	}
	
}
