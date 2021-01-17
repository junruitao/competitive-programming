public class Trie {

	boolean isWord;
	Trie[] next;

	/** Initialize your data structure here. */
	public Trie() {

	}

	/** Inserts a word into the trie. */
	public void insert(String word) {
		insert(word, 0);
	}

	public void insert(String word, int s) {
		if (word != null && word.length() > 0)
			if (s < word.length()) {
				if (next == null)
					next = new Trie[26];
				Trie trie = next[word.charAt(s) - 'a'];
				if (trie == null)
					next[word.charAt(s) - 'a'] = trie = new Trie();
				trie.insert(word, s + 1);
			} else
				isWord = true;
	}

	/** Returns if the word is in the trie. */
	public boolean search(String word) {
		return search(word, 0);
	}

	public boolean search(String word, int s) {

		if (s < word.length()) {
			if (next == null)
				return false;
			Trie trie = next[word.charAt(s) - 'a'];
			if (trie == null)
				return false;
			return trie.search(word, s + 1);
		} else
			return isWord;

	}

	public boolean startsWith(String prefix) {
		return startsWith(prefix, 0);
	}

	/**
	 * Returns if there is any word in the trie that starts with the given
	 * prefix.
	 */
	public boolean startsWith(String prefix, int s) {
		if (s < prefix.length()) {
			if (next == null)
				return false;
			Trie trie = next[prefix.charAt(s) - 'a'];
			if (trie == null)
				return false;
			return trie.startsWith(prefix, s + 1);
		} else
			return true;

	}

	public static void main(String[] args) {
		Trie obj = new Trie();
		String word = "abcd";
		obj.insert(word );
		boolean param_2 = obj.search(word);
		boolean param_3 = obj.startsWith("abd");
		System.out.println();
	}
}

/**
 * Your Trie object will be instantiated and called as such: Trie obj = new
 * Trie(); obj.insert(word); boolean param_2 = obj.search(word); boolean param_3
 * = obj.startsWith(prefix);
 */