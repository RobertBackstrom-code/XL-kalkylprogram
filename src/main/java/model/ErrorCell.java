package model;

import expr.Environment;
import expr.ErrorExpr;
import expr.ExprResult;

public class ErrorCell extends Cell{

    public ErrorCell (String expression) {
        this.expression = expression;
        this.result = new ErrorExpr("Circular dependencies");
    }

    @Override
    public ExprResult evaluate(Environment environment) {
        ExprResult tempResult = result.value(environment);
        this.guiCellRepresentation = tempResult.toString();
        return tempResult;
    }

    @Override
    public String getGuiCellRepresentation(Environment environment) {
        return guiCellRepresentation;
    }
}
