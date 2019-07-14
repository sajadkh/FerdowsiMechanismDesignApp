package algorithms.assignment.ps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;

public class PsAlgorithm {
	
	public static double [][] doPS(int [][] preferenceMatrix){
		
		double [][] assignmentMatrix = new double [preferenceMatrix.length][preferenceMatrix[0].length];
		
		while(!checkFinish(assignmentMatrix)) {
			
			// create object to user  Map
			Map<Integer, ArrayList<Integer>> preference2user = new HashMap<>();
			for (int i=0;i<preferenceMatrix.length;i++) {
				int currentUserIndex = i;
				if(checkUserFullness(currentUserIndex, assignmentMatrix)) {
					continue;
				}
				int currentPreferencedObjectIndex = getBestAvailablePreferenceObjectForUser(currentUserIndex, preferenceMatrix, assignmentMatrix);
				if(preference2user.containsKey(currentPreferencedObjectIndex)) {
					preference2user.get(currentPreferencedObjectIndex).add(currentUserIndex);
				}else {
					ArrayList<Integer> list = new ArrayList<>();
					list.add(currentUserIndex);
					preference2user.put(currentPreferencedObjectIndex, list);
				}
			}
			
			//find just next finished object
			double eatenPerUserPercentage = Double.MAX_VALUE;
			for(Integer p: preference2user.keySet()) {
				double currentRemainingObject = getRemainingObjectValue(p, assignmentMatrix);
				double currentObjectSharedUserCount =  preference2user.get(p).size();
				double currentEatenPerUserPercentage = currentRemainingObject / currentObjectSharedUserCount;
				if(currentEatenPerUserPercentage < eatenPerUserPercentage) {
					eatenPerUserPercentage = currentEatenPerUserPercentage;
				}	
			}
			
			// eat and update matrixes
			for(Integer p: preference2user.keySet()) {				
				for(int i=0;i<preference2user.get(p).size();i++) {
					int userIndex = preference2user.get(p).get(i);
					assignmentMatrix [userIndex][p] += eatenPerUserPercentage; 
				}
			}
			
		}
		
		return assignmentMatrix;
	}
	
	private static int getBestAvailablePreferenceObjectForUser(int userIndex, int [][] preferenceMatrix, double [][] assignmentMatrix) {
		
		int objIndex = -1;
		
		// create preference 2 object map for given user
		Map<Integer, Integer> preference2ObjectMap = new HashMap<>();		
		for(int j=0;j<preferenceMatrix[0].length;j++) {
			 preference2ObjectMap.put(preferenceMatrix[userIndex][j], j);
		}
		
		// check each object existence order by user preference
		
		for(int i=0 ; i <preferenceMatrix[0].length;i++) {
			
			//find min preference in remaining preferences
			int minP = Integer.MAX_VALUE;
			for(Integer p: preference2ObjectMap.keySet()) {
				if(p < minP) {
					minP = p;
				}
			}
			
			//check minP object availability
			if(checkObjectAvailibility(preference2ObjectMap.get(minP), assignmentMatrix)) {
				return preference2ObjectMap.get(minP);
			}else {
				preference2ObjectMap.remove(minP);
			}
		}
				
		return objIndex;
	}
	
	private static boolean checkFinish(double [][] assignmentMatrix) {
		for(int i=0;i<assignmentMatrix.length;i++) {
			double sum = 0;
			for(int j=0;j<assignmentMatrix[0].length;j++) {
				sum += assignmentMatrix[i][j];
			}
			if(sum<0.999) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean checkUserFullness(int userIndex, double [][] assignmentMatrix) {
		double sum = 0;
		for(int j=0;j<assignmentMatrix[0].length;j++) {
			sum += assignmentMatrix [userIndex][j];						
		}
		if(sum>=0.999) {
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean checkObjectAvailibility(int objIndex, double [][] assignmentMatrix) {
		double sum = 0;
		for(int i=0;i<assignmentMatrix.length;i++) {
			sum += assignmentMatrix [i][objIndex];						
		}
		if(sum>=0.999) {
			return false;
		}else {
			return true;
		}
	}
	
	private static double getRemainingObjectValue(int objIndex, double [][] assignmentMatrix) {
		double consemed = 0;
		for(int i=0;i<assignmentMatrix.length;i++) {
			consemed += assignmentMatrix [i][objIndex];						
		}
		return 1-consemed;
	}
	
	private static double getUserRemainingCapacity(int userIndex, double [][] assignmentMatrix) {
		double eaten = 0;
		for(int j=0;j<assignmentMatrix[0].length;j++) {
			eaten += assignmentMatrix [userIndex][j];						
		}
		return 1-eaten;
	}

}
