package algorithms.assignment.schoolChoice;

import java.util.ArrayList;

class School {
    private String name;
    private int capacity;
    private int remainCapacity;
    private String[] priority;
    private ArrayList<Student> students;
    private ArrayList<Student> proposeList;

    public School(String name, int capacity, String[] priority) {
        this.name = name;
        this.capacity = capacity;
        this.remainCapacity = capacity;
        this.priority = priority;
        this.students = new ArrayList<Student>();
        this.proposeList = new ArrayList<Student>();
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String[] getPriority() {
        return priority;
    }

    public void setPriority(String[] priority) {
        this.priority = priority;
    }

    public ArrayList<Student> getStudents() {
        return this.students;
    }

    public int getRemainCapacity() {
        return remainCapacity;
    }

    public int findPriority(String name) {
        int index = -1;
        for(int i=0; i<this.priority.length; i++){
            if(this.priority[i].equals(name)){
                index = i;
            }
        }
        return index;
    }

    public ArrayList<Student> getProposeList() {
        return proposeList;
    }

    public boolean addToProposeList(Student student) {
        this.proposeList.add(student);
        return true;
    }

    public boolean IAPropose() {
        if (this.capacity == this.students.size()) {
            this.proposeList.clear();
            return false;
        } else {
            if (this.proposeList.size() <= this.remainCapacity) {
                this.students.addAll(this.proposeList);
                this.remainCapacity -= this.proposeList.size();
                for (Student student : students) {
                    student.setSchool(this);
                }
                this.proposeList.clear();
                return true;
            } else {
                Student sortedStudent[] = new Student[this.priority.length];
                for (int i = 0; i < this.proposeList.size(); i++) {
                    int priority = this.findPriority(this.proposeList.get(i).getName());
                    if (priority != -1)
                        sortedStudent[priority] = this.proposeList.get(i);
                }
                for (int i = 0; i < sortedStudent.length && this.remainCapacity > 0; i++) {
                    if (sortedStudent[i] != null) {
                        this.students.add(sortedStudent[i]);
                        this.remainCapacity--;
                        sortedStudent[i].setSchool(this);
                    }
                }
                this.proposeList.clear();
                return true;
            }
        }
    }

    public boolean DAPropose() {
        if (this.proposeList.size() <= this.remainCapacity) {
            this.students.addAll(this.proposeList);
            this.remainCapacity -= this.proposeList.size();
            for (Student student : students) {
                student.setSchool(this);
            }
            this.proposeList.clear();
            return true;
        } else {
            Student sortedStudent[] = new Student[this.priority.length];
            for (int i = 0; i < this.proposeList.size(); i++) {
                int priority = this.findPriority(this.proposeList.get(i).getName());
                if (priority != -1)
                    sortedStudent[priority] = this.proposeList.get(i);
            }
            for (int i = 0; i < this.students.size(); i++) {
                int priority = this.findPriority(this.students.get(i).getName());
                if (priority != -1)
                    sortedStudent[priority] = this.students.get(i);
            }
            this.students.clear();
            this.remainCapacity = this.capacity;
            for (int i = 0; i < sortedStudent.length; i++) {
                if (sortedStudent[i] != null && this.remainCapacity > 0) {
                    this.students.add(sortedStudent[i]);
                    this.remainCapacity--;
                    sortedStudent[i].setSchool(this);
                } else if (sortedStudent[i] != null) {
                    sortedStudent[i].setSchool(null);
                }
            }
            this.proposeList.clear();
            return true;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public ArrayList<String> studentToString(){
        ArrayList<String> result = new ArrayList<String>();
        for(Student student: this.students){
            result.add(student.getName());
        }
        return result;
    }
}
