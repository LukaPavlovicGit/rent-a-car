package raf.rent.a.car;

import raf.rent.a.car.dto.UserDto;
import raf.rent.a.car.rest.UserService;
import raf.rent.a.car.tokenDecoder.TokenDecoder;
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

    private StartView startView;
    private CreateManagerView createManagerView;
    private CreateClientView createClientView;
    private LoginView loginView;
    private AdminView adminView;

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

        startView = new StartView();
        createManagerView = new CreateManagerView();
        createClientView = new CreateClientView();
        loginView = new LoginView();
        adminView = new AdminView();

        showStartView();
    }

    public void showStartView(){
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(startView);
        this.getContentPane().setVisible(true);
    }

    public void showCreateManagerView(){
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(createManagerView);
        this.getContentPane().setVisible(true);
    }

    public void showCreateClientView(){
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(createClientView);
        this.getContentPane().setVisible(true);
    }

    public void showLoginView(){
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(loginView);
        this.getContentPane().setVisible(true);
    }

    public void showAdminView(){
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(adminView);
        this.getContentPane().setVisible(true);
    }

    public void refresh(JPanel jPanel){
        this.getContentPane().setVisible(false);
        this.getContentPane().removeAll();
        this.getContentPane().add(jPanel);
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

    public void setTokenDecoder(TokenDecoder tokenDecoder) {
        this.tokenDecoder = tokenDecoder;
    }
}
