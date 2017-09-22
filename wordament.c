#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#define TRUE  1
#define FALSE 0
#define valid(y, x) ((x > -1 && x < 4) && (y > -1 && y < 4) && (!visited[y][x]))
#define uint int

struct node {
	char end;
	struct node *children[26];
};

/* global section */
char grid[4][4], outbuf[100];
struct node *trie;
uint end = 0, visited[4][4];

struct node *newnode() {
	int i;
	struct node *n = malloc(sizeof(struct node));

	if(!n) printf("ENOMEM");

	n->end = FALSE;
	for(i=0; i<26; i++)
		n->children[i] = NULL;
	return n;
}

void insert(struct node *trie, char *word) {
	while(*word) {
		uint index = toupper(*word) - 'A';
		if(index > 25 || index < 0)
			break;
		if(!trie->children[index])
			trie->children[index] = newnode();
		trie = trie->children[index];
		word++;
	}
	trie->end = TRUE;
}

void output() {
	if(end <3)
		return;
	int i;
	for(i=0; i<end; i++)
		putchar(outbuf[i]);
	putchar('\n');
}

void findwords(struct node *t, uint y, uint x) {
	if(!valid(y, x) || !t) return;
	t = t->children[grid[y][x] - 'A'];
	if(!t) return;

	visited[y][x] = end + 1;
	outbuf[end++] = grid[y][x];

	if(t->end) output();
	findwords(t, y-1, x-1);
	findwords(t, y-1, x);
	findwords(t, y-1, x+1);
	findwords(t, y,   x-1);
	findwords(t, y,   x+1);
	findwords(t, y+1, x-1);
	findwords(t, y+1, x);
	findwords(t, y+1, x+1);
	visited[y][x] = FALSE;
	end--;
}

void loaddict(struct node *t, char *file) {
	char word[100];
	FILE *fp = fopen(file, "r");
	while(fscanf(fp, "%s", word) != -1)
		insert(t, word);
	fclose(fp);
	puts("done loading");
}

int main(int argc, char *argv[]) {
	int i;
	char *buf = (char*)grid;
	//strcpy(buf, "NPANBIREIRESNUTN");
	trie = newnode();
	loaddict(trie, argv[1]);
	printf("Enter the grid : ");
	scanf("%s", buf);
	for(i=0; i<16; i++)
		findwords(trie, i/4, i%4);
}

