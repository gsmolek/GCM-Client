package managerWindow;

public class collectionEntity {

	private String idCollection;
	private String collectionVertion;
	private String priceOne;
	private String priceSubscription;
	public collectionEntity(String idCollection, String collectionVertion, String priceOne, String priceSubscription) {
		this.idCollection = idCollection;
		this.collectionVertion = collectionVertion;
		this.priceOne = priceOne;
		this.priceSubscription = priceSubscription;
	}
	public String getIdCollection() {
		return idCollection;
	}
	public void setIdCollection(String idCollection) {
		this.idCollection = idCollection;
	}
	public String getCollectionVertion() {
		return collectionVertion;
	}
	public void setCollectionVertion(String collectionVertion) {
		this.collectionVertion = collectionVertion;
	}
	public String getPriceOne() {
		return priceOne;
	}
	public void setPriceOne(String priceOne) {
		this.priceOne = priceOne;
	}
	public String getPriceSubscription() {
		return priceSubscription;
	}
	public void setPriceSubscription(String priceSubscription) {
		this.priceSubscription = priceSubscription;
	}
	
	
}
