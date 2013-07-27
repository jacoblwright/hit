package reports;

import java.util.Map;
import java.util.TreeMap;

import gui.common.SizeUnits;
import model.Quantity;

public class Converter {

	private  Quantity quantity;
	private  Enum<SizeUnits> from;
	private  Enum<SizeUnits> to;
	private  float value;
	private  Map<String, Float> weightConverter;
	private  Map<String, Float> volumeConverter;
	
	//----------------Weight Conversions-------------------
	
	private  float  poundsToOunces  = 16;
	private  float poundsToGrams = 453.592f;
	private  float poundsToKilograms = 0.453592f;
	
	private  float ouncesToPounds = (1 / poundsToOunces);
	private  float ouncesToGrams = 28.3495f;
	private  float ouncesToKilograms = 0.0283495f;
	
	private  float gramsToPounds = (1 / poundsToGrams);
	private  float gramsToOunces = (1 / ouncesToGrams);
	private  float gramsToKilograms = 0.001f;
	
	private  float kilogramsToPounds = (1 / poundsToKilograms);
	private  float kilogramsToOunces = (1 / ouncesToKilograms);
	private  float kilogramsToGrams = (1 / gramsToKilograms);
	
	
	//----------------VolumeConversion----------------------
	
	private  float gallonsToQuarts = 4;
	private  float gallonsToPints = 8;
	private  float gallonsToFluidOunces =  128;
	private  float gallonsToLiters = 3.78541f;
	
	private  float quartsToGallons = (1 / gallonsToQuarts);
	private  float quartsToPints = 2;
	private  float quartsToFluidOunces = 32;
	private  float quartsToLiters = 0.946353f;

	private  float pintsToGallons = (1 / gallonsToPints);	
	private  float pintsToQuarts = (1 / quartsToPints);	
	private  float pintsToFluidOunces = 16;	
	private  float pintsToLiters = 0.473176f;
	
	private  float fluidOuncesToGallons = (1 / gallonsToFluidOunces);	
	private  float fluidOuncesToQuarts = (1 / quartsToFluidOunces);	
	private  float fluidOuncesToPints = (1 / pintsToFluidOunces);	
	private  float fluidOuncesToLiters = 0.0295735f;
	
	private  float litersToGallons = (1 / gallonsToLiters);	
	private  float litersToQuarts = (1 / quartsToLiters);	
	private  float litersToPints = (1 / pintsToLiters);	
	private  float litersToFluidOunces = (1 / fluidOuncesToLiters);
	
	//------------------------------------------------------
	
	public Converter() {
		initVolumeConverter();
		initWeightConverter();
	}
	
	private  void init(Quantity q, Enum<SizeUnits> unit) {
		quantity = q;
		value = quantity.getNumber();
		from = quantity.getUnit();
		to = unit;
	}

	public  float convertVolume(Quantity quantity, Enum<SizeUnits> unit) 
			throws IllegalArgumentException {
		init(quantity, unit);
		if(!quantity.isVolume()) {
			throw new IllegalArgumentException();
		}
		
		if(from.equals(to)) {
			return value;
		}

		return value * volumeConverter.get(from.name() + to.name());
	}

	

