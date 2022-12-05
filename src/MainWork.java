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
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrollPane = new JScrollPane();
        mainFrame.add(setMenu(), BorderLayout.NORTH);
        mainFrame.setSize(800, 600);
        mainFrame.setMinimumSize(mainFrame.getSize());
        mainFrame.setVisible(true);
        // System.out.println("Hello World!");
    }

    protected static JTable toTableView(ResultSet rSet) {
        int rowCount = 0;
        
        String[][] data = null;
        try {
            rSet.last();
            rowCount = rSet.getRow();
            data = new String[rowCount][6];

            System.out.println("Количество строк: " + rowCount);
            
            rSet.beforeFirst();
            while (rSet.next()) {
                for (int i = 2; i < 7; i++) {
                    // System.out.println(rSet.getRow());
                    data[rSet.getRow() - 1][i - 2] = rSet.getString(i);
    
                    System.out.println(data[rSet.getRow() - 1][i  - 2] + " ");
                }
    
                ResultSet tempRS = fromDB("select * from trainer where trainer_id = " + rSet.getInt("trainer_id"));
                tempRS.next();

                String trainer = tempRS.getString("fullname");
                
                data[rSet.getRow() - 1][5] = trainer;
                System.out.println(data[rSet.getRow() - 1][5] + " ");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = {"ФИО", "Дата Рождения", "Общежитие", "Курс", "Группа", "ФИО Тренера"};

        JTable tempTable = new JTable(data, columnNames);

        return tempTable;
    }

    private static Component setMenu() {
        Box mainMenu = new Box(BoxLayout.X_AXIS);
        JButton guestButton = new JButton("Гости");
        guestButton.addActionListener(e -> {
            ResultSet rs = null;
            System.out.println("Guests ! :");
            rs = fromDB("select * from guest");
            JTable guestsTable = toTableView(rs);

            scrollPane = new JScrollPane(guestsTable);
            guestsTable.setFillsViewportHeight(true);
            mainFrame.add(scrollPane, BorderLayout.CENTER);
            mainFrame.pack();
        });

        mainMenu.add(guestButton);

        return mainMenu;
    }

    private static ResultSet fromDB(String sqlString) {
        ResultSet localRS = null;
        conn = null;
        stmt = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Course", "forCourse", "1234");
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                localRS = stmt.executeQuery(sqlString);
            } catch (SQLException SqlEx) {
                Logger.getLogger(MainWork.class.getName()).log(Level.SEVERE, null, SqlEx);
                System.out.println("Проблема с запросом в БД!");
            }
        } catch (ClassNotFoundException e) {
            Logger.getLogger(MainWork.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Проблема с подключением!");
        }
        return localRS;
    }

}
