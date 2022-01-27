package com.example.moveit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Lunch extends AppCompatActivity {

    RelativeLayout layout;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch);

        layout = (RelativeLayout) findViewById(R.id.relativeLayout2);
        TextView lunchRecipes = (TextView) findViewById(R.id.lunchRecipes);
        lunchRecipes.setMovementMethod(new ScrollingMovementMethod());

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                double heightDouble = Double.parseDouble(userProfile.heigth);
                double weightDouble = Double.parseDouble(userProfile.weigth);

                double bmiValue = weightDouble / (Math.pow(heightDouble, 2)/10000);

                if(bmiValue <= 18.49) {
                    layout.setBackgroundResource(R.drawable.bacon);
                    lunchRecipes.setText("Ingredients\n" +
                            "A piece of tasty bread, cut in two (sourdough, farmhouse or ciabatta)\n" +
                            "3 baby tomatoes, sliced\n" +
                            "One avocado (ready to eat)\n" +
                            "1 rasher of back bacon\n" +
                            "One egg\n" +
                            "Salt and Pepper\n" +
                            "1 tbsp Olive oil\n\n" +
                            "Instructions\n" +
                            "Peel and slice the avocado\n" +
                            "Warm the olive oil in a pan and sear both sides of the bread. Then put it aside and fry the bacon rasher.\n" +
                            "Meanwhile in a pan of boiling water poach the egg.\n" +
                            "When bacon is cooked chop it into small pieces.\n" +
                            "Place the avocado on the bread (if you prefer you can mush it up with a fork beforehand).\n " +
                            "Then add the sliced tomatoes, the bacon and the poached egg. Season with salt and pepper to suit.\n\n" +
                            "Calories: 820; Prep time: 5 min; Cook time: 5 min");

                } else if (bmiValue >= 18.5 && bmiValue <= 24.99) {
                    layout.setBackgroundResource(R.drawable.chicken);
                    lunchRecipes.setText("Ingredients\n" +
                            "Chicken\n" +
                            "6 chicken thighs (boneless, skinless)\n" +
                            "1/2 cup olive oil\n" +
                            "1 tsp turmeric\n" +
                            "1 tsp paprika\n" +
                            "1 tsp cumin\n" +
                            "1/2 tsp cinnamon\n" +
                            "1 tbsp ground ginger\n" +
                            "4 garlic cloves (minced)\n\n" +
                            "Instructions\n" +
                            "Preheat the oven to 400F.\n" +
                            "In a large bowl, mix together olive oil, turmeric, paprika, cumin, cinnamon, ground ginger, and garlic. Coat the chicken in it and leave it for at least 30 minutes.\n" +
                            "Rinse and cut your cauliflower head into florets.\n" +
                            "In a separate bowl, mix together 3 tbsp olive oil and garlic powder. Toss cauliflower in it until it’s well coated.\n" +
                            "Spread the cauliflower on a baking sheet, cover with chicken thighs, and make sure to pour all the oil in to cover some of the cauliflower too.\n" +
                            "Roast in the oven for 30 minutes until the chicken is fully cooked.\n" +
                            "Garnish with parsley, dill, and red onion.\n" +
                            "Calories: 480; Prep time: 20 min; Cook time: 30 min");

                } else if (bmiValue >= 25 && bmiValue <= 29.99) {
                    layout.setBackgroundResource(R.drawable.shrimps);
                    lunchRecipes.setText("Ingredients\n" +
                            "1 medium-sized zucchini\n" +
                            "4-5 fresh shrimps\n" +
                            "1 lettuce leaf\n" +
                            "3 cherry tomatoes\n" +
                            "Dried oregano\n" +
                            "1 tablespoon olive oil\n" +
                            "1 clove garlic\n" +
                            "Salt and pepper\n\n" +
                            "Directions\n" +
                            "Use a vegetable spiral slicer to make zucchini noodles or zoodles.\n" +
                            "Put the zoodles in a kitchen towel to soak up the extra moisture.\n" +
                            "Finely chop the garlic.\n" +
                            " Heat a little olive oil in a frying pan and fry the garlic for 15 seconds.\n" +
                            "Add the shrimps and cook for about a minute.\n" +
                            "Meanwhile, cut the cherry tomatoes into halves and roughly chop the lettuce leaf.\n" +
                            "In another frying pan, cook the zoodles in a little olive oil for about a minute.\n" +
                            "Put the cooked shrimps and the garlic-flavored oil (if any left in the pan) into a bowl.\n" +
                            "Add the zoodles, cherry tomatoes, and lettuce to the bowl.\n" +
                            "Add a little salt, pepper, and dried oregano and toss evenly to coat the spices.\n\n" +
                            "Calories – 187; Prep time - 20 min; Cook time - 20 min");


                } else if (bmiValue >= 30) {
                    layout.setBackgroundResource(R.drawable.shrimps);
                    lunchRecipes.setText("Ingredients\n" +
                            "1 medium-sized zucchini\n" +
                            "4-5 fresh shrimps\n" +
                            "1 lettuce leaf\n" +
                            "3 cherry tomatoes\n" +
                            "Dried oregano\n" +
                            "1 tablespoon olive oil\n" +
                            "1 clove garlic\n" +
                            "Salt and pepper\n\n" +
                            "Directions\n" +
                            "Use a vegetable spiral slicer to make zucchini noodles or zoodles.\n" +
                            "Put the zoodles in a kitchen towel to soak up the extra moisture.\n" +
                            "Finely chop the garlic.\n" +
                            " Heat a little olive oil in a frying pan and fry the garlic for 15 seconds.\n" +
                            "Add the shrimps and cook for about a minute.\n" +
                            "Meanwhile, cut the cherry tomatoes into halves and roughly chop the lettuce leaf.\n" +
                            "In another frying pan, cook the zoodles in a little olive oil for about a minute.\n" +
                            "Put the cooked shrimps and the garlic-flavored oil (if any left in the pan) into a bowl.\n" +
                            "Add the zoodles, cherry tomatoes, and lettuce to the bowl.\n" +
                            "Add a little salt, pepper, and dried oregano and toss evenly to coat the spices.\n\n" +
                            "Calories – 187; Prep time - 20 min; Cook time - 20 min");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}