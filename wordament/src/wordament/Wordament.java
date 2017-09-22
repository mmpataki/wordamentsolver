package wordament;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Wordament extends Thread {

    boolean valid(int y, int x) {
        return ((x > -1 && x < 4) && (y > -1 && y < 4) && (visited[y][x] == 0));
    }

    class node {

        boolean end;
        node children[];

        node() {
            children = new node[26];
            end = false;
            for (int i = 0; i < 26; i++) {
                children[i] = null;
            }
        }
    }

    /* global section */
    char[][] grid;
    node trie;
    int end = 0;
    int[][] visited = new int[4][4];
    int[][] path = new int[16][2];
    private final IWordFoundAction wordFound;

    void insert(node trie, String word) {
        
        word = word.toUpperCase();
        for (int i = 0; i < word.length(); i++) {

            int index = word.charAt(i) - 'A';
            if (index > 25 || index < 0) {
                break;
            }
            if (trie.children[index] == null) {
                trie.children[index] = new node();
            }
            trie = trie.children[index];
        }
        trie.end = true;
    }

    void output() {
        if (end < 3) {
            return;
        }
        int[][] cpath = new int[end][2];
        for (int i = 0; i < end; i++) {
            cpath[i][0] = path[i][0];
            cpath[i][1] = path[i][1];
        }
        wordFound.wordFound(cpath);
    }

    void putchar(char c) { System.out.print(c); }
    
    void findwords(node t, int y, int x) {
	if (!valid(y, x) || t == null) {
            return;
        }
        t = t.children[grid[y][x] - 'A'];
        if (t == null) {
            return;
        }

        visited[y][x] = end + 1;
        path[end][0] = y;
        path[end][1] = x;
        end++;
        
        if (t.end) {
            output();
        }
        findwords(t, y - 1, x - 1);
        findwords(t, y - 1, x);
        findwords(t, y - 1, x + 1);
        findwords(t, y, x - 1);
        findwords(t, y, x + 1);
        findwords(t, y + 1, x - 1);
        findwords(t, y + 1, x);
        findwords(t, y + 1, x + 1);
        visited[y][x] = 0;
        end--;
    }

    void loaddict(node t, String file) {
        BufferedReader br;
        try {
            String word;
            br = new BufferedReader(new FileReader(file));
            while ((word = br.readLine()) != null) {
                insert(t, word);
            }
        } catch (Exception ex) {
            Logger.getLogger(Wordament.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Wordament(String dictionary, IWordFoundAction wordFoundAction) {
        this.wordFound = wordFoundAction;
	trie = new node();
        loaddict(trie, dictionary);
    }

    void solve(char problem[][]) {
        grid = problem;
        this.start();
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 16; i++) {
            findwords(trie, i / 4, i % 4);
        }
    }
    
}
