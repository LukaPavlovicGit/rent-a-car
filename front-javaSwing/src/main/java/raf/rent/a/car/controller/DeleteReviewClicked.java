package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.GetReviewDto;
import raf.rent.a.car.dto.GetReviewListDto;
import raf.rent.a.car.utils.MyTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class DeleteReviewClicked implements ActionListener {
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

        JLabel reviewId = new JLabel("Delete reviews with id:");
        reviewId.setForeground(Color.BLACK);
        reviewId.setFont(new Font("Segoe UI", Font.BOLD, 20));
        reviewId.setBounds(50, 320, 350, 29);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(reviewId);

        JTextArea reviewIdTa = new JTextArea();
        reviewIdTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        reviewIdTa.setBounds(320, 320,40, 37);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(reviewIdTa);

        JButton deleteBtn = new JButton("DELETE");
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        deleteBtn.setBounds(520, 320,180, 50);
        MainFrame.getInstance().getCurrentPanel().getContentPanel().add(deleteBtn);
        deleteBtn.addActionListener(event -> {
            try {
                MainFrame.getInstance().getReservationService().deleteReview(reviewIdTa.getText());
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
