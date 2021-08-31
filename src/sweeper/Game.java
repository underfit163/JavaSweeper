package sweeper;

public class Game
{
    private Bomb bomb;
    private Flag flag;
    private GameState state;
    public GameState getState()
    {
        return state;
    }
    public Game(int cols, int rows, int bombs)
    {
        Ranges.setSize(new Coord(cols,rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }

    public void start ()
    {

       bomb.start();
       flag.start();
       state = GameState.PLAYED;
    }

    public Box getBox (Coord coord)//показывает что будем изображать в том или ином месте нашего экрана
    {
        if(flag.get(coord)==Box.OPENED)
            return bomb.get(coord);
        else //если закрыто, то возвращаем то, что сверху
            return flag.get(coord);
    }

    public void pressLeftButton(Coord coord)
    {
        if(gameOver()) return;//выходим, если конец игры
        openBox(coord);//открываем клетку
        checkWinner();
    }

    private void checkWinner ()
    {
        if (state == GameState.PLAYED)//если еще играем
        {
            if(flag.getCountOfClosedBoxes() == bomb.getTotalBombs())
            {
                state = GameState.WINNER;
            }
        }
    }

    private void openBox(Coord coord)
    {
        switch (flag.get(coord))
        {
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord); return;
            case FLAGED: return;
            case CLOSED:
                switch (bomb.get(coord))//вложенный свич
                {
                    case ZERO: openBoxesAround(coord); return;
                    case BOMB: openBombs(coord); return;
                    default  : flag.setOpenedToBox(coord); return;//чтобы не перечислять все цифры
                }
        }

    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord)
    {
        if (bomb.get(coord)!= Box.BOMB)
            if (flag.getCountOfFlagedBoxesAround(coord)==bomb.get(coord).getNumber())//если количество флагов вокруг равно числу на этой клетке
                for (Coord around : Ranges.getCoordsAround(coord))
                    if (flag.get(around)==Box.CLOSED)
                        openBox(around);
    }

    private void openBombs(Coord bombed)
    {
        state = GameState.BOMBED;
        flag.setBombedToBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if(bomb.get(coord)==Box.BOMB)
                flag.setOpenedToClosedBombBox(coord);//открыть бокс на закрытой клетке с бомбой
            else
                flag.setNobombToFlagedSafeBox(coord);//если не бомба,а на ней флаг
    }

    private void openBoxesAround(Coord coord)
    {
        flag.setOpenedToBox(coord);
        for (Coord around : Ranges.getCoordsAround(coord))
        {
            openBox(around);//рекурсия
        }
    }

    public void pressRightButton(Coord coord)
    {
        if(gameOver()) return;//выходим, если конец игры
        flag.toggleFlagedToBox(coord);//переключить
    }

    private boolean gameOver()
    {
        if (state == GameState.PLAYED)
            return false;
        start();
        return true;
    }
}

