package model;

import expr.Environment;
import expr.Expr;
import expr.ExprResult;

public abstract class Cell {
	protected Expr result;
	protected String expression;
	protected String guiCellRepresentation;
	
	public abstract ExprResult evaluate(Environment environment);

	public String toString() {
		return expression;
	}

	public abstract String getGuiCellRepresentation(Environment environment);


	
}
