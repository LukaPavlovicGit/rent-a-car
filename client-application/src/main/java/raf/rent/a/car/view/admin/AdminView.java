package raf.rent.a.car.view.admin;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.UserDto;
import raf.rent.a.car.dto.UsersListDto;
import raf.rent.a.car.tokenDecoder.TokenDecoder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class AdminView extends JPanel {

    public AdminView(){
        init();
    }

    private void init(){
        setBounds(450, 190, 1014, 597);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel titleLbl = new JLabel("Admin View");
        titleLbl.setBackground(Color.BLACK);
        titleLbl.setForeground(Color.RED);
        titleLbl.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        titleLbl.setBounds(400, 20, 590, 103);
        add(titleLbl);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBounds(0, 0, 1010, 26);
        add(menuBar);

        // user service
        JMenu userServiceJMenu = new JMenu("User service");
        userServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(userServiceJMenu);

        JMenu users = new JMenu("Users");
        users.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userServiceJMenu.add(users);

        JMenuItem getUsers = new JMenuItem("Get users");
        users.add(getUsers);
        getUsers.addActionListener(e -> {
            UsersListDto list = null;
            try {
                list = MainFrame.getInstance().getUserService().getUsers();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            List<UserDto> content = list.getContent();
            Object [][] data = new Object[50][50];
            int k=0;
            for (UserDto dto : content)
                data[k++] = new Object[]{dto.getId(), dto.getFirstName(), dto.getLastName(), dto.getRole()};

            String[] header = {"User id", "First name", "Last name", "Role"};
            JTable table = new JTable(data, header);
            JPanel panel = new JPanel(new BorderLayout());
            panel.setBounds(0, 150, 1110, 300);
            panel.add(table.getTableHeader(), BorderLayout.NORTH);
            panel.add(table, BorderLayout.CENTER);
            add(panel);
            MainFrame.getInstance().refresh(this);

        });

        JMenu ranks = new JMenu("Ranks");
        ranks.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userServiceJMenu.add(ranks);

        // reservation service
        JMenu reservationServiceJMenu = new JMenu("Booking service");
        reservationServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(reservationServiceJMenu);

        JMenu companies = new JMenu("Companies");
        companies.setFont(new Font("Segoe UI", Font.BOLD, 16));
        reservationServiceJMenu.add(companies);

        JMenu bookings = new JMenu("Bookings");
        bookings.setFont(new Font("Segoe UI", Font.BOLD, 16));
        reservationServiceJMenu.add(bookings);

        JMenu vehicles = new JMenu("Vehicles");
        vehicles.setFont(new Font("Segoe UI", Font.BOLD, 16));
        reservationServiceJMenu.add(vehicles);

        JMenu reviews = new JMenu("Reviews");
        reviews.setFont(new Font("Segoe UI", Font.BOLD, 16));
        reservationServiceJMenu.add(reviews);

        // notification service
        JMenu notificationServiceJMenu = new JMenu("Notification service");
        notificationServiceJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(notificationServiceJMenu);

        JMenu emailPatters = new JMenu("Emails");
        emailPatters.setFont(new Font("Segoe UI", Font.BOLD, 16));
        notificationServiceJMenu.add(emailPatters);

        JMenu sentEmails = new JMenu("Sent emails");
        sentEmails.setFont(new Font("Segoe UI", Font.BOLD, 16));
        notificationServiceJMenu.add(sentEmails);

        //************

        JButton logoutBnt = new JButton("Logout");
        logoutBnt.setFont(new Font("Tahoma", Font.PLAIN, 16));
        logoutBnt.setBounds(450, 500, 97, 25);
        add(logoutBnt);
        logoutBnt.addActionListener(e -> {
            MainFrame.getInstance().setToken(null);
            MainFrame.getInstance().setActiveUser(null);
            MainFrame.getInstance().showStartView();
        });

    }

}
