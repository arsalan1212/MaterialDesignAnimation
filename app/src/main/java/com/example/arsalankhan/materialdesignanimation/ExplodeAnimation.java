package com.example.arsalankhan.materialdesignanimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;

public class ExplodeAnimation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explode_animation);

        getSupportActionBar().setTitle("Explode Animation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getWindow().setAllowEnterTransitionOverlap(false);

        Intent intent = getIntent();

        if(intent.getExtras().getString("type").equals("explode")){

            Explode enterTransition = new Explode();
            enterTransition.setDuration(1000);
            getWindow().setEnterTransition(enterTransition);
        }

        else if(intent.getExtras().getString("type").equals("slide")){

            Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_anim);
            getWindow().setEnterTransition(transition);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
      finishAfterTransition();
        return true;
    }
}
