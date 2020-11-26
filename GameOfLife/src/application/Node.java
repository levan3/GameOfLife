package application;
    

public class Node<Q> {
	private Q value;
	private Node<Q> next;
	private Node<Q> previous;

	public Node(Q val, Node<Q> n, Node<Q> p) {
		value = val;
		next = n;
		previous = p;
	}

	public Q getValue() {
		return value;
	}

	public void setValue(Q val) {
		value = val;
	}

	public Node<Q> getNext() {
		return next;
	}

	public void setNext(Node<Q> n) {
		next = n;
	}

	public Node<Q> getPrevious() {
		return previous;
	}

	public void setPrevious(Node<Q> p) {
		previous = p;
	}

	public int size() {
		if (next == null) {
			return 1;
		} else {
			return next.size() + 1;
		}
	}

	public Node<Q> getNode(int i) {
		if (i == 0) {
			return this;
		}
		return next.getNode(i - 1);
	}

	public void insertAfter(Node<Q> x) {
		Node<Q> next = this.getNext();
		setNext(x);
		x.setNext(next);
		next.setPrevious(x);
	}

	public Q get(int j) {
		if (j == 0) {
			return  this.getValue();
		}
		return next.getNode(j - 1).getValue();
	}
}
