package org.example;

public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("salon");
        trie.insert("salon");
        trie.insert("salida");
        trie.insert("salto");
        trie.insert("salero");
        trie.insert("salida");
        trie.insert("salchicha");

        System.out.println("BUSQUEDA:");
        System.out.println("search(\"salida\") -> " + trie.search("salida"));
        System.out.println("search(\"salsa\") -> " + trie.search("salsa"));
        System.out.println();

        System.out.println("PREFIJOS:");
        System.out.println("startsWith(\"sal\") -> " + trie.startsWith("sal"));
        System.out.println("startsWith(\"sol\") -> " + trie.startsWith("sol"));
        System.out.println();

        System.out.println("AUTOCOMPLETE ORDENADO ALFABETICAMENTE:");
        System.out.println("autocomplete(\"sa\") -> " + trie.autocomplete("sa"));
        System.out.println("autocomplete(\"sali\") -> " + trie.autocomplete("sali"));
        System.out.println();

        System.out.println("TOP K:");
        System.out.println("autocomplete(\"sa\", 2) -> " + trie.autocomplete("sa", 2));
        System.out.println("autocomplete(\"sal\", 2) -> " + trie.autocomplete("sal", 2));
        System.out.println("autocomplete(\"s\", 3) -> " + trie.autocomplete("s", 3));
        System.out.println("autocomplete(\"s\", 5) -> " + trie.autocomplete("s", 5));
        System.out.println();

        trie.preorder();
        System.out.println();

        trie.postorder();
        System.out.println();

        trie.bfs();
        System.out.println();

        System.out.println("MEJORA CON COMODIN:");
        System.out.println("searchWildcard(\"sal..\") -> " + trie.searchWildcard("sal.."));
        System.out.println("searchWildcard(\"sa....\") -> " + trie.searchWildcard("sa...."));
        System.out.println("searchWildcard(\"s.l...\") -> " + trie.searchWildcard("s.l..."));
        System.out.println("searchWildcard(\"s..z\") -> " + trie.searchWildcard("s..z"));
        System.out.println();

        System.out.println("MEJORA TIPO WORDDICTIONARY:");
        trie.addWord("silla");
        trie.addWord("sirena");
        trie.addWord("sitio");

        System.out.println("searchPattern(\"si...\") -> " + trie.searchPattern("si..."));
        System.out.println("searchPattern(\"s.re.a\") -> " + trie.searchPattern("s.re.a"));
        System.out.println("searchPattern(\"si..z\") -> " + trie.searchPattern("si..z"));
    }
}