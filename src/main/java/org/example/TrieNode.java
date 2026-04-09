package org.example;

public class TrieNode {
    private char letter;
    private boolean endWord;
    private int count;
    private long order;
    private java.util.LinkedHashMap<Character, TrieNode> children;

    public TrieNode(char letter) {
        this.letter = letter;
        this.endWord = false;
        this.count = 0;
        this.order = -1;
        this.children = new java.util.LinkedHashMap<Character, TrieNode>();
    }

    public char getLetter() {
        return letter;
    }

    public boolean isEndWord() {
        return endWord;
    }

    public void setEndWord(boolean endWord) {
        this.endWord = endWord;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        count++;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public java.util.LinkedHashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public boolean hasChild(char c) {
        return children.containsKey(c);
    }

    public TrieNode getChild(char c) {
        return children.get(c);
    }

    public void addChild(char c, TrieNode node) {
        children.put(c, node);
    }
}