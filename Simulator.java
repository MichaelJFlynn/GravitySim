import javax.swing.*;
import java.awt.*;
import java.util.Random;


//want : 60 fps
//       1 meter per pixel
//       
public class Simulator extends JPanel {
    
    private Particle2D[] particles;
    int size;
    double G;
    double fps;
    Random randy; 

    public Simulator(double G, double fps, int num_particles) {
	super();
	this.G = G;
	this.fps = fps;
	size = 10;

	// initialize particles
	randy = new Random();
	particles = new Particle2D[num_particles];
	for(int i=0; i < num_particles; i++) {
	    particles[i] = new Particle2D(randy.nextDouble()*500, 
					   randy.nextDouble()*500, 
					   1, 
					   randy.nextDouble() * 20 - 10,
					   randy.nextDouble() * 20 - 10);
	}
	// set up window
	JFrame frame = new JFrame("Simulator");
	frame.add(this);
	frame.setResizable(false);
	frame.setSize(new Dimension(500, 500));
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
    }

    public void update_sim() {
	for(Particle2D p : particles) {
	    if(p.x > 0 && p.x < 500 && p.y > 0 && p.y < 500) {
		//calculate acceleration
		double ax = 0;
		double ay = 0;
		for(Particle2D d: particles) {
		    // if not the same particles
		    if(!p.equals(d)) {
			double r_squared = (p.x - d.x)*(p.x-d.x) + (p.y - d.y)*(p.y - d.y);
			if(r_squared < size*size/4.) {
			    // conservation of momentum
			    d.vx = (d.vx*d.mass + p.vx*p.mass)/(d.mass + p.mass);
			    // particle absorbtion
			    d.mass += p.mass;
			    p.mass = 1;
			    p.x = randy.nextDouble() * 500;
			    p.y = randy.nextDouble() * 500;
			    p.vx = randy.nextDouble() * 20 - 10;
			    p.vy = randy.nextDouble() * 20 - 10;
			    ax = 0;
			    ay = 0;
			    break;
			} else {
				a = F/mp = G md / r^2
				double a = G * d.mass / r_squared;
				// trying a new thing
				//a = G * d.mass * (1/Math.pow(r_squared/(size/2), 6) - 1/Math.pow(r_squared/(size/2), 3));
				//a = -G * d.mass * 6 *( Math.pow(r_squared/(size/4), 3) - 2) / Math.pow(r_squared/(size/4), 6.5);
				// cos = x/r, sin = y/r
				ax += a * (d.x - p.x)/Math.pow(r_squared, 0.5);
				ay += a * (d.y - p.y)/Math.pow(r_squared, 0.5);
			}
		    }
		}
		//move
		p.move(fps);
		//update velocity
		p.update_vel(fps, ax, ay);
	    } else {
		//p.mass = 1;
		p.x = randy.nextDouble()*500;
		p.y = randy.nextDouble()*500;
		p.vx = randy.nextDouble()*20 - 10;
		p.vy = randy.nextDouble()*20 - 10;
	    }
	}
    }

    public void paint(Graphics g) {
	super.paint(g);
	for(Particle2D p : particles) {
	    g.fillOval((int) p.x - size/2, (int)  p.y - size/2, size, size);
	}
    }

    public void run() {
	while(true) {
	    update_sim();
	    repaint();
	    try {
		Thread.sleep(1);
	    } catch(Exception e) {
	    
	    }
	}
    }


    public static void main(String[] args) {
	// run a simulator with G = 1
	Simulator simulator = new Simulator(100, 60, 500);
	simulator.run();
    }

}