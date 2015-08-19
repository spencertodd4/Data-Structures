public class BTest {
	public static void main (String [] args) {

		BTree tree = new BTree();

		int [] vals = {50, 24, 64, 14, 10};

		for (int v: vals) {
			tree.add(v);
		}

		System.out.println(tree);

		System.out.println(tree.isBalanced());

	}
}