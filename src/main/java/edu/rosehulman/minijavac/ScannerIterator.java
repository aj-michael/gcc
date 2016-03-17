package edu.rosehulman.minijavac;

import edu.rosehulman.minijavac.generated.Symbols;
import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ScannerIterator implements Iterator<Symbol> {
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
