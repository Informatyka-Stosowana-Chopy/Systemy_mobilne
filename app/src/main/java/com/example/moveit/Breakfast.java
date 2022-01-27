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

public class Breakfast extends AppCompatActivity {

    double bmiValue;
    RelativeLayout layout;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast);

        layout = (RelativeLayout) findViewById(R.id.relativeLayout);
        TextView breakfastRecipe = (TextView) findViewById(R.id.breakfastRecipes);
        breakfastRecipe.setMovementMethod(new ScrollingMovementMethod());

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

                bmiValue = weightDouble / (Math.pow(heightDouble, 2)/10000);

                if(bmiValue <= 18.49) {
                    layout.setBackgroundResource(R.drawable.oat);
                    breakfastRecipe.setText("Ingredients\n" +
                            "1/2 cup instant oats\n" +
                            "1 cup full-fat milk\n" +
                            "1 banana, sliced\n" +
                            "2 tablespoons peanut butter\n" +
                            "A handful of raisins, soaked\n" +
                            "1 tablespoon honey\n\n" +
                            "How To Prepare\n" +
                            "1. Bring the milk to a boil and add the instant oats.\n" +
                            "2. Simmer and cook until the oats become soft and the milk thickens.\n" +
                            "3. Remove from the flame and transfer to a bowl.\n" +
                            "4. Add honey and peanut butter. Stir well.\n" +
                            "5. Top it with banana slices and raisins. \n\n" +
                            "Calories – 472; Serves – 1; Cooking Time – 10 mins");

                } else if (bmiValue >= 18.5 && bmiValue <= 24.99) {
                    layout.setBackgroundResource(R.drawable.tost);
                    breakfastRecipe.setText("Ingredients\n" +
                            "1 or 2 eggs\n" +
                            "1 whole wheat bread\n" +
                            "1 clove of garlic\n" +
                            "½ teaspoon dried oregano\n" +
                            "¼ cup chopped red bell pepper\n" +
                            "1 teaspoon olive oil\n" +
                            "Salt to taste\n" +
                            "Black pepper\n" +
                            "1 banana\n\n" +
                            "How To Prepare\n" +
                            "Heat olive oil in a pan.\n" +
                            "Crack open the egg(s).\n" +
                            "Add the bell pepper, salt, and pepper.\n" +
                            "Grate the garlic and spread it on the whole wheat bread.\n" +
                            "Add a little olive oil in a skillet and toast the bread.\n" +
                            "Transfer the sunny side up on to the whole wheat bread.\n" +
                            "Sprinkle some dried oregano. To make the breakfast filling, have one banana.\n\n" +
                            "Prep Time: 3 mins; Cooking Time: 6 mins; Calories: 193");

                } else if (bmiValue >= 25 && bmiValue <= 29.99) {
                    layout.setBackgroundResource(R.drawable.smoothie);
                    breakfastRecipe.setText("Ingredients\n" +
                            "1 banana\n" +
                            "4 almonds\n" +
                            "200 mL milk\n" +
                            "2 tablespoons yogurt\n" +
                            "¼ teaspoon cinnamon powder\n\n" +
                            "How To Prepare\n" +
                            "Toss all the ingredients in a blender and blend well.\n" +
                            "Pour into a glass, and it’s ready!\n" +
                            "You can pour it into a mason jar or glass bottle and take it to work.\n\n" +
                            "Prep Time: 5 mins; Cooking Time: 2 mins; Calories: 189");


                } else if (bmiValue >= 30) {
                    layout.setBackgroundResource(R.drawable.smoothie);
                    breakfastRecipe.setText("Ingredients\n" +
                            "1 banana\n" +
                            "4 almonds\n" +
                            "200 mL milk\n" +
                            "2 tablespoons yogurt\n" +
                            "¼ teaspoon cinnamon powder\n\n" +
                            "How To Prepare\n" +
                            "Toss all the ingredients in a blender and blend well.\n" +
                            "Pour into a glass, and it’s ready!\n" +
                            "You can pour it into a mason jar or glass bottle and take it to work.\n\n" +
                            "Prep Time: 5 mins; Cooking Time: 2 mins; Calories: 189");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}