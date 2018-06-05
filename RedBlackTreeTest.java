

public class RedBlackTreeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RedBlackTree<Integer> tree= new RedBlackTree<>();
		
		
		tree.add(200);
		tree.add(2);
		tree.add(5);
		tree.add(4);
		tree.add(11);
		tree.add(7);
		tree.add(8);
		tree.add(14);
		tree.add(15);
		tree.add(18);
		tree.add(19);
		tree.add(22);
		tree.add(34);
		tree.add(100);
		tree.add(45);
		tree.add(23);
		tree.add(30);
		tree.add(31);
		tree.add(12);
		tree.add(43);
		
		
		GraphicRedBlackTree rbtree= new GraphicRedBlackTree(tree);
		System.out.println((tree.searchNode(12, tree.root)).key);
	}

}
