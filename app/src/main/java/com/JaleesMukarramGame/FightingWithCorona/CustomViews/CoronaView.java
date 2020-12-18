package com.JaleesMukarramGame.FightingWithCorona.CustomViews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.JaleesMukarramGame.FightingWithCorona.CoronaCOVID_19.CoronaAttackers;
import com.JaleesMukarramGame.FightingWithCorona.MainActivity;
import com.JaleesMukarramGame.FightingWithCorona.Models.Bullet;
import com.JaleesMukarramGame.FightingWithCorona.Models.Gun;
import com.JaleesMukarramGame.FightingWithCorona.Models.Life;
import com.JaleesMukarramGame.FightingWithCorona.Models.Sanitizer;
import com.JaleesMukarramGame.FightingWithCorona.R;

import java.util.Random;

import static com.JaleesMukarramGame.FightingWithCorona.CoronaCOVID_19.CoronaAttackers.EASY_SPEED;
import static com.JaleesMukarramGame.FightingWithCorona.CoronaCOVID_19.CoronaAttackers.FAST_SPEED;
import static com.JaleesMukarramGame.FightingWithCorona.CoronaCOVID_19.CoronaAttackers.MODIUM_SPEED;
import static com.JaleesMukarramGame.FightingWithCorona.MainActivity.BULLET_TO_CORONA;
import static com.JaleesMukarramGame.FightingWithCorona.MainActivity.CORONA_TO_GUN;
import static com.JaleesMukarramGame.FightingWithCorona.MainActivity.GUN_SOUND;
import static com.JaleesMukarramGame.FightingWithCorona.MainActivity.HEALTH_RECOVERED;

public class CoronaView extends View {

    public int numOfCurrentAttackers;
    public boolean lost;
    private Paint redFillPaint, greenFillPaint;
    private boolean waited;
    private Context context;
    private int score;
    private Bitmap coronaBitmap1, coronaBitmap2, gunBitmap, bulletBitmap, lifeAvailable, lifeGone, sanitizerBitmap, blastBitmap, healthPlusBitmap, bulletHitBitmap;
    private Drawable background;
    private int i;
    private CoronaAttackers[] attackers;
    private Bullet[] bullets;
    private Sanitizer[] sanitizers;
    private Life[] lives;
    private Gun gun;
    private boolean sanitizerAbsorbed, bulletHitCorona, coronaAttackedGun;
    private int coronaAttackedGunX, coronaAttackedGunY, coronaAttackedGunFrames, bulletHitCoronaX, bulletHitCoronaY, bulletHitCoronaFrames, sanitizerAbsorbedX, sanitizerAbsorbedY, sanitizerAbsorbedFrames;
    private Random random;
    private MainActivity mainActivity;
    private boolean initialized;
    private boolean backGroundSet;
    private boolean gunShown;
    private int sanitizerCounter;


    public CoronaView(Context context) {
        super(context);
        this.context = context;

        inilializeAll();

    }

    public CoronaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


