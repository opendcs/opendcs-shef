package org.opendcs.shefit;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opendcs.shef.parser.shefBaseVisitor;
import org.opendcs.shef.parser.shefParser;
import org.opendcs.shef.parser.shefParser.E_FORMATContext;
import org.opendcs.shef.parser.shefParser.FIELDContext;
import org.opendcs.shef.parser.shefParser.FieldContext;
import org.opendcs.shefit.ShefDateTime.ShefDate;

public class ShefFileVisitor extends shefBaseVisitor<DataSet>{

    private DataSet dataSet;
    private ShefRecord.Builder currentValue = null;
    
    private ShefDateTime.ShefDate curDate;
    private String currentTz = null;
    private Duration currentInterval = null;

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
        System.out.println(ctx.A_FORMAT().getText());
        this.currentValue = new ShefRecord.Builder(ctx.locid().getText())
                                           .withComment(comment)
                                           .withRevisionStatus(ctx.A_FORMAT().getText().endsWith("R"))
                                           ;
        curDate = new ShefDate(ctx.date().getText());
        currentTz = ctx.tz().getText();
        for (FieldContext f :ctx.field())
        {
            System.out.println(f.getClass().getName());
            System.out.println(f.getText());
            
        }
        System.out.println(ctx.toStringTree());
        DataSet visitChildren = visitChildren(ctx);
        
        System.out.println("ZonedDT: " + currentValue.build().getObservationTime().format(DateTimeFormatter.ISO_DATE_TIME));
        return visitChildren;
    }

    @Override
    public DataSet visitFIELD(FIELDContext ctx) {
        final String dateString = ctx.getText();
        if (dateString.startsWith("DH")) {
            System.out.println("FIELD: " + dateString);
            Pattern pattern = Pattern.compile("(D[a-zA-Z]+)([0-9]+)");
            Matcher matcher = pattern.matcher(dateString);
            matcher.matches();
                        
            ShefDateTime.ShefTime dt = new ShefDateTime.ShefTime(true, matcher.group(1), matcher.group(2));
            currentValue.withObservationTime(new ShefDateTime(curDate, currentTz != null ? currentTz : "UTC", dt).getZonedDateTime());
        } else {
            System.out.println("Data FIELD: " + dateString);
            final String code = ctx.IDENTIFIER().getText();
            currentValue.parameterCode = code;
            System.out.println(code);
            currentValue.value = ctx.NUMBER() !=null ? Double.parseDouble(ctx.NUMBER().getText()) : null;
            currentValue.comment = ctx.DATACOMMENT() != null ? ctx.DATACOMMENT().getText() : null;
            dataSet.addValue(currentValue.build());
        }
        return dataSet;
    }
   

    @Override
    public DataSet visitE_FORMAT(E_FORMATContext ctx) {
        String comment = null;
        System.out.println(ctx.E_FORMAT().getText());
        this.currentValue = new ShefRecord.Builder(ctx.locid().getText())
                                           .withComment(comment)
                                           .withRevisionStatus(ctx.E_FORMAT().getText().endsWith("R"))
                                           ;
        curDate = new ShefDate(ctx.date().getText());
        currentTz = ctx.tz().getText();
        for (FieldContext f :ctx.field())
        {
            System.out.println(f.getClass().getName());
            System.out.println(f.getText());
            
        }
        System.out.println(ctx.toStringTree());

        return visitChildren(ctx);
    }

    
    
}
