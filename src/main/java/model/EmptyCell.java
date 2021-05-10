package model;

import expr.Environment;
import expr.ExprResult;

public class EmptyCell extends Cell {




	@Override
	public ExprResult evaluate(Environment environment) {
		return result.value(environment);
	}

	@Override
	public String getGuiCellRepresentation(Environment environment) {
		return null;
	}

}
