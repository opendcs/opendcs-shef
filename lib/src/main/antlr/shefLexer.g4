lexer grammar shefLexer;

tokens {COMMENT, DATACOMMENT}

A_FORMAT_CONT: '.A' 'R'? DIGIT+;
A_FORMAT: '.A' 'R'?;

E_FORMAT_CONT: '.E' 'R'? DIGIT+;
E_FORMAT: '.E' 'R'?;


NUMBER: (PLUS_MINUS? DIGIT+ ('.' DIGIT+)? (LETTER|LOWER_LETTER)?|PLUS_MINUS);
IDENTIFIER: ALPHA (ALPHA|DIGIT)*;

COMMENT_START: ':' -> more, pushMode(COMMENT_MODE);
DATACOMMENT_START: '"' -> more, pushMode(DATACOMMENT_MODE);
DATACOMMENT2_START: '\'' -> more, pushMode(DATACOMMENT2_MODE);
NEWLINE: [\r?\n]+ -> skip;
WS_DEF: WS -> skip;
SLASH: '/';
fragment ID_CHAR: (ALPHA|DIGIT|'_'|'-');
fragment WS: [\p{White_Space}]+;
fragment LETTER: [A-Z];
fragment LOWER_LETTER: [a-z];
fragment ALPHA: [\p{Alpha}\p{General_Category=Other_letter}];
fragment DIGIT: [0-9];
fragment PLUS_MINUS: '-' | '+';

mode COMMENT_MODE;
COMMENT_NL: NEWLINE -> type(COMMENT), channel(HIDDEN), popMode;
COMMENT_CHAR: ':' -> type(COMMENT), channel(HIDDEN), popMode;
TEXT: . -> more;

mode DATACOMMENT_MODE;
DATACOMMENT_CHAR: '"' -> type(DATACOMMENT), popMode;
DATATEXT: . -> more;

mode DATACOMMENT2_MODE;
DATACOMMENT2_CHAR: '\'' -> type(DATACOMMENT), popMode;
DATA2TEXT: . -> more;