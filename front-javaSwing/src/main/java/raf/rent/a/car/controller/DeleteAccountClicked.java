package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.CredentialsDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeleteAccountClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel usernameLbl = new JLabel("Username");
        usernameLbl.setForeground(Color.BLACK);
        usernameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        usernameLbl.setBounds(104, 78, 166, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(usernameLbl);

        JTextArea usernameTa = new JTextArea();
        usernameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        usernameTa.setBounds(260, 78,210, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(usernameTa);

        JLabel passwordLbl = new JLabel("Password");
        passwordLbl.setForeground(Color.BLACK);
        passwordLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        passwordLbl.setBounds(104, 125, 166, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(passwordLbl);

        JLabel lbl = new JLabel("DANGER");
        lbl.setForeground(Color.red);
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 100));
        lbl.setBounds(540, 125,500, 70);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);

        JTextArea passwordTa = new JTextArea();
        passwordTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passwordTa.setBounds(260, 125,210, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(passwordTa);

        JButton deleteBtn = new JButton("DELETE");
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        deleteBtn.setBounds(570, 350,180, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(deleteBtn);
        deleteBtn.addActionListener(event -> {
            try {
                CredentialsDto credentialsDto = new CredentialsDto(usernameTa.getText(),passwordTa.getText());
                MainFrame.getInstance().getUserService().deleteAccount(credentialsDto);
                MainFrame.getInstance().clearContentPanel();
                MainFrame.getInstance().showStartView();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Account has not been deleted!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelBtn.setBounds(800, 350,150, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(cancelBtn);
        cancelBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());

        MainFrame.getInstance().refresh();
    }
}
