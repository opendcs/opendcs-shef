package org.opendcs.shefit;

import java.time.format.DateTimeFormatter;

import org.opendcs.shef.parser.shefBaseVisitor;
import org.opendcs.shef.parser.shefParser;
import org.opendcs.shefit.ShefDateTime.ShefDate;
import org.opendcs.shefit.ShefDateTime.ShefTime;

public class ShefFileVisitor extends shefBaseVisitor<DataSet>{

    private DataSet dataSet;
    private ShefRecord.Builder currentValueA = null;
    private ShefDateTime.ShefDate curDateA;

    public ShefFileVisitor(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    
    @Override
    public DataSet visitShefFormatStatement(shefParser.ShefFormatStatementContext ctx) {
        System.out.println("Processing " + ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public DataSet visitA_FORMAT(shefParser.A_FORMATContext ctx) {
        String comment = null;
        //this.currentValueA = new ShefRecord.Builder(ctx.ID().getText())
        //                                   .withComment(comment)
        //                                   .withRevisionStatus(false);
        //curDateA = new ShefDate(ctx.DATE().getText());
        DataSet visitChildren = visitChildren(ctx);
        System.out.println(ctx.getText());
        return visitChildren;
    }

    @Override
    public DataSet visitID(shefParser.IDContext ctx) {
        if (currentValueA != null) {
            dataSet.addValue(currentValueA.build());
            currentValueA = null;
        }
        this.currentValueA = new ShefRecord.Builder(ctx.getText());
        System.out.print("cur val " + currentValueA.build().toString());
        return visitChildren(ctx);
    }
/*
    @Override
    public DataSet visitFIXED_TIME(shefParser.FIXED_TIMEContext ctx) {
        
        System.out.println("TIME: " + ctx.getText());
        String timeStr = ctx.getText();
        ShefTime time = new ShefTime(true, timeStr.substring(0,1),timeStr.substring(2));
        currentValueA.withObservationTime(new ShefDateTime(curDateA, "UTC", time).getZonedDateTime());
        return visitChildren(ctx);
    }

    @Override
    public DataSet visitValue(shefParser.ValueContext ctx) {
        String sensorCode = ctx.PHYSICAL_ELEMENT().getText();
        String valueString = ctx.NUMBER().getText();
        String peCode = sensorCode.substring(0,2);
        Double value = null;
        try {
            value = Double.parseDouble(valueString);
        } catch (NumberFormatException nfe) {
            // missing value stays as null
        }
        
        this.currentValueA.withValue(value);
        this.currentValueA.withParameterCode(peCode);
        this.dataSet.addValue(this.currentValueA.build());
        return this.dataSet;
    }
    */
}
