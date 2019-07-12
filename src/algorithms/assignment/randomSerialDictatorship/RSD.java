package algorithms.assignment.randomSerialDictatorship;

import java.util.*;
import java.lang.*;

public class RSD {
    private int countOfObjects;
    private int countOfAgents;
    private ArrayList<boolean[]> AssignedMatrixes;

    private void permutation(AgentPreferencesStructure[] arr, int k) {
        if (k >= arr.length) {
            RefObject<AgentPreferencesStructure[]> tempRef_arr = new RefObject<AgentPreferencesStructure[]>(arr);
            assign(tempRef_arr);
            arr = tempRef_arr.argValue;
        } else {
            permutation(arr, k + 1);
            for (int i = k + 1; i < arr.length; i++) {
                RefObject<AgentPreferencesStructure> tempRef_Object = new RefObject<AgentPreferencesStructure>(arr[k]);
                RefObject<AgentPreferencesStructure> tempRef_Object2 = new RefObject<AgentPreferencesStructure>(arr[i]);
                swap(tempRef_Object, tempRef_Object2);
                arr[i] = tempRef_Object2.argValue;
                arr[k] = tempRef_Object.argValue;
                permutation(arr, k + 1);
                RefObject<AgentPreferencesStructure> tempRef_Object3 = new RefObject<AgentPreferencesStructure>(arr[k]);
                RefObject<AgentPreferencesStructure> tempRef_Object4 = new RefObject<AgentPreferencesStructure>(arr[i]);
                swap(tempRef_Object3, tempRef_Object4);
                arr[i] = tempRef_Object4.argValue;
                arr[k] = tempRef_Object3.argValue;
            }
        }
    }

    private <AgentPreferencesStructure> void swap(RefObject<AgentPreferencesStructure> item1,
            RefObject<AgentPreferencesStructure> item2) {
        AgentPreferencesStructure temp = item1.argValue;
        item1.argValue = item2.argValue;
        item2.argValue = temp;
    }

    private void assign(RefObject<AgentPreferencesStructure[]> arr) {
        ArrayList<Integer> servedObjects = new ArrayList<Integer>();
        boolean[] tempAssign = new boolean[countOfObjects * arr.argValue.length];
        for (int i = 0; i < arr.argValue.length; i++)
            for (int j = 0; j < countOfObjects; j++) {
                if (j >= arr.argValue[i].getObjects().size())
                    break;
                if (servedObjects.contains(arr.argValue[i].getObjects().get(j)))
                    continue;
                servedObjects.add(arr.argValue[i].getObjects().get(j));
                tempAssign[arr.argValue[i].getIndex() * arr.argValue.length
                        + arr.argValue[i].getObjects().get(j)] = true;
                break;
            }
        AssignedMatrixes.add(tempAssign);
    }

    public String DoRSD(String[] objects, String[] agentPreferences) {
        try {
            this.countOfObjects = objects.length;
            this.countOfAgents = agentPreferences.length;
            System.out.println(this.countOfAgents);
            ArrayList<AgentPreferencesStructure> agents = new ArrayList<AgentPreferencesStructure>(this.countOfObjects);
            for (int i = 0; i < this.countOfObjects; i++) {
                String objectsOrderSplitRegex = "\\s*,[,\\s]*";
                String[] temp = agentPreferences[i].split(":");
                AgentPreferencesStructure tempAgentPreferencesStructure = new AgentPreferencesStructure();
                tempAgentPreferencesStructure.setObjects(new ArrayList<Integer>());
                tempAgentPreferencesStructure.setAgent(temp[0]);
                tempAgentPreferencesStructure.setIndex(i);
                String[] tempAgentPreferencesArray = temp[1].replace("[", "").replace("]", "").split(objectsOrderSplitRegex);
                for(int j=0;j<tempAgentPreferencesArray.length;j++){
                    int selectedObjectIndex = Integer.parseInt(tempAgentPreferencesArray[j]) - 1;
                    if (selectedObjectIndex < 0 || selectedObjectIndex >= this.countOfObjects)
                        throw new Exception("Its not correct, please try again!");
                    if (tempAgentPreferencesStructure.getObjects().contains(selectedObjectIndex))
                        throw new Exception("The choosen preference was selected that, please try another one!");
                    tempAgentPreferencesStructure.getObjects().add(selectedObjectIndex);
                }
                agents.add(tempAgentPreferencesStructure);
            }
            AssignedMatrixes = new ArrayList<boolean[]>();
            permutation(agents.toArray(new AgentPreferencesStructure[0]), 0);
            int[] tempFinalMatrix = new int[this.countOfObjects * this.countOfAgents];
            String[] finalMatrix = new String[this.countOfObjects * this.countOfAgents];
            for (int i = 0; i < AssignedMatrixes.size(); i++)
                for (int j = 0; j < tempFinalMatrix.length; j++)
                    tempFinalMatrix[j] += (AssignedMatrixes.get(i)[j] ? 1 : 0);
            String result = "<table><tr><td></td>";
            for (int i = 0; i < this.countOfObjects; i++)
                result += String.format("<td>%1$s</td>", objects[i]);
            result += "</tr>";
            for (int i = 0; i < tempFinalMatrix.length; i++) {
                if (i % this.countOfObjects == 0) {
                    result += "</tr><tr>";
                    result += String.format("<td>%1$s</td>", agents.get(i / countOfObjects).getAgent());
                }
                finalMatrix[i] = String.format("%1$s/%2$s", tempFinalMatrix[i], AssignedMatrixes.size());
                result += String.format("<td>%1$s</td>", finalMatrix[i]);
            }
            result += "</tr></table>";
            return result;
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }
}