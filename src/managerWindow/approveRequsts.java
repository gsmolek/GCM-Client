package managerWindow;

public class approveRequsts {

	private String priceOnce;
	private String subscriptionPrice;
	private String collectionId;
	private String oldPriceOnce;
	private String oldSubscriptionPrice;
	private String vertion;
	private String requestId;
	
	public String getVertion() {
		return vertion;
	}
	public void setVertion(String vertion) {
		this.vertion = vertion;
	}
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
