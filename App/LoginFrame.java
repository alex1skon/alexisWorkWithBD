import javax.swing.*;
import java.awt.*;

public class LoginFrame extends Frame {
  private JTextField loginField;
  private JPasswordField passwordField;
  private JComboBox<String> profileComboBox;

  public LoginFrame() {
    super("Окно авторизации", 300, 150);
    initialize();
  }

  private void initialize() {
    JPanel panel = new JPanel(new GridLayout(4, 2));

    JLabel loginLabel = new JLabel("Логин:");
    loginField = new JTextField();

    JLabel passwordLabel = new JLabel("Пароль:");
    passwordField = new JPasswordField();

    JLabel profileLabel = new JLabel("Профиль:");
    String[] profiles = { "admin", "personnelOfficer" }; // Пример списка профилей
    profileComboBox = new JComboBox<>(profiles);

    JButton loginButton = new JButton("Войти");
    loginButton.addActionListener(new EventHandler(this)); // Связываем события с классом EventHandler

    panel.add(loginLabel);
    panel.add(loginField);
    panel.add(passwordLabel);
    panel.add(passwordField);
    panel.add(profileLabel);
    panel.add(profileComboBox);
    panel.add(loginButton);

    frame.add(panel);
  }
}
