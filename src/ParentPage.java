import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class ParentPage extends JFrame implements ActionListener {
    Catalog catalog = Catalog.getCatalog();
    Course course;
    Student student;
    Parent parent;
    JButton logOut, notification;
    JLabel title, hello, textCourses;
    JLabel titleCourse, lTeach, lAssist, lPartial, lExam, lTextAssist, lTotal;
    JLabel tTeach, tAssist, tPartial, tExam, tTotal;
    JPanel panel;
    JScrollPane scrollCourses;
    JList listCourses, listAssist, listNotif;
    Color color1 = new Color(13, 52,70);
    Color color2 = new Color(23, 109,129);
    Color color3 = new Color(133, 173, 181);
    Color color4 = new Color(200, 223, 226);
    Font font1 = new Font("TimesRoman",Font.BOLD,40);
    Font font2 = new Font("TimesRoman", 0,20);
    Font font3 = new Font("TimesRoman", Font.BOLD,30);
    Font font4 = new Font("TimesRoman", Font.BOLD,14);
    int xPanel = 350, yPanel = 200;
    int wPanel = 600, hPanel = 300;

    public JLabel createLabel(int x, int y, int w, int h, Font f, Color c){
        JLabel label = new JLabel();
        label.setBounds(x, y, w, h);
        label.setForeground(c);
        label.setFont(f);
        label.setVisible(false);
        return label;
    }

    public ParentPage(Parent parent) {
        super("Electronic catalog");

        this.parent = parent;
        student = parent.getChild();

        // Title
        title = createLabel(10, 10, 400, 50, font1, color1);
        title.setVisible(true);
        title.setText("Electronic Catalog");
        add(title);

        // "Hello Student!"
        hello = createLabel(10, 60, 400, 30, font2, color2);
        hello.setVisible(true);
        hello.setText("Hello, " + parent.getFirstName() + "!");
        add(hello);

        // Text for courses
        textCourses = createLabel(10, 150, 400, 30, font2, color2);
        textCourses.setVisible(true);
        textCourses.setText("The courses your child is enrolled in:");
        add(textCourses);

        // List of courses
        listCourses = new JList(student.getCourses());
        listCourses.setFont(font2);
        listCourses.setBackground(color3);
        listCourses.addListSelectionListener(new Listener());

        scrollCourses = new JScrollPane();
        scrollCourses.setViewportView(listCourses);
        scrollCourses.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollCourses.setBounds(10, 200, 300, 300);
        add(scrollCourses);

        // Log Out Button
        logOut = new JButton();
        logOut.setEnabled(true);
        logOut.setText("Log Out");
        logOut.setBounds(850, 10, 100, 25);
        logOut.addActionListener(this);
        add(logOut);

        // Notification Button
        notification = new JButton();
        notification.setEnabled(true);
        notification.setText("Notifications");
        notification.setBounds(650, 10, 150, 25);
        notification.addActionListener(this);
        add(notification);

        // Panel with infos about a course
        panel = new JPanel();
        panel.setBackground(color3);
        panel.setBounds(xPanel, yPanel, wPanel, hPanel);

        // "Course" <- title of the course selected
        titleCourse = createLabel(xPanel, yPanel, wPanel, 40, font3, color1);
        titleCourse.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleCourse);

        // "The titular teacher"
        lTeach = createLabel(xPanel, yPanel+50, wPanel/4, 20, font4, color2);
        lTeach.setText("  The titular Teacher:");
        add(lTeach);

        tTeach = createLabel(xPanel + wPanel/4, yPanel+50, wPanel/4, 20, font4, color2);
        add(tTeach);

        // "Your Assistant"
        lAssist = createLabel(xPanel, yPanel+80, wPanel/4, 20, font4, color2);
        lAssist.setText("  Your Assistant:");
        add(lAssist);

        tAssist = createLabel(xPanel + wPanel/4, yPanel+80, wPanel/4, 20, font4, color2);
        add(tAssist);

        // "Partial Grade"
        lPartial = createLabel(xPanel+wPanel/2, yPanel+50, wPanel/4, 20, font4, color2);
        lPartial.setText("  Partial Grade:");
        add(lPartial);

        tPartial = createLabel(xPanel + wPanel/2+wPanel/4, yPanel+50, wPanel/4, 20, font4, color2);
        add(tPartial);

        // "Exam Grade"
        lExam = createLabel(xPanel + wPanel/2, yPanel+80, wPanel/4, 20, font4, color2);
        lExam.setText("  Exam Grade:");
        add(lExam);

        tExam = createLabel(xPanel + wPanel/2+wPanel/4, yPanel+80, wPanel/4, 20, font4, color2);
        add(tExam);

        // "Total Grade"
        lTotal = createLabel(xPanel + wPanel/2, yPanel+110, wPanel/4, 20, font4, color2);
        lTotal.setText("  Total Grade:");
        add(lTotal);

        tTotal = createLabel(xPanel + wPanel/2+wPanel/4, yPanel+110, wPanel/4, 20, font4, color2);
        add(tTotal);

        // "All Assistants"
        lTextAssist = createLabel(xPanel, yPanel+110, wPanel/4, 20, font4, color2);
        lTextAssist.setText("  All Assistants:");
        add(lTextAssist);

        // List of Assistants
        listAssist = new JList();
        listAssist.setFont(font4);
        listAssist.setVisible(false);
        listAssist.setBounds(xPanel+wPanel/4, yPanel + 110, wPanel/4, hPanel-110);
        listAssist.setEnabled(false);
        listAssist.setBackground(color3);
        add(listAssist);

        // List of Notifications
        listNotif = new JList();
        listNotif.setFont(font4);
        listNotif.setVisible(false);
        listNotif.setBounds(xPanel+ 10, yPanel + 50, wPanel-10, hPanel-50);
        listNotif.setEnabled(false);
        listNotif.setOpaque(true);
        listNotif.setBackground(color3);
        listNotif.setForeground(color2);
        add(listNotif);

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
        if(e.getSource() == notification) {
            // make the information about a course invisible
            lTeach.setVisible(false);
            lAssist.setVisible(false);
            lPartial.setVisible(false);
            lExam.setVisible(false);
            lTextAssist.setVisible(false);
            lTotal.setVisible(false);

            tTeach.setVisible(false);
            tAssist.setVisible(false);
            tPartial.setVisible(false);
            tExam.setVisible(false);
            tTotal.setVisible(false);
            listAssist.setVisible(false);

            // make the list of notifications visible
            listNotif.setVisible(true);
            listNotif.setModel(parent.getNotifications());

            titleCourse.setVisible(true);
            titleCourse.setText("Notifications");
        }
    }

    class Listener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (listCourses.isSelectionEmpty())
                return;

            listNotif.setVisible(false);

            course = catalog.getCourses().get(listCourses.getSelectedIndex());
            titleCourse.setText(course.toString());
            titleCourse.setVisible(true);
            lTeach.setVisible(true);
            lAssist.setVisible(true);
            lPartial.setVisible(true);
            lExam.setVisible(true);
            lTextAssist.setVisible(true);
            lTotal.setVisible(true);

            tTeach.setVisible(true);
            tTeach.setText(course.getTeacher().toString());
            tAssist.setVisible(true);
            tAssist.setText(course.getAssistant(student).toString());
            tPartial.setVisible(true);
            tExam.setVisible(true);
            tTotal.setVisible(true);

            Grade grade;
            if (course.getGrade(student) == null)
                grade = new Grade(student, course.toString());
            else
                grade = course.getGrade(student);

            tPartial.setText(grade.getPartialScore() + "");

            tExam.setText(grade.getExamScore() + "");

            tTotal.setText(grade.getTotal() + "");

            DefaultListModel aa = new DefaultListModel<>();
            aa.addAll(course.getAssistants());
            listAssist.setModel(aa);
            listAssist.setVisible(true);
        }
    }
}