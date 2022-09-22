package server;

import dto.request.*;
import dto.response.Response;
import dto.response.SendPictureResponse;
import model.Picture;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import utils.ImageProcessor;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static String resolvePythonScriptPath(String path) {
        File file = new File(path);
        return file.getAbsolutePath();
    }

/*    private static String convertPath(String path){
        return path.replaceAll("\\", File.separator);
    }*/

    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws IOException {
        int PORT = 7777;
        ServerSocket ss = new ServerSocket(PORT);

        while (!ss.isClosed()) {
            Socket client = ss.accept();
            pool.execute(new SingleServer(client));
        }
        pool.shutdown();
    }

    /**
     * The {@code SingleServer} class creates single server socket for one client
     */
    private static class SingleServer implements Runnable {
        /**
         * client socket
         */
        private static Socket client;

        public SingleServer(Socket client) {
            SingleServer.client = client;
        }

        @Override
        public void run() {
            try {
                final OutputStream outputStream = client.getOutputStream();
                ObjectOutputStream dos = new ObjectOutputStream(outputStream);
                final InputStream inputStream = client.getInputStream();
                ObjectInputStream dis = new ObjectInputStream(inputStream);

                while (!client.isClosed()) {
                    final Request request = (Request) dis.readObject();
                    //do some logic
                    final Response response = respond(request);
                    dos.writeObject(response);
                }
            } catch (IOException | ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        private Response respond(Request request)
                throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException,
                IOException {
            if (request instanceof PictureSendRequest) {
                Picture picture = (Picture) (((PictureSendRequest) request).getPicture());
                byte[] pictureBytes = picture.getImageBytes();
                BufferedImage image = ImageProcessor.getImageFromBytes(pictureBytes);
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.command("cmd.exe", "/c",
                        "cd C:\\PyCharmProjects\\mpai_lab3 & pip install opencv-python & python C:\\PyCharmProjects\\mpai_lab3\\median_filter_script.py");
                ImageProcessor.saveImageToFile(image, "C:\\IDEA_Projects\\tpzrpLab1\\src\\test\\new_picture.jpg");

                Process process = processBuilder.start();

                try {
                    int exitCode = process.waitFor();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return new SendPictureResponse("picture successfully sent and processed");
            }
            return null;
        }
    }
}
