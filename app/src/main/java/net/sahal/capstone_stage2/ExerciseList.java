package net.sahal.capstone_stage2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExerciseList extends AppCompatActivity {


    private List<Exercises> exercisesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private Exercise_Adapter eAdapter;
    private Exercises exercises;
    private DatabaseReference mOrderDB;


    // Write a message to the database
//    private DatabaseReference mOrderDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);


        recyclerView = findViewById(R.id.exercises_row);

        eAdapter = new Exercise_Adapter(exercisesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(eAdapter);


        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference().child("Exercises");



        DatabaseReference myRef = database.getInstance().getReference().child("Exercises");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Exercises car = ds.getValue(Exercises.class);
                    recyclerView.setAdapter(eAdapter);

                    System.out.println(car.getExercise() + " bBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB ");

                }
//                Exercises exercises2 = dataSnapshot.getValue(Exercises.class);

//                String Value2 = dataSnapshot.getValue(Exercises.class).getExercise();
//                String value = dataSnapshot.getChildren().toString();

//                        .getValue(String.class);
//                System.out.println(value + " JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ ");



            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
