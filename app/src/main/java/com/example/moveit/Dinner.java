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

public class Dinner extends AppCompatActivity {

    double bmiValue;
    RelativeLayout layout;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        layout = (RelativeLayout) findViewById(R.id.relativeLayout3);
        TextView dinnerRecipes = (TextView) findViewById(R.id.dinnerRecipes);
        dinnerRecipes.setMovementMethod(new ScrollingMovementMethod());

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
                    layout.setBackgroundResource(R.drawable.macandcheese);
                    dinnerRecipes.setText("Ingredients\n" +
                            "2 1/2 lbs chicken thighs (bone-in, skin-on) (about 6 thighs)\n" +
                            "3 tsp salt\n" +
                            "3/4 tsp ground black pepper\n" +
                            "1/2 tsp paprika\n" +
                            "2 tbsp vegetable oil\n" +
                            "16 oz frozen cauliflower florets\n" +
                            "1 tbsp butter\n" +
                            "2 cups heavy whipping cream\n" +
                            "8 oz cream cheese (softened)\n" +
                            "2 cups shredded white cheddar\n" +
                            "1 cups fresh baby spinach\n" +
                            "1 tbsp chopped chives\n\n" +
                            "Instructions\n" +
                            "Preheat oven to 425 F. Pat dry chicken thighs with paper towel and season them with 2 tsp salt, 1/2 tsp pepper, and paprika on both sides.\n" +
                            "Heat oil in a 12-inch cast iron skillet over medium-high heat and sear chicken thighs skin side down for 3-4 minutes or until browned. Third, flip thighs and bake for about 18-20 minutes.\n" +
                            "Transfer to a plate and cover with aluminum foil. If needed, place a cup of water into the pan and scrub bottom with a wooden spoon to remove excess bits while heating on the stove top. \n" +
                            "Meanwhile, while thighs are baking, place cauliflower in a microwave-safe bowl with 2-3 tbsp of water, cover with a plate, and cook for about 3-4 minutes, or until tender yet firm. \n" +
                            "Drain and set aside. Using the same pan, melt butter over medium heat and whisk in heavy cream, 1 tsp salt and 1/4 tsp pepper, cream cheese, and cheese until sauce is smooth, about 2 minutes.\n" +
                            "Calories: 980; Prep time: 1 hour");

                } else if (bmiValue >= 18.5 && bmiValue <= 24.99) {
                    layout.setBackgroundResource(R.drawable.soup);
                    dinnerRecipes.setText("Ingredients\n" +
                            "½ cup red lentil\n" +
                            "1 teaspoon cumin seeds\n" +
                            "2 teaspoons ghee\n" +
                            "3 cloves garlic, chopped\n" +
                            "1 dry red chili\n" +
                            "Salt to taste\n" +
                            "½ teaspoon turmeric powder\n" +
                            "Cilantro for garnish\n" +
                            "⅔ cup water\n\n" +
                            "How To Prepare\n" +
                            "Pressure cook the daal using ⅔ cup water, a pinch of salt, and turmeric powder.\n" +
                            "Add the ghee in a pan.\n" +
                            "Add in the dry red chili and cumin seeds. Let it splutter for 10 seconds.\n" +
                            "Add in the chopped garlic and let it turn brown.\n" +
                            "Pour in the boiled daal and let it come to a boil over medium flame.\n" +
                            "Garnish with cilantro and a little bit more ghee.\n" +
                            "Calories: 440; Prep time: 30 min");

                } else if (bmiValue >= 25 && bmiValue <= 29.99) {
                    layout.setBackgroundResource(R.drawable.wraps);
                    dinnerRecipes.setText("Ingredients\n" +
                            "2 tbsp. gochujang\n" +
                            "1 tbsp. reduced-sodium soy sauce\n" +
                            "1 tbsp. fresh lime juice\n" +
                            "1/2 tbsp. packed brown sugar\n" +
                            "1 tsp. toasted sesame oil\n" +
                            "1 tbsp. olive oil\n" +
                            "Kosher salt and pepper\n" +
                            "1 1-pound pork tenderloin, trimmed\n" +
                            "Gem or baby romaine lettuce leaves\n" +
                            "Cooked rice; sliced radishes, cucumber\n\n" +
                            "Directions\n" +
                            "Heat oven to 400°F. In small bowl, combine gochujang, soy sauce, lime juice, sugar, and sesame oil. \n" +
                            "Heat oil in large oven-safe skillet on medium-high. Season pork with 1/2 teaspoon each salt and pepper and cook up to 6 - 8 minutes.\n" +
                            "Set aside half of gochujang mixture. Brush pork with remaining gochujang mixture and roast until internal temp reaches 145°F, 12 to 15 minutes.\n" +
                            "Transfer pork to cutting board. Using clean pastry brush, brush with remaining gochujang mixture and let rest 5 minutes before slicing." +
                            "Calories: 197, Prep time 30: min");


                } else if (bmiValue >= 30) {
                    layout.setBackgroundResource(R.drawable.wraps);
                    dinnerRecipes.setText("Ingredients\n" +
                            "2 tbsp. gochujang\n" +
                            "1 tbsp. reduced-sodium soy sauce\n" +
                            "1 tbsp. fresh lime juice\n" +
                            "1/2 tbsp. packed brown sugar\n" +
                            "1 tsp. toasted sesame oil\n" +
                            "1 tbsp. olive oil\n" +
                            "Kosher salt and pepper\n" +
                            "1 1-pound pork tenderloin, trimmed\n" +
                            "Gem or baby romaine lettuce leaves\n" +
                            "Cooked rice; sliced radishes, cucumber\n\n" +
                            "Directions\n" +
                            "Heat oven to 400°F. In small bowl, combine gochujang, soy sauce, lime juice, sugar, and sesame oil. \n" +
                            "Heat oil in large oven-safe skillet on medium-high. Season pork with 1/2 teaspoon each salt and pepper and cook up to 6 - 8 minutes.\n" +
                            "Set aside half of gochujang mixture. Brush pork with remaining gochujang mixture and roast until internal temp reaches 145°F, 12 to 15 minutes.\n" +
                            "Transfer pork to cutting board. Using clean pastry brush, brush with remaining gochujang mixture and let rest 5 minutes before slicing." +
                            "Calories: 197, Prep time 30: min");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}