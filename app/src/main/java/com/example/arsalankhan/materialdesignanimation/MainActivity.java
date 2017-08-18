package com.example.arsalankhan.materialdesignanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewName,textViewAnimation;
    private ImageView imageView;
    private RelativeLayout mRevealLayout, mRelativeLayout;
    Button btn_submit;
    ImageButton btn_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewAnimation = (TextView) findViewById(R.id.textViewAnimation);
        imageView = (ImageView) findViewById(R.id.imageView);

        mRevealLayout = (RelativeLayout) findViewById(R.id.reveal_click);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.RelativeLayoutAllViews);

        btn_close = (ImageButton) findViewById(R.id.btn_close);
        btn_submit = (Button) findViewById(R.id.btn_submit);


        btn_submit.setOnClickListener(this);
        btn_close.setOnClickListener(this);


        //Reenter Animation
        WindowReenterAnimation();

    }

    public void RippleAnimation(View view){
        Intent intent = new Intent(MainActivity.this,RippleAnimation.class);
        startActivity(intent);
    }

    public void TransitionAnimation(View view){

        Pair[] pair = new Pair[3];

        pair[0] = new Pair<View,String>(textViewName,"name");
        pair[1] = new Pair<View,String>(textViewAnimation,"animation");
        pair[2] = new Pair<View,String>(imageView,"image");

        Intent intent = new Intent(MainActivity.this,TransitionAnimation.class);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pair);
            startActivity(intent,options.toBundle());
        }
       else{
                startActivity(intent);
        }
    }


    //btn circular reveal animation
    public void CircularRevealAnimation(View view){

        mRelativeLayout.setVisibility(View.GONE);
        CreateRevealAnimation(mRevealLayout);
    }

    //creating circular reveal animation
    private void CreateRevealAnimation(RelativeLayout mRevealLayout) {

        int cx = (mRevealLayout.getLeft() + mRevealLayout.getRight())/2;
        int cy = (mRevealLayout.getTop() + mRevealLayout.getBottom())/2;

        float finalRadius = (mRevealLayout.getWidth() + mRevealLayout.getHeight())/2;

        Animator anim = ViewAnimationUtils.createCircularReveal(mRevealLayout,cx,cy,0,finalRadius);

        mRevealLayout.setVisibility(View.VISIBLE);
        anim.start();
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_close){
            ExitRevealAnimation();
        }

        else if(view.getId() == R.id.btn_submit){

            EditText etName = (EditText) findViewById(R.id.editTextName);
            EditText etEmail = (EditText) findViewById(R.id.editTextEmail);

            String name = etName.getText().toString();
            String email =etEmail.getText().toString();

            Toast.makeText(this, "Name: "+name +" \n Email: "+email, Toast.LENGTH_SHORT).show();

            ExitRevealAnimation();

        }
    }
//Exiting reveal animation
    private void ExitRevealAnimation() {

        int cx =(mRevealLayout.getLeft() + mRevealLayout.getRight())/2;
        int cy = (mRevealLayout.getTop() + mRevealLayout.getBottom())/2;

        float finalRadius = mRevealLayout.getWidth()/2;


        Animator anim = ViewAnimationUtils.createCircularReveal(mRevealLayout,cx,cx,finalRadius,0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                mRevealLayout.setVisibility(View.GONE);
                mRelativeLayout.setVisibility(View.VISIBLE);
            }
        });

        anim.start();
    }

    //btn for fade animation

    public void ExplodeAnimation(View view){

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);

        Intent intent = new Intent(this, ExplodeAnimation.class);
        intent.putExtra("type","explode");
        startActivity(intent, options.toBundle());

    }

    //btn for slide animation
    public void SlideAnimation(View view){

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);

        Intent intent =new Intent(this,ExplodeAnimation.class);
        intent.putExtra("type","slide");
        startActivity(intent,options.toBundle());
    }


    //animation for ReEntering to activity

    private void WindowReenterAnimation(){

        Fade fade = new Fade();
        fade.setDuration(1000);

        getWindow().setReenterTransition(fade);

        //for exiting the Activity Animation
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);


        //Avoiding overlap animation
        getWindow().setAllowReturnTransitionOverlap(false);
    }
}
