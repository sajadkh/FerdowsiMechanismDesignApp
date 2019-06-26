package algorithms.assignment.SerialDictatorship;

import java.util.ArrayList;

class Agent {
    private String name;
    private Obj[] preference;
    
    public Agent(String name, Obj[] preference) {
        this.name = name;
        this.preference = preference;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Obj[] getPreference() {
        return this.preference;
    }

    public void setPreference(Obj[] preference) {
        this.preference = preference;
    }

    @Override
    public String toString() {
        return this.name;
    }
}