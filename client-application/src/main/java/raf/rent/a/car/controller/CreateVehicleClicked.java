package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.CompanyDto;
import raf.rent.a.car.dto.VehicleDto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class CreateVehicleClicked  implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel lbl = new JLabel("CREATE VEHICLE");
        lbl.setForeground(Color.GREEN);
        lbl.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        lbl.setBounds(104, 60, 500, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);

        JLabel typeLbl = new JLabel("Type");
        typeLbl.setForeground(Color.BLACK);
        typeLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        typeLbl.setBounds(104, 128, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(typeLbl);

        JTextArea typeTa = new JTextArea();
        typeTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        typeTa.setBounds(220, 128,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(typeTa);

        JLabel nameLbl = new JLabel("Name");
        nameLbl.setForeground(Color.BLACK);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLbl.setBounds(104, 175, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(nameLbl);

        JTextArea nameTa = new JTextArea();
        nameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        nameTa.setBounds(220, 175,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(nameTa);

        JLabel priceLbl = new JLabel("Price");
        priceLbl.setForeground(Color.BLACK);
        priceLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        priceLbl.setBounds(104, 222, 150, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(priceLbl);

        JTextArea priceTa = new JTextArea();
        priceTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        priceTa.setBounds(220, 222,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(priceTa);

        JButton create = new JButton("Create");
        create.setForeground(Color.WHITE);
        create.setBackground(Color.BLACK);
        create.setFont(new Font("Segoe UI", Font.BOLD, 20));
        create.setBounds(490, 260,200, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(create);
        create.addActionListener(event -> {
            try {
                VehicleDto vehicleDto = new VehicleDto(typeTa.getText(),nameTa.getText(),Double.valueOf(priceTa.getText()));
                MainFrame.getInstance().getReservationService().createVehicle(vehicleDto);
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

