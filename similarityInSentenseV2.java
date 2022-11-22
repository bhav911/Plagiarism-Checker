 import java.util.*;
 import java.io.*;

class similarityInSentenseV2{
	static String str1[];
	static int weight[][];
	@SuppressWarnings("unchecked")
	static ArrayList<String> list[] = new ArrayList[100];
	static int index = 0;
	static int s1,s2;
	
	public static void main(String args[])throws FileNotFoundException,IOException{
		BufferedReader br = new BufferedReader(new FileReader("text1.txt"));
		str1 = br.readLine().toLowerCase().replaceAll("[,.]","").split(" ");
		br = new BufferedReader(new FileReader("text2.txt"));
		String str2[] = br.readLine().toLowerCase().replaceAll("[,.]","").split(" "); 
		SIS(str1,str2);
	}
	
	
	public static void SIS(String str1[], String str2[]){
		s1 = str1.length+1;
		s2 = str2.length+1;
		char path[][] = new char[s1][s2];
		weight = new int[s1][s2];
		for(int i = 0 ; i < list.length ; i++)
			list[i] = new ArrayList<String>();
		setupArrays(weight,path,str2);
		traverse(path,s1-1,s2-1);
		print(str1.length + str2.length);
	}
	
	
	public static void setupArrays(int weight[][],char path[][],String str2[]){
		for(int i = 0 ; i < s1 ; i++){
			weight[i][0] = 0;
			path[i][0] = '#';
		}
		for(int i = 1 ;  i < s2 ; i++){
			weight[0][i] = 0;
			path[0][i] = '#';
		}
		for(int i = 1 ; i < s1 ; i++){
			for(int j = 1 ; j < s2 ; j++){
				if(str1[i-1].equals(str2[j-1])){
					weight[i][j] = weight[i-1][j-1] + 1;
					path[i][j] = '/';
				}
				else if(weight[i][j-1] <= weight[i-1][j]){
					weight[i][j] = weight[i-1][j];
					path[i][j] = '^';
				}
				else{
					weight[i][j] = weight[i][j-1];
					path[i][j] = '<';
				}
			}
		}
	}
	
	
	public static void traverse(char path[][],int i, int j){
		for(int k = i; k >= 0 ; k--){
			for(int l = j; l >= 0 ; l--){
				if(path[k][l] == '/' && path[k-1][l-1] == '/'){		
					findSeq(path,k,l);
					index++;
				}
			}
		}
	}
	
	
	public static void findSeq(char path[][],int i, int j){
		if(i == 0 || j == 0)
			return;
		if(path[i-1][j-1] == '/'){
			path[i][j] = '-';
			findSeq(path,i-1,j-1);
			list[index].add(str1[i-1]);
		}
		else if(path[i-1][j-1] != '/'){
			path[i][j] = '-';
			list[index].add(str1[i-1]);
		}
	}
	
	
	public static void print(int total_length){
		System.out.println("\nSimilar Sequence of words found are : ");
		System.out.println("--------------------------------------");
		String listString = "";
		int similarWords = 0;
		for(int i = 0 ; i < list.length ; i++){
			listString = String.join(" ", list[i]);
			//listString = listString.replaceAll(",", "");
			//listString = Arrays.toString(list[i].toArray()); 
			//listString = String.join(", ", list[i]);
			if(list[i].size() > 2){
				System.out.println("'"+listString+"'");
				similarWords += list[i].size();
			}
		}
		System.out.println("--------------------------------------");
		System.out.println("\nSimilarity level : " + (int)((similarWords*2/(double)total_length)*100) + "%");
	}
}