        inilializeAll();
    }

    public CoronaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;

        inilializeAll();
    }


    public void inilializeAll() {

        random = new Random();
        backGroundSet = false;
        gunShown = false;

        initializeBitmaps();
        inilializeAttackers();
        initializeBullets();
        initializeSanitizers();
        initializeLives();
        initializePaints();
        gun = new Gun(gunBitmap);

        numOfCurrentAttackers = 3;


        score = 0;


        initialized = true;


    }

    private void initializePaints() {

        redFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        redFillPaint.setStyle(Paint.Style.FILL);
        redFillPaint.setColor(Color.RED);
        redFillPaint.setTextSize(200);

        greenFillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        greenFillPaint.setStyle(Paint.Style.FILL);
        greenFillPaint.setColor(Color.GREEN);
        greenFillPaint.setTextSize(70);
    }

    public void askForWait() {

        this.waited = false;
        this.bulletHitCoronaFrames = 100;
    }


    @Override
    protected void onDraw(Canvas canvas) {

        if (!initialized) {

            inilializeAll();
        }
        if (!backGroundSet) {

            gun.fillAllCoordinates(getWidth(), getHeight());
            setBackground(background);
            backGroundSet = true;

        }
        if (!gunShown) {

            canvas.drawBitmap(gun.getmGun(), Gun.FIXED_X, gun.getCurrentY(), null);
            gunShown = true;
            this.bulletHitCoronaFrames = 100;

        }

        if (!lost) {


            if (waited) {

                simulateGun(canvas);
                simulateBullets(canvas);
                simulateVirus(canvas);
                simulateSanitizers(canvas);
                showAllLives(canvas);

                if (bulletHitCorona) {

                    showBulletHitCorona(canvas);

                }
                if (coronaAttackedGun) {

                    showCoronaAttackGun(canvas);
                }
                if (sanitizerAbsorbed) {

                    showSanitizerAbsorbed(canvas);

                }

                detectCollisionOfGunWithSanitizer();
                detectCollisionOfBulletWithCorona();
                detectCollisionOfGunWithCorona();


                canvas.drawText("Score: " + score, getWidth() / 3f, 100, greenFillPaint);


            } else {



                canvas.drawText(bulletHitCoronaFrames / 40 + 1 + "", getWidth() / 2f - 100, getHeight() / 2f - 100, redFillPaint);
                bulletHitCoronaFrames--;


                if (bulletHitCoronaFrames <= 0) {
                    waited = true;
                }

            }


            invalidate();


        } else {

            if (coronaAttackedGun) {

                showCoronaAttackGun(canvas);
                invalidate();

            } else {

                mainActivity.showLostDialogue(score);

            }


        }
    }

    private void showSanitizerAbsorbed(Canvas canvas) {

        if (sanitizerAbsorbedFrames > 0) {
            canvas.drawBitmap(healthPlusBitmap, sanitizerAbsorbedX, sanitizerAbsorbedY, null);
            sanitizerAbsorbedFrames--;
        } else {
            sanitizerAbsorbed = false;
        }
    }

    private void showCoronaAttackGun(Canvas canvas) {

        if (coronaAttackedGunFrames > 0) {
            canvas.drawBitmap(blastBitmap, coronaAttackedGunX, coronaAttackedGunY, null);
            coronaAttackedGunFrames--;
        } else {
            coronaAttackedGun = false;
        }
    }

    private void showBulletHitCorona(Canvas canvas) {

        if (bulletHitCoronaFrames > 0) {
            canvas.drawBitmap(bulletHitBitmap, bulletHitCoronaX, bulletHitCoronaY, null);
            bulletHitCoronaFrames--;
        } else {
            bulletHitCorona = false;
        }
    }

    private void detectCollisionOfGunWithCorona() {

        for (i = 0; i < numOfCurrentAttackers; i++) {

            if (attackers[i].isAttacking() && !attackers[i].isHidden()) {

                if ((attackers[i].getCurrentX() < (gun.getWidth())) && (attackers[i].getCurrentY() + attackers[i].getHeight() / 2 > gun.getCurrentY()) && (attackers[i].getCurrentY() < gun.getCurrentY() + gun.getHeight() / 2)) {

                    coronaAttackedGun = true;
                    coronaAttackedGunFrames = 10;
                    coronaAttackedGunX = attackers[i].getCurrentX();
                    coronaAttackedGunY = attackers[i].getCurrentY();

                    attackers[i].setHidden(true);
                    deductLife();

                    mainActivity.simulateGameSounds(CORONA_TO_GUN);
                    return;

                }
            }
        }
    }

    private void detectCollisionOfGunWithSanitizer() {

        for (i = 0; i < sanitizers.length; i++) {

            if (sanitizers[i].isSanitizing() && !sanitizers[i].isHidden()) {

                if ((sanitizers[i].getCurrentX() < (gun.getWidth())) && (sanitizers[i].getCurrentY() + sanitizers[i].getHeight() / 2 > gun.getCurrentY()) && (sanitizers[i].getCurrentY() < gun.getCurrentY() + gun.getHeight() / 2)) {

                    sanitizerAbsorbed = true;
                    sanitizerAbsorbedX = sanitizers[i].getCurrentX();
                    sanitizerAbsorbedY = sanitizers[i].getCurrentY();
                    sanitizerAbsorbedFrames = 10;

                    sanitizers[i].setHidden(true);
                    addLife();
                    mainActivity.simulateGameSounds(HEALTH_RECOVERED);

                    return;

                }
            }
        }
    }

    private void detectCollisionOfBulletWithCorona() {

        int j;

        for (i = 0; i < numOfCurrentAttackers; i++) {

            //Attacker at I

            if (!attackers[i].isHidden()) {

                //Bullet at J

                for (j = 0; j < bullets.length; j++) {

                    //1. Attacker should be after the bullet
                    //2. bullet x + bullet width should be greater than attacker x, because the bullet will burst into attacker
                    //3. If the bullet has hit the attacker from above side, then the bullet y + bullet height will be greater than attacker height
                    //3. If the bullet has hit the attacker from below side, then the attacker y + attacker height will be greater than bullet height

                    if ((attackers[i].getCurrentX() > bullets[j].getCurrentX() && bullets[j].getCurrentX() + bullets[j].getWidth() > attackers[i].getCurrentX()) && (bullets[j].getCurrentY() + bullets[j].getHeight() > attackers[i].getCurrentY() && bullets[j].getCurrentY() < attackers[i].getCurrentY() + attackers[i].getHeight())) {

                        bulletHitCorona = true;
                        bulletHitCoronaX = attackers[i].getCurrentX();
                        bulletHitCoronaY = attackers[i].getCurrentY();
                        bulletHitCoronaFrames = 10;

                        attackers[i].setHidden(true);
                        score++;

                        mainActivity.simulateGameSounds(BULLET_TO_CORONA);
                    }
                }

            }


        }

    }

    private void simulateVirus(Canvas canvas) {

        for (i = 0; i < numOfCurrentAttackers; i++) {

            if (attackers[i].isAttacking()) {


                if (attackers[i].canMoveForward()) {

                    int mode;

                    if (numOfCurrentAttackers <= 3) {
                        mode = EASY_SPEED;
                    } else if (numOfCurrentAttackers <= 7) {
                        mode = MODIUM_SPEED;
                    } else {
                        mode = FAST_SPEED;
                    }

                    attackers[i].moveForward(mode);

                    if (!attackers[i].isHidden()) {

                        canvas.drawBitmap(attackers[i].getmCorona(), attackers[i].getCurrentX(), attackers[i].getCurrentY(), null);

                    }
                } else {

                    attackers[i].setAttacking(false);
                    attackers[i].setHidden(false);
                    return;
                }
            }

            if (!attackers[i].isAttacking()) {

                if (random.nextBoolean()) {

                    attackers[i].attack(getWidth(), 200 + random.nextInt(getHeight() - 200));
                    canvas.drawBitmap(attackers[i].getmCorona(), attackers[i].getCurrentX(), attackers[i].getCurrentY(), null);
                }

                return;

            }
        }

    }

    private void simulateGun(Canvas canvas) {

        if (gun.isTouched() || gun.shouldMoveUp()) {

            drawGunOnCanvas(canvas, true);

        } else {

            drawGunOnCanvas(canvas, false);
        }
    }

    private void simulateBullets(Canvas canvas) {


        for (i = 0; i < bullets.length; i++) {


            if (bullets[i].isShooting()) {


                if (bullets[i].canMoveForward()) {


                    bullets[i].moveForward();
                    canvas.drawBitmap(bullets[i].getmBullet(), bullets[i].getCurrentX(), bullets[i].getCurrentY(), null);

                } else {

                    bullets[i].setShooting(false);

                }
            }

            if (!bullets[i].isShooting()) {


                bullets[i].throwBullet(gun.getWidth(), gun.getCurrentY() + (gun.getHeight() / 3), getWidth());
                canvas.drawBitmap(bullets[i].getmBullet(), bullets[i].getCurrentX(), bullets[i].getCurrentY(), null);

                canvas.drawBitmap(gun.getmGun(), Gun.FIXED_X - 10, gun.getCurrentY(), null);

                mainActivity.simulateGameSounds(GUN_SOUND);

                return;

            }

        }

    }

    private void simulateSanitizers(Canvas canvas) {


        for (i = 0; i < sanitizers.length; i++) {

            if (sanitizers[i].isSanitizing()) {


                if (sanitizers[i].canMoveForward()) {

                    sanitizers[i].moveForward();

                    if (!sanitizers[i].isHidden()) {

                        canvas.drawBitmap(sanitizers[i].getmSanitizer(), sanitizers[i].getCurrentX(), sanitizers[i].getCurrentY(), null);

                    }
                } else {

                    sanitizers[i].setSanitizing(false);
                    sanitizers[i].setHidden(false);

                }

            }

            if (!sanitizers[i].isSanitizing()) {

                if (sanitizerCounter > 0) {

                    sanitizerCounter--;

                } else {

                    sanitizers[i].sanitize(getWidth(), 200 + random.nextInt(getHeight() - 200));
                    canvas.drawBitmap(sanitizers[i].getmSanitizer(), sanitizers[i].getCurrentX(), sanitizers[i].getCurrentY(), null);
                    sanitizerCounter = 500 + random.nextInt(1500);

                }
            }

        }

    }

    private void drawGunOnCanvas(Canvas canvas, boolean up) {

        if (up) {

            gun.moveUp();

        } else {

            gun.moveDown();
        }

        canvas.drawBitmap(gun.getmGun(), Gun.FIXED_X, gun.getCurrentY(), null);
    }

    private void inilializeAttackers() {

        attackers = new CoronaAttackers[10];

        for (int i = 0; i < attackers.length; i++) {

            attackers[i] = new CoronaAttackers(i % 2 == 0 ? coronaBitmap1 : coronaBitmap2);

        }
    }

    private void initializeBullets() {

        bullets = new Bullet[10];

        for (int i = 0; i < bullets.length; i++) {

            bullets[i] = new Bullet(bulletBitmap);
        }

    }

    public void initializeLives() {

        lives = new Life[3];

        for (i = 0; i < lives.length; i++) {

            lives[i] = new Life(lifeAvailable, lifeGone);
        }


    }

    private void initializeSanitizers() {

        sanitizers = new Sanitizer[2];

        for (i = 0; i < sanitizers.length; i++) {

            sanitizers[i] = new Sanitizer(sanitizerBitmap);

        }

    }


    private void showAllLives(Canvas canvas) {

        for (i = lives.length - 1; i >= 0; i--) {

            if (lives[i].isAvailable()) {

                canvas.drawBitmap(lives[i].getmLifeAvailable(), getWidth() / 2f - (100) + (i * 100), 170, null);
            } else {

                canvas.drawBitmap(lives[i].getmLifeGone(), getWidth() / 2f - (100) + (i * 100), 170, null);

            }
        }
    }

    private void deductLife() {

        for (i = lives.length - 1; i > 0; i--) {

            if (lives[i].isAvailable()) {
                lives[i].setAvailable(false);
                return;
            }
        }

        lost = true;
        waited = false;
    }

    private void addLife() {

        for (i = 0; i < lives.length; i++) {

            if (!lives[i].isAvailable()) {

                lives[i].setAvailable(true);
                return;
            }

        }
    }

    private void initializeBitmaps() {

        int rand = random.nextInt(6);

        switch (rand) {

            case 0:

                background = getResources().getDrawable(R.drawable.bg_green, context.getTheme());
                break;

            case 1:

                background = getResources().getDrawable(R.drawable.nature2, context.getTheme());
                break;

            case 2:
                background = getResources().getDrawable(R.drawable.nature3, context.getTheme());
                break;

            case 3:

                background = getResources().getDrawable(R.drawable.nature4, context.getTheme());
                break;

            case 4:

                background = getResources().getDrawable(R.drawable.nature5, context.getTheme());
                break;

            case 5:

                background = getResources().getDrawable(R.drawable.bg_gal, context.getTheme());
                break;

            default:

                background = getResources().getDrawable(R.drawable.bg_green, context.getTheme());
                break;

        }

        coronaBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_corona_48);
        coronaBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.corona_2_48);
        gunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.gun_off);
        bulletBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_sharp);
        lifeAvailable = BitmapFactory.decodeResource(getResources(), R.drawable.life_available);
        lifeGone = BitmapFactory.decodeResource(getResources(), R.drawable.life_gone);
        sanitizerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sanitizer);
        blastBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash);
        healthPlusBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.health_added);
        bulletHitBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bullet_hit);


    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            gun.setTouched(true);
        }

        return true;

    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

}
