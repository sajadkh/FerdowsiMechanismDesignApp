package ui;



import algorithms.assignment.envyFree.Envy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import javax.swing.JOptionPane;

public class EnvyFreeFrame extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 1000;

    private JTextArea preferenceMatrixInputTx;
    private JLabel preferenceMatrixInputLb;
    private JTextArea initialAssignmentMatrixInputTx;
    private JLabel initialAssignmentMatrixInputLb;
    private JTextArea initialSchoolListInputTx;
    private JLabel initialSchoolListInputLb;

    private JTextArea resultTx;
    private JScrollPane resultPane;
    private JButton startBtn;


    public EnvyFreeFrame() {
        initial();
        setListeners();
    }

    private void initial() {

        setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        preferenceMatrixInputLb = new JLabel("Preference Matrix");
        preferenceMatrixInputLb.setSize(300, 20);
        preferenceMatrixInputLb.setLocation(10, 20);
        preferenceMatrixInputLb.setFont(new Font("Tahoma", Font.BOLD, 14));

        preferenceMatrixInputTx = new JTextArea("{0,1,2} {0,2,1} {2,1,0}");
        preferenceMatrixInputTx.setSize(760, 30);
        preferenceMatrixInputTx.setLocation(10, 50);
        preferenceMatrixInputTx.setFont(new Font("Tahoma", Font.PLAIN, 14));

        initialAssignmentMatrixInputLb = new JLabel("Initial Assignment Matrix");
        initialAssignmentMatrixInputLb.setSize(300, 20);
        initialAssignmentMatrixInputLb.setLocation(10, 90);
        initialAssignmentMatrixInputLb.setFont(new Font("Tahoma", Font.BOLD, 14));

        initialAssignmentMatrixInputTx = new JTextArea("{0.5,0.5,0}{0.5,0.25,0.25}{0,0.25,0.75}");
        initialAssignmentMatrixInputTx.setSize(760, 30);
        initialAssignmentMatrixInputTx.setLocation(10, 120);
        initialAssignmentMatrixInputTx.setFont(new Font("Tahoma", Font.PLAIN, 14));

        resultTx = new JTextArea();
        resultTx.setBorder(new EmptyBorder(10, 10, 10, 10));
        resultTx.setFont(new Font("Tahoma", Font.PLAIN, 16));
        resultPane = new JScrollPane(resultTx, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultPane.setSize(760, 310);
        resultPane.setLocation(10, 220);


        startBtn = new JButton("start");
        startBtn.setSize(120, 30);
        startBtn.setLocation(650, 170);
        startBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));

        add(preferenceMatrixInputTx);
        add(preferenceMatrixInputLb);
        add(initialAssignmentMatrixInputTx);
        add(initialAssignmentMatrixInputLb);
        add(resultPane);
        add(startBtn);

    }

    private void setListeners() {

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double[][] assignmentMatrix = parseToDoubleMatrix(initialAssignmentMatrixInputTx.getText());
                int[][] perferMatrix = parseToIntMatrix(preferenceMatrixInputTx.getText());

                if (assignmentMatrix != null && perferMatrix != null) {
                    Envy envy = new Envy();
                    String result = envy.Execute(assignmentMatrix, perferMatrix);

                    resultTx.setText(result);
                } else {
                    resultTx.setText("WrongInput");
                }


            }
        });
    }

    private int[][] parseToIntMatrix(String inputStr) {

        try {
            ArrayList<String> rowStrList = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\{[^\\}]{1,}\\}");
            Matcher matcher = pattern.matcher(inputStr);
            while (matcher.find()) {
                String str = matcher.group();
                rowStrList.add(str);
            }
            int row = rowStrList.size();
            int col = rowStrList.get(0).split(",").length;
            int[][] preferenceMatrix = new int[row][col];
            for (int i = 0; i < rowStrList.size(); i++) {
                String[] rowSplit = rowStrList.get(i).replace("{", "").replace("}", "").split(",");
                for (int j = 0; j < rowSplit.length; j++) {
                    preferenceMatrix[i][j] = Integer.parseInt(rowSplit[j]);
                }
            }
            return preferenceMatrix;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private double[][] parseToDoubleMatrix(String inputStr) {

        try {
            ArrayList<String> rowStrList = new ArrayList<>();
            Pattern pattern = Pattern.compile("\\{[^\\}]{1,}\\}");
            Matcher matcher = pattern.matcher(inputStr);
            while (matcher.find()) {
                String str = matcher.group();
                rowStrList.add(str);
            }
            int row = rowStrList.size();
            int col = rowStrList.get(0).split(",").length;
            double[][] preferenceMatrix = new double[row][col];
            for (int i = 0; i < rowStrList.size(); i++) {
                String[] rowSplit = rowStrList.get(i).replace("{", "").replace("}", "").split(",");
                for (int j = 0; j < rowSplit.length; j++) {
                    preferenceMatrix[i][j] = Double.parseDouble(rowSplit[j]);
                }
            }
            return preferenceMatrix;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    private void appendResult(String result) {
        resultTx.setText(resultTx.getText().toString() + result);
    }

}
