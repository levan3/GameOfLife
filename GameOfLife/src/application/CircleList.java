package application;


public class CircleList<T> {
	private Node<T> head;

	public void prepend(T x) {
		Node<T> w = new Node<T>(x, head, null);
		head = w;
		w.setPrevious(head);
	}

	public void prepend(Node<T> w) {
		w.setNext(head);
		head.setPrevious(w);
		head = w;
	}

	public Node<T> getHead() {
		return head;
	}

	public int size() {
		if (head == null)
			return 0;
		return head.size();
	}

	public void circleLink() {
		if (head == null) {
			return;
		}
		Node<T> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.setNext(head);
		head.setPrevious(last);
	}

	public void displayNext(Node<T> x) {
		if (head == null) {
			head = x;
			return;
		}
		Node<T> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
			System.out.println(last.getValue());
		}
	}

	public void displayPrevious(Node<T> x) {
		if (head == null) {
			head = x;
			return;
		}
		Node<T> last = head;
		while (last.getPrevious() != null) {
			last = last.getPrevious();
			System.out.println(last.getValue());
		}
	}

	public void appendNode(Node<T> x) {
		if (head == null) {
			head = x;
			return;
		}
		Node<T> last = head;
		while (last.getNext() != null) {
			last = last.getNext();
		}
		last.setNext(x);
		x.setPrevious(last);
	}

	public Node<T> getNode(int i) {
		return head.getNode(i);
	}
	
	public T get(int i) {
		return head.getNode(i).getValue();
	}
	
	public Node<T> getPreviousNode(int i) {
		Node<T> node = head.getNode(i);
		return node.getPrevious();
	}
	
	public Node<T> getNextNode(int i) {
		Node<T> node = head.getNode(i);
		return node.getNext();
	}

	public int findNode(T x) {
		int i = 0;
		Node<T> w = head;
		while (!w.getValue().equals(x)) {
			w = w.getNext();
			i++;
		}
		return i;
	}

	public void remove(int i) {
		if (i == 0) {
			head = head.getNext();
		} else {
			Node<T> nodeBefore = this.getNode(i - 1);
			Node<T> removedNode = this.getNode(i);
			nodeBefore.setNext(removedNode.getNext());
		}
	}

	public void add(Node<T> x, int i) {
		Node<T> w = getNode(i);
		w.insertAfter(x);
	}

	public void set(int i, Node<T> x) {
		Node<T> w = getNode(i);
		w.setValue(x.getValue());
	}
}