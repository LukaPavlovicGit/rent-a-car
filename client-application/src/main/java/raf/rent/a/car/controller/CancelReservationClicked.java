package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.ReservationDto;
import raf.rent.a.car.dto.ReservationListDto;
import raf.rent.a.car.utils.MyTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class CancelReservationClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();
        ReservationListDto list = null;
        try {
            list = MainFrame.getInstance().getReservationService().getReservationsByClient();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        List<ReservationDto> content = list.getContent();
        Object [][] data = new Object[50][50];
        int k=0;
        for (ReservationDto dto : content)
            data[k++] = new Object[]{dto.getId(), dto.getUserId(), dto.getVehicleId(), dto.getStart(), dto.getEnd(), dto.getTotalPrice()};

        String[] header = {"Id", "User id", "Vehicle id", "Start", "End", "Total price"};
        MyTable table = new MyTable(header, data);
        table.setMyTableHeaderBackground(Color.GREEN);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 100, 1060, 150);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(panel);

        JLabel lbl = new JLabel("MY RESERVATIONS");
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lbl.setBounds(300, 20, 600, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);

        JLabel resId = new JLabel("Cancel reservation with id:");
        resId.setForeground(Color.BLACK);
        resId.setFont(new Font("Segoe UI", Font.BOLD, 20));
        resId.setBounds(50, 320, 350, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(resId);

        JTextArea resIdTa = new JTextArea();
        resIdTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        resIdTa.setBounds(320, 320,40, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(resIdTa);

        JButton cancelBtn = new JButton("CANCEL");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.RED);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelBtn.setBounds(520, 320,180, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(cancelBtn);
        cancelBtn.addActionListener(event -> {
            try {
                MainFrame.getInstance().getReservationService().deleteReservation(resIdTa.getText());
                MainFrame.getInstance().clearContentPanelAndRefresh();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(770, 320,180, 50);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(Color.BLACK);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);

        MainFrame.getInstance().refresh();
    }
}
