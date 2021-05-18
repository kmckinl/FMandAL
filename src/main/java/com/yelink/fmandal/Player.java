package com.yelink.fmandal;

import com.yelink.fmandal.font.FontController;
import com.yelink.fmandal.rendering.Shader;
import com.yelink.fmandal.rendering.Texture;
import com.yelink.fmandal.rendering.VertexArray;
import com.yelink.fmandal.utilities.Matrix4f;
import com.yelink.fmandal.utilities.Vector3f;

import java.awt.Rectangle;

import com.yelink.fmandal.development.BoundsBox;
import com.yelink.fmandal.entities.Character;
//import com.yelink.fmandal.font.FontController;

public class Player {
    private int text;
    private int swapCD = 300;
    private int frameTick = 1;
    private int frameInc = 1;
    private float defAttSpeed = 30;
    private boolean swapping = false;
    private boolean attack = false;
    private boolean moving = false;
    private boolean idle = true;
    private boolean movingLeft = false;
    private float xOffset, yOffset;
    private Vector3f position, movement;
    private Character flyMan, aquaLad;
    private VertexArray FlyMan, AquaLad;
    private Texture FlyTexture, LadTexture;
    private FontController fc;

    /* Development Parameters to be removed before deployment */
    private BoundsBox fmSpriteBounds, fmReachBounds, fmCollectBounds, aqSpriteBounds, aqReachBounds;

    private boolean isFlyMan = true;

    public Player(FontController fc) {
        this.fc = fc;

        // Initialize position
        position = new Vector3f(500.0f, 500.0f, 0.0f);
        movement = new Vector3f(0.0f, 0.0f, 0.0f);
        // Initialize movement to Fly Man


        this.xOffset = 0.0f;
        this.yOffset = 0.0f;

        float[] vertices = new float[] {
                position.x, position.y, 0.0f,
                position.x + 256.0f, position.y, 0.0f,
                position.x + 256.0f, position.y + 280.0f, 0.0f,
                position.x, position.y + 280.0f, 0.0f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                0, 2, 3
        };

		/*float[] tcs = new float[] {
			// Will need to remake the sprite sheet, try to keep to a divisible dimensions
			xOffset, yOffset,
			xOffset + (1.0f / 10.0f), yOffset,
			xOffset + (1.0f / 10.0f), yOffset + (1.0f / 3.0f),
			xOffset, yOffset + (1.0f / 3.0f)
		};*/
        float[] tcs = new float[] {
                // Will need to remake the sprite sheet, try to keep to a divisible dimensions
                xOffset, 0,
                xOffset + (0.2f), 0,
                xOffset + (0.2f), 1,
                xOffset, 1
        };

        flyMan = new Character("Fly-Man", position.x, position.y);
        aquaLad = new Character("Aqua Lad", position.x, position.y);
        FlyMan = new VertexArray(vertices, indices, tcs);
        FlyTexture = new Texture("res/fmmuaythai.png");
        AquaLad = new VertexArray(vertices, indices, tcs);
        LadTexture = new Texture("res/aqualad.png");

        /* Development Parameters to be removed before deployment */
		/*fmSpriteBounds = new BoundsBox(position.x, position.y, flyMan.getWidth(), flyMan.getHeight(), new float[] { position.x + 100.0f, position.y + 100.0f });
		fmReachBounds = new BoundsBox(getCenter()[0], getCenter()[1], flyMan.getReach(), 30.0f, new float[] { position.x + 100.0f, position.y + 100.0f });
		fmCollectBounds = new BoundsBox(getCenter()[0] - 20.0f, getCenter()[1] - 40.0f, 40.0f, 80.0f, getCenter());
		isFlyMan = false;
		aqSpriteBounds = new BoundsBox(position.x, position.y, aquaLad.getWidth(), aquaLad.getHeight(), new float[] { position.x + 100.0f, position.y + 100.0f });
		aqReachBounds = new BoundsBox(getCenter()[0], getCenter()[1], aquaLad.getReach(), 30.0f, new float[] { position.x + 100.0f, position.y + 100.0f });
		isFlyMan = true;*/

        this.defAttSpeed = this.defAttSpeed * flyMan.getAttSpeed();
    }

