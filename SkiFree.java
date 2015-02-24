import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import javalib.colors.*;
import tester.*;

import java.awt.Color;

interface Collidable {
    Posn getPosn();
    int getWidth();
    int getHeight();
    FromFileImage getImage();
    Collidable move(Posn posn);
} 

class Obstacle implements Collidable{
   
    //---//---// Fields //---//---//
    Posn position;
    int width;
    int height;
    String imgName;

    //---//---// Constructors //---//---//
    Obstacle(Posn position, int width, int height, String imgName) {
	this.imgName = imgName;
	this.width = width;
	this.height = height;
	this.position = position;
    }

    //---//---// Methods //---//---//
   
    //EFFECT: returns the Posn of the Obstacle
    public Posn getPosn() {
	return this.position;
    }
    
    //Effect: returns the width of the Obstacle
    public int getWidth() {
	return this.width;
    }

    //EFFECT: returns the height of the Obstacle
    public int getHeight() {
	return this.height;
    }

    //EFFECT: returns the image of the Obstacle
    public FromFileImage getImage() {
	return new FromFileImage(this.position, this.imgName);
    }

    //EFFECT: returns a new Obstacle with indicated Posn, and all 
    // other attributes set to those of the original
    public Collidable move(Posn position) {
	return new Obstacle(position, this.width, this.height, this.imgName);
    }
    
    
}

class Player implements Collidable{

    //---//---// Fields //---//---//

    Posn position;
    int width;
    int height;
    String imgName;
    double speed;
    int direction;


    //---//---// Constructors //---//---//

    Player(Posn position, int width, int height,
	   String imgName, double speed, int direction) {
	this.position = position;
	this.width = width;
	this.height = height;
	this.imgName = imgName;
	this.speed = speed;
	this.direction = direction;
    }


    //---//---// Methods //---//---//


    //EFFECT: returns the Posn of the Player
    public Posn getPosn() {
	return this.position;
    }
    
    //EFFECT: returns the width of the Player
    public int getWidth() {
	return this.width;
    }

    //EFFECT: returns the height of the Player
    public int getHeight() {
	return this.height;
    }

    //EFFECT: returns the image of the Player
    public FromFileImage getImage() {
	return new FromFileImage(this.position, this.imgName);
    }

    //EFFECT: returns the speed of the Player
    public double getSpeed() {
	return this.speed;
    }

    //EFFECT: returns the direction of the Player
    public int getDirection() {
	return this.direction;
    }

    //EFFECT: returns a new Player with indicated speed and direction,
    // and all other attributes set to those of the original
    public Player changeCourse(double speed, int direction) {
	return new Player(this.position, this.width, this.height,
			  this.imgName, speed, direction);
    }

    //EFFECT: returns a new player with indicated Posn, and all
    // other attricutes set to those of the original
    public Player move(Posn position) {
	return new Player(this.position, this.width,
			  this.height, this.imgName, this.speed, this.direction);
    }

}

//To be implemented once the game is working just with static obstacles
/*
class Enemy implements Collidable{
   
    //---//---// Fields //---//---//
    Posn position;
    int width;
    int height;
    FromFileImage img;
    double speed;
    int direction;
    
    //---//---// Constructors //---//---//

    Enemy(Posn position, int width, int height,
	  FromFileImage img, double speed, int direction) {
	this.position = position;
	this.width = width;
	this.height = height;
	this.img = img;
	this.speed = speed;
	this.direction = direction;
    }
    
    //---//---// Methods //---//---//

    public Posn getPosn() {
	return this.position;
    }
    
    public int getWidth() {
	return this.width;
    }

    public int getHeight() {
	return this.height;
    }

    public FromFileImage getImage() {
	return this.img;
    }

    public double getSpeed() {
	return this.speed;
    }

    public int getDirection() {
	return this.direction;
    }

    public Collidable move(Posn position) {
	return new Enemy(position, this.width, this.height,
			 this.img, this.speed, this.direction);
    }

    public Collidable changeCourse(double speed, int direction) {
	return new Enemy(this.position, this.width, this.height,
			 this.img, speed, direction);
    }
} */



//Pure Queue implementation for keeping track of Obstacles in Slopes


