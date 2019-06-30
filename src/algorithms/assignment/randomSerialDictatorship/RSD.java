import java.util.*;

public class RSD {
    private static int countOfObjects;
    private static ArrayList<boolean[]> AssignedMatrixes;

    private static void permutation(AgentPreferencesStructure[] arr, int k) {
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

    private static <AgentPreferencesStructure> void swap(RefObject<AgentPreferencesStructure> item1,
            RefObject<AgentPreferencesStructure> item2) {
        AgentPreferencesStructure temp = item1.argValue;
        item1.argValue = item2.argValue;
        item2.argValue = temp;
    }

    private static void assign(RefObject<AgentPreferencesStructure[]> arr) {
        ArrayList<Integer> servedObjects = new ArrayList<Integer>();
        boolean[] tempAssign = new boolean[countOfObjects * arr.argValue.length];
        for (int i = 0; i < arr.argValue.length; i++)
            for (int j = 0; j < countOfObjects; j++) {
                if (j >= arr.argValue[i].getObjects().size())
                    break;
                if (servedObjects.contains(arr.argValue[i].getObjects().get(j)))
                    continue;
                servedObjects.add(arr.argValue[i].getObjects().get(j));
                tempAssign[arr.argValue[i].getIndex() * arr.argValue.length + arr.argValue[i].getObjects().get(j)] = true;
                break;
            }
        AssignedMatrixes.add(tempAssign);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Please enter number of objects: ");
        countOfObjects = input.nextInt();
        ArrayList<String> objects = new ArrayList<String>(countOfObjects);
        for (int i = 0; i < countOfObjects; i++) {
            System.out.printf("Please enter name of Object #%1$s: ", i + 1);
            objects.add(input.next());
        }
        System.out.print("Please enter number of agents: ");
        int countOfAgents = input.nextInt();
        ArrayList<AgentPreferencesStructure> agents = new ArrayList<AgentPreferencesStructure>(countOfAgents);
        for (int i = 0; i < countOfAgents; i++) {
            AgentPreferencesStructure tempAgentPreferencesStructure = new AgentPreferencesStructure();
            tempAgentPreferencesStructure.setObjects(new ArrayList<Integer>());
            System.out.printf("Please enter name of Agent #%1$s: ", i + 1);
            tempAgentPreferencesStructure.setAgent(input.next());
            tempAgentPreferencesStructure.setIndex(i);
            System.out.println(
                    "Please select index of your prefered objects from the bellow list, If you dont have preference or its finished, enter 0.\\n"
                            + "\r\n" + "								The objects are:");
            for (int j = 0; j < countOfObjects; j++) {
                System.out.printf("%1$s. %2$s" + "\r\n", j + 1, objects.get(j));
            }
            for (int j = 0; j < countOfObjects; j++) {
                System.out.printf("Preference #%1$s :" + "\r\n", j + 1);
                int selectedObjectIndex = input.nextInt() - 1;
                if (selectedObjectIndex == -1)
                    break;
                if (selectedObjectIndex < 0 || selectedObjectIndex >= countOfObjects) {
                    System.out.println("Its not correct, please try again!");
                    j--;
                    continue;
                }
                if (tempAgentPreferencesStructure.getObjects().contains(selectedObjectIndex)) {
                    System.out.println("The choosen preference was selected that, please try another one!");
                    j--;
                    continue;
                }
                tempAgentPreferencesStructure.getObjects().add(selectedObjectIndex);
            }
            agents.add(tempAgentPreferencesStructure);
        }
        AssignedMatrixes = new ArrayList<boolean[]>();
        permutation(agents.toArray(new AgentPreferencesStructure[0]), 0);
        int[] tempFinalMatrix = new int[countOfObjects * countOfAgents];
        String[] finalMatrix = new String[countOfObjects * countOfAgents];
        for (int i = 0; i < AssignedMatrixes.size(); i++)
            for (int j = 0; j < tempFinalMatrix.length; j++)
                tempFinalMatrix[j] += (AssignedMatrixes.get(i)[j] ? 1 : 0);
        System.out.print("\t");
        for (int i = 0; i < countOfObjects; i++)
            System.out.print(String.format("%1$s\t", objects.get(i)));
        for (int i = 0; i < tempFinalMatrix.length; i++) {
            if (i % countOfObjects == 0) {
                System.out.print(System.lineSeparator());
                System.out.print(String.format("%1$s", agents.get(i / countOfObjects).getAgent()));
            }
            finalMatrix[i] = String.format("%1$s/%2$s", tempFinalMatrix[i], AssignedMatrixes.size());
            System.out.print(String.format("\t%1$s", finalMatrix[i]));
        }
        input.close();
    }
}