    public void update() {
        if (moving) {

        }
        if (this.attack) {
            this.defAttSpeed -= 1;
            if (this.defAttSpeed == 0) {
                if (this.isFlyMan) {
                    this.defAttSpeed = 30.0f * this.flyMan.getAttSpeed();
                } else {
                    this.defAttSpeed = 30.0f * this.aquaLad.getAttSpeed();
                }

                this.attack = false;
            }
        }
        if (this.swapping) {
            this.swapCD -= 1;
            if (this.swapCD <= 0) {
                this.swapCD = 300;
                this.swapping = false;
            }
        }

        this.moving = false;
        this.idle = true;
    }

    public void render() {
        if (isFlyMan) {
            FlyTexture.bind();
            Shader.CHARACTER.enable();
            FlyMan.bind();
            Shader.CHARACTER.setUniformMat4f("vw_matrix", Matrix4f.translate(this.movement));
            Shader.CHARACTER.setUniform2f("texOffset", this.xOffset, this.yOffset);
            FlyMan.draw();

            Shader.CHARACTER.disable();
            FlyTexture.unbind();
            //fmSpriteBounds.render();
            //fmReachBounds.render();
        } else {
            LadTexture.bind();
            Shader.CHARACTER.enable();
            AquaLad.bind();
            Shader.CHARACTER.setUniformMat4f("vw_matrix", Matrix4f.translate(this.movement));
            AquaLad.draw();

            Shader.CHARACTER.disable();
            LadTexture.unbind();
            //aqSpriteBounds.render();
            //aqReachBounds.render();
        }
    }

