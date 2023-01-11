import java.sql.*; // программа для ведения учета
import javax.swing.*;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import java.awt.*;
import java.util.Properties;
import java.util.logging.*;
import java.util.Date;

public class MainApp {

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
     * Объект полоса прокрутки главного окна
     */
    static JScrollPane mainScrollPane;

    /*
     * Объект полосы прокрутки побочного окна
     */
    static JScrollPane tempScrollPane;

    /**
     * Объект таблицы, которая находится на главном фрейме в данный момент
     */
    static JTable tableInMainFrame = null;


    /**
     * Фрейм для вывода ошибок
     */
    static JFrame msgFrame = null;

    /**
     * Объект для вывода текста ошибки внутри {@link #msgFrame}
     */
    static JLabel errorLabel = new JLabel("");

    /**
     * Объект для закрытия фрейма с ошибкой ({@link #msgFrame})
     */
    static JButton closeErrorMsg = new JButton("Ok");

    public static void main(String[] args) {
        mainFrame = new JFrame("mainFrame");
        msgFrame = new JFrame("Error");
        errorLabel.setFont(new Font("Fira Code", Font.PLAIN, 16));
        msgFrame.add(errorLabel, BorderLayout.NORTH);
        msgFrame.add(closeErrorMsg, BorderLayout.SOUTH);
        msgFrame.setVisible(false);
        msgFrame.setSize(400, 120);
        msgFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        closeErrorMsg.addActionListener(b->{
            msgFrame.setVisible(false);
            errorLabel.setText("");
        });
        msgFrame.setVisible(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainScrollPane = new JScrollPane();
        mainFrame.add(setMenu(), BorderLayout.NORTH);
        mainFrame.setSize(800, 600);
        mainFrame.setMinimumSize(mainFrame.getSize());
        mainFrame.setVisible(true);
        // System.out.println("Hello World!");
    }

    /**
     * Приводит результат запроса в БД к табличному виду
     * (Привод к табличному виду запроса на таблицу "Гости")
     * 
     * @param rs - рузельатт запроса в БД
     * @return Возвращает таблицу с вписанными в нее данными из запроса
     */
    protected static JTable guestToTableView(ResultSet rs) {
        int rowCount = 0;

        String[][] data = null;
        try {
            rs.last();
            rowCount = rs.getRow();
            data = new String[rowCount][6];

            System.out.println("Количество строк: " + rowCount);

            rs.beforeFirst();

            while (rs.next()) {
                for (int i = 2; i < 7; i++) {
                    // System.out.println(rSet.getRow());
                    data[rs.getRow() - 1][i - 2] = rs.getString(i);

                    System.out.println(data[rs.getRow() - 1][i - 2] + " ");
                }

                ResultSet tempRS = fromDB("select * from trainer where trainer_id = " + rs.getInt("trainer_id"));
                tempRS.next();

                String trainer = tempRS.getString("fullname");

                data[rs.getRow() - 1][5] = trainer;
                System.out.println(data[rs.getRow() - 1][5] + " ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = { "ФИО", "Дата Рождения", "Общежитие", "Курс", "Группа", "ФИО Тренера" };

        JTable tempTable = new JTable(data, columnNames);

        return tempTable;
    }

    /**
     * Приводит результат запроса в БД к табличному виду
     * (Привод к табличному виду запроса на таблицу "Тренеры")
     * 
     * @param rs - рузельатт запроса в БД
     * @return Возвращает таблицу с вписанными в нее данными из запроса
     */
    protected static JTable trainerToTableView(ResultSet rs) {
        int rowCount = 0;
        String[][] data = null;
        try {
            rs.last();
            rowCount = rs.getRow();
            data = new String[rowCount][4];
            System.out.println("Количество строк: " + rowCount);

            rs.beforeFirst();
            while (rs.next()) {
                for (int i = 2; i < 6; i++) {
                    data[rs.getRow() - 1][i - 2] = rs.getString(i);

                    System.out.println(data[rs.getRow() - 1][i - 2]);
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = { "ФИО", "Дата рождения", "Адрес проживания", "Тел. номер" };
        JTable tempTable = new JTable(data, columnNames);

        return tempTable;
    }

    /**
     * Приводит результат запроса в БД к табличному виду
     * (Привод к табличному виду запроса на таблицу "Упражнения")
     * 
     * @param rs - рузельатт запроса в БД
     * @return Возвращает таблицу с вписанными в нее данными из запроса
     */
    protected static JTable exerciseToTableView(ResultSet rs) {
        int rowCount = 0;
        String[][] data = null;
        try {
            rs.last();
            rowCount = rs.getRow();
            data = new String[rowCount][4];
            System.out.println("Количество строк: " + rowCount);

            rs.beforeFirst();
            while (rs.next()) {
                for (int i = 2; i < 4; i++) {
                    data[rs.getRow() - 1][i - 2] = rs.getString(i);

                    System.out.println(data[rs.getRow() - 1][i - 2]);
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = { "Упражнение", "Количество повторений" };
        JTable tempTable = new JTable(data, columnNames);

        return tempTable;
    }

    protected static JTable muslesGroupToTableView(ResultSet rs) {
        int rowCount = 0;

        String[][] data = null;
        try {
            rs.last();
            rowCount = rs.getRow();
            data = new String[rowCount][6];

            System.out.println("Количество строк: " + rowCount);

            rs.beforeFirst();

            while (rs.next()) {
                for (int i = 2; i < 4; i++) {
                    data[rs.getRow() - 1][i - 2] = rs.getString(i);

                    System.out.println(data[rs.getRow() - 1][i - 2] + " ");
                }

                ResultSet tempRS = fromDB("select * from exercises where exercise_id = " + rs.getInt("exercise_id"));
                tempRS.next();

                String exercise = tempRS.getString("exercise");

                data[rs.getRow() - 1][4] = exercise;
                System.out.println(data[rs.getRow() - 1][4] + " ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = { "Группа мышц", "Упражнения" };

        JTable tempTable = new JTable(data, columnNames);

        return tempTable;
    }

    protected static JTable timetableToTableView(ResultSet rs) {
        int rowCount = 0;
        String[][] data = null;
        try {
            rs.last();
            rowCount = rs.getRow();
            data = new String[rowCount][4];
            System.out.println("Количество строк: " + rowCount);

            rs.beforeFirst();
            while (rs.next()) {
                for (int i = 1; i < 4; i++) {
                    data[rs.getRow() - 1][i - 1] = rs.getString(i);

                    System.out.println(data[rs.getRow() - 1][i - 1]);
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] columnNames = { "Дата", "День недели", "ФИО гостя" };
        JTable tempTable = new JTable(data, columnNames);

        return tempTable;
    }

    private static Component setMenu() {
        Box mainMenu = new Box(BoxLayout.X_AXIS);
        JMenuBar menuBar = new JMenuBar();
        JMenu tablesMenu = new JMenu("Таблицы");
        JMenu viewMenu = new JMenu("Показать");

        // ---------------------------------------------------------------
        // Инициализация вариантов выбора в меню "Показать"
        JMenuItem guestMenuItem = new JMenuItem("Гости");
        JMenuItem trainerMenuItem = new JMenuItem("Тренеры");
        JMenuItem exerciseMenuItem = new JMenuItem("Упражнения");
        JMenuItem musclesGroup = new JMenuItem("Группы мышц");
        JMenuItem timetableMenuItem = new JMenuItem("Расписание");
        // ---------------------------------------------------------------

        // ---------------------------------------------------------------
        // Добавление слушателей на кнопки, для реализации вывода
        // информации из БД

        guestMenuItem.addActionListener(e -> { // "Гости"
            mainFrame.remove(mainScrollPane);
            ResultSet rs = null;
            System.out.println("Guests ! :");
            rs = fromDB("select * from guest");
            JTable guestsTable = guestToTableView(rs);

            mainScrollPane = new JScrollPane(guestsTable);
            guestsTable.setFillsViewportHeight(true);
            mainFrame.add(mainScrollPane, BorderLayout.CENTER);
            tableInMainFrame = guestsTable;
            mainFrame.pack();
        });

        trainerMenuItem.addActionListener(e -> { // "Тренеры"
            mainFrame.remove(mainScrollPane);
            ResultSet rs = null;
            System.out.println("Trainer ! :");
            rs = fromDB("select * from trainer");
            JTable trainerTable = trainerToTableView(rs);

            mainScrollPane = new JScrollPane(trainerTable);
            trainerTable.setFillsViewportHeight(true);
            mainFrame.add(mainScrollPane, BorderLayout.CENTER);
            tableInMainFrame = trainerTable;
            mainFrame.pack();
        });

        exerciseMenuItem.addActionListener(e -> { // "Упражнения"
            mainFrame.remove(mainScrollPane);
            ResultSet rs = null;
            System.out.println("Exercise ! :");
            rs = fromDB("select * from exercises");
            JTable exerciseTable = exerciseToTableView(rs);

            mainScrollPane = new JScrollPane(exerciseTable);
            exerciseTable.setFillsViewportHeight(true);
            mainFrame.add(mainScrollPane, BorderLayout.CENTER);
            tableInMainFrame = exerciseTable;
            mainFrame.pack();
        });

        musclesGroup.addActionListener(e -> { // "Группы мышц"
            mainFrame.remove(mainScrollPane);
            ResultSet rs = null;
            System.out.println("Muscles groups ! :");
            rs = fromDB("select * from musclesGroup");
            JTable musclesGroupTable = muslesGroupToTableView(rs);

            mainScrollPane = new JScrollPane(musclesGroupTable);
            musclesGroupTable.setFillsViewportHeight(true);
            mainFrame.add(mainScrollPane, BorderLayout.CENTER);
            tableInMainFrame = musclesGroupTable;
            mainFrame.pack();
        });

        timetableMenuItem.addActionListener(e -> { // "Расписание"
            mainFrame.remove(mainScrollPane);
            ResultSet rs = null;
            System.out.println("Timetable ! :");
            rs = fromDB(
                    "select cast(T.datetime as date), T.dayofweek, G.fullname from timetable T inner join guest G on T.guest_id = G.guest_id");
            JTable timetableTable = timetableToTableView(rs);

            mainScrollPane = new JScrollPane(timetableTable);
            timetableTable.setFillsViewportHeight(true);
            mainFrame.add(mainScrollPane, BorderLayout.CENTER);
            tableInMainFrame = timetableTable;
            mainFrame.pack();
        });
        // ------------------------------------------------------------------

        // ------------------------------------------------------------------
        // Инициализация вариантов выбора в меню "Добавленить"
        JMenu addMenu = new JMenu("Добавить данные в таблицу..");
        JMenuItem guestMenuItem_toAdd = new JMenuItem("Гости");
        JMenuItem trainerMenuItem_toAdd = new JMenuItem("Тренеры");
        JMenuItem exerciseMenuItem_toAdd = new JMenuItem("Упражнения");
        JMenuItem musclesGroupMenuItem_toAdd = new JMenuItem("Группа мышц");
        JMenuItem timetableMenuItem_toAdd = new JMenuItem("Расписание");
        // -------------------------------------------------------------------

        // Добавление слушателей на кнопки
        // -------------------------------------------------------------------

        guestMenuItem_toAdd.addActionListener(e -> {
            tempFrame = new JFrame("Добавление данных");
            tempFrame.setVisible(true);
            tempFrame.setSize(800, 100);
            tempFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            // Контейнер для всех элементов в фрейме
            Box mainBoxForTempFrame = new Box(BoxLayout.X_AXIS);

            // Лейблы названий столбцов
            JLabel fullnameLabel = new JLabel("ФИО");
            JLabel birthLabel = new JLabel("День рождения");
            JLabel hostelLabel = new JLabel("Общежитие");
            JLabel courseLabel = new JLabel("Курс");
            JLabel classgroupLabel = new JLabel("Группа");
            JLabel trainerLabel = new JLabel("Номер тренера");

            // Поля для заполнения данных
            JTextField fullnameTextField = new JTextField();
            JDatePicker birthTextField = createDateField();
            JTextField hostelTextField = new JTextField();
            JTextField courseTextField = new JTextField();
            JTextField classgroupTextField = new JTextField();
            JTextField trainerTextField = new JTextField();
            
            // Контейнеры для разметки элементов
            Box fullnameBox = new Box(BoxLayout.Y_AXIS);
            Box birthBox = new Box(BoxLayout.Y_AXIS);
            Box hostelBox = new Box(BoxLayout.Y_AXIS);
            Box courseBox = new Box(BoxLayout.Y_AXIS);
            Box classgroupBox = new Box(BoxLayout.Y_AXIS);
            Box trainerBox = new Box(BoxLayout.Y_AXIS);

            // Первыми добавляем названия столбцов
            fullnameBox.add(createBoxWithGlue(fullnameLabel));
            birthBox.add(createBoxWithGlue(birthLabel));
            hostelBox.add(createBoxWithGlue(hostelLabel));
            courseBox.add(createBoxWithGlue(courseLabel));
            classgroupBox.add(createBoxWithGlue(classgroupLabel));
            trainerBox.add(createBoxWithGlue(trainerLabel));

            // Затем добавляем поля для ввода данных
            fullnameBox.add(fullnameTextField);
            // Поле для даты сделано отдельной библиотекой JDatePicker
            birthBox.add((Component)(birthTextField));
            hostelBox.add(hostelTextField);
            courseBox.add(courseTextField);
            classgroupBox.add(classgroupTextField);
            trainerBox.add(trainerTextField);

            // Добавляем контейнеры с нашими элементами в общий контейнер
            mainBoxForTempFrame.add(fullnameBox);
            mainBoxForTempFrame.add(birthBox);
            mainBoxForTempFrame.add(hostelBox);
            mainBoxForTempFrame.add(courseBox);
            mainBoxForTempFrame.add(classgroupBox);
            mainBoxForTempFrame.add(trainerBox);

            // Добавляем общий контейнер в фрейм
            tempFrame.add(mainBoxForTempFrame);
            
            // Добавляем кнопку отправки данных в бд в фрейм
            JButton sendButton = new JButton("Отправить");
            tempFrame.add(sendButton, BorderLayout.SOUTH);

            sendButton.addActionListener(a->{
                String year = Integer.toString(birthTextField.getModel().getYear());
                String month = birthTextField.getModel().getMonth() + 1 < 10 ? "0" + Integer.toString(birthTextField.getModel().getMonth() + 1) : Integer.toString(birthTextField.getModel().getMonth() + 1);
                String day = birthTextField.getModel().getDay() < 10 ? "0" + Integer.toString(birthTextField.getModel().getDay()) : Integer.toString(birthTextField.getModel().getDay());
                String value = year + "-" + month + "-" + day;
                Date d = new Date();
                String todayYear = Integer.toString(d.getYear() + 1900);
                String todayMonths = d.getMonth() + 1 < 10 ? "0" + Integer.toString(d.getMonth() + 1) : Integer.toString(d.getMonth() + 1);
                String todayDays = d.getDate() < 10 ? "0" + Integer.toString(d.getDate()) : Integer.toString(d.getDate());
                String today = todayYear + "-" + todayMonths + "-" + todayDays;

                birthTextField.getModel().setDate(d.getYear() + 1900, d.getMonth() + 1, d.getDay());

                Boolean flag = false;
                if (fullnameTextField.getText().isBlank()) {
                    errorLabel.setText("<html>Fullname field is empty!<br>Please type fullname to field!</html>");
                    // System.out.println("fullname");
                    msgFrame.setVisible(true);
                    flag = true;
                }
                else if (hostelTextField.getText().isBlank()) {
                    errorLabel.setText("<html>Hostel field is empty!<br>Please type hostel name to field!</html>");
                    // System.out.println("hostel");
                    msgFrame.setVisible(true);
                    flag = true;
                }
                else if (courseTextField.getText().isBlank()) {
                    errorLabel.setText("<html>Course field is empty!<br>Please type course to field!</html>");
                    // System.out.println("course");
                    msgFrame.setVisible(true);
                    flag = true;
                }
                else if (classgroupTextField.getText().isBlank()) {
                    errorLabel.setText("<html>Class group is empty!<br>Please type class group to field!</html>");
                    msgFrame.setVisible(true);
                    flag = true;
                }
                else if (trainerTextField.getText().isBlank()) {
                    errorLabel.setText("<html>Trainer_id is empty!<br>Please type Trainer_id to field!</html>");
                    msgFrame.setVisible(true);
                    flag = true;
                }

                if (!flag) {
                    String tmpStr = "insert into guest (fullName,birth,hostel,course,classGroup,trainer_id) values (\"" + fullnameTextField.getText() + "\",\"" + value + "\",\"" + hostelTextField.getText() + "\"," + courseTextField.getText() + ",\"" + classgroupTextField.getText() + "\"," + trainerTextField.getText() + ")";
                    System.out.println(tmpStr);
                    fromDB(tmpStr);
                    System.out.println("Insert data to DB!");
                    // tempFrame.setVisible(false);
                }
                // System.out.println(value);
                // System.out.println(today);
            });
        });
        // TODO добавить остальные кнопки в меню
        addMenu.add(guestMenuItem_toAdd);
        tablesMenu.add(addMenu);

        // -------------------------------------------------------------------

        // Компоновка элементов в меню и подменю интерфейса

        // Компановка кнопок вывода таблиц
        viewMenu.add(musclesGroup);
        viewMenu.add(guestMenuItem);
        viewMenu.add(trainerMenuItem);
        viewMenu.add(exerciseMenuItem);
        viewMenu.add(timetableMenuItem);

        // Кнопка "Таблицы"
        tablesMenu.add(viewMenu);

        // Меню-бар вверху фрейма
        menuBar.add(tablesMenu);

        // Добавление главного меню-бара в Box
        mainMenu.add(menuBar);

        return mainMenu;
    }

    public static Box createBoxWithGlue(Component comp) {
        Box tempBox = new Box(BoxLayout.X_AXIS);
        tempBox.add(Box.createGlue());
        tempBox.add(comp);
        tempBox.add(Box.createGlue());
        return tempBox;
    }

    /**
     * Создает поле ввода даты
     * @return поле ввода даты
     */
    public static JDatePickerImpl createDateField() {
        JDatePickerImpl myElem = null;
        UtilDateModel uiModel = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "\u0421\u0435\u0433\u043e\u0434\u043d\u044f");
        p.put("text.month", "\u041c\u0435\u0441\u044f\u0446");
        p.put("text.year", "\u0413\u043e\u0434");
        JDatePanelImpl dtpPanel = new JDatePanelImpl(uiModel, p);
        DateComponentFormatter dlf = new DateComponentFormatter();
        myElem = new JDatePickerImpl(dtpPanel, dlf);
        myElem.setTextEditable(true);

        return myElem;
    }

    /**
     * Возвращает результат запроса в БД, указанный как строка в параметре
     * 
     * @param sqlString - запрос в БД
     * @return результат запроса
     */
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
                Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, SqlEx);
                System.out.println("Проблема с запросом в БД!");
            }
        } catch (ClassNotFoundException e) {
            Logger.getLogger(MainApp.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("Проблема с подключением!");
        }
        return localRS;
    }

}