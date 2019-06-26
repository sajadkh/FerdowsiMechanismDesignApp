package algorithms.assignment.SerialDictatorship;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;


public class SerialDictatorship {
    private List<Agent> agents;
    private List<Obj> objs;

    public SerialDictatorship(String[] agents, Obj[] objs, Obj[][] agentPreferences) {
        this.agents = new ArrayList<Agent>();
        this.objs = new ArrayList<Obj>();

        for(int i = 0; i < objs.length; i++){
            this.objs.add(new Obj(objs[i].getName()));
        }
        for(int i = 0; i < agents.length; i++){
            this.agents.add(new Agent(agents[i], agentPreferences[i]));
        }
    }

    public List<List<Integer>> SD() {
        boolean foundPreference = false;
        List<List<Integer>> result = new ArrayList<List<Integer>>();

        for(Agent agent: this.agents) {
            int[] currentAgentList = new int[objs.size()];
            int currentAgentListIndex = 0;
            foundPreference = false;
            for(Obj preference: agent.getPreference()) {
                currentAgentListIndex = 0;
                if(!foundPreference) {
                    for(Obj realObject: objs) {
                        if(realObject.getName() == preference.getName() && !realObject.getAssigned()) {
                            realObject.setAssigned(true);
                            currentAgentList[currentAgentListIndex] = 1;
                            foundPreference = true;
                            break;
                        }
                        else if(realObject.getName() == preference.getName()) {
                            break;
                        }  
                        currentAgentListIndex++; 
                    }
                }
            }

            ArrayList<Integer> currentAgentArrayList = new ArrayList<Integer>();
            for(int cell: currentAgentList) {
                currentAgentArrayList.add(cell);
            }
            result.add(currentAgentArrayList);
        }      
        return result;
    }

    // public static void main(String[] args) {
    //     Obj obj1 = new Obj("obj1");
    //     Obj obj2 = new Obj("obj2");
    //     Obj obj3 = new Obj("obj3");
    //     Obj obj4 = new Obj("obj4");


    //     String[] agents = {"agentB", "agentA"};
    //     Obj[] objs = {obj1, obj2, obj3, obj4};
    //     Obj[][] agentPreferences = {
    //         {obj2, obj3, obj4, obj1},
    //         {obj1, obj3, obj4, obj2}
    //     };

    //     SerialDictatorship sd = new SerialDictatorship(agents, objs, agentPreferences);
    //     System.out.println(sd.SD());
    // }
}