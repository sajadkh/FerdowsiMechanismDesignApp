package ui;


import algorithms.assignment.ttc.SchoolChoice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.swing.JOptionPane;

public class TtcFrame extends JFrame{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 1000;

    private JTextArea studentPreferenceMatrixInputTx;
    private JLabel studentPreferenceMatrixInputLb;
    private JTextArea schoolPriorityMatrixInputTx;
    private JLabel schoolPriorityMatrixInputLb;
    private JTextArea initialNumberOfSchoolsInputTx;
    private JLabel initialNumberOfSchoolsInputLb;
    private JTextArea initialNumberOfStudentsInputTx;
    private JLabel initialNumberOfStudentsInputLb;
    private JTextArea initialCapacityInputTx;
    private JLabel initialCapacityInputLb;

    private JTextArea resultTx;
    private JScrollPane resultPane;
    private JButton startBtn;

    private JRadioButton psRbtn;
    private JRadioButton iaRbtn;
    private JRadioButton daRbtn;
    private JRadioButton ttcRbtn;
    private JRadioButton sdRbtn;
    private JRadioButton rsdRbtn;


    public TtcFrame(){
        initial();
        setListeners();
    }

    private void initial(){

        setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        initialNumberOfSchoolsInputLb = new JLabel("Number Of Schools");
        initialNumberOfSchoolsInputLb.setSize(300,20);
        initialNumberOfSchoolsInputLb.setLocation(10,20);

        initialNumberOfSchoolsInputTx = new JTextArea();
        initialNumberOfSchoolsInputTx.setSize(760,30);
        initialNumberOfSchoolsInputTx.setLocation(10,50);

        initialCapacityInputLb = new JLabel("Schools Capacity");
        initialCapacityInputLb.setSize(300,20);
        initialCapacityInputLb.setLocation(10,90);

        initialCapacityInputTx = new JTextArea();
        initialCapacityInputTx.setSize(760,30);
        initialCapacityInputTx.setLocation(10,120);

        initialNumberOfStudentsInputLb = new JLabel("Number Of Students");
        initialNumberOfStudentsInputLb.setSize(300,20);
        initialNumberOfStudentsInputLb.setLocation(10,160);

        initialNumberOfStudentsInputTx = new JTextArea();
        initialNumberOfStudentsInputTx.setSize(760,30);
        initialNumberOfStudentsInputTx.setLocation(10,190);

        studentPreferenceMatrixInputLb = new JLabel("Student Preference Matrix");
        studentPreferenceMatrixInputLb.setSize(300,20);
        studentPreferenceMatrixInputLb.setLocation(10,230);

        studentPreferenceMatrixInputTx = new JTextArea();
        studentPreferenceMatrixInputTx.setSize(760,30);
        studentPreferenceMatrixInputTx.setLocation(10,260);

        schoolPriorityMatrixInputLb = new JLabel("School Priority Matrix");
        schoolPriorityMatrixInputLb.setSize(300, 20);
        schoolPriorityMatrixInputLb.setLocation(10,300);

        schoolPriorityMatrixInputTx = new JTextArea();
        schoolPriorityMatrixInputTx.setSize(760,30);
        schoolPriorityMatrixInputTx.setLocation(10,330);

        resultTx= new JTextArea();
        resultPane = new JScrollPane (resultTx,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultPane.setSize(760, 250);
        resultPane.setLocation(10,420);

        startBtn = new JButton("start");
        startBtn.setSize(120, 30);
        startBtn.setLocation(650,380);

        add(initialNumberOfSchoolsInputLb);
        add(initialNumberOfSchoolsInputTx);
        add(initialCapacityInputLb);
        add(initialCapacityInputTx);
        add(initialNumberOfStudentsInputLb);
        add(initialNumberOfStudentsInputTx);
        add(studentPreferenceMatrixInputTx);
        add(studentPreferenceMatrixInputLb);
        add(schoolPriorityMatrixInputTx);
        add(schoolPriorityMatrixInputLb);
        add(resultPane);
        add(startBtn);

    }

    private void setListeners(){

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int [][] studentPreferenceMatrix = parseToIntMatrix(studentPreferenceMatrixInputTx.getText().toString());
                int [][] schoolsPriorityMatrix = parseToIntMatrix(schoolPriorityMatrixInputTx.getText().toString());
                int [][] schoolsCapacity = parseToIntMatrix(initialCapacityInputTx.getText().toString());
                int NumberOfSchools =Integer.parseInt(initialNumberOfSchoolsInputTx.getText().toString());
                int NumberOfStudents = Integer.parseInt(initialNumberOfStudentsInputTx.getText().toString());
                if(studentPreferenceMatrix == null || studentPreferenceMatrix.length > NumberOfStudents || schoolsPriorityMatrix == null
                        || NumberOfSchools < 1 || NumberOfStudents < 1
                        || schoolsPriorityMatrix.length > NumberOfSchools || schoolsCapacity == null || schoolsCapacity.length < 1) {
                    appendResult("wrong input");
                    return;
                }
                appendResult("\nassignment matrix: \n");
                SchoolChoice s = new SchoolChoice();
                int[] assignmentMatrix = s.Ttcmmech(NumberOfStudents,NumberOfSchools,studentPreferenceMatrix,schoolsPriorityMatrix,schoolsCapacity[0]);
                for (int i = 1; i < assignmentMatrix.length+1; i++) {

                    appendResult("student["+(i)+"]= "+assignmentMatrix[i-1] + "\n");

                }
            }
        });
    }

    private String [][] parseToMatrix(String inputStr) {

    	try{
    		ArrayList<String> rowStrList = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\{[^\\}]{1,}\\}");
            Matcher matcher = pattern.matcher(inputStr);
            while(matcher.find()) {
            	String str = matcher.group();
            	rowStrList.add(str);
            }
            int row = rowStrList.size();
            int col = rowStrList.get(0).split(",").length;
            String [][] preferenceMatrix = new String [row][col];
            for(int i=0;i<rowStrList.size();i++) {
            	String [] rowSplit = rowStrList.get(i).replace("{", "").replace("}", "").split(",");
            	for(int j=0;j<rowSplit.length;j++) {
            		preferenceMatrix [i][j] = rowSplit[j];
            	}
            }
            return preferenceMatrix;
        }catch(Exception e){
            e.printStackTrace();
        }

    	return null;
    }

    private int [][] parseToIntMatrix(String inputStr) {

        try{
            ArrayList<String> rowStrList = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\{[^\\}]{1,}\\}");
            Matcher matcher = pattern.matcher(inputStr);
            while(matcher.find()) {
                String str = matcher.group();
                rowStrList.add(str);
            }
            int row = rowStrList.size();
            int col = rowStrList.get(0).split(",").length;
            int [][] preferenceMatrix = new int [row][col];
            for(int i=0;i<rowStrList.size();i++) {
                String [] rowSplit = rowStrList.get(i).replace("{", "").replace("}", "").split(",");
                for(int j=0;j<rowSplit.length;j++) {
                    preferenceMatrix [i][j] = Integer.parseInt(rowSplit[j]);
                }
            }
            return preferenceMatrix;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void appendResult(String result) {
    	resultTx.setText(resultTx.getText().toString()+result);
    }

}
