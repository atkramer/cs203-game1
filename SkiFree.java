import javalib.worldimages.*;
import javalib.funworld.*;
import javalib.worldcanvas.*;
import javalib.colors.*;
import tester.*;

import java.awt.Color;

interface Collidable {
    int getWidth() throws NoEnemyException;
    int getHeight() throws NoEnemyException;
    Posn getPosn() throws NoEnemyException;
    boolean collidingHuh(Collidable c);
}

class Obstacle implements Collidable {
   
    //---//---// fields //---//---//
    Posn position;
    int width;
    int height;
    String imgName;

    //---//---// constructors //---//---//
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
    public Obstacle move(Posn position) {
	return new Obstacle(position, this.width, this.height, this.imgName);
    }
    

    //EFFECT: Return true if this Obstacle is overlapping at all with the
    //        given Collidable, false otherwise
    public boolean collidingHuh(Collidable c) {
	try {
	    return Math.abs(c.getPosn().x - this.position.x) <
		(c.getWidth()/2 + this.width/2) &&
		Math.abs(c.getPosn().y - this.position.y) <
		(c.getHeight()/2 + this.height/2);
	} catch(NoEnemyException e) {
	    return false;
	}
    }
    
    
    //EFFECT: Return one of three pre-determined Obstacles at the given
    //        position
    public static Obstacle randObstacle(Posn position) {
	double rand = Math.random();
	if(rand <= .33) {
	    return new Obstacle(position, 15, 25, "Images/tree.png");
	} else if(rand <= .66) {
	    return new Obstacle(position, 35, 15, "Images/log.png");
	} else {
	    return new Obstacle(position, 20, 10, "Images/rock.png");
	}
    }
    
    
}

class Player implements Collidable {

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
    public Player changeCourse(double speed, int direction, String imgName) {
	return new Player(this.position, this.width, this.height,
			  imgName, speed, direction);
    }

    //EFFECT: returns a new player with indicated Posn, and all
    // other attricutes set to those of the original
    public Player move(Posn position) {
	return new Player(this.position, this.width,
			  this.height, this.imgName, this.speed, this.direction);
    }


    //EFFECT: Return true if this player is overlapping at all with the given
    //        Collidable, and false otherwise
    public boolean collidingHuh(Collidable c) {
	try {
	    return Math.abs(c.getPosn().x - this.position.x) <
		(c.getWidth()/2 + this.width/2) &&
		Math.abs(c.getPosn().y - this.position.y) <
		(c.getHeight()/2 + this.height/2);
	} catch(NoEnemyException e) {
	    return false;
	}
    }
}

//Interface for Enemy or nothing, since Slopes needs to be able to sometimes
//have an Enemy and sometimes not

interface EnemySpace extends Collidable{
    boolean emptyHuh();
    Posn getPosn() throws NoEnemyException;
    int getWidth() throws NoEnemyException;
    int getHeight() throws NoEnemyException;
    FromFileImage getImage() throws NoEnemyException;
    double getSpeed() throws NoEnemyException;
    boolean collidingHuh(Collidable c);
    EnemySpace move(Posn position);
    EnemySpace move(int dx, int dy);
    EnemySpace moveTowards(Posn position, int distance);
    EnemySpace changePic(String imgName);
} 


//Exception to be created when a program tries to access some information
//about an Enemy not present in the NoEnemy class
class NoEnemyException extends Exception {
    NoEnemyException(String message) {
	super(message);
    }
}

//Class to represent the option of having no Enemy
//Implements EnemySpace

class NoEnemy implements EnemySpace{
    public boolean emptyHuh() {
	return true;
    }
    public Posn getPosn() throws NoEnemyException {
	throw new NoEnemyException("Nothing here");
    }
    public int getWidth() throws NoEnemyException {
	throw new NoEnemyException("Nothing here");
    }
    public int getHeight() throws NoEnemyException {
	throw new NoEnemyException("Nothing here");
    }
    public FromFileImage getImage() throws NoEnemyException {
	throw new NoEnemyException("Nothing here");
    }
    public double getSpeed() throws NoEnemyException {
	throw new NoEnemyException("Nothing here");
    }
    public boolean collidingHuh(Collidable c) {
	return false;
    }
    public EnemySpace move(Posn position) {
	return this;
    }
    public EnemySpace move(int dx, int dy) {
	return this;
    }
    public EnemySpace moveTowards(Posn position, int distance) {
	return this;
    }
    public EnemySpace changePic(String imgName) {
	return this;
    }
}


