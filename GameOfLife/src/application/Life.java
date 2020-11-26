package application;

public class Life {
    private int x;
    private int y;
    private Boolean alive;
    private Boolean change;

	public Life(int x, int y, Boolean alive, Boolean change) {
        setX(x);
        setY(y);
        setAlive(alive);
        setChange(change);
    }

    public int getX() {
        return x;
    }

 

    public void setX(int x) {
        this.x = x;
    }

 

    public int getY() {
        return y;
    }

 

    public void setY(int y) {
        this.y = y;
    }

 

    public Boolean getAlive() {
        return alive;
    }

 

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }
    

    public Boolean getChange() {
		return change;
	}



	public void setChange(Boolean change) {
		this.change = change;
	}

}
