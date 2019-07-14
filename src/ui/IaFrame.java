package ui;


import algorithms.assignment.ps.PsAlgorithm;
import algorithms.assignment.schoolChoice.SchoolChoice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.swing.JOptionPane;

public class IaFrame extends JFrame{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 1000;

    private JTextArea studentPreferenceMatrixInputTx;
    private JLabel studentPreferenceMatrixInputLb;
    private JTextArea schoolPriorityMatrixInputTx;
    private JLabel schoolPriorityMatrixInputLb;
    private JTextArea initialSchoolListInputTx;
    private JLabel initialSchoolListInputLb;
    private JTextArea initialStudentListInputTx;
    private JLabel initialStudentListInputLb;
    private JTextArea initialCapacityInputTx;
    private JLabel initialCapacityInputLb;

    private JTextArea resultTx;
    private JScrollPane resultPane;
    private JButton startBtn;

    private JRadioButton iaRbtn;
    private JRadioButton daRbtn;


    public IaFrame(){
        initial();
        setListeners();
    }    
    
    private void initial(){
        
        setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        initialSchoolListInputLb = new JLabel("Schools List");
        initialSchoolListInputLb.setSize(300,20);
        initialSchoolListInputLb.setLocation(10,20);

        initialSchoolListInputTx = new JTextArea();
        initialSchoolListInputTx.setSize(760,30);
        initialSchoolListInputTx.setLocation(10,50);

        initialCapacityInputLb = new JLabel("Schools Capacity");
        initialCapacityInputLb.setSize(300,20);
        initialCapacityInputLb.setLocation(10,90);

        initialCapacityInputTx = new JTextArea();
        initialCapacityInputTx.setSize(760,30);
        initialCapacityInputTx.setLocation(10,120);

        initialStudentListInputLb = new JLabel("Students List");
        initialStudentListInputLb.setSize(300,20);
        initialStudentListInputLb.setLocation(10,160);

        initialStudentListInputTx = new JTextArea();
        initialStudentListInputTx.setSize(760,30);
        initialStudentListInputTx.setLocation(10,190);

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

        iaRbtn = new JRadioButton("IA");
        iaRbtn.setSize(50, 20);
        iaRbtn.setLocation(10, 380);

        daRbtn = new JRadioButton("DA");
        daRbtn.setSize(50, 20);
        daRbtn.setLocation(70, 380);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(iaRbtn);
        buttonGroup.add(daRbtn);

        add(initialSchoolListInputLb);
        add(initialSchoolListInputTx);
        add(initialCapacityInputLb);
        add(initialCapacityInputTx);
        add(initialStudentListInputLb);
        add(initialStudentListInputTx);
        add(studentPreferenceMatrixInputTx);
        add(studentPreferenceMatrixInputLb);
        add(schoolPriorityMatrixInputTx);
        add(schoolPriorityMatrixInputLb);
        add(resultPane);
        add(startBtn);
        add(iaRbtn);
        add(daRbtn);

    }
    
    private void setListeners(){
        
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String [][] studentPreferenceMatrix = parseToMatrix(studentPreferenceMatrixInputTx.getText().toString());
                String [][] schoolsPriorityMatrix = parseToMatrix(schoolPriorityMatrixInputTx.getText().toString());
                int [][] schoolsCapacity = parseToIntMatrix(initialCapacityInputTx.getText().toString());
                String [][] schoolsList = parseToMatrix(initialSchoolListInputTx.getText().toString());
                String [][] studentsList = parseToMatrix(initialStudentListInputTx.getText().toString());
                if(studentPreferenceMatrix == null || studentPreferenceMatrix.length > studentsList[0].length || schoolsPriorityMatrix == null
                        || schoolsList == null || schoolsList.length > 1 || studentsList == null || studentsList.length > 1
                        || schoolsPriorityMatrix.length > schoolsList[0].length || schoolsCapacity == null || schoolsCapacity.length > 1) {
                    appendResult("wrong input");
                    return;
                }
                appendResult("\nassignment matrix: \n");
                SchoolChoice sc = new SchoolChoice(studentsList[0], schoolsList[0], schoolsCapacity[0],
                        studentPreferenceMatrix, schoolsPriorityMatrix);
                List<List<String>> assignmentMatrix;
                if(iaRbtn.isSelected()) {
                    assignmentMatrix = sc.IA();
                }
                else {
                    assignmentMatrix = sc.DA();
                }
                for (int i = 0; i < assignmentMatrix.size(); i++) {
                    for (int j = 0; j < assignmentMatrix.get(0).size(); j++) {
                        appendResult(assignmentMatrix.get(i).get(j) + "  ");
                    }
                    appendResult("\n");
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
