package loanapp;

public class HouseLoan implements Loan {
	private double interestrate;
	public HouseLoan() {
		this.interestrate=6.5;
	}
	public HouseLoan(double interestrate) {
		this.interestrate=interestrate;
	}
	public void setInterestrate(double interestrate) { // Corrected setter method name and signature
        this.interestrate = interestrate;
    }
	public double getinterestrate() {
		return interestrate;
	}
	public double calculateamount(double principle,int time) {
		return principle * (1 + (interestrate / 100) * time);
	}
	public String loantype() {
        return "House Loan";		
	}

}
