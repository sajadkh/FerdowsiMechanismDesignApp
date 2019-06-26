package algorithms.assignment.SerialDictatorship;

class Obj {
    private String name;
    private boolean assigned;

    public Obj(String name) {
        this.name = name;
        this.assigned = false;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getAssigned() {
        return this.assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    @Override
    public String toString() {
        return this.name;
    }
}