grammar shef;
shefFile: shefFormatStatement (NEWLINE shefFormatStatement)?;
shefFormatStatement: (a_FORMAT) NEWLINE?;
a_FORMAT: '.A' ID;
ID: ID_CHAR+;
ID_CHAR: (ALPHA|DIGIT|'_'|'-');
NEWLINE: [\r?\n];
fragment ALPHA: [\p{Alpha}\p{General_Category=Other_letter}];
fragment DIGIT: [0-9];
NUMBER: DIGIT+ ('.' DIGIT*)?;
WS: [\p{White_Space}]+ -> skip;