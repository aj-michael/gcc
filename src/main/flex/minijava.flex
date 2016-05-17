package edu.rosehulman.minijavac.generated;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

%%

%public
%class Lexer
%implements Iterable<Symbol>
%unicode
%line
%column
%cup

%eofval{
  return new Symbol(Symbols.EOF, yyline + 1, yycolumn + 1);
%eofval}

%{
  StringBuffer string = new StringBuffer();

  private <T> Symbol symbol(int type, TokenDisplayName name, T value) {
    return new Symbol(type, yyline + 1, yycolumn + 1, new DisplayableValue<T>(name, value));
  }

  @Override
  public Iterator<Symbol> iterator() {
    return new ScannerIterator(this);
  }

  public static class DisplayableValue<T> {
    public TokenDisplayName name;
    public T value;

    DisplayableValue(TokenDisplayName name, T value) {
      this.name = name;
      this.value = value;
    }

    @Override public String toString() {
      return String.format("%s, %s", name, value);
    }
  }

  public enum TokenDisplayName {
    Delimiter,
    ID,
    Integer,
    LongValue,
    FloatValue,
    DoubleValue,
    Operator,
    ReservedWord;
  }

  static class ScannerIterator implements Iterator<Symbol> {
      private final Scanner scanner;
      private Optional<Symbol> nextToken;

      public ScannerIterator(Scanner scanner) {
          this.scanner = scanner;
          this.nextToken = Optional.empty();
      }

      @Override
      public boolean hasNext() {
          if (!nextToken.isPresent()) {
              try {
                  nextToken = Optional.of(scanner.next_token());
              } catch (Exception e) {
                  e.printStackTrace();
                  return false;
              }
          }
          return nextToken.get().sym != Symbols.EOF;
      }

      @Override
      public Symbol next() {
          if (hasNext()) {
              Symbol currentToken = nextToken.get();
              nextToken = Optional.empty();
              return currentToken;
          } else {
              throw new NoSuchElementException("Cannot read past EOF.");
          }
      }
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
DecFloatLiteral = [0-9][0-9]*\.[0-9][0-9]*f
DecDoubleLiteral = [0-9][0-9]*\.[0-9][0-9]*
DecLongLiteral = 0L | [1-9][0-9]*L

%state STRING

%%

/* keywords */
"class"                        { return symbol(Symbols.Class, TokenDisplayName.ReservedWord, yytext()); }
"public"                       { return symbol(Symbols.Public, TokenDisplayName.ReservedWord, yytext()); }
"static"                       { return symbol(Symbols.Static, TokenDisplayName.ReservedWord, yytext()); }
"extends"                      { return symbol(Symbols.Extends, TokenDisplayName.ReservedWord, yytext()); }
"void"                         { return symbol(Symbols.Void, TokenDisplayName.ReservedWord, yytext()); }
"int"                          { return symbol(Symbols.Int, TokenDisplayName.ReservedWord, yytext()); }
"boolean"                      { return symbol(Symbols.Boolean, TokenDisplayName.ReservedWord, yytext()); }
"double"                       { return symbol(Symbols.Double, TokenDisplayName.ReservedWord, yytext()); }
"float"                        { return symbol(Symbols.Float, TokenDisplayName.ReservedWord, yytext()); }
"long"                         { return symbol(Symbols.Long, TokenDisplayName.ReservedWord, yytext()); }
"if"                           { return symbol(Symbols.If, TokenDisplayName.ReservedWord, yytext()); }
"else"                         { return symbol(Symbols.Else, TokenDisplayName.ReservedWord, yytext()); }
"while"                        { return symbol(Symbols.While, TokenDisplayName.ReservedWord, yytext()); }
"return"                       { return symbol(Symbols.Return, TokenDisplayName.ReservedWord, yytext()); }
"null"                         { return symbol(Symbols.Null, TokenDisplayName.ReservedWord, yytext()); }
"true"                         { return symbol(Symbols.True, TokenDisplayName.ReservedWord, yytext()); }
"false"                        { return symbol(Symbols.False, TokenDisplayName.ReservedWord, yytext()); }
"this"                         { return symbol(Symbols.This, TokenDisplayName.ReservedWord, yytext()); }
"new"                          { return symbol(Symbols.New, TokenDisplayName.ReservedWord, yytext()); }
"String"                       { return symbol(Symbols.String, TokenDisplayName.ReservedWord, yytext()); }
"main"                         { return symbol(Symbols.Main, TokenDisplayName.ReservedWord, yytext()); }
"synchronized"                 { return symbol(Symbols.Synchronized, TokenDisplayName.ReservedWord, yytext()); }
"native"                       { return symbol(Symbols.Native, TokenDisplayName.ReservedWord, yytext()); }
"System.out.println"           { return symbol(Symbols.SystemOutPrintln, TokenDisplayName.ReservedWord, yytext()); }

/* Operators */
"+"                            { return symbol(Symbols.Plus, TokenDisplayName.Operator, yytext()); }
"-"                            { return symbol(Symbols.Minus, TokenDisplayName.Operator, yytext()); }
"*"                            { return symbol(Symbols.Multiply, TokenDisplayName.Operator, yytext()); }
"/"                            { return symbol(Symbols.Divide, TokenDisplayName.Operator, yytext()); }
"<"                            { return symbol(Symbols.LessThan, TokenDisplayName.Operator, yytext()); }
"<="                           { return symbol(Symbols.LessThanOrEqual, TokenDisplayName.Operator, yytext()); }
">="                           { return symbol(Symbols.GreaterThanOrEqual, TokenDisplayName.Operator, yytext()); }
">"                            { return symbol(Symbols.GreaterThan, TokenDisplayName.Operator, yytext()); }
"=="                           { return symbol(Symbols.Equals, TokenDisplayName.Operator, yytext()); }
"!="                           { return symbol(Symbols.NotEquals, TokenDisplayName.Operator, yytext()); }
"&&"                           { return symbol(Symbols.And, TokenDisplayName.Operator, yytext()); }
"||"                           { return symbol(Symbols.Or, TokenDisplayName.Operator, yytext()); }
"!"                            { return symbol(Symbols.Not, TokenDisplayName.Operator, yytext()); }

/* Delimiters */
";"                            { return symbol(Symbols.Semicolon, TokenDisplayName.Delimiter, yytext()); }
"."                            { return symbol(Symbols.Dot, TokenDisplayName.Delimiter, yytext()); }
","                            { return symbol(Symbols.Comma, TokenDisplayName.Delimiter, yytext()); }
"="                            { return symbol(Symbols.Assignment, TokenDisplayName.Delimiter, yytext()); }
"("                            { return symbol(Symbols.LeftParenthesis, TokenDisplayName.Delimiter, yytext()); }
")"                            { return symbol(Symbols.RightParenthesis, TokenDisplayName.Delimiter, yytext()); }
"{"                            { return symbol(Symbols.LeftBrace, TokenDisplayName.Delimiter, yytext()); }
"}"                            { return symbol(Symbols.RightBrace, TokenDisplayName.Delimiter, yytext()); }
"["                            { return symbol(Symbols.LeftBracket, TokenDisplayName.Delimiter, yytext()); }
"]"                            { return symbol(Symbols.RightBracket, TokenDisplayName.Delimiter, yytext()); }

{DecIntegerLiteral}            { return symbol(Symbols.Integer, TokenDisplayName.Integer, new Integer(yytext())); }
{DecLongLiteral}               { String text = yytext();
                                 return symbol(Symbols.LongValue, TokenDisplayName.LongValue, new Long(text.substring(0, text.length() - 1))); }
{DecDoubleLiteral}             { return symbol(Symbols.DoubleValue, TokenDisplayName.DoubleValue, new Double(yytext())); }
{DecFloatLiteral}              { return symbol(Symbols.FloatValue, TokenDisplayName.FloatValue, new Float(yytext())); }
{Identifier}                   { return symbol(Symbols.ID, TokenDisplayName.ID, yytext()); }
{BadSystemOutPrintln}          { yypushback(yylength() - "System".length());
                                 return symbol(Symbols.ID, TokenDisplayName.ID, yytext());
                               }

{WhiteSpace}                   { /* ignore */ }
{Comment}                      { /* ignore */ }

[^]                            { return new Symbol(Symbols.UnmatchedCharacter, yyline + 1, yycolumn + 1, yytext()); }
