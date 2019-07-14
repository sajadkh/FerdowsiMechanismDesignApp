package ui;


import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
//import javax.swing.JOptionPane;


public class MainFrame extends JFrame{

    private static final int WIDTH = 300;
    private static final int HEIGHT = 500;

    private JButton iaBtn;
    private JButton psBtn;
    private JButton daBtn;
    private JButton sdBtn;
    private JButton rsdBtn;
    private JButton envyFreeBtn;
    private JButton ttcBtn;


    public MainFrame(){
        initial();
        setListeners();
    }

    private void initial(){

        setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        psBtn = new JButton("PS");
        psBtn.setSize(100, 40);
        psBtn.setLocation(90,50);

        iaBtn = new JButton("AI");
        iaBtn.setSize(100, 40);
        iaBtn.setLocation(90,100);

        daBtn = new JButton("DA");
        daBtn.setSize(100, 40);
        daBtn.setLocation(90,150);

        sdBtn = new JButton("SD");
        sdBtn.setSize(100, 40);
        sdBtn.setLocation(90,200);

        rsdBtn = new JButton("RSD");
        rsdBtn.setSize(100, 40);
        rsdBtn.setLocation(90,250);

        ttcBtn = new JButton("TTC");
        ttcBtn.setSize(100, 40);
        ttcBtn.setLocation(90,300);

        envyFreeBtn = new JButton("EnvyFree");
        envyFreeBtn.setSize(100, 40);
        envyFreeBtn.setLocation(90,350);

        add(psBtn);
        add(iaBtn);
        add(daBtn);
        add(sdBtn);
        add(rsdBtn);
        add(ttcBtn);
        add(envyFreeBtn);
    }

    private void setListeners(){

        psBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("psBtn");
                new PsFrame().setVisible(true);
            }
        });

        iaBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("iaBtn");
                new IaFrame().setVisible(true);
            }
        });

        sdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("sdBtn");
                new SdFrame().setVisible(true);
            }
        });

        rsdBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("rsdBtn");
                new RsdFrame().setVisible(true);
            }
        });

        ttcBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("ttcBtn");
                new TtcFrame().setVisible(true);
            }
        });

        envyFreeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("envyFreeBtn");
                new EnvyFreeFrame().setVisible(true);
            }
        });

    }

}
