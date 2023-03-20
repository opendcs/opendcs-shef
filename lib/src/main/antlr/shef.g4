grammar shef;
// The file itself
shefFile: shefFormatStatement+;

// the set of statements in the file.
shefFormatStatement: (a_FORMAT) NEWLINE?;

// SHEF .A statements
a_FORMAT: '.A' ID observationTime;

observationTime: date TIMEZONE time;

time: FixedTime;
date: FULLDATE | SHORT_DATE | VERY_SHORT_DATE;

FixedTime: FixedTimeHours | FixedTimeMinutes;
FixedTimeHours: FIXED_HOURS_START TIME_HOURS TIME_MINUTES TIME_SECONDS;
FixedTimeMinutes: FIXED_MINUTES_START TIME_MINUTES TIME_SECONDS;

FIXED_HOURS_START: 'DH';
FIXED_MINUTES_START: 'DN';
TIME_HOURS: [0-2] DIGIT;
TIME_MINUTES: [0-5] DIGIT;
TIME_SECONDS: [0-5] DIGIT;
TIMEZONE: [A-Z] [PD]?;

FULLDATE: CENTURY SHORT_DATE;
SHORT_DATE: DECADE VERY_SHORT_DATE;
VERY_SHORT_DATE: MONTH DAY;
CENTURY: DIGIT DIGIT;
DECADE: DIGIT DIGIT;
MONTH: '01' | '02' | '03' | '04' | '05' | '06'  | '07' | '08' | '09' | '10' | '11' | '12';
DAY: [0-3][0-9];

ID: ID_CHAR+;
ID_CHAR: (ALPHA|DIGIT|'_'|'-');
NEWLINE: [\r?\n];
fragment ALPHA: [\p{Alpha}\p{General_Category=Other_letter}];
fragment DIGIT: [0-9];
NUMBER: '-'? DIGIT+ ('.' DIGIT+)?;
WS: [\p{White_Space}]+ -> skip;