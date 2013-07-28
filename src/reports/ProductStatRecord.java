package reports;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.Quantity;

public class ProductStatRecord implements Record, Comparable<ProductStatRecord> {

	private String description;
	private String barcode;
	private Quantity size;
	private int threeMonthSupply;
	private int currentSupply;
	private double averageSupply;
	private int minimumSupply;
	private int maximumSupply;
	private int usedSupply;
	private int addedSupply;
	private int shelfLife;
	private double usedAge;
	private int maximumAge;
	private double currentAverageSupply;
	private int maximumCurrentSupply;
	
	private DecimalFormat df = new DecimalFormat("0.##");
	
    public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getBarcode() {
		return barcode;
	}



	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}



	public Quantity getSize() {
		return size;
	}



	public void setSize(Quantity size) {
		this.size = size;
	}



	public int getThreeMonthSupply() {
		return threeMonthSupply;
	}



	public void setThreeMonthSupply(int threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	}



	public int getCurrentSupply() {
		return currentSupply;
	}



	public void setCurrentSupply(int currentSupply) {
		this.currentSupply = currentSupply;
	}



	public double getAverageSupply() {
		return averageSupply;
	}



	public void setAverageSupply(double averageSupply) {
		this.averageSupply = averageSupply;
	}



	public int getMinimumSupply() {
		return minimumSupply;
	}



	public void setMinimumSupply(int minimumSupply) {
		this.minimumSupply = minimumSupply;
	}



	public int getMaximumSupply() {
		return maximumSupply;
	}



	public void setMaximumSupply(int maximumSupply) {
		this.maximumSupply = maximumSupply;
	}



	public int getUsedSupply() {
		return usedSupply;
	}



	public void setUsedSupply(int usedSupply) {
		this.usedSupply = usedSupply;
	}



	public int getAddedSupply() {
		return addedSupply;
	}



	public void setAddedSupply(int addedSupply) {
		this.addedSupply = addedSupply;
	}



	public int getShelfLife() {
		return shelfLife;
	}



	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}



	public double getUsedAge() {
		return usedAge;
	}



	public void setUsedAge(double usedAge) {
		this.usedAge = usedAge;
	}



	public double getMaximumAge() {
		return maximumAge;
	}



	public void setMaximumAge(int maximumAge) {
		this.maximumAge = maximumAge;
	}

	

	public double getCurrentAverageSupply() {
		return currentAverageSupply;
	}



	public void setCurrentAverageSupply(double currentAverageSupply) {
		this.currentAverageSupply = currentAverageSupply;
	}



	public int getMaximumCurrentSupply() {
		return maximumCurrentSupply;
	}



	public void setMaximumCurrentSupply(int maximumCurrentSupply) {
		this.maximumCurrentSupply = maximumCurrentSupply;
	}



	@Override
    public List<String> getValuesAsStrings() {
		List<String> productAttributes = new ArrayList();
		
		productAttributes.add(description);
		productAttributes.add(barcode);
		productAttributes.add(df.format(size.getNumber()) + " " + size.getUnit().toString());
		productAttributes.add(Integer.toString(threeMonthSupply));
		
		productAttributes.add(Integer.toString(currentSupply) + " / " + 
								df.format(averageSupply));
		
		productAttributes.add(Integer.toString(minimumSupply) + " / " + 
								Integer.toString(maximumSupply));
		
		productAttributes.add(Integer.toString(usedSupply) + " / " +
								Integer.toString(addedSupply));
		
		if(shelfLife == 0)
			productAttributes.add("");
		else productAttributes.add(Integer.toString(shelfLife) + " months");
		
		productAttributes.add(df.format(usedAge) + " / " + 
								Integer.toString(maximumAge));
		
		productAttributes.add(df.format(currentAverageSupply) + " / " + 
								Integer.toString(maximumCurrentSupply));
		
        return productAttributes;
    }
	
	public String toString(){
		return "ProductStatRecord[ description= " + description + 
				", barcode= " + barcode +
				", size= " + (int)size.getNumber() + " " + size.getUnit().toString() +
				", threeMonthSupply= " + Integer.toString(threeMonthSupply) +
				", currentSupply= " + Integer.toString(currentSupply) +
				", averageSupply= " + Double.toString(averageSupply) +
				", minimumSupply= " + Integer.toString(minimumSupply) +
				", maximumSupply= " + Integer.toString(maximumSupply) +
				", shelfLife= " + Integer.toString(shelfLife) + " months" +
				", usedAverageAge= " + Double.toString(usedAge) +
				", usedMaximumAge= " + Integer.toString(maximumAge) +
				", currentAverageSupply= " + Double.toString(currentAverageSupply) +
				", currentMaximumSupply= " + Integer.toString(maximumCurrentSupply) + "\n";
	}
	
	@Override
	public int compareTo(ProductStatRecord o) {
		return this.getDescription().compareTo(o.getDescription());
	}

}
