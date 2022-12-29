package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.VehicleDto;
import raf.rent.a.car.dto.VehiclesListDto;
import raf.rent.a.car.utils.MyTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class AllVehiclesByCompanyClicked  implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();
        VehiclesListDto list = null;
        try {
            list = MainFrame.getInstance().getReservationService().getVehiclesByCompany();
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

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 0, 1060, 350);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(700, 370,180, 50);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(Color.BLACK);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());

        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(panel);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);
        MainFrame.getInstance().refresh();
    }
}
