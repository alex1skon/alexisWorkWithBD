import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainForm {
    static Connection conn = null; // объект для связи с БД
    static Statement stmt = null; // объект для создания SQL-запросов
    static JFrame mainFrame; // окно программы
    static JFrame secondFrame;
    static JScrollPane scrollPane; // панель с полосой прокрутки

    public static void main(String[] args) {
        mainFrame = new JFrame("SuperDB_Viewer");
        secondFrame = new JFrame("Info");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // secondFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        scrollPane = new JScrollPane();
        mainFrame.add(setMenu(), BorderLayout.NORTH);
        mainFrame.setSize(800, 400);
        mainFrame.setMinimumSize(mainFrame.getSize());
        secondFrame.setMinimumSize(secondFrame.getSize());
        mainFrame.setVisible(true);
    }

    private static Component setMenu() {
        Box mainMenu = new Box(BoxLayout.X_AXIS);
        JButton studentsButton = new JButton("Show students");
        studentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainFrame.remove(scrollPane);
                secondFrame.remove(scrollPane);
                ResultSet rs = null;
                System.out.println("Students Table!!");
                rs = fromDB("select * from student");
                JTable studentsTable = toTableView(rs);
                scrollPane = new JScrollPane(studentsTable);
                studentsTable.setFillsViewportHeight(true);
                mainFrame.add(scrollPane, BorderLayout.CENTER);
                mainFrame.pack();
            }
        });
        JButton examMarksButton = new JButton("Show exam marks");
        examMarksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainFrame.remove(scrollPane);
                secondFrame.remove(scrollPane);
                ResultSet rs = null;
                System.out.println("Exam Marks Table!!");
                rs = fromDB("select * from exam_marks");
                JTable studentsTable = toTableView3(rs);
                scrollPane = new JScrollPane(studentsTable);
                studentsTable.setFillsViewportHeight(true);
                mainFrame.add(scrollPane, BorderLayout.CENTER);
                mainFrame.pack();
            }
        });
        JButton universityButton = new JButton("Show university");
        universityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainFrame.remove(scrollPane);
                secondFrame.remove(scrollPane);
                ResultSet rs = null;
                System.out.println("University Table!!");
                rs = fromDB("select * from university");
                JTable studentsTable = toTableView4(rs);
                scrollPane = new JScrollPane(studentsTable);
                studentsTable.setFillsViewportHeight(true);
                secondFrame.add(scrollPane, BorderLayout.CENTER);
                secondFrame.pack();
                secondFrame.setVisible(true);
            }
        });
        JButton subjectButton = new JButton("Show subject");
        subjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainFrame.remove(scrollPane);
                secondFrame.remove(scrollPane);
                ResultSet rs = null;
                System.out.println("subject Table!!");
                rs = fromDB("select * from subject");
                JTable studentsTable = toTableView5(rs);
                scrollPane = new JScrollPane(studentsTable);
                studentsTable.setFillsViewportHeight(true);
                secondFrame.add(scrollPane, BorderLayout.CENTER);
                secondFrame.pack();
                secondFrame.setVisible(true);
            }
        });
        JButton subjLectButton = new JButton("Show subject lect");
        subjLectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainFrame.remove(scrollPane);
                secondFrame.remove(scrollPane);
                ResultSet rs = null;
                System.out.println("subject Lect Table!!");
                rs = fromDB("select * from subj_lect");
                JTable studentsTable = toTableView6(rs);
                scrollPane = new JScrollPane(studentsTable);
                studentsTable.setFillsViewportHeight(true);
                secondFrame.add(scrollPane, BorderLayout.CENTER);
                secondFrame.pack();
                secondFrame.setVisible(true);
            }
        });
        JButton lecturerButton = new JButton("Show lecturer");
        lecturerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                mainFrame.remove(scrollPane); // убираем предыдущую панель с таблицей
                secondFrame.remove(scrollPane);
                // даже если не было еще панели с таблицей
                ResultSet rs = null; // объект для хранения результатов SQL-запроса
                System.out.println("Lecturer Table!!"); // вывод в консоль для контроля
                // записываем в rs результат работы функции fromDB с переданным ей запросом
                rs = fromDB("select * from lecturer");
                // создаем таблицу из результатов запроса rs при помощи функции toTableView
                JTable studentsTable = toTableView2(rs);
                // создаем новую панель с прокруткой
                scrollPane = new JScrollPane(studentsTable);
                // нужно для корректного отображения больших таблиц
                studentsTable.setFillsViewportHeight(true);
                // вставляем панель с таблицей в центр окна
                mainFrame.add(scrollPane, BorderLayout.CENTER);
                mainFrame.pack(); // подстраиваем размеры окна под размеры таблицы
            }
        });
        mainMenu.add(studentsButton); // добавляем в Box кнопку
        mainMenu.add(examMarksButton);
        mainMenu.add(lecturerButton);
        mainMenu.add(subjLectButton);
        mainMenu.add(subjectButton);
        mainMenu.add(universityButton);
        // подобным способом можно добавить другие кнопки для показа всех таблиц БД
        return mainMenu; // возвращаем Box
    }

    // функция для формирования таблицы из результатов SQL-запроса
    protected static JTable  toTableView(ResultSet rs2) {
        int rowCount = 0; // переменная для кол-ва записей
        String[][] data = null; // массив строк для содержимого таблицы
        try { // нужен блок try … catch для работы с SQL-запросами
            rs2.last(); // переходим на последнюю запись результата SQL-запроса
            rowCount = rs2.getRow();// считаем кол-во записей в результате SQL-запроса
            data = new String[rowCount][7]; // создаем массив строк для содержимого таблицы
            System.out.println("RowsCount=" + rowCount); // вывод в консоль кол-ва строк
            rs2.beforeFirst(); // переходим на место в результате запроса перед первой записью
            while (rs2.next()) { // цикл пока есть записи в результате запроса
                // цикл от 2-й до 7-й колонки в таблице, потому что первая колонка – id,
                for (int i = 2; i < 8; i++) { // а мы не будем его выводить
                    // в массив data в соответствующую позицию записываем данные из результатов
                    // запроса
                    data[rs2.getRow() - 1][i - 2] = rs2.getString(i);
                    // контрольный вывод в консоль
                    System.out.print(data[rs2.getRow() - 1][i - 2] + " ");
                }
                // дополнительный ResultSet для получения названия университета из его id
                ResultSet tempRS = fromDB("select * from university where univ_id=" + rs2.getString("univ_id"));
                tempRS.next(); // переходим на первую запись в объекте tempRS
                // в строку university записываем значение колонки univ_name из таблицы
                // university
                String university = tempRS.getString("univ_name");
                // в последний элемент текущей строки массива записываем название университета
                data[rs2.getRow() - 1][6] = university;
                System.out.print(data[rs2.getRow() - 1][6] + " "); // вывод в консоль
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // массив строк с названиями столбцов
        String[] columnNames = { "Фамилия", "Имя", "Стипендия", "Курс", "Город", "Дата рождения", "Университет" };
        // создаем таблицу из массива с данными и массива с названиями столбцов
        JTable tempTable = new JTable(data, columnNames);
        return tempTable; // возвращаем созданную таблицу
    }

    protected static JTable toTableView2(ResultSet rs2) {
        int rowCount = 0;
        String[][] data = null;
        try {
            rs2.last();
            rowCount = rs2.getRow();
            data = new String[rowCount][7];
            System.out.println("RowsCount=" + rowCount);
            rs2.beforeFirst();
            while (rs2.next()) {
                for (int i = 2; i < 5; i++) {
                    data[rs2.getRow() - 1][i - 2] = rs2.getString(i);
                    System.out.print(data[rs2.getRow() - 1][i - 2] + " ");
                }
                ResultSet tempRS = fromDB("select * from university where univ_id=" + rs2.getString("univ_id"));
                tempRS.next();
                String university = tempRS.getString("univ_name");
                data[rs2.getRow() - 1][3] = university;
                System.out.print(data[rs2.getRow() - 1][3] + " ");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] columnNames = { "Фамилия", "Имя", "Город", "Университет" };
        JTable tempTable = new JTable(data, columnNames);
        return tempTable;
    }

    protected static JTable toTableView3(ResultSet rs2) {
        int rowCount = 0;
        String[][] data = null;
        try {
            rs2.last();
            rowCount = rs2.getRow();
            data = new String[rowCount][7];
            System.out.println("RowsCount=" + rowCount);
            rs2.beforeFirst();
            while (rs2.next()) {
                for (int i = 2; i < 6; i++) {
                    data[rs2.getRow() - 1][i - 2] = rs2.getString(i);
                    System.out.print(data[rs2.getRow() - 1][i - 2] + " ");
                }
                ResultSet tempRS = fromDB("select * from student where student_id=" + rs2.getString("student_id"));
                // System.out.println(tempRS.next());
                if (tempRS.next() == false) {
                    data[rs2.getRow() - 1][0] = "Нет данных";
                    System.out.print(data[rs2.getRow() - 1][0] + " ");
                    System.out.println();
                } else {
                    String surname = tempRS.getString("surname");
                    data[rs2.getRow() - 1][0] = surname;
                    System.out.print(data[rs2.getRow() - 1][0] + " ");
                    System.out.println();
                }
                tempRS = fromDB("select * from subject where subj_id=" + rs2.getString("subj_id"));
                tempRS.next();
                String subj_name = tempRS.getString("subj_name");
                data[rs2.getRow() - 1][1] = subj_name;
                System.out.print(data[rs2.getRow() - 1][1] + " ");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] columnNames = { "Фамилия", "Имя", "Оценка", "Дата" };
        JTable tempTable = new JTable(data, columnNames);
        return tempTable;
    }

    protected static JTable toTableView4(ResultSet rs2) {
        int rowCount = 0;
        String[][] data = null;
        try {
            rs2.last();
            rowCount = rs2.getRow();
            data = new String[rowCount][7];
            System.out.println("RowsCount=" + rowCount);
            rs2.beforeFirst();
            while (rs2.next()) {
                for (int i = 2; i < 5; i++) {
                    data[rs2.getRow() - 1][i - 2] = rs2.getString(i);
                    System.out.print(data[rs2.getRow() - 1][i - 2] + " ");
                }
                System.out.print(data[rs2.getRow() - 1][3] + " ");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] columnNames = { "Университет", "Рейтинг", "Город" };
        JTable tempTable = new JTable(data, columnNames);
        return tempTable;
    }

    protected static JTable toTableView5(ResultSet rs2) {
        int rowCount = 0;
        String[][] data = null;
        try {
            rs2.last();
            rowCount = rs2.getRow();
            data = new String[rowCount][7];
            System.out.println("RowsCount=" + rowCount);
            rs2.beforeFirst();
            while (rs2.next()) {
                for (int i = 2; i < 5; i++) {
                    data[rs2.getRow() - 1][i - 2] = rs2.getString(i);
                    System.out.print(data[rs2.getRow() - 1][i - 2] + " ");
                }
                // System.out.print(data[rs2.getRow() - 1][3] + " ");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] columnNames = { "Предмет", "Часы", "Семестр" };
        JTable tempTable = new JTable(data, columnNames);
        return tempTable;
    }

    protected static JTable toTableView6(ResultSet rs2) {
        int rowCount = 0;
        String[][] data = null;
        try {
            rs2.last();
            rowCount = rs2.getRow();
            data = new String[rowCount][7];
            System.out.println("RowsCount=" + rowCount);
            rs2.beforeFirst();
            while (rs2.next()) {
                for (int i = 1; i < 3; i++) {
                    data[rs2.getRow() - 1][i - 1] = rs2.getString(i);
                    System.out.print(data[rs2.getRow() - 1][i - 1] + " ");
                }
                ResultSet tempRS = fromDB("select * from lecturer where lecturer_id=" + rs2.getString("lecturer_id"));
                tempRS.next();
                String temp = tempRS.getString("surname");
                data[rs2.getRow() - 1][0] = temp;
                tempRS = fromDB("select * from subject where subj_id=" + rs2.getString("subj_id"));
                tempRS.next();
                temp = tempRS.getString("subj_name");
                data[rs2.getRow() - 1][1] = temp;
                System.out.print(data[rs2.getRow() - 1][0] + " ");
                System.out.print(data[rs2.getRow() - 1][1] + " ");
                System.out.println();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] columnNames = { "Фамилия", "Предмет" };
        JTable tempTable = new JTable(data, columnNames);
        return tempTable;
    }

    private static ResultSet fromDB(String sqlString) {// функция для работы с БД
        ResultSet localRS = null; // локальный объект для результатов запроса41
        conn = null; // объект для связи с БД
        stmt = null; // объект для создания SQL-запросов
        try { // нужен блок try … catch для работы с БД
            Class.forName("com.mysql.cj.jdbc.Driver"); // подгружаем драйвер для MySQL
            try { // еще один блок try … catch
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/text", "admin", "1234");
                // получаем объект для выполнения команд SQL
                stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                // объекту localRS присваиваем результат выполнения команды SQL
                localRS = stmt.executeQuery(sqlString);
            } catch (SQLException ex) { // обрабатываем возможные ошибки
                Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Trouble with query!!");
            }
        } catch (ClassNotFoundException ex) { // обрабатываем возможные ошибки
            Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Trouble with connection!!");
        }
        return localRS; // возвращаем результат
    }

    public static class User {

    }
}