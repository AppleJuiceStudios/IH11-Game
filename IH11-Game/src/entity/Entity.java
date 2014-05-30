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
	protected boolean onGround;
	
	//Size
	protected int width;
	protected int height;
	
	protected void tryMove(){
		onGround = false;
		int xTSpos1 = (int) ((xPos) / level.getTileSize());
		int yTSpos1 = (int) ((yPos) / level.getTileSize());
		int xTSpos2 = (int) ((xPos + width - 1) / level.getTileSize());
		int yTSpos2 = (int) ((yPos + width - 1) / level.getTileSize());
		int xTSpos = (int) ((xPos + (width / 2)) / level.getTileSize());
		int yTSpos = (int) ((yPos + (width / 2)) / level.getTileSize());
		if(yMoveMent >= 0){
			if(level.getTileID(xTSpos1, yTSpos + 1) != LevelTexture.AIR){
				double maxYMovement =  ((yTSpos) * level.getTileSize()) - yPos;
				yMoveMent = Math.min(yMoveMent, maxYMovement);
				if(maxYMovement == 0){
					onGround = true;
				}
			}
			if(level.getTileID(xTSpos2, yTSpos + 1) != LevelTexture.AIR){
				double maxYMovement =  ((yTSpos) * level.getTileSize()) - yPos;
				yMoveMent = Math.min(yMoveMent, maxYMovement);
				if(maxYMovement == 0){
					onGround = true;
				}           
			}
		} else {
			if(level.getTileID(xTSpos1, yTSpos - 1) != LevelTexture.AIR){
				double maxYMovement =  ((yTSpos) * level.getTileSize()) - yPos;
				yMoveMent = Math.max(yMoveMent, maxYMovement);
			}
			if(level.getTileID(xTSpos2, yTSpos - 1) != LevelTexture.AIR){
				double maxYMovement =  ((yTSpos) * level.getTileSize()) - yPos;
				yMoveMent = Math.max(yMoveMent, maxYMovement);
			}
		}
		yPos += yMoveMent;
		if(xMoveMent > 0){
			if(level.getTileID(xTSpos + 1, yTSpos1) != LevelTexture.AIR){
				double maxXMovement =  (xTSpos * level.getTileSize()) - xPos;
				xMoveMent = Math.min(xMoveMent, maxXMovement);
			}
			if(level.getTileID(xTSpos + 1, yTSpos2) != LevelTexture.AIR){
				double maxXMovement =  (xTSpos * level.getTileSize()) - xPos;
				xMoveMent = Math.min(xMoveMent, maxXMovement);
			}
		} else {
			if(level.getTileID(xTSpos - 1, yTSpos1) != LevelTexture.AIR){
				double maxXMovement =  ((xTSpos) * level.getTileSize()) - xPos;
				xMoveMent = Math.max(xMoveMent, maxXMovement);
			}
			if(level.getTileID(xTSpos - 1, yTSpos2) != LevelTexture.AIR){
				double maxXMovement =  ((xTSpos) * level.getTileSize()) - xPos;
				xMoveMent = Math.max(xMoveMent, maxXMovement);
			}
		}
		xPos += xMoveMent;
	}

	public double getxPos() {
		return xPos;
	}

	public double getyPos() {
		return yPos;
	}
	
}
