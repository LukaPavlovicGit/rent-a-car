package raf.rent.a.car.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyTable extends JTable {

    public MyTable(String[] header, Object[][] data) {

        setModel(new DefaultTableModel(data, header){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        });
        getTableHeader().setForeground(Color.WHITE);
        getTableHeader().setBackground(Color.GREEN);
        getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        setForeground(Color.BLACK);
        setBackground(Color.DARK_GRAY);
        setFont(new Font("Segoe UI", Font.BOLD, 17));
    }

    public void setMyTableHeaderBackground(Color color){
        getTableHeader().setBackground(color);
    }
}
