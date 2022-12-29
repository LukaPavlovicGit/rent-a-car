package raf.rent.a.car.view.client;

import raf.rent.a.car.controller.*;
import raf.rent.a.car.utils.MyPanel;
import raf.rent.a.car.utils.MyTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ClientView extends MyPanel {

    public ClientView(){
        init();
    }

    private void init(){
        this.setBounds(450, 190, 1014, 597);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBackground(Color.GREEN);
        this.add(getContentPanel());
        setContentPanelBackground(Color.lightGray);

        JLabel titleLbl = new JLabel("Client View");
        titleLbl.setForeground(Color.BLACK);
        titleLbl.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        titleLbl.setBounds(370, 20, 590, 103);
        this.add(titleLbl);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.white);
        menuBar.setBounds(0, 0, 1010, 26);
        this.add(menuBar);

        // reservation service

        JMenu reservationServiceJMenu = new JMenu("Reservation service");
        reservationServiceJMenu.setForeground(Color.BLACK);
        reservationServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(reservationServiceJMenu);

        // notification service
        JMenu notificationServiceJMenu = new JMenu("Notification service");
        notificationServiceJMenu.setForeground(Color.BLACK);
        notificationServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(notificationServiceJMenu);

        JMenuItem myNotifications = new JMenuItem("My notifications");
        myNotifications.setFont(new Font("Segoe UI", Font.BOLD, 15));
        notificationServiceJMenu.add(myNotifications);
        myNotifications.addActionListener(new AllSentEmailsByEmailClicked());

        // my account

        JMenu myAccountJMenu = new JMenu("My account");
        myAccountJMenu.setForeground(Color.BLACK);
        myAccountJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(myAccountJMenu);

        JMenuItem logout = new JMenuItem("Logout");
        logout.setFont(new Font("Segoe UI", Font.BOLD, 15));
        myAccountJMenu.add(logout);
        logout.addActionListener(new LogoutClicked());

        JMenuItem profileUpdate = new JMenuItem("Profile update");
        profileUpdate.setFont(new Font("Segoe UI", Font.BOLD, 15));
        myAccountJMenu.add(profileUpdate);
        profileUpdate.addActionListener(new AdminProfileUpdateClicked());

        JMenuItem changePassword = new JMenuItem("Password change");
        changePassword.setFont(new Font("Segoe UI", Font.BOLD, 15));
        myAccountJMenu.add(changePassword);
        changePassword.addActionListener(new PasswordChangeClicked());

        JMenuItem deleteAccount = new JMenuItem("Delete account");
        deleteAccount.setFont(new Font("Segoe UI", Font.BOLD, 15));
        myAccountJMenu.add(deleteAccount);
        deleteAccount.addActionListener(new DeleteAccountClicked());
    }
}
