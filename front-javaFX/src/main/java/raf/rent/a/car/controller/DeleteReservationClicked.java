package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class DeleteReservationClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel lbl = new JLabel("RESERVATION DELETE");
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lbl.setBounds(310, 65, 600, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);

        JLabel reservationId = new JLabel("Reservation id");
        reservationId.setForeground(Color.BLACK);
        reservationId.setFont(new Font("Segoe UI", Font.BOLD, 20));
        reservationId.setBounds(340, 130, 350, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(reservationId);

        JTextArea reservationIdTa = new JTextArea();
        reservationIdTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        reservationIdTa.setBounds(495, 130,40, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(reservationIdTa);

        JButton blockBtn = new JButton("DELETE");
        blockBtn.setForeground(Color.WHITE);
        blockBtn.setBackground(Color.RED);
        blockBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        blockBtn.setBounds(570, 130,140, 40);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(blockBtn);
        blockBtn.addActionListener(event -> {
            try {
                MainFrame.getInstance().getReservationService().deleteReservation(reservationIdTa.getText());
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
