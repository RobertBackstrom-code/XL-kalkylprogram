package model;

import expr.ErrorResult;
import util.XLBufferedReader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import expr.Environment;
import expr.ExprResult;
import gui.ModelObserver;

public class XLModel implements CellObservable, Environment {
	public static final int COLUMNS = 10, ROWS = 10;
	private Map<String, Cell> cells;
	private ArrayList<ModelObserver> observers;

	public XLModel() {
		cells = new HashMap<>();
		observers = new ArrayList<>();
	}

	/**
	 * Called when the code for a cell changes.
	 *
	 * @param address address of the cell that is being edited
	 * @param text    the new code for the cell - can be raw text (starting with #)
	 *                or an expression
	 */
	public void update(CellAddress address, String text) {
		// check what text is
		//1. Text is empty
		//2. Text begins with #
		//3. Text is an expression

		Cell newCell = null;


		if (text.length() == 0) {
			cells.remove(address.toString().toLowerCase());
			notifyObserver(address.toString(), "");
		}
		else if (text.charAt(0) == '#') {
			newCell = new CommentCell(text);
		} else {
			//check circular problem
			cells.put(address.toString().toLowerCase(), new DummyCell());
			try {
				newCell = new ExprCell(text);
				newCell.evaluate(this);
			} catch (Error e){
				newCell = new ErrorCell(text);
			}
		}

		if(newCell != null) {
			System.out.println(newCell.getClass().getSimpleName());
			cells.put(address.toString().toLowerCase(), newCell);
		}

		for(Map.Entry<String, Cell> c : cells.entrySet()) {
			c.getValue().evaluate(this);
			notifyObserver(c.getKey().toUpperCase(),c.getValue().getGuiCellRepresentation(this));
			System.out.println("loopar notifyObserver" + c.getValue().getGuiCellRepresentation(this));
		}
	}

	public void loadFile(File file) throws FileNotFoundException {
		XLBufferedReader reader = new XLBufferedReader(file);
		Map<String, String> loadCells = new HashMap<>();
		try {
			reader.load(loadCells);
			for(Map.Entry<String, String> cell : loadCells.entrySet()) {
				int col = cell.getKey().charAt(0) - 'A';
				int row = Integer.parseInt(cell.getKey().substring(1, cell.getKey().length())) - 1;
				CellAddress tempCellAddress = new CellAddress(col, row);
				update(tempCellAddress, cell.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	public void saveFile(File file) {

		try(FileWriter fw = new FileWriter(file, false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw))
		{
			for(Map.Entry<String, Cell> c : cells.entrySet()) {
				out.println(c.getKey().toUpperCase() + "=" + c.getValue().expression);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addObserver(ModelObserver observer) {
		observers.add(observer);
	}

	@Override
	public void notifyObserver(String address, String text) {
		for (ModelObserver mo : observers) {
			mo.cellValueUpdated(address, text);
		}
	}

	@Override
	public ExprResult value(String name) {
		Cell cell = cells.get(name.toLowerCase());
		//CellAddress/Name/Reference does not exist in the model

		if(cell == null) {
			return new ErrorResult(name + " has no value");
		}
		return cell.evaluate(this);
	}

	public String getExpression(String address) {
		String cellExpression = "";
		if(cells.containsKey(address.toString())) {
			cellExpression = cells.get(address).toString();
		}
		return cellExpression;
	}

	public void clearSelected(CellAddress address) {
		if(cells.containsKey(address.toString().toLowerCase())) {
			update(address, "");
		}
	}

	public void clearAll() {
		for(Map.Entry<String, Cell> c : cells.entrySet()) {
			notifyObserver(c.getKey().toUpperCase(), "");
		}
		cells = new HashMap<>();
	}
}
