package raf.rent.a.car.view.admin;

import raf.rent.a.car.controller.*;
import raf.rent.a.car.utils.MyPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class AdminView extends MyPanel {

    public AdminView(){
        init();
    }

    private void init(){
        this.setBounds(450, 190, 1014, 597);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBackground(Color.lightGray);
        this.add(getContentPanel());

        JLabel titleLbl = new JLabel("Admin View");
        titleLbl.setBackground(Color.BLACK);
        titleLbl.setForeground(Color.RED);
        titleLbl.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        titleLbl.setBounds(400, 20, 590, 103);
        this.add(titleLbl);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 1010, 26);
        this.add(menuBar);

        // user service
        JMenu userServiceJMenu = new JMenu("User service");
        userServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(userServiceJMenu);

        JMenu users = new JMenu("Users");
        users.setFont(new Font("Segoe UI", Font.BOLD, 15));
        userServiceJMenu.add(users);

        JMenuItem allUsers = new JMenuItem("All users");
        users.add(allUsers);
        allUsers.addActionListener(new AllUsersClicked());

        JMenuItem accountBlocking = new JMenuItem("Account blocking");
        users.add(accountBlocking);
        accountBlocking.addActionListener(new AccountBlockingClicked());

        JMenu ranks = new JMenu("Ranks");
        ranks.setFont(new Font("Segoe UI", Font.BOLD, 15));
        userServiceJMenu.add(ranks);

        // reservation service
        JMenu reservationServiceJMenu = new JMenu("Booking service");
        reservationServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(reservationServiceJMenu);

        JMenuItem allCompanies = new JMenuItem("Companies");
        allCompanies.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(allCompanies);
        allCompanies.addActionListener(new AllCompaniesClicked());


        JMenuItem allReservations = new JMenuItem("Reservations");
        allReservations.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(allReservations);
        allReservations.addActionListener(new AllReservationsClicked());


        JMenuItem allReviews = new JMenuItem("Reviews");
        allReviews.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(allReviews);
        allReviews.addActionListener(new AllReviewsClicked());

        JMenuItem vehicles = new JMenuItem("Vehicles");
        vehicles.setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationServiceJMenu.add(vehicles);
        vehicles.addActionListener(new AllVehiclesClicked());

        // notification service
        JMenu notificationServiceJMenu = new JMenu("Notification service");
        notificationServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(notificationServiceJMenu);

        JMenuItem emailPatters = new JMenuItem("Emails");
        emailPatters.setFont(new Font("Segoe UI", Font.BOLD, 15));
        notificationServiceJMenu.add(emailPatters);

        JMenuItem sentEmails = new JMenuItem("Sent emails");
        sentEmails.setFont(new Font("Segoe UI", Font.BOLD, 15));
        notificationServiceJMenu.add(sentEmails);
        sentEmails.addActionListener(new AllSentEmailsClicked());

        // my account

        JMenu myAccountJMenu = new JMenu("My account");
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

        //************

    }

}
