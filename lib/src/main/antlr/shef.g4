grammar shef;

options {tokenVocab=shefLexer;}
// The file itself
shefFile: shefFormatStatement* EOF;

// the set of statements in the file.
shefFormatStatement: a_FORMAT;

// SHEF .A statements
a_FORMAT: A_FORMAT locid date tz? field (SLASH field)* SLASH*
        (A_FORMAT_CONT SLASH? field (SLASH field)* SLASH*)*;

locid: IDENTIFIER # ID;
date: NUMBER # DATE;
tz: IDENTIFIER # TZ;
field: IDENTIFIER NUMBER? DATACOMMENT? # PEFIELD
     | IDENTIFIER # TIMEFIELD;

