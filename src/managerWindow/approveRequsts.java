/**
 * @author GILAD MOLEK
 * @author DORON TUCHMAN
 * @author MATI HALFA
 * @author MATAN ASULIN
 * @author SHARONE BURSHTIEN
 *
 *	@version 1.40
 *	@since 2019
 */
package managerWindow;

public class approveRequsts {
   /**
    * params for approved request data
    */
	private String priceOnce;
	private String subscriptionPrice;
	private String collectionId;
	private String oldPriceOnce;
	private String oldSubscriptionPrice;
	private String vertion;
	private String requestId;
	/*
	 * getter for version
	 * returns a version name
	 */
	public String getVertion() {
		return vertion;
	}
	/*
	 * setter for version
	 */
	public void setVertion(String vertion) {
		this.vertion = vertion;
	}
	/*
	 * getter and setters for all private params
	 * 
	 */
	public String getOldPriceOnce() {
		return oldPriceOnce;
	}
	public void setOldPriceOnce(String oldPriceOnce) {
		this.oldPriceOnce = oldPriceOnce;
	}
	public String getOldSubscriptionPrice() {
		return oldSubscriptionPrice;
	}
	public void setOldSubscriptionPrice(String oldSubscriptionPrice) {
		this.oldSubscriptionPrice = oldSubscriptionPrice;
	}
	public String getPriceOnce() {
		return priceOnce;
	}
	public void setPriceOnce(String priceOnce) {
		this.priceOnce = priceOnce;
	}
	public String getSubscriptionPrice() {
		return subscriptionPrice;
	}
	public void setSubscriptionPrice(String subscriptionPrice) {
		this.subscriptionPrice = subscriptionPrice;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	/**
	 * this constructor init all parameters in approves requst, gets all the written below and sets them 
	 * @param priceOnce
	 * @param subscriptionPrice
	 * @param collectionId
	 * @param oldPriceOnce
	 * @param oldSubscriptionPrice
	 * @param vertion
	 * @param requestId
	 */
	public approveRequsts(String priceOnce, String subscriptionPrice, String collectionId, String oldPriceOnce,
			String oldSubscriptionPrice, String vertion,String requestId) {
		this.priceOnce = priceOnce;
		this.subscriptionPrice = subscriptionPrice;
		this.collectionId = collectionId;
		this.oldPriceOnce = oldPriceOnce;
		this.oldSubscriptionPrice = oldSubscriptionPrice;
		this.vertion = vertion;
		this.requestId = requestId;
	}
	
}
