package com.example.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    FirebaseAuth mAuth;

    private FirebaseListAdapter<ChatMessage> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null)
        {
            // nobody is logged in... we should probably show some
            //  buttons for "Login" and "Register" that will call our cool new Activities
            //Send them to login page
            Intent intent
                    = new Intent(MainActivity.this,
                    UserLogin.class);
            startActivity(intent);
        }
        else
        {
            // we have a user already logged in... cool.  What do we do with that?
            displayMessages();
        }
        displayMessages();
        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);
                if(input.getText().toString().isEmpty())
                {
                    input.setError("Insert a Message Por Favor");
                }
                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),
                                FirebaseAuth.getInstance()
                                        .getCurrentUser()
                                        .getDisplayName())
                        );

                // Clear the input
                input.setText("");
            }
        });
    }


    void displayMessages()
    {
        ListView listOfMessages = (ListView)findViewById(R.id.list_of_messages);

        FirebaseListOptions<ChatMessage> options = new FirebaseListOptions.Builder<ChatMessage>()
                .setQuery(FirebaseDatabase.getInstance().getReference(), ChatMessage.class).setLayout(R.layout.message).build();


        adapter = new FirebaseListAdapter<ChatMessage>(options) {

            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message.xml
                TextView messageText = (TextView)v.findViewById(R.id.message_text);
                TextView messageUser = (TextView)v.findViewById(R.id.message_user);
                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());

                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));


            }
        };

        listOfMessages.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (currentUser != null) {
            adapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (currentUser != null) {
            adapter.stopListening();
        }
    }

//sign out
//FirebaseAuth.getInstance().signOut();

}