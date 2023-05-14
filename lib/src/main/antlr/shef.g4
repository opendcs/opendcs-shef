grammar shef;

options {tokenVocab=shefLexer;}
// The file itself
shefFile: shefFormatStatement+;

// the set of statements in the file.
shefFormatStatement: a_FORMAT;

// SHEF .A statements
a_FORMAT: A_FORMAT ID DATE TIMEZONE? (field)+ ;
//a_FORMAT_CONTINUATION: A_CONTINUATION (field)+;


field: (peField|timeField) SLASH?;
peField: PHYSICAL_ELEMENT NUMBER;
timeField: TIME_TYPES NUMBER SLASH;