class Enemy implements EnemySpace{
   
    //---//---// Fields //---//---//
    Posn position;
    int width;
    int height;
    String imgName;
    double speed;
    int direction;
    
    //---//---// Constructors //---//---//

    Enemy(Posn position, int width, int height,
	  String imgName, double speed) {
	this.position = position;
	this.width = width;
	this.height = height;
	this.imgName = imgName;
	this.speed = speed;
    }
    
    //---//---// Methods //---//---//

    public boolean emptyHuh() {
	return false;
    }

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
	return new FromFileImage(this.position, this.imgName);
    }

    public double getSpeed() {
	return this.speed;
    }
    

    //EFFECT: Return a new Enemy that is like this, but with its Posn set to the
    //        one given
    public EnemySpace move(Posn position) {
	return new Enemy(position, this.width, this.height,
			 this.imgName, this.speed);
    }


    //EFFECT: Return a new Enemy that is like this, but with its Posn moved by the 
    //        given values
    public EnemySpace move(int dx, int dy) {
	return new Enemy(new Posn(this.position.x + dx, this.position.y + dy),
			 this.width, this.height, this.imgName, this.speed);
    }


    //EFFECT: Returns a new Enemy that is like this, but moved the given
    //        distance towards the given destination POSN
    public EnemySpace moveTowards(Posn destination, int distance) {
	//The total distance between the EnemySpace and the DESTINATION
	int distToTarget = (int) Math.hypot(destination.x-this.position.x,
					 destination.y-this.position.y);
	int xDist = (destination.x - this.position.x);
	int yDist = (destination.y - this.position.y);
	//The new x-distance to be traveled, that will cause the Enemy to move
	//a total of DISTANCE towards the destination
	int newX = (distance*xDist)/distToTarget;
	//The new y-distance to be traveled, that will cause the Enemy to move
	//a total of DISTANCE towards the destination
	int newY = (distance*yDist)/distToTarget;
	if(distToTarget >= distance) {
	    return this.move((int) (this.speed*newX), (int) (this.speed*newY));
	}
	//If the destination is closer than the given DISTANCE, just move to the 
	//destination
	else {
	    return this.move((int) (this.speed*xDist), (int) (this.speed*yDist));
	}

    }
    

    //EFFECT: Return a new Enemy with all of the same attributes as this, except
    //        for the imgName which is set to the given string
    public EnemySpace changePic(String imgName) {
	return new Enemy(this.position, this.width, this.height, imgName,
			 this.speed);
    }


    //EFFECT: Return true if there exists any overlap between this and 
    //        the given Collidable, false otherwise
    public boolean collidingHuh(Collidable c) {
	try {
	    return Math.abs(c.getPosn().x - this.position.x) <
		(c.getWidth()/2 + this.width/2) &&
		Math.abs(c.getPosn().y - this.position.y) <
		(c.getHeight()/2 + this.height/2);
	} catch(NoEnemyException e) {
	    return false;
	}
    }
} 



//Pure Queue implementation for keeping track of Obstacles in Slopes


//Exception for the case when an attempt is made to access an element
// of the null Queue
class NullQueueException extends Exception {
    NullQueueException(String message) {
	super(message);
    }
}

interface PureQueue {
    boolean isEmptyHuh();
    PureQueue add(Obstacle c);
    PureQueue remove();
    PureQueue moveAll(int tickDistance, int playerDir);
    int length();
    Obstacle getFirst() throws NullQueueException;
    PureQueue getOthers();
    WorldImage drawAll();
}

class NullQueue implements PureQueue {
    NullQueue() {}
    
