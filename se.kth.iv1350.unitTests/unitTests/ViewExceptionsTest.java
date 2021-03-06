package unitTests;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import controller.CannotFetchItemException;
import controller.Controller;
import controller.OperationFailedException;
import controller.SaleNotCompleteException;
import dBHandler.ExternalAccounting;
import dBHandler.ExternalInventory;
import dBHandler.Logger;
import model.CashRegister;
import model.SaleDTO;

//Seminar 4
public class ViewExceptionsTest {
	private Controller contr;
	
	@BeforeEach
	public void setUp() throws Exception {
		CashRegister cashReg = new CashRegister();
		ExternalInventory eInventory = new ExternalInventory();
		ExternalAccounting eAccounting = new ExternalAccounting();
		Logger logger = new Logger();
		contr = new Controller(cashReg, eInventory, eAccounting, logger);
	}
	
	@Test
	/*Assert that an exception is returned if no item
	 * with the given itemID is found*/
	public void wrongItemIDGiven() {
		String wrongItemID = "413";
		int amount = 10;
		
		try {
			contr.addItem(wrongItemID, amount);
		}
		catch (CannotFetchItemException e) {
			Assert.assertThat("Incorrect item identfier", e, CoreMatchers.isA(CannotFetchItemException.class)); 
		} 
		catch (Exception e) {
			//will not happen
		}
	}	
	
	@Test
	/*Assert that an exception is returned if an 
	 * "unexpected error occurs during an operation
	 */
	public void unknownErrorOccured() {
		String UnkownItemID = "000";
		try {
			contr.addItem(UnkownItemID, 99);
		}
		catch (OperationFailedException e) {
			Assert.assertThat("Unknown error occurred", e, CoreMatchers.isA(OperationFailedException.class)); 
		} 
		catch (Exception e) {
			//will not happen
		}
	}
	
	@Test
	public void notEnoughPaymentRecieved() throws Exception {
		SaleDTO TestSale = null;
		try {
			TestSale = preparePayment();
		} catch (CannotFetchItemException e) {
		} catch (OperationFailedException e) {
		}
		
		//ignore these catch-blocks, as they are not part of the test
		try {
			contr.enterAmountPaid(92, TestSale);
		} 
		catch (SaleNotCompleteException e) {
			Assert.assertThat("Not enough was paid", e, CoreMatchers.isA(SaleNotCompleteException.class)); 
		} 
	}
	
	private SaleDTO preparePayment() throws Exception {
		contr.startNewSale();
		contr.addItem("111", 3);
		contr.addItem("333", 2);
		return contr.indicateAllItemsRegistered();	
	}
}
