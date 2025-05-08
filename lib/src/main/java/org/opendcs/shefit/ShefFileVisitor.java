package org.opendcs.shefit;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opendcs.shef.parser.shefBaseVisitor;
import org.opendcs.shef.parser.shefParser;
import org.opendcs.shef.parser.shefParser.FIELDContext;
import org.opendcs.shef.parser.shefParser.FieldContext;
import org.opendcs.shefit.ShefDateTime.ShefDate;
import org.opendcs.shefit.ShefDateTime.ShefTime;

public class ShefFileVisitor extends shefBaseVisitor<DataSet>{

    private DataSet dataSet;
    private ShefRecord.Builder currentValueA = null;
    private ShefDateTime.ShefDate curDateA;
    private String currentTz = null;

    public ShefFileVisitor(DataSet dataSet) {
        this.dataSet = dataSet;
    }
    
    @Override
    public DataSet visitShefFormatStatement(shefParser.ShefFormatStatementContext ctx) {
        //System.out.println("Processing " + ctx.getText());
        return visitChildren(ctx);
    }

    @Override
    public DataSet visitA_FORMAT(shefParser.A_FORMATContext ctx) {
        String comment = null;
        this.currentValueA = new ShefRecord.Builder(ctx.locid().getText())
                                           .withComment(comment)
                                           .withRevisionStatus(false)
                                           ;
        curDateA = new ShefDate(ctx.date().getText());
        currentTz = ctx.tz().getText();
        for (FieldContext f :ctx.field())
        {
            System.out.println(f.getClass().getName());
            System.out.println(f.getText());
            
        }
        System.out.println(ctx.toStringTree());
        DataSet visitChildren = visitChildren(ctx);
        
        System.out.println("ZonedDT: " + currentValueA.build().getObservationTime().format(DateTimeFormatter.ISO_DATE_TIME));
        return visitChildren;
    }

    @Override
    public DataSet visitFIELD(FIELDContext ctx) {
        final String dateString = ctx.getText();
        if (dateString.startsWith("D")) {
            System.out.println("FIELD: " + dateString);
            Pattern pattern = Pattern.compile("(D[a-zA-Z]+)([0-9]+)");
            Matcher matcher = pattern.matcher(dateString);
            matcher.matches();
            System.out.println(matcher.toString());
                        
            ShefDateTime.ShefTime dt = new ShefDateTime.ShefTime(true, matcher.group(1), matcher.group(2));
            currentValueA.withObservationTime(new ShefDateTime(curDateA, currentTz != null ? currentTz : "UTC", dt).getZonedDateTime());
        }
        return dataSet;
    }

    
    
}
