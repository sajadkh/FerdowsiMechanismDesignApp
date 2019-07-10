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

public class AppFrame extends JFrame{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    
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

    
    public AppFrame(){
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
        
        psRbtn = new JRadioButton("PS");
        psRbtn.setSize(50, 20);
        psRbtn.setLocation(10, 170);
        psRbtn.setSelected(true);
        
        iaRbtn = new JRadioButton("IA");
        iaRbtn.setSize(50, 20);
        iaRbtn.setLocation(60, 170);
        
        daRbtn = new JRadioButton("DA");
        daRbtn.setSize(50, 20);
        daRbtn.setLocation(110, 170);
        
        ttcRbtn = new JRadioButton("TTC");
        ttcRbtn.setSize(50, 20);
        ttcRbtn.setLocation(160, 170);
        
        sdRbtn = new JRadioButton("SD");
        sdRbtn.setSize(50, 20);
        sdRbtn.setLocation(210, 170);
        
        rsdRbtn = new JRadioButton("RSD");
        rsdRbtn.setSize(50, 20);
        rsdRbtn.setLocation(260, 170);
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(psRbtn);
        buttonGroup.add(iaRbtn);
        buttonGroup.add(daRbtn);
        buttonGroup.add(ttcRbtn);
        buttonGroup.add(sdRbtn);
        buttonGroup.add(rsdRbtn);
           
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
        add(psRbtn);
        add(iaRbtn);
        add(daRbtn);
        add(ttcRbtn);
        add(sdRbtn);
        add(rsdRbtn);

    }
    
    private void setListeners(){
        
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	if(psRbtn.isSelected()) {
            		int [][] preferenceMatrix = parseToIntMatrix(preferenceMatrixInputTx.getText().toString());
            		if(preferenceMatrix == null) {
            			appendResult("wrong input");
            			return;
            		}
            		appendResult("\nassignment matrix: \n");
            		double [][] assignmentMatrix = PsAlgorithm.doPS(preferenceMatrix);
					for (int i = 0; i < assignmentMatrix.length; i++) {
						for (int j = 0; j < assignmentMatrix[0].length; j++) {
							appendResult(assignmentMatrix[i][j] + "  ");
						}
						appendResult("\n");
					}            		
                }
            	
            	if(iaRbtn.isSelected()) {
            		appendResult("\nnull\n");
                }
            	
            	if(daRbtn.isSelected()) {
            		appendResult("\nnull\n");
                }
            	
            	if(ttcRbtn.isSelected()) {
            		appendResult("\nnull\n");
                }
            	
            	if(sdRbtn.isSelected()) {
            		appendResult("\nnull\n");
                }
            	
            	if(rsdRbtn.isSelected()) {
            		appendResult("\nnull\n");
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
