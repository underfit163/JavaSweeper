import sweeper.Game;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.xml.bind.Marshaller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;

public class JavaSetSweeper extends JFrame
{
    public static void main(String[] args) {
        new JavaSetSweeper();
    }

    private Game game;
    private JavaSweeper javaSweeper;
    private Integer COLS = 10;//размер нашего поля(столбцы)
    private Integer ROWS = 10;//размер нашего экрана(строки)
    private Integer BOMBS = 10;//сколько всего бомб

    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton button1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JPanel panel1;

    public  JavaSetSweeper()
    {
        initLabel();
        initField();
        initButton();
        initPanel();//вызываем инициализацию панели из конструктора
        initFrame();//вызываем инициализацию фрейма из конструктора

    }

    public void setCOLS(int COLS) {
        this.COLS = COLS;
    }

    public void setROWS(int ROWS) {
        this.ROWS = ROWS;
    }

    public void setBOMBS(int BOMBS) throws Exception {
        if(BOMBS<=0) {
            throw new Exception();
        }
        this.BOMBS = BOMBS;
    }

    public int getBOMBS() {
        return BOMBS;
    }

    public int getCOLS() {
        return COLS;
    }

    public int getROWS() {
        return ROWS;
    }

    private void initField() {

        textField1 = new JTextField("9", 2);
        textField2 = new JTextField("9", 2);
        textField3 = new JTextField("10", 2);
        add(textField1);
        add(textField2);
        add(textField3);

        textField1.setBounds(50, 5, 200, 20);
        textField2.setBounds(50, 150, 200, 20);
        textField3.setBounds(100, 300, 80, 20);

    }

    private void initButton()
    {
        button1 = new JButton("Click");
        add(button1,BorderLayout.PAGE_END);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    setCOLS(Integer.parseInt(textField1.getText()));
                    setROWS(Integer.parseInt(textField2.getText()));
                    setBOMBS(Integer.parseInt(textField3.getText()));
                    javaSweeper = new JavaSweeper(getCOLS(),getROWS(),getBOMBS());

                }
                catch (Exception ef)
                {
                    JOptionPane.showMessageDialog(JavaSetSweeper.this,"Ошибка ввода числа","ОШИБКА!",1);
                }

            }
        });

    }

    private void initLabel()//добавит метку на экран
    {
        label1 = new JLabel("X:",Label.LEFT);
        label2 = new JLabel("Y:",Label.LEFT);
        label3 = new JLabel("Count bomb:",Label.LEFT);
        add(label1);
        add(label2);
        add(label3);
        label1.setBounds(5,5,20, 20);
        label2.setBounds(5,150,20, 20);
        label3.setBounds(5,300,80, 20);
    }
    private void initPanel()//инициализация панели
    {
        panel1 = new JPanel()
        {//анонимный класс
            @Override
            protected void paintComponent(Graphics g)//рисуем
            {
                super.paintComponent(g);
            }

        };

        panel1.setPreferredSize(new Dimension(
                10*40,
                10*40));//размер панели
        add (panel1);//добавляет панель на нашу форму

    }
    private void initFrame()//отображение формы
    {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // закрытие на крестик
        setTitle("Java Sweeper");//текст титульника
        setResizable(false);//изменение размера окна
        setVisible(true);//видимость
        pack();//устанавливает минимальный размер контейнера
        setLocationRelativeTo(null);// окошко по центру
    }


}
