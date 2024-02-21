package app;

import java.util.Random;
public class AtomGrid
{
    private int[][] squareGrid;

    //this is a 10x10 so far
    public AtomGrid()
    {
        this.squareGrid = new int[10][10];
        //this is just for testing v
        fillGrid();
    }

    private void fillGrid()
    {
        for(int i=0;i<10;i++)
        {
            for(int k=0;k<10;k++)
            {
                squareGrid[i][k] = 0; //just to distinguish
            }
        }
    }

    class AssignRandom
    {
        public AssignRandom() {
            assignRandomValues();
        }
    }
    private void assignRandomValues()
    {
        Random random = new Random();
        int tally = 0;
        while (tally<3) //three atoms so far
        {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            squareGrid[x][y] = 1;
            tally++;

        }

    }

    public void diplay()
    {
        for(int i=0;i<10;i++)
        {
            for(int k=0;k<10;k++)
            {
                System.out.print(squareGrid[i][k] + " | ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args)
    {
        AtomGrid squareGrid = new AtomGrid();
        squareGrid.new AssignRandom();
        squareGrid.diplay();

    }
}


