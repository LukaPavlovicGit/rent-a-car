package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.CompaniesListDto;
import raf.rent.a.car.dto.CompanyDto;
import raf.rent.a.car.dto.PostReviewDto;
import raf.rent.a.car.dto.ReservationDto;
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

public class CreateReviewClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();
        CompaniesListDto list = null;
        try {
            list = MainFrame.getInstance().getReservationService().getCompanies();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        List<CompanyDto> content = list.getContent();
        Object [][] data = new Object[50][50];
        int k=0;
        for (CompanyDto dto : content)
            data[k++] = new Object[]{dto.getId(), dto.getName(), dto.getCity()};

        String[] header = {"id", "Name", "City"};
        MyTable table = new MyTable(header, data);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 100, 1060, 150);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(panel);

        JLabel lbl = new JLabel("ALL COMPANIES");
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lbl.setBounds(325, 20, 600, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);

        JLabel companyIdLbl = new JLabel("Company id");
        companyIdLbl.setForeground(Color.BLACK);
        companyIdLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        companyIdLbl.setBounds(104, 270, 150, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(companyIdLbl);

        JTextArea companyIdTa = new JTextArea();
        companyIdTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        companyIdTa.setBounds(250, 270,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(companyIdTa);

        JLabel rateLbl = new JLabel("Rate");
        rateLbl.setForeground(Color.BLACK);
        rateLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        rateLbl.setBounds(104, 320, 150, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(rateLbl);

        JTextArea rateTa = new JTextArea();
        rateTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        rateTa.setBounds(250, 320,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(rateTa);

        JLabel commentLbl = new JLabel("Comment");
        commentLbl.setForeground(Color.BLACK);
        commentLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        commentLbl.setBounds(104, 370, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(commentLbl);

        JTextArea commentTa = new JTextArea();
        commentTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        commentTa.setBounds(250, 370,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(commentTa);

        JButton createBtn = new JButton("Create");
        createBtn.setBounds(510, 320,180, 50);
        createBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        createBtn.setForeground(Color.GREEN);
        createBtn.setBackground(Color.BLACK);
        createBtn.addActionListener(event -> {
            try {

                PostReviewDto postReviewDto = new PostReviewDto(Long.valueOf(companyIdTa.getText()), Integer.valueOf(rateTa.getText()), commentTa.getText());
                MainFrame.getInstance().getReservationService().createReview(postReviewDto);

                companyIdTa.setText("");
                rateTa.setText("");
                commentTa.setText("");

            } catch (IOException ex) {
                throw new RuntimeException();
            }
        });
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(createBtn);

        JButton backBtn = new JButton("Back");
        backBtn.setBounds(730, 320,180, 50);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(Color.BLACK);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);

        MainFrame.getInstance().refresh();
    }
}
