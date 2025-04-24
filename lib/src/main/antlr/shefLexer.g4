lexer grammar shefLexer;

tokens {COMMENT, DATACOMMENT, IDENTIFIER, NUMBER, SLASH, NEWLINE, LOCATION_IDENTIFIER, SEP}

B_FORMAT: '.B' 'R'? ' ' -> mode(B_MODE);
//B_END: NEWLINE '.END';

A_FORMAT_CONT: '.A' 'R'? DIGIT+ ->  pushMode(AE_MODE);
A_FORMAT: '.A' 'R'? ' ' -> pushMode(AE_MODE);

E_FORMAT_CONT:  '.E' 'R'? DIGIT+ -> pushMode(AE_MODE);
E_FORMAT: '.E' 'R'? ' ' -> pushMode(AE_MODE);
COMMENT: COMMENT_START -> more, pushMode(COMMENT_MODE);
JUNK_TEXT: .? -> skip;
fragment COMMENT_START: ':';
fragment DATACOMMENT_START: '"';
fragment DATACOMMENT2_START: '\'';
fragment NEWLINE: [\r\n\f]+;
fragment SLASH: '/';
fragment ID_CHAR: (ALPHA|DIGIT|'_'|'-');
fragment ALL_WS: [\p{White_Space}]+;
fragment WS: [ \t]+;
fragment LETTER: [A-Z];
fragment LOWER_LETTER: [a-z];
fragment ALPHA: [\p{Alpha}\p{General_Category=Other_letter}];
fragment DIGIT: [0-9];
fragment PLUS_MINUS: '-' | '+';

mode AE_MODE;
AE_NL: NEWLINE -> skip, popMode;
AE_WS: WS -> skip;
AE_COMMENT_START: COMMENT_START -> more, pushMode(COMMENT_MODE);
AE_DATACOMMENT_START: DATACOMMENT_START -> more, pushMode(DATACOMMENT_MODE);
AE_DATACOMMENT2_START: DATACOMMENT2_START -> more, pushMode(DATACOMMENT2_MODE);
AE_IDENTIFIER: ALPHA (ALPHA|DIGIT)* -> type(IDENTIFIER);
AE_NUMBER: ('??' | (PLUS_MINUS? DIGIT+ ('.' DIGIT+)? (LETTER|LOWER_LETTER)?|PLUS_MINUS)) -> type(NUMBER);
AE_SLASH: SLASH -> type(SLASH);

// TODO: determine how to deal with B continuation and that fact the the 
// actual data *MUST* start on the next line. Perhaps a B_HEADER_MODE?
mode B_MODE;
B_IDENTIFIER: ALPHA (ALPHA|DIGIT)* -> type(IDENTIFIER);
B_NUMBER: ('??' | (PLUS_MINUS? DIGIT+ ('.' DIGIT+)? (LETTER|LOWER_LETTER)?|PLUS_MINUS)) -> type(NUMBER);
B_SLASH: SLASH -> type(SLASH);
B_SEP: ',' ;
B_COMMENT_START: COMMENT_START -> more, pushMode(COMMENT_MODE_IN_B);
B_DATACOMMENT_START: DATACOMMENT_START -> more, pushMode(DATACOMMENT_MODE);
B_DATACOMMENT2_START: DATACOMMENT2_START -> more, pushMode(DATACOMMENT2_MODE);
B_NL: NEWLINE -> skip, mode(B_DATA_CONT_MODE);
B_WS: WS -> skip;

mode B_DATA_CONT_MODE;
B_FORMAT_CONT: '.B' 'R'? DIGIT+ -> skip, mode(B_MODE);
BCD_COMMENT_START: COMMENT_START -> more, pushMode(COMMENT_MODE_IN_B);
B_DATA: . -> more, mode(B_DATA_MODE);

mode B_DATA_MODE;
B_END: '.END' -> skip, mode(DEFAULT_MODE);
BD_IDENTIFIER: ALPHA (ALPHA|DIGIT)* -> type(LOCATION_IDENTIFIER);
BD_NUMBER: ('??' | (PLUS_MINUS? DIGIT+ ('.' DIGIT+)? (LETTER|LOWER_LETTER)?|PLUS_MINUS)) -> type(NUMBER);
BD_SLASH: SLASH -> type(SLASH);
BD_SEP: ',' -> type(SEP);
BD_COMMENT_START: COMMENT_START -> more, pushMode(COMMENT_MODE_IN_B);
BD_DATACOMMENT_START: DATACOMMENT_START -> more, pushMode(DATACOMMENT_MODE);
BD_DATACOMMENT2_START: DATACOMMENT2_START -> more, pushMode(DATACOMMENT2_MODE);
BD_WS: ALL_WS -> skip;

mode COMMENT_MODE_IN_B;
B_COMMENT_NL: NEWLINE -> more, type(COMMENT), channel(HIDDEN), popMode;
B_COMMENT_CHAR: ':' -> type(COMMENT), channel(HIDDEN), popMode;
B_TEXT: . -> more;


mode COMMENT_MODE;
COMMENT_NL: NEWLINE -> more, type(COMMENT), channel(HIDDEN), mode(DEFAULT_MODE);
COMMENT_CHAR: ':' -> type(COMMENT), channel(HIDDEN), popMode;
TEXT: . -> more;

mode DATACOMMENT_MODE;
DATACOMMENT_CHAR: '"' -> type(DATACOMMENT), popMode;
DATATEXT: . -> more;

mode DATACOMMENT2_MODE;
DATACOMMENT2_CHAR: '\'' -> type(DATACOMMENT), popMode;
DATA2TEXT: . -> more;