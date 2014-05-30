package entity;

import level.Level;
import level.graphics.LevelTexture;

public abstract class Entity {
	
	protected Level level;
	
	//Position & Movement
	protected double xPos;
	protected double yPos;
	protected double xMoveMent;
	protected double yMoveMent;
	protected double speed;
	protected double jumpSpeed;
	protected double falingSpeed;
	
	//Size
	protected int width;
	protected int height;
	
	protected void tryMove(){
		int xTSpos = (int) ((xPos + (width / 2)) / level.getTileSize());
		int yTSpos = (int) ((yPos + (width / 2)) / level.getTileSize());
		if(yMoveMent > 0){
			if(level.getTileID(xTSpos, yTSpos + 1) != LevelTexture.AIR){
				double maxYMovement =  ((yTSpos + 1) * level.getTileSize()) - yPos;
				yMoveMent = Math.min(yMoveMent, maxYMovement);
			}
		} else {
			if(level.getTileID(xTSpos, yTSpos - 1) != LevelTexture.AIR){
				double maxYMovement =  ((yTSpos - 1) * level.getTileSize()) - yPos;
				yMoveMent = Math.max(yMoveMent, maxYMovement);
			}
		}
		if(xMoveMent > 0){
			if(level.getTileID(xTSpos + 1, yTSpos) != LevelTexture.AIR){
				double maxXMovement =  ((xTSpos + 1) * level.getTileSize()) - xPos;
				xMoveMent = Math.min(xMoveMent, maxXMovement);
			}
		} else {
			if(level.getTileID(xTSpos - 1, yTSpos) != LevelTexture.AIR){
				double maxXMovement =  ((xTSpos - 1) * level.getTileSize()) - xPos;
				xMoveMent += Math.max(xMoveMent, maxXMovement);
			}
		}
	}
}
