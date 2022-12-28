package raf.rent.a.car.view.manager;

import raf.rent.a.car.controller.*;
import raf.rent.a.car.utils.MyPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ManagerView extends MyPanel {

    public ManagerView() {
        init();
    }
    private void init(){
        this.setBounds(450, 190, 1014, 597);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.add(getContentPanel());
        setContentPanelBackground(Color.lightGray);

        JLabel titleLbl = new JLabel("Manager View");
        titleLbl.setBackground(Color.BLACK);
        titleLbl.setForeground(Color.yellow);
        titleLbl.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        titleLbl.setBounds(370, 20, 590, 103);
        this.add(titleLbl);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.DARK_GRAY);
        menuBar.setBounds(0, 0, 1010, 26);
        this.add(menuBar);

        // reservation service
            // company
        JMenu reservationServiceJMenu = new JMenu("Reservation service");
        reservationServiceJMenu.setForeground(Color.WHITE);
        reservationServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(reservationServiceJMenu);

        JMenuItem createCompany = new JMenuItem("Create company");
        createCompany.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(createCompany);
        createCompany.addActionListener(new CreateCompanyClicked());

        JMenuItem updateCompany = new JMenuItem("Update company");
        updateCompany.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(updateCompany);
        updateCompany.addActionListener(new UpdateCompanyClicked());

        JMenuItem deleteCompany = new JMenuItem("Delete company");
        deleteCompany.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(deleteCompany);
        deleteCompany.addActionListener(new UpdateCompanyClicked());
            // reservation
        JMenuItem reservationByCompany = new JMenuItem("Reservations");
        reservationByCompany.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(reservationByCompany);
        reservationByCompany.addActionListener(new ReservationsByCompanyClicked());

        JMenuItem deleteReservation = new JMenuItem("Delete reservation");
        deleteReservation.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(deleteReservation);
        deleteReservation.addActionListener(new DeleteReservationClicked());

    }
}
