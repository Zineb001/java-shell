grammar ShellGrammar;

/*
 * Parser Rules
*/

command : pipe
        | seq
        | call;

pipe    : call '|' call
        | pipe '|' call;

seq     : (pipe | call) (';' (pipe | call))+;

call    : (redirection)* argument (atom)*;

atom    : redirection | argument;

argument : (quoted | UNQUOTED)+;

redirection : '<' argument
            | '>' argument;

quoted  : (BACKOQUTED | SINGLEQUOTED | DOUBLE_QUOTE);

/*
 * Lexer Rules
 */
UNQUOTED   :~['"`;|< >\t]+;
BACKOQUTED   :'`' ~[`]* '`';
SINGLEQUOTED :'\'' ~['`]* '\'';
DOUBLE_QUOTE :'"' ~[\n]* '"';
WSS :[ \t]+ -> skip;