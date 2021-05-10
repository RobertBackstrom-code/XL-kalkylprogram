package model;

import expr.Environment;
import expr.ErrorExpr;
import expr.ExprResult;

public class CommentCell extends Cell {

	public CommentCell(String expression) {
		this.expression = expression;
		result = new ErrorExpr("Comment cant be part of an expression");
		this.guiCellRepresentation = expression.substring(1);
	}
	
	@Override
	public ExprResult evaluate(Environment environment) {

		return result.value(environment);
	}

	@Override
	public String getGuiCellRepresentation(Environment environment) {
		return guiCellRepresentation;
	}


}
