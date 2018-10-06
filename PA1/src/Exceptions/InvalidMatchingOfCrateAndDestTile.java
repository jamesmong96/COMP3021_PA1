package Exceptions;

//The number and the ID of all the crate should same as those of the DestTile
//If the number is not the same or the ID is not matching, it will throw to this exception

public class InvalidMatchingOfCrateAndDestTile extends InvalidMapException {
    /**
     * @param s The exception message
     */
    public InvalidMatchingOfCrateAndDestTile(String s) {
        super(s);
    }

}
