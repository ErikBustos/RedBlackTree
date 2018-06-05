
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphicRedBlackTree extends JFrame {
	private RedBlackTree<Integer> tree;
	private JTextField textfield = new JTextField(5);
	private PaintTree paintTree = new PaintTree();
	private JButton buttonInsert = new JButton("Insert Node");
	private JButton buttonDelete = new JButton("Delete Node");
	private JButton buttonRestart = new JButton("Restart");

	public GraphicRedBlackTree(RedBlackTree<Integer> tree) {
		this.tree = tree;
		setInterface();
	}

	private void setInterface() {
		this.setLayout(new BorderLayout());
		add(paintTree, BorderLayout.CENTER);
		JPanel panel = new JPanel();
		panel.add(new JLabel("Ingresa un entero: "));
		panel.add(textfield);
		panel.add(buttonInsert);
		panel.add(buttonDelete);
		
		panel.add(buttonRestart);
		add(panel, BorderLayout.NORTH);

		buttonInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				try{
					int key = Integer.parseInt(textfield.getText());
					if (tree.search(key,tree.root)) {
						JOptionPane.showMessageDialog(null,"El nodo : "+key+" ya está en el RBTree");
					} else {
						tree.add(key);
						paintTree.founded = tree.NIL;
						paintTree.repaint();
					}
				} catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Ingresa un valor entero.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		buttonDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				try {
					int key = Integer.parseInt(textfield.getText());
					if (tree.search(key,tree.root)== false) { 
						JOptionPane.showMessageDialog(null, "El nodo : "+key+" no está en el RBTree");
					} else {
						tree.delete(key); 
						paintTree.founded = tree.NIL;
						paintTree.repaint();
					}
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Ingresa un valor entero ", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		
		buttonRestart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionevent) {
				if(tree.root.equals(tree.NIL))
					JOptionPane.showMessageDialog(null, "El árbol ya está vacío.");
				while(!tree.root.equals(tree.NIL)) { 
					tree.delete(tree.root.key);
					paintTree.repaint();
				}
				paintTree.founded = tree.NIL;
			}
		});
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("RedBlackTree");
		setBounds(100, 100, 1000, 800);
		setVisible(true);
	}

	class PaintTree extends JPanel {
		
		public boolean contains = false;
		public RedBlackTree<Integer>.Node founded;
		private int radius = 30; // Tree node radius
		private int space = 40; 

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (!tree.root.equals(tree.NIL)) {
				showTree(g, tree.root, getWidth() / 2, 30, getWidth() / 4);
			}
		}

		private void showTree(Graphics g, RedBlackTree<Integer>.Node root, int width, int height, int heightGap) {
			Color white = new Color(255, 255, 255);
			Color Red = new Color(231, 37, 18);
			Color black = new Color(0, 0, 0);
			
			if(root.isBlack()) 
				g.setColor(white);
			else 
				g.setColor(white);
			g.fillOval(width - radius, height - radius, 2 * radius, 2 * radius);
			if(root.isBlack()) 
				g.setColor(black);
			else 
				g.setColor(Red);
			g.fillOval(width - radius + 2, height - radius + 2, 2 * radius - 4, 2 * radius - 4);
			if(root.isBlack()) 
				g.setColor(white);
			else 
				g.setColor(white);
			g.drawString(root.key + " ", width - 6, height + 4);
			g.setColor(black);
		
			if (!root.right.equals(tree.NIL)) {
				connectRightChild(g, width + heightGap, height + space, width, height);
				showTree(g, root.right, width + heightGap, height + space, heightGap / 2);
			}
			if (!root.left.equals(tree.NIL)) {
				connectLeftChild(g, width - heightGap, height + space, width, height);
				showTree(g, root.left, width - heightGap, height + space, heightGap / 2);
			}
		}

		private void connectLeftChild(Graphics g, int width1, int height1, int width2, int height2) {
			double d = Math.sqrt(space * space + (width2 - width1) * (width2 - width1));
			int width11 = (int) (width1 + radius * (width2 - width1) / d);
			int height11 = (int) (height1 - radius * space / d);
			int width21 = (int) (width2 - radius * (width2 - width1) / d);
			int height21 = (int) (height2 + radius * space / d);
			g.drawLine(width11, height11, width21, height21);
		}

		private void connectRightChild(Graphics g, int width1, int height1, int width2, int height2) {
			double d = Math.sqrt(space * space + (width2 - width1) * (width2 - width1));
			int width11 = (int) (width1 - radius * (width1 - width2) / d);
			int height11 = (int) (height1 - radius * space / d);
			int width21 = (int) (width2 + radius * (width1 - width2) / d);
			int height21 = (int) (height2 + radius * space / d);
			g.drawLine(width11, height11, width21, height21);
		}
	}
}
