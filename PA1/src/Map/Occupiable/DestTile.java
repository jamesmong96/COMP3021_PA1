package Map.Occupiable;

import Map.Occupant.Crate;

import java.nio.file.DirectoryStream;
import java.util.function.Function;

/**
 * A destination tile. To win the game, we must push the crate with the corresponding ID onto this tile
 */
public class DestTile extends Tile {
    private char destID;

    /**
     * @param destID The destination uppercase char corresponding to a crate with the same lowercase letter
     */
    public DestTile(char destID) {
        this.destID = destID;
    }

    /**
     * @return Whether or not this destination tile has been completed, i.e. a crate with the matching lowercase letter
     * is currently occupying this tile.
     */
    public boolean isCompleted() {
        //TODO

        //check if there is occupant
        if (!super.getOccupant().isPresent())
            return false;
        //check if the occupant is crate
        if (!(super.getOccupant().get() instanceof Crate))
            return false;
        //initialize the variables to compare ID of the crate and dest
        char dest = Character.toLowerCase(this.destID);
        char c = ((Crate) super.getOccupant().get()).getID();
        //compare the two ID
        if(dest == c)
            return true;
        else return false;
    }

    /**
     * @return The uppercase letter corresponding to the crate with the matching lowercase letter
     */
    private char getDestID() {
        return destID;
    }

    @Override
    public char getRepresentation() {
        //TODO

        if (super.getOccupant().isPresent())
            return super.getOccupant().get().getRepresentation();
        else return getDestID();
    }
}
