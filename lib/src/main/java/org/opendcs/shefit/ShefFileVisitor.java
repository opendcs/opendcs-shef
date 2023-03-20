package org.opendcs.shefit;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.opendcs.shef.parser.shefBaseVisitor;
import org.opendcs.shef.parser.shefLexer;
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
        this.currentValueA = new ShefRecord.Builder(ctx.ID().getText());
        DataSet visitChildren = visitChildren(ctx);
        System.out.println("ZonedDT: " + currentValueA.build().getObservationTime().format(DateTimeFormatter.ISO_DATE_TIME));
        return visitChildren;
    }

    @Override
    public DataSet visitObservationTime(shefParser.ObservationTimeContext ctx) {
        
        //ctx.DATE().getSymbol().equals(shefLexer.)
        return visitChildren(ctx);
    }

    @Override
    public DataSet visitDate(shefParser.DateContext ctx) {
        System.out.println("FULL : " + (ctx.FULLDATE() == null));
        System.out.println("SHORT : " + (ctx.SHORT_DATE() == null));
        System.out.println("VERY : " + (ctx.VERY_SHORT_DATE() == null));
        curDateA = new ShefDate(ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public DataSet visitTime(shefParser.TimeContext ctx) {
        System.out.println("TIME: " + ctx.getText());
        System.out.println("FIXED TIME: " + ctx.FixedTime().getText());
        boolean fixed = ctx.FixedTime() == null;
        String timeStr = ctx.getText();
        ShefTime time;
        if (fixed) {
            time = new ShefTime(fixed, timeStr.substring(0,1),timeStr.substring(2));
        } else {
            time = new ShefTime(fixed, timeStr.substring(0,2),timeStr.substring(3));
        }
        currentValueA.withObservationTime(new ShefDateTime(curDateA, "UTC", time).getZonedDateTime());
        return visitChildren(ctx);
    }
}