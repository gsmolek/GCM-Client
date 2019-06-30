package Login;

public class visa {
	private int cardNumber;
	private int expirationM;
	private int expirationy;
	private int cvv;

	public visa() {
		this.cardNumber =0;
		this.expirationM =0;
		this.expirationy = 0;
		this.cvv = 0;
	}

	public visa(int card, int exM, int exY, int cvv) {
		this.cardNumber = card;
		this.expirationM = exM;
		this.expirationy = exY;
		this.cvv = cvv;
	}

	public int getCard() {
		return this.cardNumber;
	}

	public int getExM() {
		return this.expirationM;
	}

	public int getExY() {
		return this.expirationy;
	}

	public int getCvv() {
		return this.cvv;
	}

	public void setCard(int card) {
		this.cardNumber = card;
	}

	public void setExM(int exM) {
		this.expirationM = exM;
	}

	public void setExY(int exY) {
		this.expirationy = exY;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}

	public boolean isEmptyVisa() {
		if(cardNumber == 0 ||  expirationM ==0 ||  expirationy == 0 || cvv==0)
			return true;
		return false;
		
	}
}
