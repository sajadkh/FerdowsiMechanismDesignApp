/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms.assignment.ttc;

/**
 *
 * @author sahar
 */
import java.lang.reflect.Array;
import java.util.*;
import java.lang.Math;

public class SchoolChoice implements SchoolChoiceInterface {

	@Override
	public int[] Ttcmmech(int n, int m, int[][] stuPrefs, int[][] schoolPrefs, int[] quota) {
		int[] choice = new int[n];
		int num = 0;     //keep track the num of students who have been allocated
		Map<Integer, ArrayList<Integer>> stuRemain = new HashMap<Integer, ArrayList<Integer>>();    //for storing the students who are not allocated
		Map<Integer, ArrayList<Integer>> schRemain = new HashMap<Integer, ArrayList<Integer>>();  //for storing schools who have seats remains
		int [] counter = new int[m];          //keep track how many seats are still available at this school

		for(int i=0;i<n;i++){                    //initialization
			choice[i] = -1;
			ArrayList<Integer> ls = new ArrayList<>();
			for(int j=0; j<m; j++){
				ls.add(stuPrefs[i][j]);
			}
			stuRemain.put(i+1,ls);
		}

		for(int i=0;i<m;i++){             //initialization
			counter[i] = quota[i];
			ArrayList<Integer> ls = new ArrayList<>();
			for(int j=0;j<n;j++){
				ls.add(schoolPrefs[i][j]);
			}
			schRemain.put(i+1,ls);
		}
		while (!stuRemain.isEmpty()){            //if there are still students who are not allocated
            HashMap<Integer,Integer> stuChoice = new HashMap<>();        //for storing the new matching pairs in this step
			findAllCircles(stuRemain,schRemain,stuChoice);              //find all the circles in this step and find the ner matching pairs
			num += stuChoice.keySet().size();
			Iterator<Integer> keyStu = stuChoice.keySet().iterator();    //use the stuChoice for updating the final choice table and the remaining schools and students
			while (keyStu.hasNext()){
				int stuId = keyStu.next();
				int schId = stuChoice.get(stuId);
				choice[stuId-1] = schId;       //update the student choice table
				if(num!=n){
					updateRemain(stuId,schId,stuRemain,schRemain,counter);   //updating the remaining schools and students
				}
			}


		}

		return choice;



	}

	public static void studentListInsert(ArrayList<Student> ls, Student s) {             //insert sorting
	    int index=0;
		for(int i=0;i<ls.size();i++) {
			if (s.PrefforSchool <= ls.get(i).PrefforSchool) {
				index = i;
				break;
			}
		}
		ls.add(index, s);

    }

    public static void findAllCircles(Map<Integer, ArrayList<Integer>> stuRemain,Map<Integer, ArrayList<Integer>>schRemain,HashMap<Integer, Integer> stuChoice){
		Map<Integer,Boolean>  schRead = new HashMap<>();                 //keep track if a certain school node has been read
		Iterator<Integer> schKey = schRemain.keySet().iterator();        //initialization
		while(schKey.hasNext()){
			int schId = schKey.next();
			schRead.put(schId,false);
		}
		Iterator<Integer> keyIter = schRemain.keySet().iterator();
		while(keyIter.hasNext()){                                     //ensure that every school node will be considered
			int schId = keyIter.next();
			Stack<Integer> schStack = new Stack<>();               //store the non-read nodes which can be reached from this school node
			strongConnected(schId,schRead,stuRemain,schRemain,stuChoice,schStack);      // find the circle which include this school node if exist
			while(!schStack.isEmpty()){                //update
				int term = schStack.pop();
				schRead.replace(term,true);
			}
		}


	}

	public static void strongConnected(int schId, Map<Integer,Boolean>schRead, Map<Integer, ArrayList<Integer>> stuRemain,Map<Integer, ArrayList<Integer>>schRemain,
									   Map<Integer,Integer> stuChoice, Stack<Integer> schStack){
    	if(!schRead.get(schId)){
    		if(!schStack.contains(schId)){     //if it's not read
    			schStack.push(schId);
    			//schRead[schId-1] = true;
    			int stuId = schRemain.get(schId).get(0);
    			int schNext = stuRemain.get(stuId).get(0);
    			strongConnected(schNext,schRead,stuRemain,schRemain,stuChoice,schStack);
			}
			else{       //if it is already in the stack, we can find a circle
    			int sch = schStack.pop();
    			int w = schId;
    			while(sch!=schId){
    				int stu = schRemain.get(sch).get(0);
    				stuChoice.put(stu,w);
    				schRead.replace(sch,true);
    				w=sch;
    				sch = schStack.pop();
				}
				schRead.replace(schId,true);
				stuChoice.put(schRemain.get(schId).get(0),w);
			}

		}

	}

	public  static void updateRemain(int stuId, int schId, Map<Integer, ArrayList<Integer>> stuRemain, Map<Integer, ArrayList<Integer>>schRemain, int [] counter){
		stuRemain.remove(stuId);
		counter[schId-1] -= 1;
		if(counter[schId-1]<= 0 ){            //remove this school from the list
			schRemain.remove(schId);
			Iterator<Integer> keyStu = stuRemain.keySet().iterator();
			while(keyStu.hasNext()){
				int stuKey = keyStu.next();
				ArrayList<Integer> schList = stuRemain.get(stuKey);
				int index = schList.indexOf(schId);
				schList.remove(index);
			}
		}
		Iterator<Integer> keySch = schRemain.keySet().iterator();
		while(keySch.hasNext()){
			int schKey = keySch.next();
			ArrayList<Integer> stuList = schRemain.get(schKey);
			int index = stuList.indexOf(stuId);
			stuList.remove(index);
		}
	}




}
