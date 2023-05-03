package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.ManagerDto;
import raf.rent.a.car.dto.UpdateAdminDto;
import raf.rent.a.car.dto.UpdateManagerDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ManagerProfileUpdateClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel usernameLbl = new JLabel("Username");
        usernameLbl.setForeground(Color.BLACK);
        usernameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        usernameLbl.setBounds(104, 78, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(usernameLbl);

        JTextArea usernameTa = new JTextArea();
        usernameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        usernameTa.setBounds(205, 78,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(usernameTa);

        JLabel firstnameLbl = new JLabel("Firstname");
        firstnameLbl.setForeground(Color.BLACK);
        firstnameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        firstnameLbl.setBounds(104, 125, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(firstnameLbl);

        JTextArea firstnameTa = new JTextArea();
        firstnameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        firstnameTa.setBounds(205, 125,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(firstnameTa);

        JLabel lastnameLbl = new JLabel("Lastname");
        lastnameLbl.setForeground(Color.BLACK);
        lastnameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lastnameLbl.setBounds(104, 172, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lastnameLbl);

        JTextArea lastnameTa = new JTextArea();
        lastnameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lastnameTa.setBounds(205, 172,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lastnameTa);

        JLabel phoneLbl = new JLabel("Phone");
        phoneLbl.setForeground(Color.BLACK);
        phoneLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        phoneLbl.setBounds(104, 219, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(phoneLbl);

        JTextArea phoneTa = new JTextArea();//yyyy-MM-dd
        phoneTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        phoneTa.setBounds(205, 219,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(phoneTa);

        JLabel birthLbl = new JLabel("Birthdate");
        birthLbl.setForeground(Color.BLACK);
        birthLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        birthLbl.setBounds(104, 266, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(birthLbl);

        JLabel lbl = new JLabel("(yyyy-MM-dd)");
        lbl.setForeground(Color.BLACK);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setBounds(107, 293, 95, 16);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);

        JTextArea birthTa = new JTextArea();
        birthTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        birthTa.setBounds(205, 266,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(birthTa);

        JButton updateAdmin = new JButton("Update");
        updateAdmin.setForeground(Color.WHITE);
        updateAdmin.setBackground(Color.BLACK);
        updateAdmin.setFont(new Font("Segoe UI", Font.BOLD, 20));
        updateAdmin.setBounds(450, 210,200, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(updateAdmin);
        updateAdmin.addActionListener(event -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate =  sdf.parse(birthTa.getText());
                java.sql.Date birthDateToSqlDate = new java.sql.Date(birthDate.getTime());
                UpdateManagerDto updateManagerDto = new UpdateManagerDto(usernameTa.getText(),firstnameTa.getText(),
                        lastnameTa.getText(),phoneTa.getText(),birthDateToSqlDate);
                MainFrame.getInstance().getUserService().updateManager(updateManagerDto);
                JOptionPane.showMessageDialog(null, "Admin successfully updated!", "Success", JOptionPane.WARNING_MESSAGE);
                MainFrame.getInstance().clearContentPanelAndRefresh();
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(null, "Account has not been updated!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelBtn.setBounds(700, 210,150, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(cancelBtn);
        cancelBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());

        MainFrame.getInstance().refresh();
    }
}
