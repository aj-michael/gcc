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

  public static class NameAndValue {
    public TokenDisplayName name;
    public Object value;
    NameAndValue(TokenDisplayName name, Object value) {
      this.name = name;
      this.value = value;
    }
  }

  public enum TokenDisplayName {
    Delimiter,
    ID,
    Integer,
    Operator,
    ReservedWord;
  }

  private Symbol symbol(int type, TokenDisplayName name, Object value) {
    return new Symbol(type, yyline, yycolumn, new NameAndValue(name, value));
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
"class"                        { return symbol(sym.Class, TokenDisplayName.ReservedWord, yytext()); }
"public"                       { return symbol(sym.Public, TokenDisplayName.ReservedWord, yytext()); }
"static"                       { return symbol(sym.Static, TokenDisplayName.ReservedWord, yytext()); }
"extends"                      { return symbol(sym.Extends, TokenDisplayName.ReservedWord, yytext()); }
"void"                         { return symbol(sym.Void, TokenDisplayName.ReservedWord, yytext()); }
"int"                          { return symbol(sym.Int, TokenDisplayName.ReservedWord, yytext()); }
"boolean"                      { return symbol(sym.Boolean, TokenDisplayName.ReservedWord, yytext()); }
"if"                           { return symbol(sym.If, TokenDisplayName.ReservedWord, yytext()); }
"else"                         { return symbol(sym.Else, TokenDisplayName.ReservedWord, yytext()); }
"while"                        { return symbol(sym.While, TokenDisplayName.ReservedWord, yytext()); }
"return"                       { return symbol(sym.Return, TokenDisplayName.ReservedWord, yytext()); }
"null"                         { return symbol(sym.Null, TokenDisplayName.ReservedWord, yytext()); }
"true"                         { return symbol(sym.True, TokenDisplayName.ReservedWord, yytext()); }
"false"                        { return symbol(sym.False, TokenDisplayName.ReservedWord, yytext()); }
"this"                         { return symbol(sym.This, TokenDisplayName.ReservedWord, yytext()); }
"new"                          { return symbol(sym.New, TokenDisplayName.ReservedWord, yytext()); }
"String"                       { return symbol(sym.String, TokenDisplayName.ReservedWord, yytext()); }
"main"                         { return symbol(sym.Main, TokenDisplayName.ReservedWord, yytext()); }
"System.out.println"           { return symbol(sym.SystemOutPrintln, TokenDisplayName.ReservedWord, yytext()); }

/* Operators */
"+"                            { return symbol(sym.Plus, TokenDisplayName.Operator, yytext()); }
"-"                            { return symbol(sym.Minus, TokenDisplayName.Operator, yytext()); }
"*"                            { return symbol(sym.Multiply, TokenDisplayName.Operator, yytext()); }
"/"                            { return symbol(sym.Divide, TokenDisplayName.Operator, yytext()); }
"<"                            { return symbol(sym.LessThan, TokenDisplayName.Operator, yytext()); }
"<="                           { return symbol(sym.LessThanOrEqual, TokenDisplayName.Operator, yytext()); }
">="                           { return symbol(sym.GreaterThanOrEqual, TokenDisplayName.Operator, yytext()); }
">"                            { return symbol(sym.GreaterThan, TokenDisplayName.Operator, yytext()); }
"=="                           { return symbol(sym.Equals, TokenDisplayName.Operator, yytext()); }
"!="                           { return symbol(sym.NotEquals, TokenDisplayName.Operator, yytext()); }
"&&"                           { return symbol(sym.And, TokenDisplayName.Operator, yytext()); }
"||"                           { return symbol(sym.Or, TokenDisplayName.Operator, yytext()); }
"!"                            { return symbol(sym.Not, TokenDisplayName.Operator, yytext()); }

/* Delimiters */
";"                            { return symbol(sym.Semicolon, TokenDisplayName.Delimiter, yytext()); }
"."                            { return symbol(sym.Dot, TokenDisplayName.Delimiter, yytext()); }
","                            { return symbol(sym.Comma, TokenDisplayName.Delimiter, yytext()); }
"="                            { return symbol(sym.Assignment, TokenDisplayName.Delimiter, yytext()); }
"("                            { return symbol(sym.LeftParenthesis, TokenDisplayName.Delimiter, yytext()); }
")"                            { return symbol(sym.RightParenthesis, TokenDisplayName.Delimiter, yytext()); }
"{"                            { return symbol(sym.LeftBrace, TokenDisplayName.Delimiter, yytext()); }
"}"                            { return symbol(sym.RightBrace, TokenDisplayName.Delimiter, yytext()); }
"["                            { return symbol(sym.LeftBracket, TokenDisplayName.Delimiter, yytext()); }
"]"                            { return symbol(sym.RightBracket, TokenDisplayName.Delimiter, yytext()); }

{DecIntegerLiteral}            { return symbol(sym.Integer, TokenDisplayName.Integer, new Integer(yytext())); }
{Identifier}                   { return symbol(sym.ID, TokenDisplayName.ID, yytext()); }
{BadSystemOutPrintln}          { yypushback(yylength() - "System".length());
                                 return symbol(sym.ID, TokenDisplayName.ID, yytext());
                               }
{WhiteSpace}                   { /* ignore */ }
{Comment}                      { /* ignore */ }

/* Error fallback */
[^]                              { throw new Error("Illegal character <"+
                                                    yytext()+">"); }