	public  float convertWeight(Quantity quantity, Enum<SizeUnits> unit) 
			throws IllegalArgumentException {
		init(quantity, unit);
		if(!quantity.isWeight()) {
			throw new IllegalArgumentException();
		}
		
		if(from.equals(to)) {
			return value;
		}
		
		return value * weightConverter.get(from.name() + to.name());
	}


	
	private  void initVolumeConverter() {
		volumeConverter = new TreeMap<String, Float>();
		volumeConverter.put(SizeUnits.Gallons.name() + SizeUnits.Quarts.name(), gallonsToQuarts);
		volumeConverter.put(SizeUnits.Gallons.name() + SizeUnits.Pints.name(), gallonsToPints);
		volumeConverter.put(
				SizeUnits.Gallons.name() + SizeUnits.FluidOunces.name(), gallonsToFluidOunces);
		volumeConverter.put(SizeUnits.Gallons.name() + SizeUnits.Liters.name(), gallonsToLiters);
		
		volumeConverter.put(SizeUnits.Quarts.name() + SizeUnits.Gallons.name(), quartsToGallons);
		volumeConverter.put(SizeUnits.Quarts.name() + SizeUnits.Pints.name(), quartsToPints);
		volumeConverter.put(
				SizeUnits.Quarts.name() + SizeUnits.FluidOunces.name(), quartsToFluidOunces);
		volumeConverter.put(SizeUnits.Quarts.name() + SizeUnits.Liters.name(), quartsToLiters);
		
		volumeConverter.put(SizeUnits.Pints.name() + SizeUnits.Gallons.name(), pintsToGallons);
		volumeConverter.put(SizeUnits.Pints.name() + SizeUnits.Quarts.name(), pintsToQuarts);
		volumeConverter.put(
				SizeUnits.Pints.name() + SizeUnits.FluidOunces.name(), pintsToFluidOunces);
		volumeConverter.put(SizeUnits.Pints.name() + SizeUnits.Liters.name(), pintsToLiters);
		
		volumeConverter.put(
				SizeUnits.FluidOunces.name() + SizeUnits.Gallons.name(), fluidOuncesToGallons);
		volumeConverter.put(
				SizeUnits.FluidOunces.name() + SizeUnits.Quarts.name(), fluidOuncesToQuarts);
		volumeConverter.put(
				SizeUnits.FluidOunces.name() + SizeUnits.Pints.name(), fluidOuncesToPints);
		volumeConverter.put(
				SizeUnits.FluidOunces.name() + SizeUnits.Liters.name(), fluidOuncesToLiters);
		
		volumeConverter.put(SizeUnits.Liters.name() + SizeUnits.Gallons.name(), litersToGallons);
		volumeConverter.put(SizeUnits.Liters.name() + SizeUnits.Quarts.name(), litersToQuarts);
		volumeConverter.put(SizeUnits.Liters.name() + SizeUnits.Pints.name(), litersToPints);
		volumeConverter.put(
				SizeUnits.Liters.name() + SizeUnits.FluidOunces.name(), litersToFluidOunces);
	}
	
	private  void initWeightConverter() {
		weightConverter = new TreeMap<String, Float>();
		weightConverter.put(SizeUnits.Pounds.name() + SizeUnits.Ounces.name(), poundsToOunces);
		weightConverter.put(SizeUnits.Pounds.name() + SizeUnits.Grams.name(), poundsToGrams);
		weightConverter.put(
				SizeUnits.Pounds.name() + SizeUnits.Kilograms.name(), poundsToKilograms);
		
		weightConverter.put(SizeUnits.Ounces.name() + SizeUnits.Pounds.name(), ouncesToPounds);
		weightConverter.put(SizeUnits.Ounces.name() + SizeUnits.Grams.name(), ouncesToGrams);
		weightConverter.put(
				SizeUnits.Ounces.name() + SizeUnits.Kilograms.name(), ouncesToKilograms);
		
		weightConverter.put(SizeUnits.Grams.name() + SizeUnits.Pounds.name(), gramsToPounds);
		weightConverter.put(SizeUnits.Grams.name() + SizeUnits.Ounces.name(), gramsToOunces);
		weightConverter.put(
				SizeUnits.Grams.name() + SizeUnits.Kilograms.name(), gramsToKilograms);
		
		weightConverter.put(
				SizeUnits.Kilograms.name() + SizeUnits.Pounds.name(), kilogramsToPounds);
		weightConverter.put(
				SizeUnits.Kilograms.name() + SizeUnits.Ounces.name(), kilogramsToOunces);
		weightConverter.put(
				SizeUnits.Kilograms.name() + SizeUnits.Grams.name(), kilogramsToGrams);
		
	}


}
