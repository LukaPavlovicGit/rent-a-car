package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.*;
import raf.rent.a.car.utils.MyTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class UpdateReviewClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();
        GetReviewListDto list = null;
        try {
            list = MainFrame.getInstance().getReservationService().getReviewsByClient();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        List<GetReviewDto> content = list.getContent();
        Object [][] data = new Object[50][50];
        int k=0;
        for (GetReviewDto dto : content)
            data[k++] = new Object[]{dto.getId(), dto.getRate(), dto.getComment(), dto.getCompanyName(), dto.getCity()};

        String[] header = {"id", "Rate", "Comment", "Company name", "City"};
        MyTable table = new MyTable(header, data);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 100, 1060, 150);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(panel);

        JLabel lbl = new JLabel("MY REVIEWS");
        lbl.setForeground(Color.RED);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 40));
        lbl.setBounds(360, 20, 600, 35);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(lbl);

        JLabel reviewId = new JLabel("Review id");
        reviewId.setForeground(Color.BLACK);
        reviewId.setFont(new Font("Segoe UI", Font.BOLD, 20));
        reviewId.setBounds(104, 296, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(reviewId);

        JTextArea reviewIdTa = new JTextArea();
        reviewIdTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        reviewIdTa.setBounds(220, 296,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(reviewIdTa);

        JLabel rateLbl = new JLabel("Rate");
        rateLbl.setForeground(Color.BLACK);
        rateLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        rateLbl.setBounds(104, 343, 100, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(rateLbl);

        JTextArea RateTa = new JTextArea();
        RateTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        RateTa.setBounds(220, 343,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(RateTa);

        JLabel commentLbl = new JLabel("Comment");
        commentLbl.setForeground(Color.BLACK);
        commentLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        commentLbl.setBounds(104, 390, 150, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(commentLbl);

        JTextArea commentTa = new JTextArea();
        commentTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        commentTa.setBounds(220, 390,166, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(commentTa);

        JButton create = new JButton("Update");
        create.setForeground(Color.WHITE);
        create.setBackground(Color.BLACK);
        create.setFont(new Font("Segoe UI", Font.BOLD, 20));
        create.setBounds(490, 343,200, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(create);
        create.addActionListener(event -> {
            try {
                PostReviewDto reviewDto = new PostReviewDto(Integer.valueOf(RateTa.getText()),commentTa.getText());
                MainFrame.getInstance().getReservationService().updateReview(reviewIdTa.getText(), reviewDto);
                MainFrame.getInstance().clearContentPanelAndRefresh();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(Color.BLACK);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        backBtn.setBounds(740, 343,150, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(backBtn);
        backBtn.addActionListener(event -> MainFrame.getInstance().clearContentPanelAndRefresh());

        MainFrame.getInstance().refresh();
    }
}
