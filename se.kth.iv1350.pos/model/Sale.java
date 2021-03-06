package model;

import dBHandler.ItemDTO;

import java.util.ArrayList;

/**
 * Sale represents an instance of the ongoing sale
 *@author Abyel Tesfay
 */
public class Sale {
	private int runningTotal;
	private int runningVAT;
	private double vATrate;
	private TotalPayment payment;
	private ArrayList<ItemDTO> soldItems;
	
	/**
	 * Creates an instance with a new array
	 * of itemDTOs
	 */
	public Sale() {
		soldItems = new ArrayList<ItemDTO>();
	}
	
	/**
	 * updateSale updates the current sale with the running
	 * total, sold items and VATrate.
	 * @param currentItem the current item in focus
	 */
	public void updateSale(ItemDTO currentItem) {
		calculateTotal(currentItem);
		
		if(itemOfSameType(currentItem))
			return;
		else
			soldItems.add(currentItem);
	}
	/**
	 * printRecipe returns an Recipe instance
	 * by using an saleDTO as parameter
	 * @param completedSale an SaleDTO instance with 
	 * information about the completed sale 
	 * @return a Recipe instance
	 */
	public Receipe printRecipe(SaleDTO completedSale) {
		return Printer.printRecipe(completedSale);
	}

	/**
	 * payForSale handles the payment required for an complete
	 * sale, until the amount cash requested has been paid off
	 * it will continue throwing exceptions
	 * @param amountPaid the amount cash given
	 * @param totalRequired the amount cash the ongoing sale requires
	 * @return any change left by the payment
	 * @throws AmountLeftException if the total cash paid
	 * is less than the amount required
	 */
	public int payForSale(int amountPaid, int totalRequired) throws Exception {
		if (this.payment == null)
			this.payment = new TotalPayment(totalRequired);
		this.payment.addPayment(amountPaid);
		int amountLeft = this.payment.getChange();
		if(amountLeft > 0)
			throw new CashAmountLeftException(amountLeft);
		else
			return -amountLeft;
	}
	
	public int getRunningTotal() {
		return this.runningTotal;
	}
	
	public int getRunningVAT() {
		return this.runningVAT;
	}
	
	public ArrayList<ItemDTO> getSoldItems() {
		return this.soldItems;
	}
	
	public TotalPayment getPayment() {
		return this.payment;
	}
	
	public double getVATrate() {
		return this.vATrate;
	}
	
	private boolean itemOfSameType(ItemDTO currentItem) {
		for(int i = 0; i < this.soldItems.size(); i++) {
			if( this.soldItems.get(i).getItemID().equals(currentItem.getItemID())) {
				this.soldItems.set(i, new ItemDTO(currentItem.getName(), currentItem.getItemID(), 
						(this.getSoldItems().get(i).getQuantity()) + 1, currentItem.getPrice() 
						+ this.getSoldItems().get(i).getPrice(), currentItem.getVATrate()));
				return true;
			}
		}
		return false;
	}
	
	private void calculateTotal(ItemDTO currentItem) {
		this.runningTotal += (currentItem.getPrice());
	}
	
}


