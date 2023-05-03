package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeleteCompanyClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel lbl = new JLabel("COMPANY DELETE");
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lbl.setBounds(360, 65, 600, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);


        JButton deleteBtn = new JButton("DELETE");
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        deleteBtn.setBounds(570, 130,140, 40);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(deleteBtn);
        deleteBtn.addActionListener(event -> {
            try {
                MainFrame.getInstance().getReservationService().deleteCompany();
                MainFrame.getInstance().clearContentPanelAndRefresh();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setBounds(450, 330,140, 40);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);

        MainFrame.getInstance().refresh();
    }
}