    /* -- -- -- GETTERS / SETTERS -- -- -- */
    public boolean getFlyMan() {
        return this.isFlyMan;
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public Vector3f getPosition() {
        return new Vector3f(this.position.x + this.movement.x, this.position.y + this.movement.y, 0.0f);
    }

    public Rectangle getCharBounds() {

        if (this.isFlyMan) {
            return this.flyMan.getBounds();
        } else {
            return this.aquaLad.getBounds();
        }
    }

    public Rectangle getReachBounds() {

        if (this.isFlyMan) {
            return this.flyMan.getReachBounds();
        } else {
            return this.aquaLad.getReachBounds();
        }
    }

    public Rectangle getCollectBounds() {

        if (this.isFlyMan) {
            return this.flyMan.getCollectBounds();
        } else {
            return this.aquaLad.getCollectBounds();
        }
    }

    public Character getCharacter() {
        if (this.isFlyMan) {
            return this.flyMan;
        } else {
            return this.aquaLad;
        }
    }

    public float getReach() {
        if (this.isFlyMan) {
            return this.flyMan.getReach();
        } else {
            return this.aquaLad.getReach();
        }
    }

    public void setSwap() {
        if (this.swapCD >= 300) {
            this.swapping = true;
            this.isFlyMan = !this.isFlyMan;
            setAttSpeed();
        }
    }

    public void setAttackCheck(boolean att) {
        this.attack = att;
    }

    public boolean getAttack() {
        return this.attack;
    }

    public float[] getCenter() {
        if (this.isFlyMan) {
            return new float[] { this.position.x + this.movement.x + (this.flyMan.getWidth()  / 2), this.position.y + (this.flyMan.getHeight() / 2) };
        } else {
            return new float[] { this.position.x + this.movement.x + (this.aquaLad.getWidth()  / 2), this.position.y + (this.aquaLad.getHeight() / 2) };
        }

    }

    public float getAttSpeed() {
        return this.defAttSpeed;
    }

    public void setAttSpeed() {
        if (this.isFlyMan) {
            this.defAttSpeed = 30.0f * this.flyMan.getAttSpeed();
        } else {
            this.defAttSpeed = 30.0f * this.aquaLad.getAttSpeed();
        }
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public float[] checkBounds(float[] bounds) {
        // Returns a float array of the left, right, up, and down boundaries
        return new float[] { this.position.x - (bounds[0] / 2), this.position.x + (bounds[0] / 2),
                this.position.y - (bounds[1] / 2), this.position.y + (bounds[1] / 2) };
    }

    public void attack(boolean hit) {
        if (hit) {
            if (attack == false) {
                //text = fc.loadText("bangers", "POW!", this.position.x + 50.0f, this.position.y - 50.0f, true);
                attack = true;
            }
        }

    }

    public void updateFrame() {
        this.frameTick++;
		/*if (this.frameTick % 9 == 0) {
			if (this.idle) {
				this.yOffset = 0.0f;
				if (this.movingLeft) {
					if (this.xOffset < 3.0f / 10.0f) {
						this.xOffset = 3.0f / 10.0f;
					}
					this.xOffset += (1.0f / 10.0f);
					if (this.xOffset > 5.0f / 10.0f) {
						this.xOffset = 3.0f / 10.0f;
					}
				} else {
					if (this.xOffset >= 3.0f / 10.0f) {
						this.xOffset = 0.0f;
					}
					this.xOffset += (1.0f / 10.0f);
					if (this.xOffset >= 3.0f / 10.0f) {
						this.xOffset = 0.0f;
					}
				}
			}

			if (this.moving) {

				this.yOffset = (1.0f / 3.0f);
				if (this.movingLeft) {
					if (this.xOffset < 5.0f / 10.0f) {
						this.xOffset = 5.0f / 10.0f;
					}
					this.xOffset += (1.0f / 10.0f);
					if (this.xOffset >= 1) {
						this.xOffset = 5.0f / 10.0f;
					}
				} else {
					if (this.xOffset >= 5.0f / 10.0f) {
						this.xOffset = 0.0f;
					}
					this.xOffset += 1.0f / 10.0f;
					if (this.xOffset >= 5.0f / 10.0f) {
						this.xOffset = 0.0f;
					}
				}
			}

		}*/
        if (this.frameTick % 10 == 0) {
            if (this.frameTick < 50) {
                this.xOffset += 0.2f;

            } else if (this.frameTick >= 50){
                this.xOffset -= 0.2f;
            }



        }

        if (this.frameTick > 80) {
            this.frameTick = 1;
        }

        if (this.frameTick == 1005) {
            this.frameTick = 0;
        }
    }

    public void move(float x, float y) {

        if (x < 0) {
            if (!this.movingLeft) {
                if (this.moving) {
                    this.xOffset = 5.0f / 10.0f;
                } else {
                    this.xOffset = 3.0f / 10.0f;
                }

            }
            this.movingLeft = true;
        } else if (x > 0) {
            if (this.movingLeft) {
                this.xOffset = 0.0f;
            }
            this.movingLeft = false;
        }
        if (this.moving) {
            if (this.isFlyMan) {
                if (!checkBorder(new float[] {x, y},this.flyMan.getHeight(), this.flyMan.getWidth(), this.flyMan.getSpeed())) {
                    updatePosition(x * this.flyMan.getSpeed(), y * this.flyMan.getSpeed());
                    this.flyMan.setBoundsPosition((int) (this.flyMan.getSpeed() * x), (int) (this.flyMan.getSpeed() * y));
                }
            } else {
                if (!checkBorder(new float[] { x, y }, this.aquaLad.getHeight(), this.aquaLad.getWidth(), this.aquaLad.getSpeed())) {
                    updatePosition(x * this.aquaLad.getSpeed(), y * this.aquaLad.getSpeed());
                    this.flyMan.setBoundsPosition((int) (this.flyMan.getSpeed() * x), (int) (this.flyMan.getSpeed() * y));
                }
            }

        }

    }

    public boolean checkBorder(float[] dir, float height, float width, float speed) {
        if (dir[0] != 0) {
            if (this.position.x + this.movement.x + (dir[0] * speed) + width > 1920.0f ||
                    this.position.x + this.movement.x + (dir[0] * speed) < 0.0f) {
                return true;
            }
        }
        if (dir[1] != 0) {
            if (this.position.y + this.movement.y + (dir[1] * speed) + height > 1080.0f ||
                    this.position.y + this.movement.y + (dir[1] * speed) < 0.0f) {
                return true;
            }
        }
        return false;

    }

    public void updatePosition(float x, float y) {
        this.movement.x += x;
        this.movement.y += y;
        //this.fmSpriteBounds.updatePosition(x, y);
        //this.fmReachBounds.updatePosition(x, y);
        //this.aqSpriteBounds.updatePosition(x, y);
        //this.aqReachBounds.updatePosition(x, y);
        //this.moving = false;
    }

    public void updateHealth(int health) {
        if (isFlyMan) {
            this.flyMan.setHealth(health);
        } else {
            this.aquaLad.setHealth(health);
        }
    }
}
