
import Exceptions.InvalidMapException;
import Exceptions.UnknownElementException;
import Map.*;
import Map.Occupant.Crate;
import Map.Occupant.Player;
import Map.Occupiable.DestTile;
import Map.Occupiable.Occupiable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Holds the necessary components for running the game.
 */
public class Game {

    private Map m;
    private int numRows;
    private int numCols;
    private char[][] rep;

    /**
     * Loads and reads the map line by line, instantiates and initializes Map m.
     * Print out the number of rows, then number of cols (on two separate lines).
     *
     * @param filename the map text filename
     * @throws InvalidMapException
     */
    public void loadMap(String filename) throws InvalidMapException {
        //TODO

        //initialize variables
        File file = null;
        Scanner source = null;

        //check if the file is valid
        try {
            file = new File(filename);
            source = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.err.println("The file can't be read!");
            return;
        }

        //get number of row
        if (source.hasNextInt()) {
            numRows = Integer.valueOf(source.nextLine());
            System.out.println(String.valueOf(numRows));  //display numRows
        }
        else {
            throw new UnknownElementException("The first number is not an integer!");
        }

        //get number of col
        if (source.hasNextInt()) {
            numCols = Integer.valueOf(source.nextLine());
            System.out.println(String.valueOf(numCols));  //display numCols
        }
        else {
            throw new UnknownElementException("The first number is not an integer!");
        }

        if (numRows <= 0 || numCols <= 0) {
           throw new UnknownElementException("The numbe row or col is invalid!");
        }
        //initialize variable
        m = new Map();
        rep = new char[numRows][numCols];
        String R;
        char temp;

        //get the char in the file
        for (int i = 0; i < numRows; i++) {
            {
                if (source.hasNextLine()) {
                    R = source.nextLine();
                    for (int j = 0; j < numCols; j++) {
                        temp = R.charAt(j);
                        rep[i][j] = temp;
                        //System.out.print(String.valueOf(temp));  //for debug purpose
                    }
                }
                else {
                    throw new UnknownElementException("The map is not in a proper shape!");
                }
            }
            //System.out.println();  //for debug purpose
        }

        source.close();

        m.initialize(numRows, numCols, rep);
        return;
    }

    /**
     * Can be done using functional concepts.
     * @return Whether or not the win condition has been satisfied
     */
    public boolean isWin() {
        //TODO

            for (int i = 0; i < m.getDestTiles().size(); i++)
                if (!m.getDestTiles().get(i).isCompleted())
                    return false;

            return true; // You may also modify this line.

    }

    /**
     * When no crates can be moved but the game is not won, then deadlock has occurred.
     *
     * @return Whether deadlock has occurred
     */

    //helper function for isDeadlocked()
    //return true if the cell contains a player
    public boolean isPlayer(int r, int c) {

        if (m.getCells()[r][c] instanceof Wall)
            return false;

        if (!((Occupiable) m.getCells()[r][c]).getOccupant().isPresent())
            return false;

        if (((Occupiable) m.getCells()[r][c]).getOccupant().get() instanceof Player)
            return true;
        else return false;

    }

    public boolean isDeadlocked() {
        //TODO
        int dead = 0;
        int deadLock = 0;

            for (int i = 0; i < m.getCrates().size(); i++) {

                //reset flag
                dead = 0;

                int r = m.getCrates().get(i).getR();
                int c = m.getCrates().get(i).getC();

                //check for it is the matching DestTile
                if (m.getCells()[r][c] instanceof DestTile)
                    if (m.getCrates().get(i).getRepresentation() == (m.getCells()[r][c]).getRepresentation()) {
                        deadLock++;
                        continue;
                    }
                //check all four directions
                if (m.isOccupiableAndNotOccupiedWithCrate(r + 1, c)) {
                    if (m.isOccupiableAndNotOccupiedWithCrate(r - 1, c)) {
                        dead = 0;
                        continue;
                    }
                    else if (isPlayer(r - 1, c)) {
                        dead = 0;
                        continue;
                    }
                    else dead++;
                }
                else dead++;

                if (m.isOccupiableAndNotOccupiedWithCrate(r - 1, c)) {
                    if (isPlayer(r + 1, c)) {
                        dead = 0;
                        continue;
                    }
                    else dead++;
                }
                else dead++;

                if (m.isOccupiableAndNotOccupiedWithCrate(r, c + 1)) {
                    if (m.isOccupiableAndNotOccupiedWithCrate(r, c - 1)) {
                        dead = 0;
                        continue;
                    }
                    else if (isPlayer(r, c - 1)) {
                        dead = 0;
                        continue;
                    }
                    else dead++;
                }
                else dead++;

                if (m.isOccupiableAndNotOccupiedWithCrate(r, c - 1)) {
                   if (isPlayer(r, c + 1)) {
                        dead = 0;
                        continue;
                    }
                    else dead++;
                }
                else dead++;

                //if a crate is deadlocked in all four directions, dead in all four cases
                //deadLock keep track for is all crate is dead
                if (dead == 4)
                {
                    deadLock++;

                    if (deadLock == m.getCrates().size())
                        return true;
                }
            }

            return false; // You may also modify this line.
    }

    /**
     * Print the map to console
     */
    public void display() {
        //TODO
            for (int i = 0; i < this.numRows; i++) {
                for (int j = 0; j < this.numCols; j++) {
                    System.out.print(m.getCells()[i][j].getRepresentation());
                }
                System.out.println();
            }

    }

    /**
     * @param c The char corresponding to a move from the user
     *          w: up
     *          a: left
     *          s: down
     *          d: right
     *          r: reload map (resets any progress made so far)
     * @return Whether or not the move was successful
     */
    public boolean makeMove(char c) throws InvalidMapException{
        //TODO

        try {
            switch (c) {
                case 'w':
                    if (m.movePlayer(Map.Direction.UP))
                        return true;
                    break;
                case 'a':
                    if (m.movePlayer(Map.Direction.LEFT))
                        return true;
                    break;
                case 's':
                    if (m.movePlayer(Map.Direction.DOWN))
                        return true;
                    break;
                case 'd':
                    if (m.movePlayer(Map.Direction.RIGHT))
                        return true;
                    break;
                case 'r':
                        m.initialize(this.numRows, this.numCols, this.rep);
                        return true;
                default:
                    throw new UnknownElementException("The input character is invalid!");
            }

            return false; // You may also modify this line.
        } catch (UnknownElementException e) {
            return false;
        }

    }


}
