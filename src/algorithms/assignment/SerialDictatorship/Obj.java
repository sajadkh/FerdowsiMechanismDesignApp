package algorithms.assignment.serialDictatorship;

class Obj {
    private int name;
    private boolean assigned;

    public Obj(int name) {
        this.name = name;
        this.assigned = false;
    }

    public int getName() {
        return this.name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public boolean getAssigned() {
        return this.assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

   // @Override
   // public String toString() {
    //    return this.name;
   // }
}