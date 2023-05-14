grammar shef;

options {tokenVocab=shefLexer;}
// The file itself
shefFile: shefFormatStatement+;

// the set of statements in the file.
shefFormatStatement: a_FORMAT;

// SHEF .A statements
a_FORMAT: A_FORMAT ID DATE TIMEZONE? field (SLASH field)* ;

field: peField
     | timeField;

peField: PHYSICAL_ELEMENT NUMBER?; // ELEMENT WITH NO number is "missing value"

timeField: TIME_TYPES NUMBER ;
