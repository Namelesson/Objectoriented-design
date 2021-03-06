package dBHandler;

/**
 * InvalidItemException is thrown when none 
 * of the itemIDs in the inventory matches
 * the given itemID 
 * @author Abyel Tesfay
 */
public class InvalidItemException extends Exception {
	
	/**
	 * Creates an ExternalInventoryException instance explaining that
	 * the given itemID was not found in the database
	 * @param WrongItemID the given ItemID
	 */
	public InvalidItemException(String itemID) {
		super("No matching itemID was found in inventory, Given ID: " + itemID);
	}
}
