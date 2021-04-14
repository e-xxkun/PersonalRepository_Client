package com.xxkun.client.controller;

import com.xxkun.client.common.PersonalInfo;
import com.xxkun.client.component.transfer.LocalServer;
import com.xxkun.client.component.transfer.Transfer;
import com.xxkun.client.pojo.request.HeartbeatRequest;
import com.xxkun.client.pojo.request.Request;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.InetSocketAddress;
import java.net.SocketException;

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

    private final Transfer transfer;
    private InetSocketAddress socketAddress;
    private PersonalInfo info;

    public MainController() {
        Transfer transferTmp;
        try {
            transferTmp = LocalServer.getTransfer();
        } catch (SocketException e) {
            transferTmp = null;
            e.printStackTrace();
        }
        transfer = transferTmp;
        socketAddress = new InetSocketAddress("127.0.0.1", 8876);
    }


    public void login(ActionEvent actionEvent) {
    }

    public void punch(ActionEvent actionEvent) {

    }

    public void heartbeat(ActionEvent actionEvent) {
        HeartbeatRequest request = new HeartbeatRequest(socketAddress);
        request.setToken(heartbeatText.getCharacters().toString());
        transfer.send(request);
    }

    public void logout(ActionEvent actionEvent) {
    }
}
