package sweeper;

class Flag
{
    private Matrix flagMap;
    private int countOfClosedBoxes;//количество закрытых боксов

    void  start ()
    {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x*Ranges.getSize().y;//начальное знач. равно площади поля
    }
    Box get (Coord coord)
    {
        return flagMap.get(coord);
    }

     void setOpenedToBox(Coord coord)
     {
        flagMap.set(coord,Box.OPENED);
        countOfClosedBoxes --;
    }

    void toggleFlagedToBox (Coord coord)
    {
        switch (flagMap.get(coord))
        {
            case FLAGED: setClosedToBox (coord); break;
            case CLOSED: setFlagedToBox (coord); break;

        }
    }

    private void setClosedToBox(Coord coord)
    {
        flagMap.set(coord,Box.CLOSED);
    }

    private void setFlagedToBox(Coord coord)
    {
        flagMap.set(coord,Box.FLAGED);
    }

    int getCountOfClosedBoxes()
    {
        return countOfClosedBoxes;
    }

    void setBombedToBox(Coord coord)
    {
        flagMap.set(coord,Box.BOMBED);
    }

    void setOpenedToClosedBombBox(Coord coord)
    {
        if(flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

    void setNobombToFlagedSafeBox(Coord coord)
    {
        if(flagMap.get(coord) == Box.FLAGED)
            flagMap.set(coord,Box.NOBOMB);
    }



    int getCountOfFlagedBoxesAround(Coord coord)
    {
        int count = 0;
        for (Coord around : Ranges.getCoordsAround(coord))
            if(flagMap.get(around) == Box.FLAGED)
                count++;
        return count;
    }
}
