import java.sql.*; // программа для ведения учета
import javax.swing.*;
// import java.swing.*;

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
}
