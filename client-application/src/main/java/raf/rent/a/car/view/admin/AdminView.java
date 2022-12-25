package raf.rent.a.car.view.admin;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.*;
import raf.rent.a.car.tokenDecoder.TokenDecoder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminView extends JPanel {

    private JPanel contentPanel = new JPanel();

    // inputs fields

    public AdminView(){
        init();
    }

    private void init(){
        this.setBounds(450, 190, 1014, 597);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setLayout(null);
        this.setBackground(Color.lightGray);

        //*************
        contentPanel = new JPanel();
        contentPanel.setBounds(0, 100, 1110, 700);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.darkGray);
        this.add(contentPanel);
        //*************

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
        users.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userServiceJMenu.add(users);

        JMenuItem getUsers = new JMenuItem("Get users");
        users.add(getUsers);
        getUsers.addActionListener(e -> getUsersClicked());

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

        // my account

        JMenu myAccountJMenu = new JMenu("My account");
        myAccountJMenu.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuBar.add(myAccountJMenu);

        JMenuItem logout = new JMenuItem("Logout");
        myAccountJMenu.add(logout);
        logout.addActionListener(e -> logoutClicked());

        JMenuItem profileUpdate = new JMenuItem("Profile update");
        myAccountJMenu.add(profileUpdate);
        profileUpdate.addActionListener(e -> profileUpdateClicked());

        JMenuItem changePassword = new JMenuItem("Password change");
        myAccountJMenu.add(changePassword);
        changePassword.addActionListener(e -> passwordChangeClicked());

        JMenuItem deleteAccount = new JMenuItem("Delete account");
        myAccountJMenu.add(deleteAccount);
        deleteAccount.addActionListener(e -> deleteAccountClicked());



        //************

    }

    private void getUsersClicked(){
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

        JTable table = new JTable();
        table.setModel(new DefaultTableModel(data, header){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setBackground(Color.GREEN);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setForeground(Color.BLACK);
        table.setBackground(Color.DARK_GRAY);
        table.setFont(new Font("Segoe UI", Font.BOLD, 17));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBounds(0, 0, 1060, 500);
        panel.add(table.getTableHeader(), BorderLayout.NORTH);
        panel.add(table, BorderLayout.CENTER);
        contentPanel.add(panel);
        MainFrame.getInstance().refresh(this);
    }

    private void profileUpdateClicked(){

        contentPanel.removeAll();

        JLabel usernameLbl = new JLabel("Username");
        usernameLbl.setForeground(Color.BLACK);
        usernameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        usernameLbl.setBounds(104, 78, 100, 29);
        contentPanel.add(usernameLbl);

        JTextArea usernameTa = new JTextArea();
        usernameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        usernameTa.setBounds(205, 78,166, 37);
        contentPanel.add(usernameTa);

        JLabel firstnameLbl = new JLabel("Firstname");
        firstnameLbl.setForeground(Color.BLACK);
        firstnameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        firstnameLbl.setBounds(104, 125, 100, 29);
        contentPanel.add(firstnameLbl);

        JTextArea firstnameTa = new JTextArea();
        firstnameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        firstnameTa.setBounds(205, 125,166, 37);
        contentPanel.add(firstnameTa);

        JLabel lastnameLbl = new JLabel("Lastname");
        lastnameLbl.setForeground(Color.BLACK);
        lastnameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lastnameLbl.setBounds(104, 172, 100, 29);
        contentPanel.add(lastnameLbl);

        JTextArea lastnameTa = new JTextArea();
        lastnameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lastnameTa.setBounds(205, 172,166, 37);
        contentPanel.add(lastnameTa);

        JLabel phoneLbl = new JLabel("Phone");
        phoneLbl.setForeground(Color.BLACK);
        phoneLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        phoneLbl.setBounds(104, 219, 100, 29);
        contentPanel.add(phoneLbl);

        JTextArea phoneTa = new JTextArea();//yyyy-MM-dd
        phoneTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        phoneTa.setBounds(205, 219,166, 37);
        contentPanel.add(phoneTa);

        JLabel birthLbl = new JLabel("Birthdate");
        birthLbl.setForeground(Color.BLACK);
        birthLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        birthLbl.setBounds(104, 266, 100, 29);
        contentPanel.add(birthLbl);

        JLabel lbl = new JLabel("(yyyy-MM-dd)");
        lbl.setForeground(Color.BLACK);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setBounds(107, 293, 95, 16);
        contentPanel.add(lbl);

        JTextArea birthTa = new JTextArea();
        birthTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        birthTa.setBounds(205, 266,166, 37);
        contentPanel.add(birthTa);

        JButton updateAdmin = new JButton("Update");
        updateAdmin.setForeground(Color.WHITE);
        updateAdmin.setBackground(Color.BLACK);
        updateAdmin.setFont(new Font("Segoe UI", Font.BOLD, 20));
        updateAdmin.setBounds(450, 210,200, 50);
        contentPanel.add(updateAdmin);
        updateAdmin.addActionListener(e -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate =  sdf.parse(birthTa.getText());
                java.sql.Date birthDateToSqlDate = new java.sql.Date(birthDate.getTime());
                UpdateAdminDto updateAdminDto = new UpdateAdminDto(usernameTa.getText(),firstnameTa.getText(),
                        lastnameTa.getText(),phoneTa.getText(),birthDateToSqlDate);
                MainFrame.getInstance().getUserService().updateAdmin(updateAdminDto);
                JOptionPane.showMessageDialog(null, "Admin successfully updated!", "Success", JOptionPane.WARNING_MESSAGE);
                contentPanel.removeAll();
                MainFrame.getInstance().refresh(this);
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(null, "Account has not been updated!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelBtn.setBounds(700, 210,150, 50);
        contentPanel.add(cancelBtn);
        cancelBtn.addActionListener(e -> clearContentPanel());

        MainFrame.getInstance().refresh(this);

    }

    private void passwordChangeClicked(){
        contentPanel.removeAll();

        JLabel oldPasswordLbl = new JLabel("Old password");
        oldPasswordLbl.setForeground(Color.BLACK);
        oldPasswordLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        oldPasswordLbl.setBounds(104, 78, 166, 29);
        contentPanel.add(oldPasswordLbl);

        JTextArea oldPasswordTa = new JTextArea();
        oldPasswordTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        oldPasswordTa.setBounds(260, 78,210, 37);
        contentPanel.add(oldPasswordTa);

        JLabel newPasswordLbl = new JLabel("New password");
        newPasswordLbl.setForeground(Color.BLACK);
        newPasswordLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        newPasswordLbl.setBounds(104, 125, 166, 29);
        contentPanel.add(newPasswordLbl);

        JTextArea newPasswordLTa = new JTextArea();
        newPasswordLTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        newPasswordLTa.setBounds(260, 125,210, 37);
        contentPanel.add(newPasswordLTa);

        JButton updatePassword = new JButton("Update");
        updatePassword.setForeground(Color.WHITE);
        updatePassword.setBackground(Color.BLACK);
        updatePassword.setFont(new Font("Segoe UI", Font.BOLD, 20));
        updatePassword.setBounds(570, 210,180, 50);
        contentPanel.add(updatePassword);
        updatePassword.addActionListener(e -> {
            try {
                PasswordDto passwordDto = new PasswordDto(oldPasswordTa.getText(),newPasswordLTa.getText());
                MainFrame.getInstance().getUserService().passwordChange(passwordDto);
                JOptionPane.showMessageDialog(null, "Password successfully changed!", "Success", JOptionPane.WARNING_MESSAGE);
                clearContentPanel();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Password has not been updated!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelBtn.setBounds(800, 210,150, 50);
        contentPanel.add(cancelBtn);
        cancelBtn.addActionListener(e -> clearContentPanel());

        MainFrame.getInstance().refresh(this);
    }

    private void deleteAccountClicked(){

        JLabel usernameLbl = new JLabel("Username");
        usernameLbl.setForeground(Color.BLACK);
        usernameLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        usernameLbl.setBounds(104, 78, 166, 29);
        contentPanel.add(usernameLbl);

        JTextArea usernameTa = new JTextArea();
        usernameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        usernameTa.setBounds(260, 78,210, 37);
        contentPanel.add(usernameTa);

        JLabel passwordLbl = new JLabel("Password");
        passwordLbl.setForeground(Color.BLACK);
        passwordLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        passwordLbl.setBounds(104, 125, 166, 29);
        contentPanel.add(passwordLbl);

        JLabel lbl = new JLabel("DANGER");
        lbl.setForeground(Color.red);
        lbl.setFont(new Font("Times New Roman", Font.BOLD, 100));
        lbl.setBounds(540, 125,500, 70);
        contentPanel.add(lbl);

        JTextArea passwordTa = new JTextArea();
        passwordTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passwordTa.setBounds(260, 125,210, 37);
        contentPanel.add(passwordTa);

        JButton deleteBtn = new JButton("DELETE");
        deleteBtn.setForeground(Color.WHITE);
        deleteBtn.setBackground(Color.RED);
        deleteBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        deleteBtn.setBounds(570, 350,180, 50);
        contentPanel.add(deleteBtn);
        deleteBtn.addActionListener(e -> {
            try {
                CredentialsDto credentialsDto = new CredentialsDto(usernameTa.getText(),passwordTa.getText());
                MainFrame.getInstance().getUserService().deleteAccount(credentialsDto);
                contentPanel.removeAll();
                MainFrame.getInstance().showStartView();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Account has not been deleted!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setForeground(Color.WHITE);
        cancelBtn.setBackground(Color.BLACK);
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        cancelBtn.setBounds(800, 350,150, 50);
        contentPanel.add(cancelBtn);
        cancelBtn.addActionListener(e -> clearContentPanel());

        MainFrame.getInstance().refresh(this);
    }

    private void logoutClicked(){
        contentPanel.removeAll();
        MainFrame.getInstance().setToken(null);
        MainFrame.getInstance().setActiveUser(null);
        MainFrame.getInstance().showStartView();
    }

    private void clearContentPanel(){
        contentPanel.removeAll();
        MainFrame.getInstance().refresh(this);
    }

}
