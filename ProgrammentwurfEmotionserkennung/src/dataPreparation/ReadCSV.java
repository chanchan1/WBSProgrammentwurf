package dataPreparation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import dataTypes.Frame;

public class ReadCSV {

	private String				csvFile;
	private static final String	CSV_SEPERATOR	= ";";

	public ReadCSV(String csvFile){
		this.csvFile = csvFile;
	}

	/**
	 * Reads a CSV file with the columns Frame, Stirnfalten, Mundfalte, Lid (in
	 * that order) and generates an ArrayList<Frame> out of it
	 * 
	 * @return ArrayList<Frame>
	 * @throws IOException
	 */
	public ArrayList<Frame> readCSVData() throws IOException{
		String line = "";
		ArrayList<Frame> frames = new ArrayList<Frame>();

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			// read column definition line
			line = br.readLine();
			String[] columns = line.split(CSV_SEPERATOR); // column =>
															// {Frame,Stirnfalten,Mundfalte,Lid}

			// read all frames
			while ((line = br.readLine()) != null) {
				String[] properties = line.split(CSV_SEPERATOR);// =>
																// {Frame,Stirnfalten,Mundfalte,Lid}

				int abs_furrowedbrow = Integer.parseInt(properties[1]);
				int abs_marionettelines = Integer.parseInt(properties[2]);
				boolean beyelid = properties[3].equals("1") ? true : false;

				frames.add(new Frame(abs_furrowedbrow, abs_marionettelines,
						beyelid));
			}
		} catch (NumberFormatException e) {
			System.out.println("One or more numbers couldn't be processed!");
		}
		return frames;
	}

}
