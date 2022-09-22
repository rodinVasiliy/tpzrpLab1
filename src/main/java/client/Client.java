package client;

import dto.request.PictureSendRequest;
import dto.response.Response;
import model.Picture;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import utils.ImageProcessor;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

public class Client {
    private static String resolvePythonScriptPath(String path){
        File file = new File(path);
        return file.getAbsolutePath();
    }

    public static void main(String[] args)
            throws IOException, ClassNotFoundException, InterruptedException {
        final CommunicationInterface clientInterface = new CommunicationInterface();
        Thread.sleep(1000);  // just to simulate some waiting...

        String IMAGE_PATH = "C:\\IDEA_Projects\\tpzrpLab1\\src\\test\\picture.jpg";
        BufferedImage sourceImage = ImageProcessor.getImageFromFile(IMAGE_PATH);
        byte[] bytes = ImageProcessor.getImageBytes(sourceImage);
        Picture picture = new Picture(bytes);

        // зашумляем изображение
        String line = "python " + resolvePythonScriptPath("C:\\IDEA_Projects\\tpzrpLab1\\src\\main\\resources\\noise_script.py");
        CommandLine cmdLine = CommandLine.parse(line);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(streamHandler);

        int exitCode = executor.execute(cmdLine);
        String noisyImagePath = "C:\\IDEA_Projects\\tpzrpLab1\\src\\test\\noisy_picture.jpg";
        BufferedImage newSourceImage = ImageProcessor.getImageFromFile(noisyImagePath);
        bytes = ImageProcessor.getImageBytes(newSourceImage);
        Picture noisyImage = new Picture(bytes);

        final PictureSendRequest sendPictureRequest = new PictureSendRequest(noisyImage);

        Response sendPictureResponse = clientInterface.exchange(sendPictureRequest);

        System.out.println("sendPictureResponse " + sendPictureResponse.getMessage());

        Thread.sleep(1000); // just to simulate some waiting...

    }

}
