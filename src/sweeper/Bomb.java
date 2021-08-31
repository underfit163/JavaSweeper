package sweeper;

class Bomb {
    private Matrix bombMap;//куда устанавливаем бомбу
    private int totalBombs;//количество бомб

    Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int j = 0; j< totalBombs; j++)
        placeBomb();
    }

    Box get (Coord coord)
    {
        return bombMap.get(coord);
    }

    private void fixBombsCount ()//максимальное кол. бомб при данном поле
    {
        int maxBombs = Ranges.getSize().x* Ranges.getSize().y/2;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }

    private void placeBomb()
    {
        while (true)//цикл выбирает координату бомбы которойеще нет
        {
            Coord coord = Ranges.getRandomCoord();
            if(Box.BOMB == bombMap.get(coord))
                continue;// продолжаем выполнение цикла, будем искать другую бомбу
            bombMap.set(coord, Box.BOMB);//если нет, то идем дальше
            incNumbersAroundBomb (coord);
            break;//выходим из бескончного цикла
        }

    }

    private void incNumbersAroundBomb (Coord coord)
    {
        for (Coord around : Ranges.getCoordsAround(coord))
            if (Box.BOMB != bombMap.get(around))//если не бомба
            bombMap.set(around, bombMap.get(around).getNextNumberBox());
    }

    int getTotalBombs()
    {
        return totalBombs;
    }
}