//Exception for the case when an attempt is made to access an element
// of the null Queue
// -- This part I'm really unsure on. Is this the correct situation to
//    use an exception, and if so, is this exception implemented correctly?
class NullQueueException extends Exception {
    NullQueueException(String message) {
	super(message);
    }
}

interface PureQueue {
    boolean isEmptyHuh();
    PureQueue add(Collidable c);
    PureQueue remove();
    PureQueue moveAll(int tickDistance, int playerDir);
    int length();
    Collidable getFirst() throws NullQueueException;
    PureQueue getOthers();
    WorldImage drawAll();
}

class NullQueue implements PureQueue {
    NullQueue() {}
    
    //EFFECT: return true
    public boolean isEmptyHuh() { return true; }
    //Effect: return a new Queue containing only the new item
    public PureQueue add(Collidable c) {
	return new Queue(c, this);
    }
    //Effect: return this
    public PureQueue remove() {return this;}
    //Effect: return 0
    public int length() { return 0; }
    //Effect: throw a NullQueueException, since there is no first
    // item in a NullQueue to be accessed
    public Collidable getFirst() throws NullQueueException {
	throw new NullQueueException("No members in an empty queue");
    }
    //EFFECT: return this, since there are no others
    public PureQueue getOthers() { return this; }
    //EFFECT: return this
    public PureQueue moveAll(int tickDistance, int playerDir) { return this; }
    public WorldImage drawAll() {
	return new CircleImage(new Posn(0,0), 0, Color.WHITE);
    }
}

class Queue implements PureQueue {
    Collidable first;
    PureQueue others;

    Queue(Collidable first, PureQueue others) {
	this.first = first;
	this.others = others;
    }

    //EFFECT: return false
    public boolean isEmptyHuh() { return false; }

    //EFFECT: return a new Queue that's like the original, but
    // with the new item added onto the end
    public PureQueue add(Collidable c) {
	return new Queue(this.first, this.others.add(c));
    }

    //EFFECT: return a new Queue that's like the original, but with
    // the very first item removed
    public PureQueue remove() {
	return new Queue(this.first, this.others.remove());
    }

    //EFFECT: return an int representing the number of elements in this
    // Queue
    public int length() {
	return 1 + this.others.length();
    }
    
    //EFFECT: return the first item in this Queue
    public Collidable getFirst() {
	return this.first;
    }

    //EFFECT: return others
    public PureQueue getOthers() {
	return this.others;
    }

    //EFFECT: return a new Queue, containing all the same Collidables, but
    // with each one's Posn moved up or down by the indicated distance
    public PureQueue moveAll(int tickDistance, int playerDir) {
	return new Queue(this.first.move(new Posn(this.first.getPosn().x - playerDir,
						  this.first.getPosn().y - tickDistance)),
			 this.others.moveAll(tickDistance, playerDir));
    }

    public WorldImage drawAll() {
	return new OverlayImages(this.first.getImage(), others.drawAll());
    }
}

class Slopes {
    //---//---// Fields //---//---//
    PureQueue obstacles;
    Player skier;
    int width;
    int height;
    
    //---//---// Constructors //---//---//
    Slopes(PureQueue obstacles, Player skier, int width, int height) {
	this.obstacles = obstacles;
	this.skier = skier;
	this.width = width;
	this.height = height;
    }

    //---//---// Methods //---//---//
    
    public Slopes moveSlopes(int tickDistance) {	
	PureQueue temp = this.obstacles;
	try {
	    if(temp.getFirst().getPosn().y <= tickDistance + 5*this.skier.getSpeed()) {
		temp = temp.remove();
	    }
	}
	catch(NullQueueException e) { }
	finally {
	    if(Math.random() <= .1) {
		temp = temp.add(new Obstacle(new Posn((int) (Math.random() * this.width), this.height),
					     20, 20, "Images/fish.png"));
	    }
	    return new Slopes(temp.moveAll(tickDistance + (int) (5*this.skier.getSpeed()),
					   5*this.skier.getDirection()), 
			      this.skier, this.width, this.height);
	}
    }


