import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.*;

import sweeper.*;
import sweeper.Box;

  public class JavaSweeper extends JFrame {
    private Game game;
    private JPanel panel;//панель, чтобы можно было рисовать
    private JLabel label;//сообщение о статусе игры
    private final int IMAGE_SIZE = 50;//размер картинки

    public JavaSweeper (int x, int y, int b)
    {
        game = new Game(x,y,b);
        game.start();
        setImages();//все картинки будут погружены
        initLabel();
        initPanel();//вызываем инициализацию панели из конструктора
        initFrame();//вызываем инициализацию фрейма из конструктора
    }

    private void initLabel()//добавит метку на экран
    {
        label = new JLabel("Welcome!");
        add(label,BorderLayout.SOUTH);
    }

    private void initPanel()//инициализация панели
    {
        panel = new JPanel()
        {//анонимный класс
            @Override
            protected void paintComponent(Graphics g)//рисуем
            {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords())
                {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x*IMAGE_SIZE, coord.y*IMAGE_SIZE, this);//box.ordinal()порядковый нимер в списке
                }
            }
        };

    panel.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e)
        {
            int x = e.getX() / IMAGE_SIZE;
            int y = e.getY() / IMAGE_SIZE;
            Coord coord = new Coord(x,y);
            if (e.getButton()==MouseEvent.BUTTON1)//если нажата левая кнопка мышки
                game.pressLeftButton(coord);
            if (e.getButton() == MouseEvent.BUTTON3)
                game.pressRightButton(coord);
            if (e.getButton() == MouseEvent.BUTTON2)
                game.start();
            label.setText(getMassage());
            panel.repaint();//перерисовываем форму, чтобы видеть изменения
        }
    });//добавляем слушителя
    panel.setPreferredSize(new Dimension(
                Ranges.getSize().x*IMAGE_SIZE,
                Ranges.getSize().y*IMAGE_SIZE));//размер панели
        add (panel);//добавляет панель на нашу форму



    }

    private String getMassage()
    {
        switch (game.getState())
        {
            case PLAYED: return "Think twice!";
            case BOMBED: return "YOU LOSE!";
            case WINNER: return "Congratulation!";
            default: return "Welcome";
        }
    }


    private void initFrame()//отображение формы
    {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // закрытие на крестик
        setTitle("Java Sweeper");//текст титульника
        setResizable(false);//изменение размера окна
        setVisible(true);//видимость
        setIconImage(getImage("icon"));
        pack();//устанавливает минимальный размер контейнера
        setLocationRelativeTo(null);// окошко по центру
    }

    private void setImages()
    {
        for(Box box : Box.values())
        {
            box.image = getImage(box.name().toLowerCase());
        }
    }



    private Image getImage(String name)//функция получения картинок
    {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();

    }
}
