
public class RedBlackTree <T extends Comparable<? super T>>{

	int treeSize;
	Node root;
	Node NIL;
	
	
	protected class Node{
		T key;
		boolean isRed;
		Node left,right,parent;
		
		public Node(T key){			
			this.key=key;
			this.isRed= true;			
		}
		
	
		public Node() {
			// TODO Auto-generated constructor stub
		}


		public void setBlack(){
			isRed=false;
		}
		
		public void setRed(){
			isRed=true;
		}
		
		public boolean isRed(){
			return(isRed);
		}
		public boolean isBlack(){
			if(isRed==false) return true;
			return false;
		}
		
	}
	
	public RedBlackTree(){
		NIL=new Node(null);
		NIL.setBlack();
		root=NIL;
		NIL.right=root;
		NIL.left=root;
		NIL.parent=root;
		treeSize=0;
	}
	
	private void leftRotate(Node p){
		Node y=p.right;
		p.right= y.left;
		if(y.left!=NIL)
			y.left.parent=p;
		y.parent=p.parent;
		if(p.parent==NIL) //si el padre no tiene padre significa que es la raíz
			root=y;
		else 
			if(p== p.parent.left)
			p.parent.left=y;
		else 
			p.parent.right=y;
		
		y.left=p;
		p.parent=y;
		
	}
	
	private void rightRotate(Node p){
		Node x=p.left;
		p.left=x.right;
		if(p.left!=NIL)
			x.right.parent=p;
		x.parent=p.parent;
		
		x.right=p;
		p.parent=x;
		
		if(root == p)
			root=x;
		else
			if(x.parent.left==p)
				x.parent.left=x;
			else 
				x.parent.right=x;
	}
	
	public boolean add(T key){
		Node z= new Node(key);
		Node y= NIL;
		Node x= this.root;
		while(x!= NIL){
			y=x;  
			if(z.key.compareTo(x.key)<0)
				x=x.left;
			else x=x.right;			
		}
		z.parent=y;
		if(y==NIL)
			root=z;
		else if(z.key.compareTo(y.key)<0)
			y.left=z;
		else y.right=z;
		z.left=NIL;
		z.right=NIL;
		z.setRed();
		
		addFixUp(z);
		
		treeSize++;
		return true;
	}
	
	private void addFixUp(Node z){
		Node y=NIL;
		while(z.parent.isRed()==true){ //propiedad 3 
			if(z.parent==z.parent.parent.left){  // si el ppadre es hijo izq
				y=z.parent.parent.right;  // y es el tio derecho de z 
				if(y.isRed()){   //caso 1:tio rojo
					z.parent.setBlack();  //colorea el padre
					y.setBlack();    //colorea el tio
					z.parent.parent.setRed();   //colorea el abuelo 
					z=z.parent.parent;
				}
				else {   //si el tio es negro...
					if(z==z.parent.right){  //si z es el hijo derecho(triangulo)
					z=z.parent;  
					leftRotate(z);
				}
					
					//sino es el hijo izq (LINEA)
				z.parent.setBlack();
				z.parent.parent.setRed();
				rightRotate(z.parent.parent);
				}
			}
			else{   // si el padre es hijo derecho
				
					 y=z.parent.parent.left;  //y es el tio izquierdo de Z
					if(y.isRed()){
						z.parent.setBlack();
						y.setBlack();
						z.parent.parent.setRed();
						z=z.parent.parent;
					}
					else {
						if(z==z.parent.left){
					
						z=z.parent;
						rightRotate(z);
					}
					z.parent.setBlack();
					z.parent.parent.setRed();
					leftRotate(z.parent.parent);
					}
			}
				
		}
		root.setBlack();
	}

