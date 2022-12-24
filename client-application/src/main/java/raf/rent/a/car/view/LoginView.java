package raf.rent.a.car.view;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.TokenRequestDto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginView extends JPanel {

    public LoginView(){
        init();
    }

    public void init(){
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel label = new JLabel("User Login");
        label.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        label.setBounds(364, 60, 273, 93);
        add(label);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblUsername.setBounds(250, 166, 193, 52);
        add(lblUsername);

        JTextField usernameTf = new JTextField();
        usernameTf.setFont(new Font("Tahoma", Font.PLAIN, 32));
        usernameTf.setBounds(481, 170, 281, 68);
        add(usernameTf);
        usernameTf.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBackground(Color.CYAN);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
        lblPassword.setBounds(250, 286, 193, 52);
        add(lblPassword);

        JPasswordField passwordTf = new JPasswordField();
        passwordTf.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordTf.setBounds(481, 286, 281, 68);
        add(passwordTf);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Tahoma", Font.PLAIN, 26));
        loginBtn.setBounds(545, 392, 162, 73);
        add(loginBtn);
        loginBtn.addActionListener(e -> {

            TokenRequestDto tokenRequestDto = new TokenRequestDto(usernameTf.getText(), new String(passwordTf.getPassword()));
            try {
                MainFrame.getInstance().getUserService().login(tokenRequestDto);
                JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.WARNING_MESSAGE);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Tahoma", Font.PLAIN, 26));
        backBtn.setBounds(250, 387, 193, 83);
        add(backBtn);
        backBtn.addActionListener(e -> MainFrame.getInstance().showStartView());
    }
}
