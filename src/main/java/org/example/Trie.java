package org.example;

public class Trie {
    private TrieNode root;
    private long time;

    public Trie() {
        root = new TrieNode('*');
        time = 0;
    }

    public void insert(String word) {
        if (word == null || word.length() == 0) {
            return;
        }

        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            char letterNow = word.charAt(i);

            if (!current.hasChild(letterNow)) {
                current.addChild(letterNow, new TrieNode(letterNow));
            }

            current = current.getChild(letterNow);
        }

        if (!current.isEndWord()) {
            current.setEndWord(true);
            current.setOrder(time);
            time++;
        }

        current.increaseCount();
    }

    public boolean search(String word) {
        TrieNode nodeFound = findNode(word);
        return nodeFound != null && nodeFound.isEndWord();
    }

    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    public java.util.List<String> autocomplete(String prefix) {
        java.util.ArrayList<String> wordsFound = new java.util.ArrayList<String>();

        if (prefix == null) {
            return wordsFound;
        }

        TrieNode lastNode = findNode(prefix);

        if (lastNode == null) {
            return wordsFound;
        }

        saveWords(lastNode, prefix, wordsFound);
        sortAlphabetically(wordsFound);
        return wordsFound;
    }

    public java.util.List<String> autocomplete(String prefix, int k) {
        java.util.ArrayList<DataWord> storedWords = new java.util.ArrayList<DataWord>();
        java.util.ArrayList<String> finalAnswer = new java.util.ArrayList<String>();

        if (prefix == null || k <= 0) {
            return finalAnswer;
        }

        TrieNode lastNode = findNode(prefix);

        if (lastNode == null) {
            return finalAnswer;
        }

        saveWordsTop(lastNode, prefix, storedWords);
        sortList(storedWords);

        for (int i = 0; i < storedWords.size() && i < k; i++) {
            finalAnswer.add(storedWords.get(i).word);
        }

        return finalAnswer;
    }

    public void preorder() {
        System.out.println("PREORDER:");
        showPreorder(root, "", 0);
    }

    private void showPreorder(TrieNode node, String formedWord, int level) {
        if (node == root) {
            System.out.println("Nivel " + level + ": root");
        } else {
            System.out.print("Nivel " + level + ": " + node.getLetter());
            if (node.isEndWord()) {
                System.out.print(" -> " + formedWord);
            }
            System.out.println();
        }

        java.util.Iterator<java.util.Map.Entry<Character, TrieNode>> iteratorChildren =
                node.getChildren().entrySet().iterator();

        while (iteratorChildren.hasNext()) {
            java.util.Map.Entry<Character, TrieNode> currentEntry = iteratorChildren.next();
            showPreorder(currentEntry.getValue(), formedWord + currentEntry.getKey(), level + 1);
        }
    }

    public void postorder() {
        System.out.println("POSTORDER:");
        showPostorder(root, "", 0);
    }

    private void showPostorder(TrieNode node, String formedWord, int level) {
        java.util.Iterator<java.util.Map.Entry<Character, TrieNode>> iteratorChildren =
                node.getChildren().entrySet().iterator();

        while (iteratorChildren.hasNext()) {
            java.util.Map.Entry<Character, TrieNode> currentEntry = iteratorChildren.next();
            showPostorder(currentEntry.getValue(), formedWord + currentEntry.getKey(), level + 1);
        }

        if (node == root) {
            System.out.println("Nivel " + level + ": root");
        } else {
            System.out.print("Nivel " + level + ": " + node.getLetter());
            if (node.isEndWord()) {
                System.out.print(" -> " + formedWord);
            }
            System.out.println();
        }
    }

    public void bfs() {
        System.out.println("BFS:");

        java.util.LinkedList<TrieNode> queueNodes = new java.util.LinkedList<TrieNode>();
        java.util.LinkedList<String> queueWords = new java.util.LinkedList<String>();
        java.util.LinkedList<Integer> queueLevels = new java.util.LinkedList<Integer>();

        queueNodes.add(root);
        queueWords.add("");
        queueLevels.add(0);

        while (!queueNodes.isEmpty()) {
            TrieNode currentNode = queueNodes.removeFirst();
            String formedWord = queueWords.removeFirst();
            int level = queueLevels.removeFirst();

            if (currentNode == root) {
                System.out.println("Nivel " + level + ": root");
            } else {
                System.out.print("Nivel " + level + ": " + currentNode.getLetter());
                if (currentNode.isEndWord()) {
                    System.out.print(" -> " + formedWord);
                }
                System.out.println();
            }

            java.util.Iterator<java.util.Map.Entry<Character, TrieNode>> iteratorChildren =
                    currentNode.getChildren().entrySet().iterator();

            while (iteratorChildren.hasNext()) {
                java.util.Map.Entry<Character, TrieNode> currentEntry = iteratorChildren.next();
                queueNodes.add(currentEntry.getValue());
                queueWords.add(formedWord + currentEntry.getKey());
                queueLevels.add(level + 1);
            }
        }
    }

    public boolean searchWildcard(String word) {
        if (word == null) {
            return false;
        }

        return checkWildcard(root, word, 0);
    }

    private boolean checkWildcard(TrieNode node, String word, int position) {
        if (position == word.length()) {
            return node.isEndWord();
        }

        char letterNow = word.charAt(position);

        if (letterNow == '.') {
            java.util.Iterator<java.util.Map.Entry<Character, TrieNode>> iteratorChildren =
                    node.getChildren().entrySet().iterator();

            while (iteratorChildren.hasNext()) {
                java.util.Map.Entry<Character, TrieNode> currentEntry = iteratorChildren.next();
                if (checkWildcard(currentEntry.getValue(), word, position + 1)) {
                    return true;
                }
            }

            return false;
        } else {
            if (!node.hasChild(letterNow)) {
                return false;
            }

            return checkWildcard(node.getChild(letterNow), word, position + 1);
        }
    }

    public void addWord(String word) {
        insert(word);
    }

    public boolean searchPattern(String pattern) {
        return searchWildcard(pattern);
    }

    private TrieNode findNode(String text) {
        if (text == null) {
            return null;
        }

        TrieNode current = root;

        for (int i = 0; i < text.length(); i++) {
            char letterNow = text.charAt(i);

            if (!current.hasChild(letterNow)) {
                return null;
            }

            current = current.getChild(letterNow);
        }

        return current;
    }

    private void saveWords(TrieNode node, String formedWord, java.util.ArrayList<String> listWords) {
        if (node.isEndWord()) {
            listWords.add(formedWord);
        }

        java.util.Iterator<java.util.Map.Entry<Character, TrieNode>> iteratorChildren =
                node.getChildren().entrySet().iterator();

        while (iteratorChildren.hasNext()) {
            java.util.Map.Entry<Character, TrieNode> currentEntry = iteratorChildren.next();
            saveWords(currentEntry.getValue(), formedWord + currentEntry.getKey(), listWords);
        }
    }

    private void saveWordsTop(TrieNode node, String formedWord, java.util.ArrayList<DataWord> listWords) {
        if (node.isEndWord()) {
            listWords.add(new DataWord(formedWord, node.getCount(), node.getOrder()));
        }

        java.util.Iterator<java.util.Map.Entry<Character, TrieNode>> iteratorChildren =
                node.getChildren().entrySet().iterator();

        while (iteratorChildren.hasNext()) {
            java.util.Map.Entry<Character, TrieNode> currentEntry = iteratorChildren.next();
            saveWordsTop(currentEntry.getValue(), formedWord + currentEntry.getKey(), listWords);
        }
    }

    private void sortAlphabetically(java.util.ArrayList<String> listWords) {
        for (int i = 0; i < listWords.size() - 1; i++) {
            for (int j = 0; j < listWords.size() - 1 - i; j++) {
                String firstWord = listWords.get(j);
                String secondWord = listWords.get(j + 1);

                if (firstWord.compareTo(secondWord) > 0) {
                    listWords.set(j, secondWord);
                    listWords.set(j + 1, firstWord);
                }
            }
        }
    }

    private void sortList(java.util.ArrayList<DataWord> listWords) {
        for (int i = 0; i < listWords.size() - 1; i++) {
            for (int j = 0; j < listWords.size() - 1 - i; j++) {
                DataWord firstData = listWords.get(j);
                DataWord secondData = listWords.get(j + 1);

                if (change(firstData, secondData)) {
                    listWords.set(j, secondData);
                    listWords.set(j + 1, firstData);
                }
            }
        }
    }

    private boolean change(DataWord firstData, DataWord secondData) {
        if (firstData.count < secondData.count) {
            return true;
        }

        if (firstData.count == secondData.count) {
            if (firstData.order > secondData.order) {
                return true;
            }
        }

        return false;
    }

    private class DataWord {
        String word;
        int count;
        long order;

        public DataWord(String word, int count, long order) {
            this.word = word;
            this.count = count;
            this.order = order;
        }
    }
}