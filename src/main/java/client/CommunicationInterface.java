package client;

import dto.request.Request;
import dto.response.Response;

import server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CommunicationInterface {
    public static final int PORT = 7777;
    public static final String LOCALHOST = "localhost";
    private ObjectInputStream dis;
    private ObjectOutputStream dos;

    private static CommunicationInterface communicationInterface;

    public static CommunicationInterface getInstance() {
        if (communicationInterface == null) {
            communicationInterface = new CommunicationInterface();
        }
        return communicationInterface;
    }

    public CommunicationInterface() {

        try {
            Socket socket = new Socket(LOCALHOST, PORT);
            dis = new ObjectInputStream(socket.getInputStream());
            dos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response exchange(Request request) throws IOException, ClassNotFoundException {
        dos.writeObject(request);
        final Response commandResponse = (Response) dis.readObject();
        return commandResponse;
    }

}
