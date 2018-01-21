package helpers;

import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glVertex2f;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.*;

public class Artist {
	
	public static final int WIDTH = 1280 + 64 * 3, HEIGHT = 960;
	public static final int TILE_SIZE = 64;
	
	public static void beginSession(){
		Display.setTitle("Tower Defense");
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);//used with glblendfunc to blend
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);//transparent;
		
	}
	
	public static void drawQuad(float x, float y, float width, float height) {
		glBegin(GL_QUADS);
		glVertex2f(x, y);//top left
		glVertex2f(x + width,y);//top eight
		glVertex2f(x + width,y + height);//bottom right
		glVertex2f(x,y + height);//bottom left
		glEnd();
	}
	
	public static void drawQuadTex(Texture tex, float x, float y, float width, float height){
		tex.bind();//all drawing will be filled with this texture until replaced by another texture
		glColor4f(1f, 1f, 1f, 1f);
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();//stops continues drawing?
	}
	
	//alpha is the transparency
	public static void drawQuadTexTrans(Texture tex, float alpha,  float x, float y, float width, float height) {
		tex.bind();//all drawing will be filled with this texture until replaced by another texture
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		glColor4f(1f, 1f, 1f, alpha);
		glTranslatef(x, y, 0);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();
//		glColor4f(1f, 1f, 1f, 1f);
	}
	
	public static boolean checkCollision(float x1, float y1, float width1, float height1,
			float x2, float y2, float width2, float height2){
		if(x1 + width1 > x2 && x1 < x2 + width2 && y1 + height1 > y2 && y1 < y2 + height2){
			return true;
		}
		return false;
	}
	
	public static void drawQuadTexRot(Texture tex, float x, float y, float width, float height, float angle){
		tex.bind();//all drawing will be filled with this texture until replaced by another texture
		glTranslatef(x + width / 2, y + height / 2, 0);//translate to the center of the tile
		glRotatef(angle, 0, 0, 1);
		glTranslatef(- width / 2, - height / 2, 0);//translate back to the top left corner
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0);
		glVertex2f(0, 0);
		glTexCoord2f(1, 0);
		glVertex2f(width, 0);
		glTexCoord2f(1, 1);
		glVertex2f(width, height);
		glTexCoord2f(0, 1);
		glVertex2f(0, height);
		glEnd();
		glLoadIdentity();//stops continues drawing?
	}
	
	public static Texture loadTexture(String path, String fileType){
		Texture tex = null;
		InputStream in = ResourceLoader.getResourceAsStream(path);
		try {
			tex = TextureLoader.getTexture(fileType, in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tex;
	}
	
	public static Texture quickLoad(String name){
		Texture tex = null;
		tex = loadTexture("res/" + name + ".png", "PNG");
		return tex;
	}
}