    //EFFECTS: returns true if the player is overlapping with a
    // particular obstacle
    public boolean collidingHuh(Collidable c, Player p) {
	return Math.abs(c.getPosn().x - p.getPosn().x) <
	    (c.getWidth()/2 + p.getWidth()/2) &&
	    Math.abs(c.getPosn().y - p.getPosn().y) <
	    (c.getHeight()/2 + p.getHeight()/2);
    }

    //EFFECTS: returns true if the player is overlapping with
    // any of the obstacles on the field
    public boolean isCollisionHuh(PureQueue c) {
	try {
	    return collidingHuh(c.getFirst(), this.skier) ||
		isCollisionHuh(c.getOthers());
	}
	catch(NullQueueException e) {
	    return false;
	}
    }

    public PureQueue getObstacles() {
	return this.obstacles;
    }

    public Player getSkier() {
	return this.skier;
    }
    
    public int getWidth() {
	return this.width;
    }

    public int getHeight() {
	return this.height;
    }
}


public class SkiFree extends World {
   
    //---//---// Fields //---//---//
    int width;
    int height;
    Slopes slopes;
    static final int LEFT = -1;
    static final int STRAIGHT = 0;
    static final int RIGHT = 1;
    static final double SLOW = .75;
    static final double NORMAL = 1.0;
    static final double FAST = 1.25;
    static final int TICKDISTANCE = 10;

    SkiFree(Slopes slopes, int width, int height) {
	this.width = width;
	this.height = height;
	this.slopes = slopes;
    }

    public World onKeyEvent(String key) {
	
	if(key.equals("left")) {		
	    return new SkiFree(new Slopes(slopes.getObstacles(),
					  slopes.getSkier().changeCourse(SLOW, LEFT),
					  slopes.getWidth(),
					  slopes.getHeight()),
			       this.width, this.height);
	    
	} else if(key.equals("right")) {
	    return new SkiFree(new Slopes(slopes.getObstacles(),
					  slopes.getSkier().changeCourse(SLOW, RIGHT),
					  slopes.getWidth(),
					  slopes.getHeight()),
			       this.width, this.height);
	    
	} else if(key.equals("down")) {
	    return new SkiFree(new Slopes(slopes.getObstacles(),
					  slopes.getSkier().changeCourse(FAST, STRAIGHT),
					  slopes.getWidth(),
					  slopes.getHeight()),
			       this.width, this.height);
	    
	} else if(key.equals("up")) {
	    return new SkiFree(new Slopes(slopes.getObstacles(),
					  slopes.getSkier().changeCourse(SLOW, slopes.getSkier().getDirection()),
					  slopes.getWidth(),
					  slopes.getHeight()),
			       this.width, this.height);
	    
	} else if(key.equals("q")) {
	    return this.endOfWorld("Thanks for playing!");
	} else
	    return this;
    }

    public World onTick() {
	return new SkiFree(slopes.moveSlopes((int) (TICKDISTANCE *
						    slopes.getSkier().getSpeed())),
			   this.width, this.height);
    }
    
    public WorldImage makeImage() {
	return new OverlayImages(slopes.getSkier().getImage(),
				 slopes.getObstacles().drawAll());		 
    }

    public WorldImage lastImage(String s) {
	return new OverlayImages(new RectangleImage(new Posn(this.width/2, this.height/2),
						    this.width,
						    this.height,
						    Color.BLACK),
				 new TextImage(this.slopes.getSkier().getPosn(),
					       s,
					       30,
					       Color.RED)); 
    }

    public WorldEnd worldEnds() {
	if(this.slopes.isCollisionHuh(this.slopes.obstacles)) {
	    return new WorldEnd(true, this.lastImage("Ouch! Better luck next time!"));
	} else {
	    return new WorldEnd(false, this.makeImage());
	}
    } 

    public static void main(String[] args) {
	
	PureQueue qu = new NullQueue();
	Player plyr = new Player(new Posn(400, 200), 20, 40,
				 "Images/fish.png", NORMAL, STRAIGHT);
	Slopes slps = new Slopes(qu, plyr, 1000, 800);
	SkiFree s = new SkiFree(slps, 800, 600);
	s.bigBang(800, 600, .03);
    }
    
}
	    

    
