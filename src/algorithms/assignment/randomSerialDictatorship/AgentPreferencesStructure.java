import java.util.*;

public final class AgentPreferencesStructure {
    private int Index;

    public int getIndex() {
        return Index;
    }

    public void setIndex(int value) {
        Index = value;
    }

    private String Agent;

    public String getAgent() {
        return Agent;
    }

    public void setAgent(String value) {
        Agent = value;
    }

    private ArrayList<Integer> Objects;

    public ArrayList<Integer> getObjects() {
        return Objects;
    }

    public void setObjects(ArrayList<Integer> value) {
        Objects = value;
    }
}