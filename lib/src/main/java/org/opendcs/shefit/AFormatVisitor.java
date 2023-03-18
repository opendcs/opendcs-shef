package org.opendcs.shefit;

import org.opendcs.shef.parser.shefBaseVisitor;
import org.opendcs.shef.parser.shefParser;

public class AFormatVisitor extends shefBaseVisitor<String>{
    @Override
    public String visitA_FORMAT(shefParser.A_FORMATContext ctx) { 
        return ctx.ID().getText();
        //return visitChildren(ctx); 
    }
}
