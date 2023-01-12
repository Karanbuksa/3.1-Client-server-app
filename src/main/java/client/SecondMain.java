package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SecondMain {
    public static void main(String[] args) throws RuntimeException {
        String host = "127.0.0.1";
        int port = 8282;
        try (Socket socket = new Socket(host, port)) {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            String response = bufferedReader.readLine();
            while (!response.equals("end")) {
                System.out.println(response);
                printWriter.println(scanner.nextLine());
                response = bufferedReader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
