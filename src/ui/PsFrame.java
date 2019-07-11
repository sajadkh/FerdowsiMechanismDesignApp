package ui;


import algorithms.assignment.ps.PsAlgorithm;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PsFrame extends JFrame{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 1000;
    
    private JTextArea preferenceMatrixInputTx;
    private JLabel preferenceMatrixInputLb;
    private JTextArea initialAssignmentMatrixInputTx;
    private JLabel initialAssignmentMatrixInputLb;

    private JTextArea resultTx;
    private JScrollPane resultPane;   
    private JButton startBtn;
    
    private JRadioButton psRbtn;
    private JRadioButton iaRbtn;
    private JRadioButton daRbtn;
    private JRadioButton ttcRbtn;
    private JRadioButton sdRbtn;
    private JRadioButton rsdRbtn;

    
    public PsFrame(){
        initial();
        setListeners();
    }    
    
    private void initial(){
        
        setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        preferenceMatrixInputLb = new JLabel("Preference Matrix");
        preferenceMatrixInputLb.setSize(300,20);
        preferenceMatrixInputLb.setLocation(10,20);
        
        preferenceMatrixInputTx = new JTextArea("{1,2,3} {1,3,2} {2,1,3}");
        preferenceMatrixInputTx.setSize(760,30);
        preferenceMatrixInputTx.setLocation(10,50);
        
        initialAssignmentMatrixInputLb = new JLabel("Initial Assignment Matrix");
        initialAssignmentMatrixInputLb.setSize(300, 20);
        initialAssignmentMatrixInputLb.setLocation(10,90);

        initialAssignmentMatrixInputTx = new JTextArea();
        initialAssignmentMatrixInputTx.setSize(760,30);
        initialAssignmentMatrixInputTx.setLocation(10,120);
           
        resultTx= new JTextArea();
        resultPane = new JScrollPane (resultTx,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultPane.setSize(760, 310);
        resultPane.setLocation(10,220);
        
        startBtn = new JButton("start");
        startBtn.setSize(120, 30);
        startBtn.setLocation(650,170);

        add(preferenceMatrixInputTx);
        add(preferenceMatrixInputLb);
        add(initialAssignmentMatrixInputTx);
        add(initialAssignmentMatrixInputLb);
        add(resultPane);
        add(startBtn);


    }
    
    private void setListeners(){
        
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(iaRbtn.isSelected()) {
                    int[][] preferenceMatrix = parseToIntMatrix(preferenceMatrixInputTx.getText().toString());
                    if (preferenceMatrix == null) {
                        appendResult("wrong input");
                        return;
                    }
                    appendResult("\nassignment matrix: \n");
                    double[][] assignmentMatrix = PsAlgorithm.doPS(preferenceMatrix);
                    for (int i = 0; i < assignmentMatrix.length; i++) {
                        for (int j = 0; j < assignmentMatrix[0].length; j++) {
                            appendResult(assignmentMatrix[i][j] + "  ");
                        }
                        appendResult("\n");
                    }
                }
                else if(daRbtn.isSelected()){
                    int[][] preferenceMatrix = parseToIntMatrix(preferenceMatrixInputTx.getText().toString());
                    if (preferenceMatrix == null) {
                        appendResult("wrong input");
                        return;
                    }
                    appendResult("\nassignment matrix: \n");
                    double[][] assignmentMatrix = PsAlgorithm.doPS(preferenceMatrix);
                    for (int i = 0; i < assignmentMatrix.length; i++) {
                        for (int j = 0; j < assignmentMatrix[0].length; j++) {
                            appendResult(assignmentMatrix[i][j] + "  ");
                        }
                        appendResult("\n");
                    }
                }
            }
        });          
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
