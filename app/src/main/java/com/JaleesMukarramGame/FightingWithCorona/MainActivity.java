package com.JaleesMukarramGame.FightingWithCorona;

import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.JaleesMukarramGame.FightingWithCorona.CustomViews.CoronaView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    public static final int GUN_SOUND = 10;
    public static final int BULLET_TO_CORONA = 20;
    public static final int HEALTH_RECOVERED = 30;
    public static final int CORONA_TO_GUN = 40;

    public static final String SCORE_PREFERENCE = "SCORE";

    private int currentHighScore;

    private SoundPool player;

    private AdView bannerAdTop;
    private AdRequest request;

    private RewardedAdCallback lifeAdShowCallback;
    private RewardedAdLoadCallback lifeAddLoadCallback;
    private RewardedAd lifeRewardingAd;
    private CoronaView coronaView;

    private AlertDialog lostDialog;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private boolean rewarded;
    private int bulletHitToCoronaSound, healthRecoverSound, gunShootingSound, coronaHitToGunSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initlalizeAll();
        initializeSoundPool();
        initializeAds();

    }


    private void initlalizeAll() {

        coronaView = this.findViewById(R.id.CVVMainActivityCoronaVirusView);
        coronaView.setMainActivity(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (coronaView.numOfCurrentAttackers < 10) {
                    coronaView.numOfCurrentAttackers++;
                }
            }
        }, 0, 8000);

        bannerAdTop = findViewById(R.id.ADVMainActivityBannerAddTop);
        lifeRewardingAd = new RewardedAd(this, "ca-app-pub-1114273220063566/5919005233");

        preferences = this.getSharedPreferences("default", MODE_PRIVATE);
        editor = preferences.edit();
        getHighScore();

    }

    private void initializeAds() {

        request = new AdRequest.Builder().build();

        bannerAdTop.loadAd(request);
        bannerAdTop.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

            }


            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();

            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();

            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();

            }
        });

        lifeAddLoadCallback = new RewardedAdLoadCallback() {

            @Override
            public void onRewardedAdLoaded() {
                super.onRewardedAdLoaded();

            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                super.onRewardedAdFailedToLoad(i);
            }
        };

        lifeAdShowCallback = new RewardedAdCallback() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

                rewarded = true;
                lifeRewardingAd.loadAd(request, lifeAddLoadCallback);

            }

            @Override
            public void onRewardedAdFailedToShow(int i) {
                super.onRewardedAdFailedToShow(i);

                lifeRewardingAd.loadAd(request, lifeAddLoadCallback);

                Toast.makeText(MainActivity.this, "Failed to show add.", Toast.LENGTH_SHORT).show();
                rewarded = false;

            }

            @Override
            public void onRewardedAdClosed() {
                super.onRewardedAdClosed();

                lifeRewardingAd.loadAd(request, lifeAddLoadCallback);

                if (rewarded) {

                    coronaView.lost = false;
                    lostDialog.cancel();
                    coronaView.initializeLives();
                    coronaView.askForWait();
                    coronaView.invalidate();
                } else {

                    Toast.makeText(MainActivity.this, "ad canceled", Toast.LENGTH_SHORT).show();
                }

                rewarded = false;
            }

        };

        lifeRewardingAd.loadAd(request, lifeAddLoadCallback);

    }

    public void showRewardedAd() {

        if (lifeRewardingAd.isLoaded()) {

            lifeRewardingAd.show(this, lifeAdShowCallback);

        } else {

            Toast.makeText(this, "Failed to show ad", Toast.LENGTH_SHORT).show();

        }

    }

    public void showLostDialogue(int score) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View dialogueView = getLayoutInflater().inflate(R.layout.dialogue_lost, null);

        TextView scoreView = dialogueView.findViewById(R.id.TVDialogueLostScoreShowing);
        TextView tryAgainView = dialogueView.findViewById(R.id.TVDialogueLostTryAgain);
        final TextView showAdView = dialogueView.findViewById(R.id.TVDialogueLostShowAd);
        TextView exit = dialogueView.findViewById(R.id.TVDialogueLostExit);

        if (score > currentHighScore) {
            currentHighScore = score;
            setHighScore(score);
        }

        if (currentHighScore > 0) {

            scoreView.setText("You lost\nScore: " + score + "\n\nHigh score: " + currentHighScore);

        } else {
            scoreView.setText("You lost\nScore: " + score);

        }

        tryAgainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coronaView.inilializeAll();
                coronaView.lost = false;
                lostDialog.cancel();
                coronaView.invalidate();
            }
        });

        showAdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showRewardedAd();

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

        builder.setView(dialogueView);


        lostDialog = builder.create();
        lostDialog.setCanceledOnTouchOutside(false);
        lostDialog.show();

    }

    private void initializeSoundPool() {

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_GAME)
                .build();

        player = new SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(4)
                .build();

        gunShootingSound = player.load(this, R.raw.gun_shooting, 1);
        bulletHitToCoronaSound = player.load(this, R.raw.bullet_hit, 2);
        healthRecoverSound = player.load(this, R.raw.life_added, 3);
        coronaHitToGunSound = player.load(this, R.raw.danger, 4);

    }

    public void simulateGameSounds(int sound) {

        if (sound == GUN_SOUND) {

            player.play(gunShootingSound, 0.5f, 0.5f, 1, 0, 1);

        } else if (sound == BULLET_TO_CORONA) {

            player.play(bulletHitToCoronaSound, 0.8f, 0.8f, 2, 0, 1);

        } else if (sound == HEALTH_RECOVERED) {

            player.play(healthRecoverSound, 0.5f, 0.5f, 3, 0, 1);

        } else if (sound == CORONA_TO_GUN) {

            player.play(coronaHitToGunSound, 1, 1, 4, 0, 1);

        }
    }

    private void setHighScore(int score) {

        editor.putInt(SCORE_PREFERENCE, score);
        editor.commit();

    }

    private void getHighScore() {

        currentHighScore = preferences.getInt(SCORE_PREFERENCE, -1);

    }

    @Override
    public void onBackPressed() {

    }

}
