package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.CompanyDto;
import raf.rent.a.car.dto.UpdateAdminDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateCompanyClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel lbl = new JLabel("CREATE COMPANY");
        lbl.setForeground(Color.GREEN);
        lbl.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lbl.setBounds(104, 60, 500, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);

        JLabel nameLbl = new JLabel("Name");
        nameLbl.setForeground(Color.BLACK);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLbl.setBounds(104, 128, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(nameLbl);

        JTextArea nameTa = new JTextArea();
        nameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        nameTa.setBounds(220, 128,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(nameTa);

        JLabel cityLbl = new JLabel("City");
        cityLbl.setForeground(Color.BLACK);
        cityLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cityLbl.setBounds(104, 175, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(cityLbl);

        JTextArea cityTa = new JTextArea();
        cityTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        cityTa.setBounds(220, 175,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(cityTa);

        JLabel descriptionLbl = new JLabel("Description");
        descriptionLbl.setForeground(Color.BLACK);
        descriptionLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        descriptionLbl.setBounds(104, 222, 150, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(descriptionLbl);

        JTextArea descriptionTa = new JTextArea();
        descriptionTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        descriptionTa.setBounds(220, 222,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(descriptionTa);

        JButton create = new JButton("Create");
        create.setForeground(Color.WHITE);
        create.setBackground(Color.BLACK);
        create.setFont(new Font("Segoe UI", Font.BOLD, 20));
        create.setBounds(490, 260,200, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(create);
        create.addActionListener(event -> {
            try {
                CompanyDto companyDto = new CompanyDto(nameTa.getText(),cityTa.getText(),descriptionTa.getText());
                MainFrame.getInstance().getReservationService().createCompany(companyDto);
                MainFrame.getInstance().clearContentPanelAndRefresh();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton backBtn = new JButton("Cancel");
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(Color.BLACK);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setBounds(740, 260,150, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());

        MainFrame.getInstance().refresh();

    }
}
