package ui;



import algorithms.assignment.serialDictatorship.SerialDictatorship;
import algorithms.assignment.serialDictatorship.Obj;
import algorithms.assignment.serialDictatorship.Agent;




import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.swing.JOptionPane;

public class SdFrame extends JFrame{

    private static final int WIDTH = 800;
    private static final int HEIGHT = 1000;

    private JTextArea agentPreferenceMatrixInputTx;
    private JLabel agentPreferenceMatrixInputLb;
    private JTextArea initialObjectListInputTx;
    private JLabel initialObjectListInputLb;
    private JTextArea initialAgentListInputTx;
    private JLabel initialAgentListInputLb;
    


    private JTextArea resultTx;
    private JScrollPane resultPane;
    private JButton startBtn;

    private JRadioButton psRbtn;
    private JRadioButton iaRbtn;
    private JRadioButton daRbtn;
    private JRadioButton ttcRbtn;
    private JRadioButton sdRbtn;
    private JRadioButton rsdRbtn;


    public SdFrame(){
        initial();
        setListeners();
    }    
    
    private void initial(){
        
        setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        initialObjectListInputLb = new JLabel("Objects List");
        initialObjectListInputLb.setSize(300,20);
        initialObjectListInputLb.setLocation(10,20);
        
        initialObjectListInputTx = new JTextArea();
        initialObjectListInputTx.setSize(760,30);
        initialObjectListInputTx.setLocation(10,50);

        initialAgentListInputLb = new JLabel("Agents List");
        initialAgentListInputLb.setSize(300,20);
        initialAgentListInputLb.setLocation(10,90);
        
        initialAgentListInputTx = new JTextArea();
        initialAgentListInputTx.setSize(760,30);
        initialAgentListInputTx.setLocation(10,120);

        agentPreferenceMatrixInputLb = new JLabel("Agents Preference Matrix");
        agentPreferenceMatrixInputLb.setSize(300,20);
        agentPreferenceMatrixInputLb.setLocation(10,160);
        
        agentPreferenceMatrixInputTx = new JTextArea();
        agentPreferenceMatrixInputTx.setSize(760,30);
        agentPreferenceMatrixInputTx.setLocation(10,190);
        
        
        resultTx= new JTextArea();
        resultPane = new JScrollPane (resultTx,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultPane.setSize(760, 310);
        resultPane.setLocation(10,290);
        
        startBtn = new JButton("start");
        startBtn.setSize(120, 30);
        startBtn.setLocation(650,240);

        add(initialObjectListInputLb);
        add(initialObjectListInputTx);
        add(initialAgentListInputLb);
        add(initialAgentListInputTx);
        add(agentPreferenceMatrixInputTx);
        add(agentPreferenceMatrixInputLb);
        add(resultPane);
        add(startBtn);
        

    }
    
    private void setListeners(){
        
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            if(sdRbtn.isSelected()) {
                    String [][] agentsList = parseToMatrix(initialAgentListInputTx.getText().toString());
                    String [][] objectsList = parseToMatrix(initialObjectListInputTx.getText().toString());
                    String [][] agentPreferenceMatrix = parseToMatrix(agentPreferenceMatrixInputTx.getText().toString());
                    Obj[] objs;
                    Obj [][] agentPreferences;
                    for (int i=0; i < objectsList[0].length; i++)
                    		objs[i] = new Obj(i); 
                    
                    for (int i=0; i < agentPreferenceMatrix.length; i++)
                        for (int j=0; j < agentPreferenceMatrix[0].length; j++)
                        	agentPreferences[i][j] = objs[i];       		
                         	
            	    appendResult("\nAssignment matrix:\n");
            		SerialDictatorship s= new SerialDictatorship(agentsList[0],objs,agentPreferences);
                	List<List<Integer>> output= s.SD();
                	String finalOutput= output.toString();
                	appendResult(finalOutput);

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
