package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.PasswordDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class PasswordChangeClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel oldPasswordLbl = new JLabel("Old password");
        oldPasswordLbl.setForeground(Color.BLACK);
        oldPasswordLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        oldPasswordLbl.setBounds(104, 78, 166, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(oldPasswordLbl);

        JTextArea oldPasswordTa = new JTextArea();
        oldPasswordTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        oldPasswordTa.setBounds(260, 78,210, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(oldPasswordTa);

        JLabel newPasswordLbl = new JLabel("New password");
        newPasswordLbl.setForeground(Color.BLACK);
        newPasswordLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        newPasswordLbl.setBounds(104, 125, 166, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(newPasswordLbl);

        JTextArea newPasswordLTa = new JTextArea();
        newPasswordLTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        newPasswordLTa.setBounds(260, 125,210, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(newPasswordLTa);

        JButton updatePassword = new JButton("Update");
        updatePassword.setForeground(Color.WHITE);
        updatePassword.setBackground(Color.BLACK);
        updatePassword.setFont(new Font("Segoe UI", Font.BOLD, 20));
        updatePassword.setBounds(570, 210,180, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(updatePassword);
        updatePassword.addActionListener(event -> {
            try {
                PasswordDto passwordDto = new PasswordDto(oldPasswordTa.getText(),newPasswordLTa.getText());
                MainFrame.getInstance().getUserService().passwordChange(passwordDto);
                JOptionPane.showMessageDialog(null, "Password successfully changed!", "Success", JOptionPane.WARNING_MESSAGE);
                MainFrame.getInstance().clearContentPanelAndRefresh();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Password has not been updated!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelBtn.setBounds(800, 210,150, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(cancelBtn);
        cancelBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());

        MainFrame.getInstance().refresh();
    }
}
