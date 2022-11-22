import java.io.*;
import java.util.*;

class similarityInSentense{
	static String str1[];
	static int weight[][];
	static int start,stop;
	public static void main(String args[])throws FileNotFoundException,IOException{
		BufferedReader br = new BufferedReader(new FileReader("text1.txt"));
		str1 = br.readLine().split(" ");
		br = new BufferedReader(new FileReader("text2.txt"));
		String str2[] = br.readLine().split(" ");
		SIS(str1,str2);
	}
	
	public static void SIS(String str1[], String str2[]){
		int s1 = str1.length+1;
		int s2 = str2.length+1;
		char path[][] = new char[s1][s2];
		weight = new int[s1][s2];
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
		for(int i = 0 ; i < s1 ; i++){
			for(int j = 0 ; j < s2 ; j++)
				System.out.print(weight[i][j]+"  ");
			System.out.println();
		}
		for(int i = 0 ; i < s1 ; i++){
			for(int j = 0 ; j < s2 ; j++)
				System.out.print(path[i][j]+"  ");
			System.out.println();
		}  
		//traverse(path,s1-1,s2-1);
	}
	public static void print(char path[][],int i, int j){
		if(i == 0 || j == 0){
			System.out.println();
			return;
		}
		if(path[i-1][j-1] =='/'){
			path[i][j] = '-';
			print(path,i-1,j-1);
			if(i == start && j == stop)
				System.out.println(str1[i-1]+" ");
			else
				System.out.print(str1[i-1]+" ");
		}
		else if(path[i-1][j-1] != '/'){
			path[i][j] = '-';
			System.out.print(str1[i-1]+" ");
		}
		
	}
	
	public static void traverse(char path[][],int i, int j){
		for(int k = i; k >= 0 ; k--){
			for(int l = j; l >= 0 ; l--){
				if(path[k][l] == '/' && path[k-1][l-1] == '/' && weight[k][l] > 1){		
					start = k;
					stop = l;
					print(path,k,l);
				}
			}
		}
	}
}