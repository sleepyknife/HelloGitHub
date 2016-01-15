package iac.sleepy.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class RegistMain extends Activity
{
    EditText usernameinput;
    EditText passwordinput;
    EditText passwordvalidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registlayout);
        usernameinput = (EditText) findViewById(R.id.regusernameInput);
        passwordinput = (EditText) findViewById(R.id.regpasswordInput);
        passwordvalidate = (EditText) findViewById(R.id.regpasswordInput2);
    }

    public void regcancelBtnClicked(View view)
    {
        Intent i = new Intent (this,LoginMain.class);
        startActivity(i);
    }
    public void regokBtnClicked(View view)
    {
        String usernametext;
        String passwdtext;
        String passwdvd;

        usernametext = usernameinput.getText().toString();
        passwdtext = passwordinput.getText().toString();
        passwdvd = passwordvalidate.getText().toString();

        DBHandler db = new DBHandler(this,null,null,1);

        if(passwdtext.compareTo(passwdvd) != 0)
        {
            Toast.makeText(this,"The pass word not the same!!",Toast.LENGTH_LONG).show();
        }
        else if(db.Checkdb(usernametext) != -1)
        {
                Toast.makeText(this,"This username already in DB",Toast.LENGTH_LONG).show();
        }
        else
        {
            db.addProfile(usernametext,passwdtext);
            Toast.makeText(this,"The username "+usernametext+" had create successfully!",Toast.LENGTH_LONG).show();
            Intent i =new Intent(this,LoginMain.class);
            startActivity(i);
        }
    }
}
