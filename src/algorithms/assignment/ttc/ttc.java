/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ttc;
import java.util.Scanner;
/**
 *
 * @author sahar
 */

public class ttc {
	
	public static void main(String[] args){
                int n=0 ;  //number of student
		int m =0;  //number of school
	//	int q[]={2,2,2};
                
                
                   
                  if(m==0){
                       Scanner user_input = new Scanner(System.in);
                       System.out.println("please enter number of school :  ");
                       m = user_input.nextByte();
                          }
        
                  
                  
                  if(n==0){
                      Scanner user_input = new Scanner(System.in);
                      System.out.println("please enter number of student :  ");
                      n = user_input.nextByte();
                          }
        
                System.out.println(" capacity for schools:  ");
                int[] qlist = new int[m];
                int q[]=new int[m];
                for (int i=0; i<m;i++){
                    Scanner user_input = new Scanner(System.in);
                    int c=i+1;
                    System.out.println("please enter capacity for school ["+c+"] :  ");
                    qlist[i]=user_input.nextByte();
                    q[i]=qlist[i];
                                      }
                
                
                
                System.out.println("Prefs for school: ");
                int[][] slist = new int[m][n];
                int schoolPrefs[][]=new int[m][n];
                for (int i=0; i<m;i++){
                     int x=i+1;
                     System.out.println("please enter Prefs for school ["+x+"] : ");
                     for(int j=0; j<n; j++){
                         Scanner user_input = new Scanner(System.in);
                         slist[i][j]=user_input.nextByte();
                         schoolPrefs[i][j]=slist[i][j];
                                            }
                                        }
                   
                    
                 System.out.println("please enter Prefs for students: ");
                 int[][] Ilist = new int[n][m];
                 int stuPrefs[][]=new int[n][m];
                 for (int i=0; i<n;i++){
                     int z=i+1;
                     System.out.println("please enter Prefs for student ["+z+"]  ");
                     for(int j=0; j<m; j++){
                         Scanner user_input = new Scanner(System.in);
                         Ilist[i][j]=user_input.nextByte();
                         stuPrefs[i][j]=Ilist[i][j];
                                           }
                                        }
		//int schoolPrefs[][] = {{1,2,3,4,5,6,7,8},
		               //    {1,5,4,8,7,2,3,6},
		                 //  {5,3,1,7,2,8,6,4},
		                 //  {8,6,7,4,2,3,5,1}};
		//int stuPrefs[][] = {{2,1,3,4},{1,2,3,4},{3,2,1,4},{3,4,1,2},
		                  // {1,3,4,2},{4,1,2,3},{1,2,3,4},{1,2,4,3}};

		//int stuPrefs[][] = {}
		//int stuPrefs[][] = {{1,3,2,4},{1,2,3,4},{1,3,2,4},{1,4,3,2},
			//	{1,3,4,2},{2,1,4,3},{1,2,3,4},{1,2,4,3}};
		SchoolChoice s = new SchoolChoice();
		int [] result = s.Ttcmmech(n,m,stuPrefs,schoolPrefs,q);
		for(int i=1;i<n+1;i++){
		System.out.println("Assignment for student["+i+"]= "+result[i-1]);
		}
	}

}

