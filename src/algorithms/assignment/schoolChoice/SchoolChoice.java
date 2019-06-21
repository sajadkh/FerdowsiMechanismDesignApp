package algorithms.assignment.schoolChoice;

import java.util.ArrayList;
import java.util.List;

public class SchoolChoice {
    private List<Student> students;
    private List<School> schools;

    public SchoolChoice(String[] students, String[] schools, int[] capacity, String[][] studentsPreference, String[][] schoolsPriority) {
        this.schools = new ArrayList<School>();
        this.students = new ArrayList<Student>();
        for(int i=0; i<schools.length; i++){
            this.schools.add(new School(schools[i], capacity[i], schoolsPriority[i]));
        }
        for(int i=0; i<students.length; i++){
            List<School> stSchools = new ArrayList<School>();
            for(int j=0; j<studentsPreference[i].length; j++){
                int index = -1;
                for(int k=0; k<this.schools.size(); k++){
                    if(this.schools.get(k).getName() == studentsPreference[i][j]){
                        index = i;
                    }
                }
                stSchools.add(this.schools.get(index));
            }
            this.students.add(new Student(students[i], stSchools.toArray(new School[stSchools.size()])));
        }
    }

    public List<List<String>> AI() {
        boolean sw = true;

        while (sw) {
            sw = false;
            for (Student student : this.students) {
                if (student.getSchool() == null) {
                    if (student.initialPropose())
                        sw = true;
                }
            }
            if (sw) {
                for (School school : this.schools) {
                    school.AIPropose();
                }
            }
        }

        List<List<String>> result = new ArrayList<List<String>>();
        for (School school : this.schools) {
            result.add(school.studentToString());
        }


        return result;
    }

    public List<List<String>> DA() {
        boolean sw = true;

        while (sw) {
            sw = false;
            for (Student student : this.students) {
                if (student.getSchool() == null) {
                    if (student.initialPropose())
                        sw = true;
                }
            }
            if (sw) {
                for (School school : this.schools) {
                    school.DAPropose();
                }
            }
        }
        List<List<String>> result = new ArrayList<List<String>>();
        for (School school : this.schools) {
            result.add(school.studentToString());
        }


        return result;
    }
}

