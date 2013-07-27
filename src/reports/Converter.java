package reports;

import gui.common.SizeUnits;
import model.Quantity;

public class Converter {

	private static Quantity quantity;
	private static Enum<SizeUnits> from;
	private static Enum<SizeUnits> to;
	private static float value;
	
	private static void init(Quantity q, Enum<SizeUnits> unit) {
		quantity = q;
		value = quantity.getNumber();
		from = quantity.getUnit();
		to = unit;
	}

	public static float convertVolume(Quantity quantity, Enum<SizeUnits> unit) 
			throws IllegalArgumentException {
		init(quantity, unit);
		if(!quantity.isVolume()) {
			throw new IllegalArgumentException();
		}
		return 0;
	}

	public static float convertWeight(Quantity quantity, Enum<SizeUnits> unit) 
			throws IllegalArgumentException {
		init(quantity, unit);
		if(!quantity.isWeight()) {
			throw new IllegalArgumentException();
		}
		
		return 0;
	}

}
