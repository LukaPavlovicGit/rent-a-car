package raf.rent.a.car;

import raf.rent.a.car.dto.UserDto;
import raf.rent.a.car.rest.NotificationService;
import raf.rent.a.car.rest.ReservationService;
import raf.rent.a.car.rest.UserService;
import raf.rent.a.car.tokenDecoder.TokenDecoder;
import raf.rent.a.car.utils.MyPanel;
import raf.rent.a.car.view.LoginView;
import raf.rent.a.car.view.StartView;
import raf.rent.a.car.view.admin.AdminView;
import raf.rent.a.car.view.client.CreateClientView;
import raf.rent.a.car.view.manager.CreateManagerView;

import javax.swing.*;

public class MainFrame extends JFrame {
    private static MainFrame instance = null;
    private String token;
    private UserDto activeUser;
    private String str = null;
    private TokenDecoder tokenDecoder;

    private UserService userService;
    private ReservationService reservationService;
    private NotificationService notificationService;

    private MyPanel currentPanel = null;
    private MyPanel startView;
    private MyPanel createManagerView;
    private MyPanel createClientView;
    private MyPanel loginView;
    private MyPanel adminView;

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public MainFrame(){
        setTitle("Rent A Car Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1074, 597);
        setResizable(false);

        tokenDecoder = new TokenDecoder();
        userService = new UserService();
        reservationService = new ReservationService();
        notificationService = new NotificationService();

        startView = new StartView();
        createManagerView = new CreateManagerView();
        createClientView = new CreateClientView();
        loginView = new LoginView();
        adminView = new AdminView();

        showStartView();
    }

    public void showStartView(){
        currentPanel = startView;
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(startView);
        this.getContentPane().setVisible(true);
    }

    public void showCreateManagerView(){
        currentPanel = createManagerView;
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(createManagerView);
        this.getContentPane().setVisible(true);
    }

    public void showCreateClientView(){
        currentPanel = createClientView;
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(createClientView);
        this.getContentPane().setVisible(true);
    }

    public void showLoginView(){
        currentPanel = loginView;
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(loginView);
        this.getContentPane().setVisible(true);
    }

    public void showAdminView(){
        currentPanel = adminView;
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(adminView);
        this.getContentPane().setVisible(true);
    }

    public void refresh(){
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(currentPanel);
        this.getContentPane().setVisible(true);
    }
    public void clearContentPanel(){
        currentPanel.clearContentPanel();
    }
    public void clearContentPanelAndRefresh(){
        currentPanel.clearContentPanel();
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(currentPanel);
        this.getContentPane().setVisible(true);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(UserDto activeUser) {
        this.activeUser = activeUser;
    }

    public UserService getUserService() {
        return userService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public NotificationService getNotificationService() {
        return notificationService;
    }

    public MyPanel getCurrentPanel() {
        return currentPanel;
    }
}
