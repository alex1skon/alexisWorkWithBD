import java.sql.*; // программа для ведения учета
import javax.swing.*;
import java.awt.*;
import java.awt.Event.*;
import java.util.logging.*;

public class MainWork {

    /**
     * Объект подключения к базе данных
     */
    static Connection conn = null;

    /**
     * Объект для создания SQL-запросов
     */
    static Statement stmt = null;

    /**
     * Основное окно программы
     */
    static JFrame mainFrame = null;

    /**
     * Дополнительное окно
     */
    static JFrame tempFrame = null;

    /**
     * Объект полоса прокрутки
     */
    static JScrollPane scrollPane;

    public static void main(String[] args) {
        mainFrame = new JFrame("mainFrame");
        // System.out.println("Hello World!");
    }

    protected static JTable toTableView(ResultSet rs) {
        return null;
    }

    private Component setMenu() {
        Box mainMenu = new Box(BoxLayout.X_AXIS);
        JButton guestButton = new JButton("Гости");
        guestButton.addActionListener(e -> {
            ResultSet rs = null;
            System.out.println("Guests ! :");

        });

        return null;
    }

    private static ResultSet fromDB(String sqlString) {
        ResultSet localRS = null;
        conn = null;
        stmt = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        try {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/course", "forCourse", "1234");
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                localRS = stmt.executeQuery(sqlString);
            } catch (SQLException SqlEx) {
                Logger.getLogger(MainWork.class.getName()).log(Level.SEVERE, null, SqlEx);
                System.out.println("Проблема с запосом в БД!");
            }
        } catch (ClassNotFoundException e) {
            Logger.getLogger(MainWork.class.getName()).log(Level.SEVERE, null, e));
            System.out.println("Проблема с подключением!");
        }
        return localRS;
    }
}
