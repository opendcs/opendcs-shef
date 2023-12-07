lexer grammar shefLexer;

tokens {COMMENT}

A_FORMAT: '.A' 'R'?;
NUMBER: '-'? DIGIT+ ('.' DIGIT+)?;
IDENTIFIER: ALPHA (ALPHA|DIGIT)*;
DATEFIELD: LETTER LETTER+ PLUS_MINUS? DIGIT?;

COMMENT_START: ':' -> more, pushMode(COMMENT_MODE);
NEWLINE: [\r?\n]+ -> skip;
WS_DEF: WS -> skip;
SLASH: '/';
fragment ID_CHAR: (ALPHA|DIGIT|'_'|'-');
fragment WS: [\p{White_Space}]+;
fragment LETTER: [A-Z];
fragment ALPHA: [\p{Alpha}\p{General_Category=Other_letter}];
fragment DIGIT: [0-9];
fragment PLUS_MINUS: '-' | '+';

mode COMMENT_MODE;
COMMENT_NL: NEWLINE -> type(COMMENT), channel(HIDDEN), popMode;
COMMENT_CHAR: ':' -> type(COMMENT), channel(HIDDEN), popMode;
TEXT: . -> more;