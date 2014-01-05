

public class Particle2D {

    protected double x;
    protected double y;
    protected double mass;
    protected double vx;
    protected double vy;

    public Particle2D(double x, double y, double mass, double vx, double vy) {
	this.x = x;
	this.y = y;
	this.mass = mass;
	this.vx = vx;
	this.vy = vy;
    }
    
    public void move(double fps) {
	x = x + vx/fps;
	y = y + vy/fps;
    }

    public void update_vel(double fps, double ax, double ay) {
	vx = vx + ax/fps;
	vy = vy + ay/fps;
    }

    public boolean equals(Particle2D other) {
	if( x == other.x &&
	    y == other.y &&
	    mass == other.mass &&
	    vx == other.vx &&
	    vy == other.vy) 
	    {
	    return true;
	}
    }
} 