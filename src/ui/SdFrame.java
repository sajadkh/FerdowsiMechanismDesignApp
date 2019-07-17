package ui;

//import algorithms.assignment.serialDictatorship.Agent;
//import algorithms.assignment.serialDictatorship.Obj;
//import algorithms.assignment.serialDictatorship.SerialDictatorship;
//import algorithms.assignment.serialDictatorship.Obj;
//import algorithms.assignment.serialDictatorship.Agent;


import javax.swing.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Locale;


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
        
        initialObjectListInputTx = new JTextArea("{obj1,obj2,obj3,obj4}");
        initialObjectListInputTx.setSize(760,30);
        initialObjectListInputTx.setLocation(10,50);

        initialAgentListInputLb = new JLabel("Agents List");
        initialAgentListInputLb.setSize(300,20);
        initialAgentListInputLb.setLocation(10,90);
        
        initialAgentListInputTx = new JTextArea("{agentA,agentB}") ;
        initialAgentListInputTx.setSize(760,30);
        initialAgentListInputTx.setLocation(10,120);

        agentPreferenceMatrixInputLb = new JLabel("Agents Preference Matrix");
        agentPreferenceMatrixInputLb.setSize(300,20);
        agentPreferenceMatrixInputLb.setLocation(10,160);
        
        agentPreferenceMatrixInputTx = new JTextArea("{{obj2,obj3,obj4,obj1},{obj1,obj3,obj4,obj2}}")  ;
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
            	
                    String [][] agentsList = parseToMatrix(initialAgentListInputTx.getText().toString());
                    String [][] objectsList = parseToMatrix(initialObjectListInputTx.getText().toString());
                    String [][] agentPreferenceMatrix = parseToMatrix(agentPreferenceMatrixInputTx.getText().toString());
                    
                    Obj [] objs =new Obj[objectsList[0].length];
                	String [] agentList = new String [agentsList[0].length];
                    Obj [][] agentPreferences = new Obj[agentPreferenceMatrix.length][agentPreferenceMatrix[0].length];
                   
                    for (int i=0; i < objectsList[0].length; i++)
                    	objs[i] = new Obj(objectsList[0][i]); 
                    
                    for (int i=0; i < agentPreferenceMatrix.length; i++)
                        for (int j=0; j < agentPreferenceMatrix[0].length; j++) {
                        	for(int k=0; k < objectsList[0].length;k++) {
                            if (agentPreferenceMatrix[i][j].equals(objs[k].getName())) {
                            	agentPreferences[i][j]=objs[k];
                            }

                        	}
                        }
                    

                    for (int i=0; i<agentsList[0].length;i++)  
                    	 agentList[i]=agentsList[0][i];
                                       
            	    appendResult("\nAssignment matrix:\n");
            		SerialDictatorship s= new SerialDictatorship(agentList,objs,agentPreferences);
                	List<List<Integer>> output= s.SD();
                	for (int i = 0; i < output.size(); i++) {
                        for (int j = 0; j < output.get(0).size(); j++) {
                            appendResult(output.get(i).get(j).toString() + "  ");
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
  
   /////////////////////////////////////////////////////////////////////////// 
   public class Agent {
        private String name;
        private Obj[] preference;
        
        public Agent(String name, Obj[] preference) {
            this.name = name;
            this.preference = preference;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Obj[] getPreference() {
            return this.preference;
        }

        public void setPreference(Obj[] preference) {
            this.preference = preference;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
       
 //////////////////////////////////////////////////////////////////////   
    
  public  class Obj {
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
    
    
 //////////////////////////////////////////////////////////////////////////////////////   
  public class SerialDictatorship {
      private List<Agent> agents;
      private List<Obj> objs;

      public SerialDictatorship(String[] agents, Obj[] objs, Obj[][] agentPreferences) {
          this.agents = new ArrayList<Agent>();
          this.objs = new ArrayList<Obj>();

          for(int i = 0; i < objs.length; i++){
              this.objs.add(new Obj(objs[i].getName()));
          }
          for(int i = 0; i < agents.length; i++){
              this.agents.add(new Agent(agents[i], agentPreferences[i]));
          }
      }

      public List<List<Integer>> SD() {
          boolean foundPreference = false;
          List<List<Integer>> result = new ArrayList<List<Integer>>();

          for(Agent agent: this.agents) {
              int[] currentAgentList = new int[objs.size()];
              int currentAgentListIndex = 0;
              foundPreference = false;
              for(Obj preference: agent.getPreference()) {
                  currentAgentListIndex = 0;
                  if(!foundPreference) {
                      for(Obj realObject: objs) {
                          if(realObject.getName() == preference.getName() && !realObject.getAssigned()) {
                              realObject.setAssigned(true);
                              currentAgentList[currentAgentListIndex] = 1;
                              foundPreference = true;
                              break;
                          }
                          else if(realObject.getName() == preference.getName()) {
                              break;
                          }  
                          currentAgentListIndex++; 
                      }
                  }
              }

              ArrayList<Integer> currentAgentArrayList = new ArrayList<Integer>();
              for(int cell: currentAgentList) {
                  currentAgentArrayList.add(cell);
              }
              result.add(currentAgentArrayList);
          }      
          return result;
      }

     
  }
}