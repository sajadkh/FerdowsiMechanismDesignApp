package algorithms.assignment.schoolChoice;

class Student {
    private String name;
    private School[] preference;
    private School school;
    private int lastProposed;

    Student(String name, School[] preference) {
        this.name = name;
        this.preference = preference;
        this.lastProposed = -1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School[] getPreference() {
        return preference;
    }

    public void setPreference(School[] preference) {
        this.preference = preference;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public boolean initialPropose() {
        this.lastProposed++;
        if(this.lastProposed >= this.preference.length)
            return false;
        return this.preference[this.lastProposed].addToProposeList(this);
    }

    @Override
    public String toString() {
        return this.name;
    }
}