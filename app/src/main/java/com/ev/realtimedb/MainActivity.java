package com.ev.realtimedb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ev.realtimedb.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.ev.realtimedb.adapters.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    EditText editMessage;
    EditText edtName;
    Button sendButton;
    Button clearButton;
    RecyclerView recyclerMenssagens;
    MessageAdapter adapter;
    List<Message> messageList = new ArrayList<>();
    DatabaseReference refMessage;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        
        initComponents();
    }
    
    private void initComponents()
    {
        refMessage = FirebaseDatabase.getInstance().getReference( "mensagens" );
        
        editMessage = findViewById( R.id.edtMensagem );
        edtName = findViewById( R.id.edtNome );
        sendButton = findViewById( R.id.btnEnviar );
        clearButton = findViewById( R.id.btnClear );
        recyclerMenssagens = findViewById( R.id.recyclerMensagens );
        
        adapter = new MessageAdapter( messageList );
        recyclerMenssagens.setLayoutManager( new LinearLayoutManager( this ) );
        recyclerMenssagens.setAdapter( adapter );
        
        sendButton.setOnClickListener( v ->
        {
            String text = editMessage.getText().toString();
            String user =  edtName.getText().toString().trim();
            
            if ( !user.isEmpty() && !text.isEmpty() )
            {
                Message msg = new Message( user, text );
                refMessage.push().setValue( msg );
                editMessage.setText( "" );
            }
        });
        
        refMessage.addValueEventListener( new ValueEventListener()
        {
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot )
            {
                messageList.clear();
                
                for ( DataSnapshot dados : snapshot.getChildren() )
                {
                    Message msg = dados.getValue( Message.class );
                    messageList.add( msg );
                }
                
                adapter.notifyDataSetChanged();
                recyclerMenssagens.scrollToPosition( messageList.size() - 1 );
            }
            
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        
        clearButton.setOnClickListener( l -> 
        {
            refMessage.removeValue()
                      .addOnSuccessListener( aVoid ->
                            Toast.makeText( MainActivity.this, "Chat limpo!", Toast.LENGTH_SHORT).show() )
                      .addOnFailureListener(e ->
                            Toast.makeText( MainActivity.this, "Erro ao limpar o chat", Toast.LENGTH_SHORT).show() );
        } );
    }
}