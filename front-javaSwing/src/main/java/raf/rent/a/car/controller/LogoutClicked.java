package raf.rent.a.car.controller;

import raf.rent.a.car.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogoutClicked implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        MainFrame.getInstance().clearContentPanel();
        MainFrame.getInstance().setToken(null);
        MainFrame.getInstance().setActiveUser(null);
        MainFrame.getInstance().showStartView();
    }
}
