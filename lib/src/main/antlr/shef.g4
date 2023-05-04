grammar shef;
// The file itself
shefFile: shefFormatStatement+;

// the set of statements in the file.
shefFormatStatement: (a_FORMAT) NEWLINE?;

// SHEF .A statements
a_FORMAT: '.A' REVISED? ID observationTime (field)+ COMMENT?; 
field: (value);
value: '/' PHYSICAL_ELEMENT NUMBER;


observationTime: date TIMEZONE time;

time: FIXED_TIME;
date: FULLDATE | SHORT_DATE | VERY_SHORT_DATE;

FIXED_TIME: 'DH' TIME_HOURS (TIME_MINUTES TIME_SECONDS?)?;

FIXED_HOURS_START: 'DH';
FIXED_MINUTES_START: 'DN';
TIME_HOURS: [0-2] DIGIT;
TIME_MINUTES: [0-5] DIGIT;
TIME_SECONDS: [0-5] DIGIT;
TIMEZONE: [A-Z] [PD]?;

FULLDATE: CENTURY SHORT_DATE;
SHORT_DATE: DECADE_YEAR VERY_SHORT_DATE;
VERY_SHORT_DATE: MONTH DAY;
CENTURY: DIGIT DIGIT;
DECADE_YEAR: DIGIT DIGIT;
MONTH: '01' | '02' | '03' | '04' | '05' | '06'  | '07' | '08' | '09' | '10' | '11' | '12';
DAY: [0-3][0-9];

PHYSICAL_ELEMENT: [A-CE-Z] LETTER; // D is reserved for the date fields
ID: ID_CHAR+;
ID_CHAR: (ALPHA|DIGIT|'_'|'-');
NEWLINE: [\r?\n] -> channel(HIDDEN);
REVISED: 'R';
COMMENT: COMMENT_CHAR ~[\r\n]*;
COMMENT_CHAR: ':' -> skip;
PRINTABLE: (ALPHA|DIGIT|'_'|'-'|'.');
NUMBER: '-'? DIGIT+ ('.' DIGIT+)?;
WS: [\p{White_Space}]+ -> skip;
//SPACE: [\p{Space_Separator}]+;
fragment LETTER: [A-Z];
fragment ALPHA: [\p{Alpha}\p{General_Category=Other_letter}];
fragment DIGIT: [0-9];
