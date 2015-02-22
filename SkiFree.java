//Should these files be in the repository as well,
// or only the code that I'm writing?

import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import javalib.colors.*;
import tester.*;

//Not sure if I really want all the moving parts to be
//part of this interface, but it seemed like a good place
//to start
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
    FromFileImage img;

    //---//---// Constructors //---//---//
    Obstacle(Posn position, int width, int height, FromFileImage img) {
	this.img = img;
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
    // -- Should maybe be a string of the image's location rather
    //    than the actual image?
    public FromFileImage getImage() {
	return this.img;
    }

    //EFFECT: returns a new Obstacle with indicated Posn, and all 
    // other attributes set to those of the original
    public Collidable move(Posn position) {
	return new Obstacle(position, this.width, this.height, this.img);
    }
    
    
}

class Player implements Collidable{

    //---//---// Fields //---//---//

    Posn position;
    int width;
    int height;
    FromFileImage img;
    int speed;
    int direction;
    static final int LEFT = -1;
    static final int STRAIGHT = 0;
    static final int RIGHT = 1;


    //---//---// Constructors //---//---//

    Player(Posn position, int width, int height,
	   FromFileImage img, int speed, int direction) {
	this.position = position;
	this.width = width;
	this.height = height;
	this.img = img;
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
	return this.img;
    }

    //EFFECT: returns the speed of the Player
    public int getSpeed() {
	return this.speed;
    }

    //EFFECT: returns the direction of the Player
    public int getDirection() {
	return this.direction;
    }

    //EFFECT: returns a new Player with indicated speed and direction,
    // and all other attributes set to those of the original
    public Player changeCourse(int speed, int direction) {
	return new Player(this.position, this.width,
			  this.height, this.img, speed, direction);
    }

    //EFFECT: returns a new player with indicated Posn, and all
    // other attricutes set to those of the original
    public Collidable move(Posn position) {
	return new Player(this.position, this.width,
			  this.height, this.img, this.speed, this.direction);
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
    int speed;
    int direction;
    
    //---//---// Constructors //---//---//

    Enemy(Posn position, int width, int height,
	  FromFileImage img, int speed, int direction) {
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

    public int getSpeed() {
	return this.speed;
    }

    public int getDirection() {
	return this.direction;
    }

    public Collidable move(Posn position) {
	return new Enemy(position, this.width, this.height,
			 this.img, this.speed, this.direction);
    }

    public Collidable changeCourse(int speed, int direction) {
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
    PureQueue moveAll(int tickDistance);
    int length();
    Collidable getFirst() throws NullQueueException;
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
    //EFFECT: return this
    public PureQueue moveAll(int tickDistance) { return this; }
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

    //EFFECT: return a new Queue, containing all the same Collidables, but
    // with each one's Posn moved up or down by the indicated distance
    public PureQueue moveAll(int tickDistance) {
	return new Queue(this.first.move(new Posn(this.first.getPosn().x,
						  this.first.getPosn().y - tickDistance)),
			 this.others.moveAll(tickDistance));
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
    
    /**
     * Method to move all obstacles up in the field except the player,
     * simulating movement down the mountain
     */    
    public Slopes moveAll(int tickDistance) {
	return new Slopes(obstacles.moveAll(tickDistance), this.skier,
			  this.width, this.height);
    }

    public boolean isCollisionHuh() {
    }
}


public class SkiFree {

}
