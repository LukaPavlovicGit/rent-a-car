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

public class AllSentEmailsClicked implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();

        JLabel title = new JLabel("FILTER CRITERIA");
        title.setForeground(Color.white);
        title.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        title.setBounds(80, 30, 500, 93);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(title);

        JLabel optionalLbl = new JLabel("(optional)");
        optionalLbl.setForeground(Color.white);
        optionalLbl.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        optionalLbl.setBounds(180, 70, 200, 93);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(optionalLbl);

        JLabel byTypeLbl = new JLabel("By type");
        byTypeLbl.setForeground(Color.BLACK);
        byTypeLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        byTypeLbl.setBounds(104, 180, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(byTypeLbl);

        JTextArea byTypeTa = new JTextArea();
        byTypeTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        byTypeTa.setBounds(215, 180,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(byTypeTa);

        JLabel byEmailLbl = new JLabel("By email");
        byEmailLbl.setForeground(Color.BLACK);
        byEmailLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        byEmailLbl.setBounds(104, 230, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(byEmailLbl);

        JTextArea byEmailTa = new JTextArea();
        byEmailTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        byEmailTa.setBounds(215, 230,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(byEmailTa);

        JLabel youngerThanLbl = new JLabel("Younger than");
        youngerThanLbl.setForeground(Color.BLACK);
        youngerThanLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        youngerThanLbl.setBounds(104, 280, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(youngerThanLbl);

        JLabel lbl1 = new JLabel("(yyyy-MM-dd)");
        lbl1.setForeground(Color.BLACK);
        lbl1.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl1.setBounds(107, 307, 95, 16);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl1);

        JTextArea youngerThanTa = new JTextArea();
        youngerThanTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        youngerThanTa.setBounds(215, 280,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(youngerThanTa);

        JLabel olderThanLbl = new JLabel("Older than");
        olderThanLbl.setForeground(Color.BLACK);
        olderThanLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        olderThanLbl.setBounds(104, 330, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(olderThanLbl);

        JLabel lbl2 = new JLabel("(yyyy-MM-dd)");
        lbl2.setForeground(Color.BLACK);
        lbl2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl2.setBounds(107, 357, 95, 16);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl2);

        JTextArea olderThanTa = new JTextArea();
        olderThanTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        olderThanTa.setBounds(215, 330,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(olderThanTa);

        JButton fetchBtn = new JButton("Fetch");
        fetchBtn.setForeground(Color.WHITE);
        fetchBtn.setBackground(Color.BLACK);
        fetchBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        fetchBtn.setBounds(570, 230,180, 50);
        fetchBtn.addActionListener(event -> {
            SentEmailFilterDto filterDto = new SentEmailFilterDto();
            java.sql.Date youngerThanToSqlDate = null;
            java.sql.Date olderThanToSqlDate = null;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date youngerThanDate =  sdf.parse(youngerThanTa.getText());
                Date olderThanDate =  sdf.parse(olderThanTa.getText());
                youngerThanToSqlDate = new java.sql.Date(youngerThanDate.getTime());
                olderThanToSqlDate = new java.sql.Date(olderThanDate.getTime());

                filterDto.setLowerBound(youngerThanToSqlDate);
                filterDto.setUpperBound(olderThanToSqlDate);

            } catch ( ParseException ex) {
                if(youngerThanToSqlDate != null)
                    filterDto.setLowerBound(youngerThanToSqlDate);
                if(olderThanToSqlDate != null)
                    filterDto.setUpperBound(olderThanToSqlDate);
            }
            String type = byTypeTa.getText();
            String email = byEmailTa.getText();

            if(type != null && !type.isEmpty())
                filterDto.setType(type);
            if(email != null && !email.isEmpty())
                filterDto.setEmail(email);

            MainFrame.getInstance().clearContentPanel();
            SentEmailsListDto list = null;
            try {
                list = MainFrame.getInstance().getNotificationService().getSentEmails(filterDto);
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException(ex);
            }
            List<SentEmailDto> content = list.getContent();
            Object [][] data = new Object[50][50];
            int k=0;
            for (SentEmailDto dto : content)
                data[k++] = new Object[]{dto.getType(), dto.getDestinationEmail(), dto.getSubject(), dto.getMessage(), dto.getDateSent()};

            String[] header = {"Type", "Receiver", "Subject", "Content", "Date sent"};
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

        JButton backBtn = new JButton("Cancel");
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(Color.BLACK);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setBounds(800, 230,150, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());

        MainFrame.getInstance().refresh();
    }
}