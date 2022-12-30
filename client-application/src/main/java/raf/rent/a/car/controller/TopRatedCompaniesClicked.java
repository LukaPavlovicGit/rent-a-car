package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.CompaniesListDto;
import raf.rent.a.car.dto.CompanyDto;
import raf.rent.a.car.utils.MyTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class TopRatedCompaniesClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();
        CompaniesListDto list = null;
        try {
            list = MainFrame.getInstance().getReservationService().getTopRatedCompanies();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        List<CompanyDto> content = list.getContent();
        Object [][] data = new Object[50][50];
        int k=0;
        for (CompanyDto dto : content)
            data[k++] = new Object[]{dto.getId(), dto.getManagerId(), dto.getName(), dto.getCity()};

        String[] header = {"id", "Manager id", "Name", "City"};
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
