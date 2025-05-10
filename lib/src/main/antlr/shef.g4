grammar shef;

options {tokenVocab=shefLexer;}
// The file itself
shefFile: shefFormatStatement* EOF;

// the set of statements in the file.
shefFormatStatement: a_FORMAT | e_FORMAT | b_FORMAT;

// SHEF .A statements
a_FORMAT: A_FORMAT locid date tz? field (SLASH field|A_FORMAT_CONT|field|SLASH)*;

e_FORMAT: E_FORMAT locid date tz? field (SLASH (field|value)|E_FORMAT_CONT|(field|value)|SLASH)*;

b_FORMAT: B_FORMAT locid date tz? b_header b_data*;
//field (SLASH field|field|SLASH)*
  //      (locid (SLASH value|value|SLASH)* B_SEP?)*
    //    B_END;
b_header: field (SLASH field|field|SLASH)*;
b_data: LOCATION_IDENTIFIER (SLASH value|value|SLASH)* SEP?;

locid: IDENTIFIER # ID;
date: NUMBER # DATE;
tz: IDENTIFIER # TZ;
value: NUMBER DATACOMMENT? # B_VALUE
     | LETTER DATACOMMENT? # B_VALUE;
//field: IDENTIFIER # FIELD
//     | (IDENTIFIER NUMBER? DATACOMMENT?) # FIELD
//     | NUMBER # VALUE
//     | LETTER # VALUE;
field: IDENTIFIER (NUMBER|LETTER)? DATACOMMENT? # FIELD;
