package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.*;
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

public class AvailableVehiclesClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel title = new JLabel("CRITERIA");
        title.setForeground(Color.white);
        title.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        title.setBounds(80, 30, 500, 93);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(title);

        JLabel optionalLbl = new JLabel("(optional)");
        optionalLbl.setForeground(Color.white);
        optionalLbl.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        optionalLbl.setBounds(180, 70, 200, 93);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(optionalLbl);

        JLabel companyNameLbl = new JLabel("Company name (optional)");
        companyNameLbl.setForeground(Color.BLACK);
        companyNameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        companyNameLbl.setBounds(50, 180, 250, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(companyNameLbl);

        JTextArea companyNameTa = new JTextArea();
        companyNameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        companyNameTa.setBounds(300, 180,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(companyNameTa);

        JLabel cityLbl = new JLabel("City (optional)");
        cityLbl.setForeground(Color.BLACK);
        cityLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cityLbl.setBounds(50, 230, 180, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(cityLbl);

        JTextArea cityTa = new JTextArea();
        cityTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        cityTa.setBounds(300, 230,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(cityTa);

        JLabel startLbl = new JLabel("Start date");
        startLbl.setForeground(Color.BLACK);
        startLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        startLbl.setBounds(50, 280, 150, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(startLbl);

        JLabel lbl1 = new JLabel("(yyyy-MM-dd)");
        lbl1.setForeground(Color.BLACK);
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl1.setBounds(50, 307, 95, 16);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl1);

        JTextArea starTa = new JTextArea();
        starTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        starTa.setBounds(300, 280,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(starTa);

        JLabel endLbl = new JLabel("End date");
        endLbl.setForeground(Color.BLACK);
        endLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        endLbl.setBounds(50, 330, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(endLbl);

        JLabel lbl2 = new JLabel("(yyyy-MM-dd)");
        lbl2.setForeground(Color.BLACK);
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl2.setBounds(50, 357, 95, 16);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl2);

        JTextArea endTa = new JTextArea();
        endTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        endTa.setBounds(300, 330,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(endTa);

        JButton fetchBtn = new JButton("Search");
        fetchBtn.setForeground(Color.WHITE);
        fetchBtn.setBackground(Color.BLACK);
        fetchBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        fetchBtn.setBounds(570, 230,180, 50);
        fetchBtn.addActionListener(event -> {
            AvailableVehiclesFilterDto filterDto = new AvailableVehiclesFilterDto();
            java.sql.Date startToSqlDate;
            java.sql.Date endToSqlDate;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate =  sdf.parse(starTa.getText());
                Date endDate =  sdf.parse(endTa.getText());
                startToSqlDate = new java.sql.Date(startDate.getTime());
                endToSqlDate = new java.sql.Date(endDate.getTime());

                filterDto.setStart(startToSqlDate);
                filterDto.setEnd(endToSqlDate);

            } catch ( ParseException ex) {
                throw new RuntimeException();
            }
            String companyName = companyNameTa.getText();
            String city = cityTa.getText();

            if(companyName != null && !companyName.isEmpty())
                filterDto.setCompanyName(companyName);
            if(city != null && !city.isEmpty())
                filterDto.setCity(city);

            MainFrame.getInstance().clearContentPanel();
            VehiclesListDto list = null;
            try {
                list = MainFrame.getInstance().getReservationService().getAvailableVehicles(filterDto);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
            List<VehicleDto> content = list.getContent();
            Object [][] data = new Object[50][50];
            int k=0;
            for (VehicleDto dto : content)
                data[k++] = new Object[]{dto.getId(), dto.getType(), dto.getName(), dto.getPrice()};

            String[] header = {"Id", "Type", "Name", "Name", "Price"};
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
            backBtn.addActionListener(ev -> MainFrame.getInstance().clearContentPanelAndRefresh());

            MainFrame.getInstance().getCurrentPanel().getContentPanel().add(panel);
            MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);
            MainFrame.getInstance().refresh();
        });
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(fetchBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(Color.BLACK);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setBounds(800, 230,150, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());

        MainFrame.getInstance().refresh();
    }
}
