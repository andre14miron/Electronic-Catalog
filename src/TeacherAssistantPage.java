import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class TeacherAssistantPage extends JFrame implements ActionListener {
    Catalog catalog = Catalog.getCatalog();
    ScoreVisitor scoreVisitor = ScoreVisitor.getScoreVisitor();
    User user;
    JButton logOut, validate;
    JLabel title, hello, textCourses, titleSituation, textSituation;
    JList listCourses, listStudents;
    JPanel panel;
    Color color1 = new Color(13, 52,70);
    Color color2 = new Color(23, 109,129);
    Color color3 = new Color(133, 173, 181);
    Color color4 = new Color(200, 223, 226);
    Font font1 = new Font("TimesRoman",Font.BOLD,40);
    Font font2 = new Font("TimesRoman", 0,20);
    Font font3 = new Font("TimesRoman", Font.BOLD,30);
    Font font4 = new Font("TimesRoman", 0,15);
    int xPanel = 350, yPanel = 200;
    int wPanel = 600, hPanel = 300;

    public JLabel createLabel(int x, int y, int w, int h, Font f, Color c){
        JLabel label = new JLabel();
        label.setBounds(x, y, w, h);
        label.setForeground(c);
        label.setFont(f);
        label.setVisible(true);
        return label;
    }

    public TeacherAssistantPage(User user) {
        super("Electronic catalog");
        this.user = user;

        // Title
        title = createLabel(10, 10, 400, 50, font1, color1);
        title.setText("Electronic Catalog");
        add(title);

        // "Good day, Teacher/Assistant!"
        hello = createLabel(10, 60, 400, 30, font2, color2);
        hello.setText("Good day, " + user.getFirstName() + "!");
        add(hello);

        // Log Out Button
        logOut = new JButton();
        logOut.setEnabled(true);
        logOut.setText("Log Out");
        logOut.setBounds(850, 10, 100, 25);
        logOut.addActionListener(this);
        add(logOut);

        // Validate Button
        validate = new JButton();
        validate.setText("Validate");
        validate.setBounds(xPanel+50, yPanel+hPanel-50, wPanel-100, 30);
        validate.setHorizontalAlignment(SwingConstants.CENTER);
        validate.addActionListener(this);
        add(validate);

        // Text for courses
        textCourses = createLabel(10, 150, 300, 30, font2, color2);
        textCourses.setText("Your courses that you teach:");
        add(textCourses);

        // List of courses
        listCourses = new JList(user.getCourses());
        listCourses.setFont(font2);
        listCourses.setBounds(10, 200, 300, 300);
        listCourses.setOpaque(true);
        listCourses.setBackground(color3);
        listCourses.setForeground(color1);
        listCourses.setEnabled(false);
        listCourses.addListSelectionListener(new Listener());
        add(listCourses);

        // Action Panel
        panel = new JPanel();
        panel.setBackground(color3);
        panel.setBounds(xPanel, yPanel, wPanel, hPanel);

        // "Grades Situation" <- title of the course selected
        titleSituation = createLabel(xPanel, yPanel, wPanel, 40, font3, color1);
        titleSituation.setText("Grades Situation");
        titleSituation.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleSituation);

        // Text about situation
        textSituation = createLabel(xPanel + 10, yPanel + 40, wPanel/2-10, 30, font4, color1);
        add(textSituation);

        // List of Students with unvalidated grades
        listStudents = new JList();
        listStudents.setFont(font4);
        listStudents.setBounds(xPanel+10, yPanel + 70, wPanel/2-10, hPanel-130);
        listStudents.setOpaque(true);
        listStudents.setBackground(color3);
        listStudents.setForeground(color2);
        if (user.getClass() == Teacher.class)
            listStudents.setModel(scoreVisitor.getUnvalidatedExamGrades((Teacher) user));
        else
            listStudents.setModel(scoreVisitor.getUnvalidatedPartialGrades((Assistant) user));

        add(listStudents);

        add(panel);

        setLayout(null);
        setVisible(true);
        setLocationRelativeTo(null);
        setSize(1000, 600);
        getContentPane().setBackground(color4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == validate) {
            if (user.getClass() == Teacher.class) {
                ((Teacher) user).accept(scoreVisitor);
                textSituation.setText("There are no grades to validate :)");
                textSituation.setForeground(Color.GREEN);
                listStudents.setModel(scoreVisitor.getUnvalidatedExamGrades((Teacher) user));
            }
            else {
                ((Assistant) user).accept(scoreVisitor);
                textSituation.setText("There are no grades to validate :)");
                textSituation.setForeground(Color.GREEN);
                listStudents.setModel(scoreVisitor.getUnvalidatedPartialGrades((Assistant) user));
            }
        }
    }

    class Listener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (listCourses.isSelectionEmpty())
                return;

            // the situation of grades
            ScoreVisitor scoreVisitor = ScoreVisitor.getScoreVisitor();
            if (user.getClass() == Teacher.class) {
                listStudents.setModel(scoreVisitor.getUnvalidatedExamGrades((Teacher)user));
                if (scoreVisitor.getUnvalidatedExamGrades((Teacher)user).isEmpty()) {
                    textSituation.setText("There are no grades to validate :)");
                    textSituation.setForeground(Color.GREEN);
                }
                else {
                    textSituation.setText("These students have unvalidated grades:");
                }
            }
            else {
                listStudents.setModel(scoreVisitor.getUnvalidatedPartialGrades((Assistant) user));
                if (scoreVisitor.getUnvalidatedPartialGrades((Assistant) user).isEmpty()) {
                    textSituation.setText("There are no grades to validate :)");
                    textSituation.setForeground(Color.GREEN);
                }
                else {
                    textSituation.setText("These students have unvalidated grades:");
                }
            }
        }
    }
}