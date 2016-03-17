package edu.rosehulman.minijavac.generated;

import java_cup.runtime.*;

%%

%public
%class Lexer
%unicode
%line
%column
%cup

%eofval{
  return new java_cup.runtime.Symbol(Symbols.EOF);
%eofval}

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
DocumentationComment = "/**" {CommentContent}? "*"+ "/"
CommentContent       = [^/] ( [^*] | \*+ [^/*] )*

BadSystemOutPrintln = "System.out.println" [:jletterdigit:]+

Identifier = [:jletter:] [:jletterdigit:]*

DecIntegerLiteral = 0 | [1-9][0-9]*

%state STRING

%%

/* keywords */
"class"                        { return symbol(Symbols.ReservedWord, yytext()); }
"public"                       { return symbol(Symbols.ReservedWord, yytext()); }
"static"                       { return symbol(Symbols.ReservedWord, yytext()); }
"extends"                      { return symbol(Symbols.ReservedWord, yytext()); }
"void"                         { return symbol(Symbols.ReservedWord, yytext()); }
"int"                          { return symbol(Symbols.ReservedWord, yytext()); }
"boolean"                      { return symbol(Symbols.ReservedWord, yytext()); }
"if"                           { return symbol(Symbols.ReservedWord, yytext()); }
"else"                         { return symbol(Symbols.ReservedWord, yytext()); }
"while"                        { return symbol(Symbols.ReservedWord, yytext()); }
"return"                       { return symbol(Symbols.ReservedWord, yytext()); }
"null"                         { return symbol(Symbols.ReservedWord, yytext()); }
"true"                         { return symbol(Symbols.ReservedWord, yytext()); }
"false"                        { return symbol(Symbols.ReservedWord, yytext()); }
"this"                         { return symbol(Symbols.ReservedWord, yytext()); }
"new"                          { return symbol(Symbols.ReservedWord, yytext()); }
"String"                       { return symbol(Symbols.ReservedWord, yytext()); }
"main"                         { return symbol(Symbols.ReservedWord, yytext()); }
"System.out.println"           { return symbol(Symbols.ReservedWord, yytext()); }

/* operators */
"+"                            { return symbol(Symbols.Operator, yytext()); }
"-"                            { return symbol(Symbols.Operator, yytext()); }
"*"                            { return symbol(Symbols.Operator, yytext()); }
"/"                            { return symbol(Symbols.Operator, yytext()); }
"<"                            { return symbol(Symbols.Operator, yytext()); }
"<="                           { return symbol(Symbols.Operator, yytext()); }
">="                           { return symbol(Symbols.Operator, yytext()); }
">"                            { return symbol(Symbols.Operator, yytext()); }
"=="                           { return symbol(Symbols.Operator, yytext()); }
"!="                           { return symbol(Symbols.Operator, yytext()); }
"&&"                           { return symbol(Symbols.Operator, yytext()); }
"||"                           { return symbol(Symbols.Operator, yytext()); }
"!"                            { return symbol(Symbols.Operator, yytext()); }

/* delimiters */
";"                            { return symbol(Symbols.Delimiter, yytext()); }
"."                            { return symbol(Symbols.Delimiter, yytext()); }
","                            { return symbol(Symbols.Delimiter, yytext()); }
"="                            { return symbol(Symbols.Delimiter, yytext()); }
"("                            { return symbol(Symbols.Delimiter, yytext()); }
")"                            { return symbol(Symbols.Delimiter, yytext()); }
"{"                            { return symbol(Symbols.Delimiter, yytext()); }
"}"                            { return symbol(Symbols.Delimiter, yytext()); }
"["                            { return symbol(Symbols.Delimiter, yytext()); }
"]"                            { return symbol(Symbols.Delimiter, yytext()); }

{DecIntegerLiteral}            { return symbol(Symbols.Integer, new Integer(yytext())); }
{Identifier}                   { return symbol(Symbols.ID, yytext()); }
{BadSystemOutPrintln}          { yypushback(yylength() - "System".length());
                                 return symbol(Symbols.ID, yytext());
                               }
{WhiteSpace}                   { /* ignore */ }
{Comment}                      { /* ignore */ }

/* Error fallback */
[^]                              { throw new Error("Illegal character <"+
                                                    yytext()+">"); }
