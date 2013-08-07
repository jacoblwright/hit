package data;

import gui.common.SizeUnits;

import java.util.Date;

import model.*;

/**
 * Data transfer object for Product.
 */
public class ProductDTO {
    
    private int id;
    private Date creationDate;
    private String upc;
    private String description;
    private float number;
    private Enum<SizeUnits> unit;
    private int shelfLife;
    private int threeMonthSupply;

    public void setProduct(Product product) {
        this.id = product.getId();
        this.creationDate = product.getCreationDate();
        this.upc = product.getUPC().getBarcode();
        this.description = product.getDescription();
        this.number = product.getSize().getNumber();
        this.unit = product.getSize().getUnit();
        this.shelfLife = product.getShelfLife();
        this.threeMonthSupply = product.getThreeMonthSupply();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date date) {
		this.creationDate = date;
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String barcode) {
		this.upc = barcode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getNumber() {
		return number;
	}

	public void setNumber(float number) {
		this.number = number;
	}

	public Enum<SizeUnits> getUnit() {
		return unit;
	}
	
	public String getUnitStr(){
		return unit.toString();
	}

	public void setUnit(Enum<SizeUnits> unit) {
		this.unit = unit;
	}
	
	public void setUnit(String unit) {
		String capUnit = "";
		if(unit.equals("fluid ounces")){
			capUnit = "FluidOunces";
		}
		else capUnit = unit.substring(0, 1).toUpperCase() + unit.substring(1);
		this.unit = SizeUnits.valueOf(capUnit);
	}

	public int getShelfLife() {
		return shelfLife;
	}

	public void setShelfLife(int shelfLife) {
		this.shelfLife = shelfLife;
	}

	public int getThreeMonthSupply() {
		return threeMonthSupply;
	}

	public void setThreeMonthSupply(int threeMonthSupply) {
		this.threeMonthSupply = threeMonthSupply;
	} 
    
    

}
