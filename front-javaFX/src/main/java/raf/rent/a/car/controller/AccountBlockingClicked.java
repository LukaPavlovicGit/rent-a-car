package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AccountBlockingClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel blockLbl = new JLabel("Block user's account:");
        blockLbl.setForeground(Color.RED);
        blockLbl.setFont(new Font("Segoe UI", Font.BOLD, 35));
        blockLbl.setBounds(40, 65, 400, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(blockLbl);

        JLabel userId1 = new JLabel("User id");
        userId1.setForeground(Color.BLACK);
        userId1.setFont(new Font("Segoe UI", Font.BOLD, 20));
        userId1.setBounds(74, 130, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(userId1);

        JTextArea userIdTa1 = new JTextArea();
        userIdTa1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        userIdTa1.setBounds(147, 130,40, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(userIdTa1);

        JButton blockBtn = new JButton("BLOCK");
        blockBtn.setForeground(Color.WHITE);
        blockBtn.setBackground(Color.RED);
        blockBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        blockBtn.setBounds(220, 130,140, 40);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(blockBtn);
        blockBtn.addActionListener(event -> {
            try {
                MainFrame.getInstance().getUserService().banUser(userIdTa1.getText());
                MainFrame.getInstance().clearContentPanelAndRefresh();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "User has not been blocked!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JLabel unblockLbl = new JLabel("Unblock user's account:");
        unblockLbl.setForeground(Color.GREEN);
        unblockLbl.setFont(new Font("Segoe UI", Font.BOLD, 35));
        unblockLbl.setBounds(600, 65, 450, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(unblockLbl);

        JLabel userId2 = new JLabel("User id");
        userId2.setForeground(Color.BLACK);
        userId2.setFont(new Font("Segoe UI", Font.BOLD, 20));
        userId2.setBounds(634, 130, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(userId2);

        JTextArea userIdTa2 = new JTextArea();
        userIdTa2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        userIdTa2.setBounds(707, 130,40, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(userIdTa2);

        JButton unblockBtn = new JButton("UNBLOCK");
        unblockBtn.setForeground(Color.WHITE);
        unblockBtn.setBackground(Color.GREEN);
        unblockBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        unblockBtn.setBounds(780, 130,140, 40);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(unblockBtn);
        unblockBtn.addActionListener(event -> {
            try {
                MainFrame.getInstance().getUserService().removeBanUser(userIdTa2.getText());
                MainFrame.getInstance().clearContentPanelAndRefresh();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "User has not been unblocked!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setBounds(430, 370,140, 40);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);

        MainFrame.getInstance().refresh();
    }
}
