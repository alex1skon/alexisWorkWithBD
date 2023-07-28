import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class EventHandler implements ActionListener {
    private Frame frame;

    public EventHandler(Frame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Войти")) {
            // Получаем данные из текстовых полей (логин/пароль) и профиля (например, выпадающего списка)
            // В этом примере используется "admin" как логин и "password" как пароль для администратора
            String login = "admin";
            String password = "password";
            String selectedProfile = "admin";

            // Создаем объект User с данными из текстовых полей
            User user = new User(login, password, selectedProfile);

            // Аутентификация пользователя
            AuthenticationManager authManager = new AuthenticationManager();
            boolean isAuthenticated = authManager.authenticate(user);

            if (isAuthenticated) {
                // Если аутентификация успешна, переключаемся на главное окно
                // В зависимости от профиля пользователя, можно создать различные окна (наследованные от Frame) или использовать панели для различных функциональных частей приложения.
                // В данном примере, показывается просто сообщение об успешной аутентификации.
                JOptionPane.showMessageDialog(frame.frame, "Успешная аутентификация!", "Вход", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Если аутентификация не удалась, показываем сообщение об ошибке
                JOptionPane.showMessageDialog(frame.frame, "Ошибка аутентификации!", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
