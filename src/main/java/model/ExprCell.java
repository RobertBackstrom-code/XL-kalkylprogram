package model;

import java.io.IOException;

import expr.Environment;
import expr.ErrorExpr;
import expr.ExprParser;
import expr.ExprResult;

public class ExprCell extends Cell {

	public ExprCell(String expression) {
		this.expression = expression;
		try {
			this.result = new ExprParser().build(expression);
		} catch (IOException e) {
			this.result = new ErrorExpr(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public ExprResult evaluate(Environment environment) {
		ExprResult tempResult = result.value(environment);
		guiCellRepresentation = tempResult.toString();
		return tempResult;
	}

	@Override
	public String getGuiCellRepresentation(Environment environment) {
		return guiCellRepresentation;
	}
}
