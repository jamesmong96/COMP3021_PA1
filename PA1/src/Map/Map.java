package Map;

import Exceptions.InvalidMapException;
import Exceptions.InvalidMatchingOfCrateAndDestTile;
import Exceptions.InvalidNumberOfPlayersException;
import Exceptions.UnknownElementException;
import Map.Occupant.Crate;
import Map.Occupant.Player;
import Map.Occupiable.DestTile;
import Map.Occupiable.Occupiable;
import Map.Occupiable.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * A class holding a the 2D array of cells, representing the world map
 */
public class Map {
    private Cell[][] cells;
    private ArrayList<DestTile> destTiles = new ArrayList<>();
    private ArrayList<Crate> crates = new ArrayList<>();

    private Player player;

    /**
     * This function instantiates and initializes cells, destTiles, crates to the correct map elements (the # char
     * means a wall, @ the player, . is unoccupied Tile, lowercase letter is crate on a Tile,
     * uppercase letter is an unoccupied DestTile).
     *
     * @param rows The number of rows in the map
     * @param cols The number of columns in the map
     * @param rep  The 2d char array read from the map text file
     * @throws InvalidMapException Throw the correct exception when necessary. There should only be 1 player.
     */
    public void initialize(int rows, int cols, char[][] rep) throws InvalidMapException {
        //TODO

        cells = new Cell[rows][cols];
        int countPlayer = 0;
        int countDest = 0;
        int countCrate = 0;

        try {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    //case of Uppercase letters
                    if ((int) rep[i][j] > 64 && (int) rep[i][j] < 91) {
                        cells[i][j] = new DestTile(rep[i][j]);
                        destTiles.add(countDest, (DestTile) cells[i][j]);
                        countDest++;
                        continue;
                    }
                    //case of Lowercase letters
                    else if ((int) rep[i][j] > 96 && (int) rep[i][j] < 123) {
                        cells[i][j] = new Tile();
                        Crate ctemp = new Crate(i, j, rep[i][j]);
                        ((Tile) cells[i][j]).setOccupant(ctemp);
                        crates.add(countCrate, ctemp);
                        countCrate++;
                        continue;
                    }

                    switch ((int) rep[i][j]) {
                        //case of #
                        case 35:
                            cells[i][j] = new Wall();
                            break;

                        //case of .
                        case 46:
                            cells[i][j] = new Tile();
                            break;

                        //case of @
                        case 64:
                            cells[i][j] = new Tile();
                            player = new Player(i, j);
                            ((Tile) cells[i][j]).setOccupant(player);
                            countPlayer++;
                            break;

                        default :
                            throw new UnknownElementException("The map consist invalid character!");
                    }
                }
            }
        } catch (IndexOutOfBoundsException e) {
            throw new UnknownElementException("The row or col specific in the first two line is invalid!");
        }


        if (countPlayer != 1)
            throw new InvalidNumberOfPlayersException("The map should have one Player!");
        else if (countCrate == 0 || countDest == 0) {
            throw new InvalidMatchingOfCrateAndDestTile("The number of crate or destTile cannot be 0!");
        }
        else if (countCrate != countDest) {
            throw new InvalidMatchingOfCrateAndDestTile("The number of crate and DestTile is not the same!");
        }
        else {
            //check does all crates' ID match with those of destTile
            //initialize two list to compare the representation
            char[] crateTest = new char[crates.size()];
            char[] destTileTest = new char[destTiles.size()];

            //initialize both list since the number is the same
            //initialize variables for helping changing cases
            int temp;
            for (int i = 0; i < crates.size(); i++) {
                destTileTest[i]= destTiles.get(i).getRepresentation();
                temp = (int) crates.get(i).getRepresentation() - 32;
                crateTest[i] = (char) temp;
            }

            //compare the content is both array
            for (int i = 0; i < crateTest.length; i++) {
                if (crateTest[i] == '\0') {
                    continue;
                }

                for (int j = 0; j < destTileTest.length; j++) {
                    if (destTileTest[j] == '\0')
                        continue;

                    if (crateTest[i] == destTileTest[j]) {
                        crateTest[i] = '\0';
                        destTileTest[j] = '\0';
                        i = -1;
                        break;
                    }
                }
            }

            //check the resulting array is all \0 or not
            for (int i = 0; i < crateTest.length; i++) {
                if (crateTest[i] != '\0' || destTileTest[i] != '\0')
                    throw new InvalidMatchingOfCrateAndDestTile("The IDs of crates doesn't match with the IDs of destTile!");
            }

        }

    }

    public ArrayList<DestTile> getDestTiles() {
        return destTiles;
    }

    public ArrayList<Crate> getCrates() {
        return crates;
    }

    public Cell[][] getCells() {
        return cells;
    }

    /**
     * Attempts to move the player in the specified direction. Note that the player only has the strength to push
     * one crate. It cannot push 2 or more crates simultaneously. The player cannot walk through walls or walk beyond
     * map coordinates.
     *
     * @param d The direction the player wants to move
     * @return Whether the move was successful
     */
    public boolean movePlayer(Direction d) {
        //TODO

        //initialize variables
        int r = player.getR();
        int c = player.getC();

        switch (d) {
            //All the cases follows the structure and comments in UP.
            case UP:
                //check the next block if it is valid
                if (!this.isValid((r - 1), c))
                    return false;
                //check the next block if it is Wall
                if (cells[r - 1][c] instanceof Wall)
                    return false;
                //check the next block if it is empty
                if (isOccupiableAndNotOccupiedWithCrate((r - 1), c)) {
                    player.setPos((r - 1), c);
                    ((Occupiable) cells[r - 1][c]).setOccupant(player);
                    ((Occupiable) cells[r][c]).removeOccupant();
                    return true;
                }
                //check the next block if it is an occupant
                //shouldn't happen, a safe check for the next if statement
                if (!((Occupiable) cells[r - 1][c]).getOccupant().isPresent())
                    return false;
                //check the next block if it is crate
                //shouldn't happen, a safe check in case some unexpected logic error
                if (((Occupiable) cells[r - 1][c]).getOccupant().get() instanceof Crate) {
                    //check the next next block if it is valid
                    if (!this.isValid((r - 2), c))
                        return false;
                    //if yes then check the next next block if it is empty
                    if (isOccupiableAndNotOccupiedWithCrate((r - 2), c)) {
                        if (!this.moveCrate(((Crate) ((Occupiable) cells[r - 1][c]).getOccupant().get()), Direction.UP))
                            return false;
                        //safe check for the next block is good to move
                        if (isOccupiableAndNotOccupiedWithCrate((r - 1), c)) {
                            player.setPos((r - 1), c);
                            ((Occupiable) cells[r - 1][c]).setOccupant(player);
                            ((Occupiable) cells[r][c]).removeOccupant();
                        }
                        else return false;

                        break;
                    }
                }
                return false;

            case DOWN:
                //check the next block if it is valid
                if (!this.isValid((r + 1), c))
                    return false;
                //check the next block if it is Wall
                if (cells[r + 1][c] instanceof Wall)
                    return false;
                //check the next block if it is empty
                if (isOccupiableAndNotOccupiedWithCrate((r + 1), c)) {
                    player.setPos((r + 1), c);
                    ((Occupiable) cells[r + 1][c]).setOccupant(player);
                    ((Occupiable) cells[r][c]).removeOccupant();
                    break;
                }
                //check the next block if it is an occupant
                if (!((Occupiable) cells[r + 1][c]).getOccupant().isPresent())
                    return false;
                //check the next block if it is crate
                if (((Occupiable) cells[r + 1][c]).getOccupant().get() instanceof Crate) {
                    //check the next next block if it is valid
                    if (!this.isValid((r + 2), c))
                        return false;
                    //if yes then check the next next block if it is empty
                    if (isOccupiableAndNotOccupiedWithCrate((r + 2), c)) {
                        if (!this.moveCrate(((Crate) ((Occupiable) cells[r + 1][c]).getOccupant().get()), Direction.DOWN))
                            return false;
                        //safe check for the next block is good to move
                        if (isOccupiableAndNotOccupiedWithCrate((r + 1), c)) {
                            player.setPos((r + 1), c);
                            ((Occupiable) cells[r + 1][c]).setOccupant(player);
                            ((Occupiable) cells[r][c]).removeOccupant();
                        }
                        else return false;

                        break;
                    }
                }
                return false;

            case LEFT:
                //check the next block if it is valid
                if (!this.isValid(r, (c - 1)))
                    return false;
                //check the next block if it is Wall
                if (cells[r][c - 1] instanceof Wall)
                    return false;
                //check the next block if it is empty
                if (isOccupiableAndNotOccupiedWithCrate(r, (c - 1))) {
                    player.setPos(r, (c - 1));
                    ((Occupiable) cells[r][c - 1]).setOccupant(player);
                    ((Occupiable) cells[r][c]).removeOccupant();
                    break;
                }
                //check the next block if it is an occupant
                if (!((Occupiable) cells[r][c - 1]).getOccupant().isPresent())
                    return false;
                //check the next block if it is crate
                if (((Occupiable) cells[r][c - 1]).getOccupant().get() instanceof Crate) {
                    //check the next next block if it is valid
                    if (!this.isValid(r, (c - 2)))
                        return false;
                    //if yes then check the next next block if it is empty
                    if (isOccupiableAndNotOccupiedWithCrate(r, (c - 2))) {
                        if (!this.moveCrate(((Crate) ((Occupiable) cells[r][c - 1]).getOccupant().get()), Direction.LEFT))
                            return false;
                        //safe check for the next block is good to move
                        if (isOccupiableAndNotOccupiedWithCrate((r), c - 1)) {
                            player.setPos((r), c - 1);
                            ((Occupiable) cells[r][c - 1]).setOccupant(player);
                            ((Occupiable) cells[r][c]).removeOccupant();
                        }
                        else return false;

                        break;
                    }
                }
                return false;

            case RIGHT:
                //check the next block if it is valid
                if (!this.isValid(r, (c + 1)))
                    return false;
                //check the next block if it is Wall
                if (cells[r][c + 1] instanceof Wall)
                    return false;
                //check the next block if it is empty
                if (isOccupiableAndNotOccupiedWithCrate(r, (c + 1))) {
                    player.setPos(r, (c + 1));
                    ((Occupiable) cells[r][c + 1]).setOccupant(player);
                    ((Occupiable) cells[r][c]).removeOccupant();
                    break;
                }
                //check the next block if it is an occupant
                if (!((Occupiable) cells[r][c + 1]).getOccupant().isPresent())
                    return false;
                //check the next block if it is crate
                if (((Occupiable) cells[r][c + 1]).getOccupant().get() instanceof Crate) {
                    //check the next next block if it is valid
                    if (!this.isValid(r, (c + 2)))
                        return false;
                    //if yes then check the next next block if it is empty
                    if (isOccupiableAndNotOccupiedWithCrate(r, (c + 2))) {
                        if (!this.moveCrate(((Crate) ((Occupiable) cells[r][c + 1]).getOccupant().get()), Direction.RIGHT))
                            return false;
                        //safe check for the next block is good to move
                        if (isOccupiableAndNotOccupiedWithCrate((r), c + 1)) {
                            player.setPos((r), c + 1);
                            ((Occupiable) cells[r][c + 1]).setOccupant(player);
                            ((Occupiable) cells[r][c]).removeOccupant();
                        }
                        else return false;

                        break;
                    }
                }
                return false;

            default: return false;
        }

        return true;

    }

    /**
     * Attempts to move the crate into the specified direction by 1 cell. Will only succeed if the destination
     * implements the occupiable interface and is not currently occupied.
     *
     * @param c The crate to be moved
     * @param d The desired direction to move the crate in
     * @return Whether or not the move was successful
     */
    private boolean moveCrate(Crate c, Direction d) {
        //TODO

        //initialize variables
        int row = c.getR();
        int col = c.getC();

        switch (d) {
            //All the cases follows the structure and comments in UP.
            case UP:
                //check out of bound
                if (!this.isValid((row - 1), col))
                    return false;
                //check the next block is valid to move
                if (!isOccupiableAndNotOccupiedWithCrate((row - 1), col)) {
                    return false;
                }
                //move the crate and remove the old one
                c.setPos((row - 1), col);
                ((Occupiable) cells[row - 1][col]).setOccupant(c);
                ((Occupiable) cells[row][col]).removeOccupant();
                return true;

            case DOWN:
                //check out of bound
                if (!this.isValid((row + 1), col))
                    return false;
                //check the next block is valid to move
                if (!isOccupiableAndNotOccupiedWithCrate((row + 1), col)) {
                    return false;
                }
                //move the crate and remove the old one
                c.setPos(row + 1, col);
                ((Occupiable) cells[row + 1][col]).setOccupant(c);
                ((Occupiable) cells[row][col]).removeOccupant();
                return true;

            case LEFT:
                //check out of bound
                if (!this.isValid(row, (col - 1)))
                    return false;
                //check the next block is valid to move
                if (!isOccupiableAndNotOccupiedWithCrate(row, (col - 1))) {
                    return false;
                }
                //move the crate and remove the old one
                c.setPos(row, (col - 1));
                ((Occupiable) cells[row][col - 1]).setOccupant(c);
                ((Occupiable) cells[row][col]).removeOccupant();
                return true;

            case RIGHT:
                //check out of bound
                if (!this.isValid(row, (col + 1)))
                    return false;
                //check the next block is valid to move
                if (!isOccupiableAndNotOccupiedWithCrate(row, (col + 1))) {
                    return false;
                }
                //move the crate and remove the old one
                c.setPos(row, (col + 1));
                ((Occupiable) cells[row][col + 1]).setOccupant(c);
                ((Occupiable) cells[row][col]).removeOccupant();
                return true;

            default: return false;
        }
    }

    private boolean isValid(int r, int c) {
        return (r >= 0 && r < cells.length && c >= 0 && c < cells[0].length);
    }

    /**
     * @param r The row coordinate
     * @param c The column coordinate
     * @return Whether or not the specified location on the grid is a location which implements Occupiable,
     * yet does not currently have a crate in it. Will return false if out of bounds.
     */
    public boolean isOccupiableAndNotOccupiedWithCrate(int r, int c) {
        //TODO

        //check for out of bound
        if (!this.isValid(r, c))
            return false;

        //check if it is wall
        if (cells[r][c] instanceof Wall)
            return false;

        //check if it is crate
        if (((Occupiable) cells[r][c]).getOccupant().isPresent()) {
            if (((Occupiable) cells[r][c]).getOccupant().get() instanceof Crate)
                return false;
        }

        //check if it is player(safe check)
        if (((Occupiable) cells[r][c]).getOccupant().isPresent()) {
            if (((Occupiable) cells[r][c]).getOccupant().get() instanceof Player)
                return false;
        }

        //check if it is tile
        if (!((Occupiable) cells[r][c]).getOccupant().isPresent()) {
            if (cells[r][c] instanceof Tile)
                return true;
        }

        //check if it is DestTile
        if (!((Occupiable) cells[r][c]).getOccupant().isPresent()) {
            if (cells[r][c] instanceof DestTile)
                return true;
        }

        return false;

    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
