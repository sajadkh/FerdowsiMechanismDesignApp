package ui;

import algorithms.assignment.randomSerialDictatorship.RSD;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RsdFrame extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 1000;

    private JTextArea agentPreferenceInputTx;
    private JLabel agentPreferenceInputLb;
    private JTextArea objectsInputTx;
    private JLabel objectsInputLb;

    private JEditorPane resultTx;
    private JScrollPane resultPane;
    private JButton startBtn;

    public RsdFrame() {
        initial();
        setListeners();
    }

    private void initial() {

        setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        objectsInputLb = new JLabel("Objects (comma delimited):");
        objectsInputLb.setSize(300, 20);
        objectsInputLb.setLocation(10, 20);

        objectsInputTx = new JTextArea("Apple,Orange,Pear,Strawberry");
        objectsInputTx.setSize(760, 30);
        objectsInputTx.setLocation(10, 50);

        agentPreferenceInputLb = new JLabel(
                "<html>Agent Preferences:<br/>Exmaple: an agent Carrol prefered in order to (Pearl,Streawberry,Apple,Orange), then the structure must be like this: Carrol:[3,4,1,2]</html>");
        agentPreferenceInputLb.setSize(760, 40);
        agentPreferenceInputLb.setLocation(10, 90);

        agentPreferenceInputTx = new JTextArea("Alice:[1,2,3,4];Bob:[1,2,3,4];Carrol:[2,1,4,3];Dennis:[2,1,4,3]");
        agentPreferenceInputTx.setSize(760, 60);
        agentPreferenceInputTx.setLocation(10, 140);

        startBtn = new JButton("start");
        startBtn.setSize(120, 30);
        startBtn.setLocation(650, 210);

        resultTx = new JEditorPane();
        resultPane = new JScrollPane(resultTx, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultPane.setSize(760, 310);
        resultPane.setLocation(10, 250);

        add(objectsInputTx);
        add(objectsInputLb);
        add(agentPreferenceInputTx);
        add(agentPreferenceInputLb);
        add(resultPane);
        add(startBtn);

    }

    private void setListeners() {

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String objectsSplitRegex = "\\s*,[,\\s]*";
                    String agentPreferencesSplitRegex = "\\s*;[;\\s]*";
                    String[] objects = objectsInputTx.getText().split(objectsSplitRegex);
                    String[] agentPreferences = agentPreferenceInputTx.getText().split(agentPreferencesSplitRegex);
                    RSD rsd = new RSD();
                    resultTx.setContentType("text/html");
                    resultTx.setText(rsd.DoRSD(objects, agentPreferences));
                } catch (Exception ex) {
                    resultTx.setText(ex.getMessage());
                }
            }
        });
    }
}
