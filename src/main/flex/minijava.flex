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

  public static class NameAndValue {
    public String name;
    public Object value;
    NameAndValue(String name, Object value) {
      this.name = name;
      this.value = value;
    }
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
"class"                        { return symbol(sym.Class, new NameAndValue("ReservedWord", yytext())); }
"public"                       { return symbol(sym.Public, new NameAndValue("ReservedWord", yytext())); }
"static"                       { return symbol(sym.Static, new NameAndValue("ReservedWord", yytext())); }
"extends"                      { return symbol(sym.Extends, new NameAndValue("ReservedWord", yytext())); }
"void"                         { return symbol(sym.Void, new NameAndValue("ReservedWord", yytext())); }
"int"                          { return symbol(sym.Int, new NameAndValue("ReservedWord", yytext())); }
"boolean"                      { return symbol(sym.Boolean, new NameAndValue("ReservedWord", yytext())); }
"if"                           { return symbol(sym.If, new NameAndValue("ReservedWord", yytext())); }
"else"                         { return symbol(sym.Else, new NameAndValue("ReservedWord", yytext())); }
"while"                        { return symbol(sym.While, new NameAndValue("ReservedWord", yytext())); }
"return"                       { return symbol(sym.Return, new NameAndValue("ReservedWord", yytext())); }
"null"                         { return symbol(sym.Null, new NameAndValue("ReservedWord", yytext())); }
"true"                         { return symbol(sym.True, new NameAndValue("ReservedWord", yytext())); }
"false"                        { return symbol(sym.False, new NameAndValue("ReservedWord", yytext())); }
"this"                         { return symbol(sym.This, new NameAndValue("ReservedWord", yytext())); }
"new"                          { return symbol(sym.New, new NameAndValue("ReservedWord", yytext())); }
"String"                       { return symbol(sym.String, new NameAndValue("ReservedWord", yytext())); }
"main"                         { return symbol(sym.Main, new NameAndValue("ReservedWord", yytext())); }
"System.out.println"           { return symbol(sym.SystemOutPrintln, new NameAndValue("ReservedWord", yytext())); }

/* operators */
"+"                            { return symbol(sym.Plus, new NameAndValue("Operator", yytext())); }
"-"                            { return symbol(sym.Minus, new NameAndValue("Operator", yytext())); }
"*"                            { return symbol(sym.Multiply, new NameAndValue("Operator", yytext())); }
"/"                            { return symbol(sym.Divide, new NameAndValue("Operator", yytext())); }
"<"                            { return symbol(sym.LessThan, new NameAndValue("Operator", yytext())); }
"<="                           { return symbol(sym.LessThanOrEqual, new NameAndValue("Operator", yytext())); }
">="                           { return symbol(sym.GreaterThanOrEqual, new NameAndValue("Operator", yytext())); }
">"                            { return symbol(sym.GreaterThan, new NameAndValue("Operator", yytext())); }
"=="                           { return symbol(sym.Equals, new NameAndValue("Operator", yytext())); }
"!="                           { return symbol(sym.NotEquals, new NameAndValue("Operator", yytext())); }
"&&"                           { return symbol(sym.And, new NameAndValue("Operator", yytext())); }
"||"                           { return symbol(sym.Or, new NameAndValue("Operator", yytext())); }
"!"                            { return symbol(sym.Not, new NameAndValue("Operator", yytext())); }

/* delimiters */
";"                            { return symbol(sym.Semicolon, new NameAndValue("Delimiter", yytext())); }
"."                            { return symbol(sym.Dot, new NameAndValue("Delimiter", yytext())); }
","                            { return symbol(sym.Comma, new NameAndValue("Delimiter", yytext())); }
"="                            { return symbol(sym.Assignment, new NameAndValue("Delimiter", yytext())); }
"("                            { return symbol(sym.LeftParenthesis, new NameAndValue("Delimiter", yytext())); }
")"                            { return symbol(sym.RightParenthesis, new NameAndValue("Delimiter", yytext())); }
"{"                            { return symbol(sym.LeftBrace, new NameAndValue("Delimiter", yytext())); }
"}"                            { return symbol(sym.RightBrace, new NameAndValue("Delimiter", yytext())); }
"["                            { return symbol(sym.LeftBracket, new NameAndValue("Delimiter", yytext())); }
"]"                            { return symbol(sym.RightBracket, new NameAndValue("Delimiter", yytext())); }

{DecIntegerLiteral}            { return symbol(sym.Integer, new NameAndValue("Integer", new Integer(yytext()))); }
{Identifier}                   { return symbol(sym.ID, new NameAndValue("ID", yytext())); }
{BadSystemOutPrintln}          { yypushback(yylength() - "System".length());
                                 return symbol(sym.ID, new NameAndValue("ID", yytext()));
                               }
{WhiteSpace}                   { /* ignore */ }
{Comment}                      { /* ignore */ }

/* Error fallback */
[^]                              { throw new Error("Illegal character <"+
                                                    yytext()+">"); }
