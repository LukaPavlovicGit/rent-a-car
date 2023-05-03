package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.ReservationDto;
import raf.rent.a.car.dto.VehicleDto;
import raf.rent.a.car.dto.VehiclesListDto;
import raf.rent.a.car.utils.MyTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookVehicleClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();
        VehiclesListDto list;
        try {
            list = MainFrame.getInstance().getReservationService().getVehicles();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        List<VehicleDto> content = list.getContent();
        Object [][] data = new Object[50][50];
        int k=0;
        for (VehicleDto dto : content)
            data[k++] = new Object[]{dto.getId(), dto.getType(), dto.getName(), dto.getPrice()};

        String[] header = {"Id", "Type", "Name", "Price"};
        MyTable table = new MyTable(header, data);
        table.setMyTableHeaderBackground(Color.lightGray);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 0, 1060, 230);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);

        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(panel);
        //***********************************

        JLabel vehicleIdLbl = new JLabel("Vehicle id");
        vehicleIdLbl.setForeground(Color.BLACK);
        vehicleIdLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        vehicleIdLbl.setBounds(104, 250, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(vehicleIdLbl);

        JTextArea vehicleIdTa = new JTextArea();
        vehicleIdTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        vehicleIdTa.setBounds(250, 250,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(vehicleIdTa);

        JLabel startDateLbl = new JLabel("Start date");
        startDateLbl.setForeground(Color.BLACK);
        startDateLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        startDateLbl.setBounds(104, 300, 150, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(startDateLbl);

        JLabel lbl1 = new JLabel("(yyyy-MM-dd)");
        lbl1.setForeground(Color.BLACK);
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl1.setBounds(107, 327, 95, 16);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl1);

        JTextArea startDateTa = new JTextArea();
        startDateTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        startDateTa.setBounds(250, 300,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(startDateTa);

        JLabel endDateLbl = new JLabel("End date");
        endDateLbl.setForeground(Color.BLACK);
        endDateLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        endDateLbl.setBounds(104, 350, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(endDateLbl);

        JLabel lbl2 = new JLabel("(yyyy-MM-dd)");
        lbl2.setForeground(Color.BLACK);
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl2.setBounds(107, 377, 95, 16);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl2);

        JTextArea endDateTa = new JTextArea();
        endDateTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        endDateTa.setBounds(250, 350,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(endDateTa);

        JButton bookBtn = new JButton("Book");
        bookBtn.setBounds(510, 340,180, 50);
        bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        bookBtn.setForeground(Color.GREEN);
        bookBtn.setBackground(Color.BLACK);
        bookBtn.addActionListener(event -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date start =  sdf.parse(startDateTa.getText());
                Date end =  sdf.parse(endDateTa.getText());
                java.sql.Date startToSqlDate = new java.sql.Date(start.getTime());
                java.sql.Date endToSqlDate = new java.sql.Date(end.getTime());

                ReservationDto reservationDto = new ReservationDto(Long.valueOf(vehicleIdTa.getText()), startToSqlDate, endToSqlDate);
                MainFrame.getInstance().getReservationService().createReservation(reservationDto);

                vehicleIdTa.setText("");
                startDateTa.setText("");
                endDateTa.setText("");
                 
            } catch ( ParseException | IOException ex) {
                throw new RuntimeException();
            }
        });
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(bookBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(730, 340,180, 50);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(Color.BLACK);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);

        MainFrame.getInstance().refresh();
    }
}
