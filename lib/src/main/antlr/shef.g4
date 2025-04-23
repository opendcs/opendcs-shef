grammar shef;

options {tokenVocab=shefLexer;}
// The file itself
shefFile: shefFormatStatement* EOF;

// the set of statements in the file.
shefFormatStatement: a_FORMAT | e_FORMAT | b_FORMAT;

// SHEF .A statements
a_FORMAT: A_FORMAT locid date tz? field (SLASH field|A_FORMAT_CONT|field|SLASH)*;

e_FORMAT: E_FORMAT locid date tz? field (SLASH field|E_FORMAT_CONT|field|SLASH)*;

b_FORMAT: B_FORMAT locid date tz? field (SLASH field|field|SLASH)*
        (
                (locid (SLASH value|value|SLASH)*)  |
                (locid (SLASH value|value|SLASH)* B_SEP)
        )*
        B_END;

locid: IDENTIFIER # ID;
date: NUMBER # DATE;
tz: IDENTIFIER # TZ;
value: NUMBER DATACOMMENT? # B_VALUE
      | LETTER DATACOMMENT? # B_VALUE;
field: IDENTIFIER NUMBER? DATACOMMENT? # PEFIELD
     | IDENTIFIER # TIMEFIELD
     | NUMBER # VALUE
     | LETTERS # VALUE;

