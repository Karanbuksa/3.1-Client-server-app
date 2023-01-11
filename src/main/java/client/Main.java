package client;

import server.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import static server.Parser.*;

public class Main {

    static List<User> users = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        int port = 8282;
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        users.addAll(jsonToList(readString("users.json")));
        boolean f = true;

        System.out.println("New connection accepted");

        while (f) {
            out.println("1. Войти      2.Зарегистрироваться       3.Выйти");
            switch (in.readLine()) {
                case "1" -> {
                    out.println("Введите почту или логин");
                    String usernameOrEmail = in.readLine();
                    out.println("Введите пароль");
                    String password = in.readLine();
                    List<User> foundUsers = users.stream()
                            .filter(x -> x.getEmail().equals(usernameOrEmail) ||
                                    x.getUsername().equals(usernameOrEmail)).toList();
                    if (foundUsers.isEmpty()) {
                        out.println("Такой пользователь не зарегистирован");
                        break;
                    }
                    if (!Objects.equals(password, foundUsers.get(0).getPassword())) {
                        out.println("Неверный пароль");
                    } else {
                        out.println("Добро пожаловать, " + foundUsers.get(0).getName()+". Нажмите Enter, чтобы продолжить.");
                        in.readLine();
                    }
                }
                case "2" -> {
                    out.println("Введите ник");
                    String nickName = in.readLine();
                    out.println("Введите электронную почту");
                    String email = in.readLine();
                    out.println("Введите пароль");
                    String password = in.readLine();
                    out.println("Введите возраст");
                    Long age = Long.parseLong(in.readLine());
                    out.println("Введите имя");
                    String name = in.readLine();
                    out.println("Введите фамилию");
                    String surname = in.readLine();
                    User user = new User();
                    user.setUsername(nickName);
                    user.setAge(age);
                    user.setName(name);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setSurname(surname);
                    users.add(user);
                    writeString(listToJson(users), "users.json");
                    out.println("Вы зарегистрированы. Нажмите Enter, чтобы продолжить.");
                    in.readLine();
                }
                case "3" -> {
                    out.print("end");
                    f = false;
                }
                default -> {
                }
            }
        }

    }
}
