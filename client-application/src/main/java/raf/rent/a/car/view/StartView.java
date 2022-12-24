package raf.rent.a.car.view;

import raf.rent.a.car.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartView extends JPanel {

    public StartView(){
        init();
    }

    private void init() {
        setBounds(450, 190, 1014, 597);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel titleLbl = new JLabel("Rent A Car Application");
        titleLbl.setBackground(Color.BLACK);
        titleLbl.setForeground(Color.RED);
        titleLbl.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        titleLbl.setBounds(300, 0, 590, 103);
        add(titleLbl);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 24));
        loginBtn.setBounds(100, 245, 243, 93);
        add(loginBtn);
        loginBtn.addActionListener(e -> MainFrame.getInstance().showLoginViewView());

        JButton createUser = new JButton("Create user");
        createUser.setFont(new Font("Tahoma", Font.PLAIN, 24));
        createUser.setBounds(400, 245, 243, 93);
        add(createUser);

        JButton createManager = new JButton("Create manager");
        createManager.setFont(new Font("Tahoma", Font.PLAIN, 24));
        createManager.setBounds(700, 245, 243, 93);
        add(createManager);
        createManager.addActionListener(e -> MainFrame.getInstance().showCreateManagerView());

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setBounds(0, 0, 1010, 562);
        add(lblNewLabel_1);
    }
}