	public void delete(T key) {
		Node z = searchNode( key, root);
		Node y = z;
		Node x;
		if (z==null) return;
		boolean originalColor = y.isRed();
		
		if(z.left == NIL) { //si z no tiene hijo izq 
			x = z.right;
			transplant(z, z.right);  //quita z , por hijo derecho 
		} else if (z.right == NIL) { // si z no tiene hijo derecho
			x = z.left; 
			transplant(z, z.left);  //quita z por el hijo izq
		} else { // si tiene hijo izq y derecho
			y = minimum(z.right); // busca el minimo del hijo derecho de z 
			originalColor = y.isRed();  //color del minimo (y)
			x = y.right;    // x es el hijo derecho del minimo
			if(y.parent == z) { //si z es el papa del minimo.. el padre del hijo derecho del minimo es y 
				x.parent = y;   
			} else {
				transplant(y, y.right);
				y.right = z.right;
				y.right.parent = y;
			}
			transplant(z, y);
			y.left = z.left;
			y.left.parent = y;
			y.isRed = z.isRed();
		}
		if(originalColor == false) { //false = black
			deleteFixup(x);
		}		
		
	}

	public void deleteFixup(Node x) {
		Node w;
		while(x != root && x.isBlack()) {
			if(x == x.parent.left) {
				w = x.parent.right;
				if(w.isRed()) {
					w.setBlack();
					x.parent.setRed();
					leftRotate(x.parent);
					w = x.parent.right;
				}
				if(w.left.isBlack() && w.right.isBlack()) {
					w.setRed();
					x = x.parent;
				} else {
					if(w.right.isBlack()) {
						w.left.setBlack();
						w.setRed();
						rightRotate(w);
						w = x.parent.right;
					}
					w.isRed = x.parent.isRed();
					x.parent.setBlack();
					w.right.setBlack();
					leftRotate(x.parent);
					x = root;
				}
			} else {
				w = x.parent.left;
				if(w.isRed()) {
					w.setBlack();
					x.parent.setRed();
					rightRotate(x.parent);
					w = x.parent.left;
				}
				if(w.right.isBlack() && w.left.isBlack()) {
					w.setRed();
					x = x.parent;
				} else {
					if(w.left.isBlack()) {
						w.right.setBlack();
						w.setRed();
						leftRotate(w);
						w = x.parent.left;
					}
					w.isRed = x.parent.isRed();
					x.parent.setBlack();
					w.left.setBlack();
					rightRotate(x.parent);
					x = root;
				}
			}
		}
		x.setBlack();
	}
	
	public void transplant(Node x, Node y) {  //saca a la verga x y lo cambia por y
		if(x.parent == NIL) {
			root = y;
		} else if(x == x.parent.left) { // si x es hijo izquierdo 
			x.parent.left = y;   
		} else {  //si x es el hijo derecho 
			x.parent.right = y;
		}
		y.parent = x.parent;  
	}
	
	public Node minimum(Node x) {
		Node y = x;
		while(y.left != NIL) {
			y = y.left;
		}
		return y;
	}
	
	private Node treeMaximum(Node x){
		while(x.right!=NIL)
			x=x.right;
		return x;
	}
	
	public boolean search (T key, Node current) {
		if (current == NIL)
			return false;
		int response = current.key.compareTo(key);

		if (response == 0)
			return true;
		else if (response < 0)
			return search(key, current.right);
		else
			return search(key, current.left);
	}

	public Node searchNode(T key, Node current) {
		if (current == NIL) return null;
		int result = key.compareTo(current.key);
		if (result == 0) return current;
		else if (result < 0) return searchNode(key, current.left);
		else return searchNode(key, current.right);
	}
	private void print(Node node) {
		
		if (node != NIL) {
			String color;
			if(node.isRed())
				color= "red";
			else color="black";
			
			print(node.left);
			System.out.println(node.key+ " "+ color);
			print(node.right);
		}
	}

	public void print() {
		print(this.root);
		System.out.println("-------");
	}
	
    
    private void imprimirConNivel(Node node, int nivel){

    	if (node != NIL) {
			String color;
			if(node.isRed())
				color= "red";
			else color="black";
			
        if(node !=null){
            imprimirConNivel(node.left,nivel+1);
            System.out.println(node.key + " "+color+ "("+nivel+") ");
                    imprimirConNivel(node.right,nivel+1);

        }

    }
    }

     	public void imprimirConNivel(){

    		imprimirConNivel(root,1);
    		System.out.println();
    	}

    }


