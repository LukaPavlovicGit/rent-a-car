package raf.rent.a.car.utils;

import lombok.Getter;
import lombok.Setter;
import raf.rent.a.car.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Getter
@Setter
public class MyPanel extends JPanel {

    private JPanel contentPanel;

    public MyPanel(){
        contentPanel = new JPanel();
        contentPanel.setBounds(0, 100, 1110, 700);
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPanel.setLayout(null);
        contentPanel.setBackground(Color.darkGray);
    }

    public void clearContentPanel(){
        contentPanel.removeAll();
    }
}
