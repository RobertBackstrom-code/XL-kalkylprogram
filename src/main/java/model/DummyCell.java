package model;

import expr.Environment;
import expr.ExprResult;

public class DummyCell extends Cell {


	@Override
	public ExprResult evaluate(Environment environment) {
		throw new Error("Circular Value");
	}

	@Override
	public String getGuiCellRepresentation(Environment environment) {
		return null;
	}
}
