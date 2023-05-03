package raf.rent.a.car.view.client;

import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.ClientDto;
import raf.rent.a.car.dto.ManagerDto;
import raf.rent.a.car.utils.MyPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateClientView extends MyPanel {

    public CreateClientView(){
        init();
    }

    public void init(){
        setBounds(458, 319, 500, 400);
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setLayout(null);

        JLabel usernameLbl = new JLabel("Username");
        usernameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        usernameLbl.setBounds(104, 79, 100, 29);
        add(usernameLbl);

        JTextArea usernameTa = new JTextArea();
        usernameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        usernameTa.setBounds(205, 78,166, 37);
        add(usernameTa);

        JLabel passwordLbl = new JLabel("Password");
        passwordLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passwordLbl.setBounds(104, 125, 100, 29);
        add(passwordLbl);

        JTextArea passwordTa = new JTextArea();
        passwordTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passwordTa.setBounds(205, 125,166, 37);
        add(passwordTa);

        JLabel firstnameLbl = new JLabel("Firstname");
        firstnameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        firstnameLbl.setBounds(104, 172, 100, 29);
        add(firstnameLbl);

        JTextArea firstnameTa = new JTextArea();
        firstnameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        firstnameTa.setBounds(205, 172,166, 37);
        add(firstnameTa);

        JLabel lastnameLbl = new JLabel("Lastname");
        lastnameLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lastnameLbl.setBounds(104, 219, 100, 29);
        add(lastnameLbl);

        JTextArea lastnameTa = new JTextArea();
        lastnameTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lastnameTa.setBounds(205, 219,166, 37);
        add(lastnameTa);

        JLabel emailLbl = new JLabel("Email");
        emailLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        emailLbl.setBounds(104, 266, 100, 29);
        add(emailLbl);

        JTextArea emailTa = new JTextArea();
        emailTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        emailTa.setBounds(205, 266,166, 37);
        add(emailTa);

        JLabel phoneLbl = new JLabel("Phone");
        phoneLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        phoneLbl.setBounds(104, 313, 100, 29);
        add(phoneLbl);

        JTextArea phoneTa = new JTextArea();
        phoneTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        phoneTa.setBounds(205, 313,166, 37);
        add(phoneTa);

        JLabel birthLbl = new JLabel("Birthdate");
        birthLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        birthLbl.setBounds(104, 360, 100, 29);
        add(birthLbl);

        JLabel lbl = new JLabel("(yyyy-MM-dd)");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setBounds(104, 387, 80, 16);
        add(lbl);

        JTextArea birthTa = new JTextArea();
        birthTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        birthTa.setBounds(205, 360,166, 37);
        add(birthTa);

        JLabel passportLbl = new JLabel("Passport");
        passportLbl.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passportLbl.setBounds(104, 407, 100, 29);
        add(passportLbl);

        JTextArea passportTa = new JTextArea();
        passportTa.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passportTa.setBounds(205, 407,166, 37);
        add(passportTa);

        JButton createBtn = new JButton("Create client");
        createBtn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        createBtn.setBounds(450, 210,200, 50);
        add(createBtn);
        createBtn.addActionListener(e -> {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date birthDate =  sdf.parse(birthTa.getText());
                java.sql.Date birthDateToSqlDate = new java.sql.Date(birthDate.getTime());
                ClientDto clientDto = new ClientDto(usernameTa.getText(),passwordTa.getText(),firstnameTa.getText(),
                        lastnameTa.getText(),emailTa.getText(),passwordTa.getText(),birthDateToSqlDate,passportTa.getText());
                MainFrame.getInstance().getUserService().createClient(clientDto);
                JOptionPane.showMessageDialog(null, "Client successfully created!", "Success", JOptionPane.WARNING_MESSAGE);
                MainFrame.getInstance().showLoginView();
            } catch (IOException | ParseException ex) {
                JOptionPane.showMessageDialog(null, "Client account not created!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        backBtn.setBounds(700, 210,150, 50);
        add(backBtn);
        backBtn.addActionListener(e -> MainFrame.getInstance().showStartView());
    }
}