    //EFFECT: return true
    public boolean isEmptyHuh() { return true; }
    //Effect: return a new Queue containing only the new item
    public PureQueue add(Obstacle c) {
	return new Queue(c, this);
    }
    //Effect: return this
    public PureQueue remove() {return this;}
    //Effect: return 0
    public int length() { return 0; }
    //Effect: throw a NullQueueException, since there is no first
    // item in a NullQueue to be accessed
    public Obstacle getFirst() throws NullQueueException {
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
    Obstacle first;
    PureQueue others;

    Queue(Obstacle first, PureQueue others) {
	this.first = first;
	this.others = others;
    }

    //EFFECT: return false
    public boolean isEmptyHuh() { return false; }

    //EFFECT: return a new Queue that's like the original, but
    // with the new item added onto the end
    public PureQueue add(Obstacle c) {
	return new Queue(this.first, this.others.add(c));
    }

    //EFFECT: return a new Queue that's like the original, but with
    // the very first item removed
    public PureQueue remove() {
	try {
	    return new Queue(this.others.getFirst(), this.others.getOthers());
	} 
	catch(NullQueueException e) {
	    return new NullQueue();
	}
    }

    //EFFECT: return an int representing the number of elements in this
    // Queue
    public int length() {
	return 1 + this.others.length();
    }
    
    //EFFECT: return the first item in this Queue
    public Obstacle getFirst() {
	return this.first;
    }

    //EFFECT: return others
    public PureQueue getOthers() {
	return this.others;
    }

    //EFFECT: return a new Queue, containing all the same Obstacles, but
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
    EnemySpace yeti;
    int width;
    int height;
    static final int LEFT = -1;
    static final int STRAIGHT = 0;
    static final int RIGHT = 1;
    static final double SLOW = .75;
    static final double NORMAL = 1.0;
    static final double YETISPEED = 1.4;
    static final double FAST = 1.6;
    static final int TICKDISTANCE = 10;
    
    //---//---// Constructors //---//---//
    Slopes(PureQueue obstacles, Player skier, int width, int height) {
	this.obstacles = obstacles;
	this.skier = skier;
	this.yeti = new NoEnemy();
	this.width = width;
	this.height = height;
    }

    Slopes(PureQueue obstacles, Player skier, EnemySpace yeti, int width, int height) {
	this.obstacles = obstacles;
	this.skier = skier;
	this.yeti = yeti;
	this.width = width;
	this.height = height;
    }

    //---//---// Methods //---//---//
    

    //EFFECT: Return a new Slopes, with the current EnemySpace of Slopes changed
    //        to a new Enemy, added at a random location at the top of Slopes, and
    //        the rest of the attributes of Slopes unchanged
    public Slopes addYeti() {
	return new Slopes(this.obstacles,
			  this.skier,
			  new Enemy(new Posn((int) (Math.random() * this.width), 0),
				    30,
				    40,
				    "Images/yeti-1-left.png",
				    YETISPEED),
			  this.width,
			  this.height);	    
    }
    
    //EFFECT: Return a new Slopes with all attributes unchanged except for the Enemy,
    //        which is replaced by an Enemy with the same attributes, except for a
    //        new String for the image name
    public Slopes setYetiPic(String imgName) {
	return new Slopes(this.obstacles, this.skier, this.yeti.changePic(imgName),
			  this.width, this.height);
    }
    

    //EFFECT: Return a new Slopes, with all of the obstacles in the queue and the enemy
    //        moved a distance based on the Player's speed and direction, and then the Enemy
    //        moved toward the Player. In addition to moving Obstacles, it has a change to randomly
    //        add an Obstacle to the Queue, and will remove any Obstacle that goes above the
    //        top of Slopes
    public Slopes moveSlopes(int tickDistance) {	
	PureQueue temp = this.obstacles;
	try {
	    if(temp.getFirst().getPosn().y <= tickDistance + 5*this.skier.getSpeed()) {
		temp = temp.remove();
	    }
	}
	catch(NullQueueException e) { }
	finally {
	    if(Math.random() <= .2 * this.skier.getSpeed()) {
		temp = temp.add(Obstacle.randObstacle(new Posn((int) (Math.random() * this.width), this.height)));
	    }
	    if(this.skier.getDirection() <= LEFT && Math.random() <= .2 * this.skier.getSpeed()) {
		temp = temp.add(Obstacle.randObstacle(new Posn(0, (int) (Math.random() * this.height))));
	    }
	    if(this.skier.getDirection() >= RIGHT && Math.random() <= .2 * this.skier.getSpeed()) {
		temp = temp.add(Obstacle.randObstacle(new Posn(this.width, (int) (Math.random() * this.height))));
	    }
	    return new Slopes(temp.moveAll(tickDistance + (int) (5*this.skier.getSpeed()),
					   5*this.skier.getDirection()), 
			      this.skier,
			      this.yeti.move(-this.skier.getDirection(),
					     (int) -(tickDistance*this.skier.getSpeed())).moveTowards(this.skier.getPosn(), tickDistance),
			      this.width, this.height);
	}
    }


    //EFFECTS: returns true if the player is overlapping with
    // any of the obstacles on the field
    public boolean isCollisionHuh(PureQueue c) {
	try {
	    return this.skier.collidingHuh(this.yeti) ||
		this.skier.collidingHuh(c.getFirst()) ||
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

    public EnemySpace getYeti() {
	return this.yeti;
    }
    
    public int getWidth() {
	return this.width;
    }

    public int getHeight() {
	return this.height;
    }
}

enum GameState {
    START, GAME, CRASH, GAMEOVER
}

public class SkiFree extends World {
   
    //---//---// Fields //---//---//
    int width;
    int height;
    Slopes slopes;
    int score;
    int yetiCount;
    GameState state;

    public static final int LEFTER = -2;
    public static final int LEFT = -1;
    public static final int STRAIGHT = 0;
    public static final int RIGHT = 1;
    public static final int RIGHTER = 2;

    public static final double SLOW = .75;
    public static final double NORMAL = 1.0;
    public static final double YETISPEED = 1.4;
    public static final double FAST = 1.6;

    public static final int TICKDISTANCE = 10;

    SkiFree(Slopes slopes, int width, int height, int score, int yetiCount, GameState state) {
	this.slopes = slopes;
	this.width = width;
	this.height = height;
	this.score = score;
	this.yetiCount = yetiCount;
	this.state = state;
    }

    public Slopes getSlopes() {
	return this.slopes;
    }

    public int getWidth() {
	return this.width;
    }

    public int getHeight() {
	return this.height;
    }

    public int getScore() {
	return this.score;
    }

    public int getYetiCount() {
	return this.yetiCount;
    }

    public GameState getState() {
	return this.state;
    }


    //EFFECT: Returns a new SkiFree with the same dimensions as the one this is called
    //        on, and all other attributes initialized for the beginning of the game
    public World restart() {
	return new SkiFree(new Slopes(new NullQueue(),
				      new Player(this.slopes.getSkier().getPosn(),
						 this.slopes.getSkier().getWidth(),
						 this.slopes.getSkier().getHeight(),
						 "Images/playerStraight.png",
						 NORMAL,
						 STRAIGHT),
				      new NoEnemy(),
				      this.slopes.getWidth(), this.slopes.getHeight()),
			   this.width, this.height,
			   0,
			   1,
			   GameState.GAME);
    }


    //EFFECT: Return a new SkiFree with the proper changes made based on key input from
    //        the user, and the current state of the game
    public World onKeyEvent(String key) {
	//If the game's in the START or GAMEOVER state, only recognize "N", and restart
	//the game on that input
	if(this.state == GameState.START || this.state == GameState.GAMEOVER) {
	    if(key.equals("n")) {
		return this.restart();
	    } else {
		return this;
	    }
	    //If the game's in the GAME state, then recognize arrow key input, changing the 
	    //player accordingly, and recognize "Q", ending the game
	} else if(this.state == GameState.GAME) {
	    int dir = this.slopes.getSkier().getDirection();
	    if(key.equals("left") && (dir==LEFT || dir==LEFTER)) {		
		return new SkiFree(new Slopes(slopes.getObstacles(),
					      slopes.getSkier().changeCourse(SLOW, LEFTER, "Images/playerVeryLeft.png"),
					      slopes.getYeti(),
					      slopes.getWidth(),
					      slopes.getHeight()),
				   this.width, this.height, this.score, this.yetiCount, this.state);
		
	    } else if(key.equals("left")) {		
		return new SkiFree(new Slopes(slopes.getObstacles(),
					      slopes.getSkier().changeCourse(NORMAL, LEFT, "Images/playerSomeLeft.png"),
					      slopes.getYeti(),
					      slopes.getWidth(),
					      slopes.getHeight()),
				   this.width, this.height, this.score, this.yetiCount, this.state);
		
	    } else if(key.equals("right") && (dir==RIGHT || dir==RIGHTER)) {
		return new SkiFree(new Slopes(slopes.getObstacles(),
					      slopes.getSkier().changeCourse(SLOW, RIGHTER, "Images/playerVeryRight.png"),
					      slopes.getYeti(),
					      slopes.getWidth(),
					      slopes.getHeight()),
				   this.width, this.height, this.score, this.yetiCount, this.state);
		
	    } else if(key.equals("right")) {
		return new SkiFree(new Slopes(slopes.getObstacles(),
					      slopes.getSkier().changeCourse(NORMAL, RIGHT, "Images/playerSomeRight.png"),
					      slopes.getYeti(),
					      slopes.getWidth(),
					      slopes.getHeight()),
				   this.width, this.height, this.score, this.yetiCount, this.state);
		
	    } else if(key.equals("down") && dir==STRAIGHT) {
		return new SkiFree(new Slopes(slopes.getObstacles(),
					      slopes.getSkier().changeCourse(FAST, STRAIGHT, "Images/playerStraight.png"),
					      slopes.getYeti(),
					      slopes.getWidth(),
					      slopes.getHeight()),
				   this.width, this.height, this.score, this.yetiCount, this.state);
		
	    } else if(key.equals("down")) {
		return new SkiFree(new Slopes(slopes.getObstacles(),
					      slopes.getSkier().changeCourse(NORMAL, STRAIGHT, "Images/playerStraight.png"),
					      slopes.getYeti(),
					      slopes.getWidth(),
					      slopes.getHeight()),
				   this.width, this.height, this.score, this.yetiCount, this.state);
		
	    } else if(key.equals("up") && dir==STRAIGHT) {
		return new SkiFree(new Slopes(slopes.getObstacles(),
					      slopes.getSkier().changeCourse(SLOW, STRAIGHT, "Images/playerStraight.png"),
					      slopes.getYeti(),
					      slopes.getWidth(),
					      slopes.getHeight()),
				   this.width, this.height, this.score, this.yetiCount, this.state);
		
	    } else if(key.equals("q")) {
		return new SkiFree(this.slopes, this.width, this.height,
				   this.score, this.yetiCount, GameState.GAMEOVER);
	    } else
		return this;
	    //If the game's currently in the CRASH state, then on any key input switch
	    //to the GAMEOVER state
	} else {
	    if(!key.equals("")) {
		return new SkiFree(this.slopes, this.width, this.height,
				   this.score, this.yetiCount, GameState.GAMEOVER);
	    }
	    else {
		return this;
	    }
	}
    }
    
    //EFFECT: Returns true if the yeti is to the left of the player
    //        Used to determine which yeti image to use
    private boolean yetiLeftHuh() {
	try {
	return this.slopes.getYeti().getPosn().x < this.slopes.getSkier().getPosn().x;
	} catch(NoEnemyException e) {
	    return false;
	}
    }

    public World onTick() {
	//If the game's in the GAME state, then move the slopes, update the score, and increment
	//the yetiCount accordingly
	if(this.state == GameState.GAME) {
	    if(this.slopes.isCollisionHuh(this.slopes.getObstacles())) {
		return new SkiFree(this.slopes, this.width, this.height,
				   this.score, this.yetiCount, GameState.CRASH);
	    } else if(this.yetiCount % 500 == 0 && this.slopes.yeti.emptyHuh()) {
		return new SkiFree(slopes.moveSlopes((int) (TICKDISTANCE * slopes.getSkier().getSpeed())).addYeti(),
				   this.width, this.height,
				   (int) (this.score +  1.5*this.slopes.getSkier().getSpeed()),
				   this.yetiCount + 1, this.state);
	    } else if(yetiCount % 20 == 0 && yetiLeftHuh()) {
		return new SkiFree(slopes.moveSlopes((int) (TICKDISTANCE * 
							    slopes.getSkier().getSpeed())).setYetiPic("Images/yeti-2-left.png"),
				   this.width, this.height,
				   (int) (this.score +  1.5*this.slopes.getSkier().getSpeed()),
				   this.yetiCount + 1, this.state);
	    } else if(yetiCount % 20 == 0) {
		return new SkiFree(slopes.moveSlopes((int) (TICKDISTANCE * 
							    slopes.getSkier().getSpeed())).setYetiPic("Images/yeti-2-right.png"),
				   this.width, this.height,
				   (int) (this.score +  1.5*this.slopes.getSkier().getSpeed()),
				   this.yetiCount + 1, this.state);
	    } else if(yetiCount % 10 == 0 && yetiLeftHuh()) {
		return new SkiFree(slopes.moveSlopes((int) (TICKDISTANCE *
							    slopes.getSkier().getSpeed())).setYetiPic("Images/yeti-1-left.png"),
				   this.width, this.height,
				   (int) (this.score +  1.5*this.slopes.getSkier().getSpeed()),
				   this.yetiCount + 1, this.state);
	    } else if(yetiCount % 10 == 0) {
		return new SkiFree(slopes.moveSlopes((int) (TICKDISTANCE *
							    slopes.getSkier().getSpeed())).setYetiPic("Images/yeti-1-right.png"),
				   this.width, this.height,
				   (int) (this.score +  1.5*this.slopes.getSkier().getSpeed()),
				   this.yetiCount + 1, this.state);
	    } else {
		return new SkiFree(slopes.moveSlopes((int) (TICKDISTANCE *
							    slopes.getSkier().getSpeed())),
				   this.width, this.height,
				   (int) (this.score +  1.5*this.slopes.getSkier().getSpeed()),
				   this.yetiCount + 1, this.state);
	    }
	    //If the game's in any state beside GAME, nothing should be updating on a new tick, so just return this
	} else {
	    return this;
	}
    }
    

    //EFFECT: Return an image appropriate to the game's current state
    public WorldImage makeImage() {
	//If the game's in the GAME or CRASH state, then draw the Slopes and score counter
	if(this.state == GameState.GAME || this.state == GameState.CRASH) {
	    try {
		return new OverlayImages(slopes.getObstacles().drawAll(),
					 new OverlayImages(new RectangleImage(new Posn(this.width-60, 30),
									      100,
									      40,
									      Color.DARK_GRAY),
							   new OverlayImages(new TextImage(new Posn(this.width-60, 30),
											   "Score: " + this.score,
											   Color.BLUE),
									     new OverlayImages(slopes.getSkier().getImage(),
											       slopes.getYeti().getImage()))));
	    } catch(NoEnemyException e) {
		return new OverlayImages(slopes.getObstacles().drawAll(),
					 new OverlayImages(new RectangleImage(new Posn(this.width-60, 30),
									      100,
									      40,
									      Color.DARK_GRAY),
							   new OverlayImages(new TextImage(new Posn(this.width-60, 30),
											   "Score: " + this.score,
											   Color.BLUE),
									     slopes.getSkier().getImage())));
	    }
	    //If the game's in the GAMEOVER state, just display a black screen with text
	    //telling the player the game is over and their score
	} else if(this.state == GameState.GAMEOVER) {
	    return new OverlayImages(new RectangleImage(new Posn(this.width/2, this.height/2),
							this.width,
							this.height,
							Color.BLACK),
				     new OverlayImages(new TextImage(this.slopes.getSkier().getPosn(),
								     "GAME OVER: Press N to restart!",
								     20,
								     Color.RED),
						       //Second text image to display score on a new line, since
						       //TextImage doesn't work with escape characters
						       new TextImage(new Posn(this.slopes.getSkier().getPosn().x,
									      this.slopes.getSkier().getPosn().y + 50),
								     "You scored " + this.score + " points!",
								     30,
								     Color.RED)));
	    //If the game's in the start state, just display a black screen with a message welcoming
	    //the player to the game
	} else {
	    return new OverlayImages(new RectangleImage(new Posn(this.width/2, this.height/2),
							this.width,
							this.height,
							Color.BLACK),
				     new TextImage(this.slopes.getSkier().getPosn(),
						   "Press N to start the game!",
						   30,
						   Color.RED));
	}		 
    }

    public static void main(String[] args) {
	int slpWidth = 1366;
	int slpHeight = 768;

	int width = slpWidth*9/10;
	int height = slpHeight*9/10;
	
	int yetiStart = 1;

	PureQueue qu = new NullQueue();
	Player plyr = new Player(new Posn(width/2, height*2/5), 30, 30,
				 "Images/playerStraight.png", NORMAL, STRAIGHT);
	Slopes slps = new Slopes(qu, plyr, slpWidth, slpHeight);
	SkiFree s = new SkiFree(slps, width, height, 0, yetiStart, GameState.START);
	s.bigBang(width, height, .035);
    }
    
}
	    

 
