lexer grammar shefLexer;

tokens {COMMENT,A_FORMAT}
fragment DAY:        '01' | '02' | '03' | '04' | '05' | '06' | '07' | '08' | '09' |
              '10' | '11' | '12' | '13' | '14' | '15' | '16' | '17' | '18' | '19' | 
              '20' | '21' | '22' | '23' | '24' | '25' | '26' | '27' | '28' | '29' |
              '30' | '31';

fragment MONTH: '01' | '02' | '03' | '04' | '05' | '06'  | '07' | '08' | '09' | '10' | '11' | '12';

DEFAULT_A_FMT: A_FORMAT_STR -> type(A_FORMAT), mode(LOCID);
fragment A_FORMAT_STR: '.A' 'R'?;// -> type(A_FORMAT), mode(LOCID);

COMMENT_START: ':' -> more, pushMode(COMMENT_MODE); 
fragment ID_CHAR: (ALPHA|DIGIT|'_'|'-');
NEWLINE: [\r?\n]+ -> skip;
fragment WS: [\p{White_Space}]+;
WS_DEF: WS -> skip;
fragment THE_SLASH : '/';
fragment LETTER: [A-Z];
fragment ALPHA: [\p{Alpha}\p{General_Category=Other_letter}];
fragment DIGIT: [0-9];


mode LOCID;
ID: (ALPHA|DIGIT) ID_CHAR* -> mode(PROCESS_DATE);
WS_ID: WS -> skip;

mode PROCESS_DATE;
WS_PD: WS -> skip;
//      century       decade
DATE: ((DIGIT DIGIT)? DIGIT DIGIT)? MONTH DAY WS_PD+;
TIMEZONE: [A-Z] [PD]? WS_PD+ -> mode(A_FIELDS);
TO_FIELDS: . -> more, mode(A_FIELDS);

mode A_FIELDS;
COMMENT_FIELDS: ':' -> more, pushMode(COMMENT_MODE);
A_CONTINUATION: '.A' 'R'? DIGIT+ -> skip;
                    // HGIRVZ5
PHYSICAL_ELEMENT: [A-CE-Z] LETTER ((LETTER|DIGIT) ((LETTER|DIGIT) ((LETTER|DIGIT) ((LETTER|DIGIT) (LETTER|DIGIT)?)?)?)?)?; // D is reserved for the date fields
TIME_TYPES: 'D' 'R'? [A-Z];
SLASH: THE_SLASH -> skip;
NUMBER: '-'? DIGIT+ ('.' DIGIT+)?;
WS_FIELDS: WS -> skip;
FIELD_A_FORMAT: A_FORMAT_STR -> type(A_FORMAT), mode(LOCID);

mode COMMENT_MODE;
COMMENT_NL: NEWLINE -> type(COMMENT), channel(HIDDEN), popMode;
COMMENT_CHAR: ':' -> type(COMMENT), channel(HIDDEN), popMode;
TEXT: . -> more;