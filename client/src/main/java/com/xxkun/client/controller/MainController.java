package com.xxkun.client.controller;

import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.common.ServerInfo;
import com.xxkun.client.connection.ServerConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.InetSocketAddress;

public class MainController {
    @FXML
    private TextField loginText;
    @FXML
    private Button loginButton;

    @FXML
    private TextField punchTest;
    @FXML
    private Button punchButton;

    @FXML
    private TextField heartbeatText;
    @FXML
    private Button heartbeatButton;

    @FXML
    private TextField logoutText;
    @FXML
    private Button logoutButton;

    public MainController() {
        ServerInfo.SERVER_1.setServerId(-87994);
        ServerInfo.SERVER_1.setSocketAddress(new InetSocketAddress("127.0.0.1", 8874));
        ServerInfo.SERVER_2.setServerId(-87995);
        ServerInfo.SERVER_2.setSocketAddress(new InetSocketAddress("127.0.0.1", 8974));
//        ServerInfo.SERVER_2.setSocketAddress(new InetSocketAddress("127.0.0.1", 8931));
    }

    public void login(ActionEvent actionEvent) {
    }

    public void punch(ActionEvent actionEvent) {

    }

    public void heartbeat(ActionEvent actionEvent) {
        PersonalInfo.INSTANCE.setToken(heartbeatText.getCharacters().toString());
        ServerConnection.INSTANCE.connect();
    }

    public void logout(ActionEvent actionEvent) {
    }
}
