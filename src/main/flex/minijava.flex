package edu.rosehulman.minijavac.generated;

import java_cup.runtime.*;

%%

%public
%class Lexer
%unicode
%line
%column
%cup

%{
  StringBuffer string = new StringBuffer();

  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}

LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace     = {LineTerminator} | [ \t\f] | {Comment}
Comment = {TraditionalComment} | {EndOfLineComment} | {DocumentationComment}
TraditionalComment   = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment     = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/**" {CommentContent} "*"+ "/"
CommentContent       = ( [^*] | \*+ [^/*] )*

Identifier = [:jletter:] [:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*

%state STRING

%%

/* keywords */
"class"              { return symbol(sym.ReservedWord, yytext()); }
"public"             { return symbol(sym.ReservedWord, yytext()); }
"static"             { return symbol(sym.ReservedWord, yytext()); }
"extends"            { return symbol(sym.ReservedWord, yytext()); }
"void"               { return symbol(sym.ReservedWord, yytext()); }
"int"                { return symbol(sym.ReservedWord, yytext()); }
"boolean"            { return symbol(sym.ReservedWord, yytext()); }
"if"                 { return symbol(sym.ReservedWord, yytext()); }
"else"               { return symbol(sym.ReservedWord, yytext()); }
"while"              { return symbol(sym.ReservedWord, yytext()); }
"return"             { return symbol(sym.ReservedWord, yytext()); }
"null"               { return symbol(sym.ReservedWord, yytext()); }
"true"               { return symbol(sym.ReservedWord, yytext()); }
"false"              { return symbol(sym.ReservedWord, yytext()); }
"this"               { return symbol(sym.ReservedWord, yytext()); }
"new"                { return symbol(sym.ReservedWord, yytext()); }
"String"             { return symbol(sym.ReservedWord, yytext()); }
"main"               { return symbol(sym.ReservedWord, yytext()); }
"System.out.println" { return symbol(sym.ReservedWord, yytext()); }

/* operators */
"+"                            { return symbol(sym.Operator, yytext()); }
"-"                            { return symbol(sym.Operator, yytext()); }
"*"                            { return symbol(sym.Operator, yytext()); }
"/"                            { return symbol(sym.Operator, yytext()); }
"<"                            { return symbol(sym.Operator, yytext()); }
"<="                           { return symbol(sym.Operator, yytext()); }
">="                           { return symbol(sym.Operator, yytext()); }
">"                            { return symbol(sym.Operator, yytext()); }
"=="                           { return symbol(sym.Operator, yytext()); }
"!="                           { return symbol(sym.Operator, yytext()); }
"&&"                           { return symbol(sym.Operator, yytext()); }
"||"                           { return symbol(sym.Operator, yytext()); }
"!"                            { return symbol(sym.Operator, yytext()); }

/* delimiters */
";"                            { return symbol(sym.Delimiter, yytext()); }
"."                            { return symbol(sym.Delimiter, yytext()); }
","                            { return symbol(sym.Delimiter, yytext()); }
"="                            { return symbol(sym.Delimiter, yytext()); }
"("                            { return symbol(sym.Delimiter, yytext()); }
")"                            { return symbol(sym.Delimiter, yytext()); }
"{"                            { return symbol(sym.Delimiter, yytext()); }
"}"                            { return symbol(sym.Delimiter, yytext()); }
"["                            { return symbol(sym.Delimiter, yytext()); }
"]"                            { return symbol(sym.Delimiter, yytext()); }

/* literals */
{DecIntegerLiteral}            { return symbol(sym.Integer, new Integer(yytext())); }

/* identifiers */
{Identifier}                   { return symbol(sym.ID, yytext()); }

/* whitespace */
{WhiteSpace}                   { /* ignore */ }

/* comments */
{Comment}                      { /* ignore */ }

/* error fallback */
[^]                              { throw new Error("Illegal character <"+
                                                    yytext()+">"); }
