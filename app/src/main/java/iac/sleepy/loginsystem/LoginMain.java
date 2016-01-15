package iac.sleepy.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;




public class LoginMain extends Activity
{
    DBHandler dbHandler;
    EditText usernameinput;
    EditText passwdinput;
    int exp=3;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login_main);
        dbHandler = new DBHandler(this,null,null,1);
        usernameinput = (EditText) findViewById(R.id.usernameInput);
        passwdinput = (EditText) findViewById(R.id.passwordInput);
        usernameinput.setHint("username");
        passwdinput.setHint("password");
    }
/*
    @Override
    public void onBackPressed() {
        //Disable BackButton
    }*/

    //Change layout view into RegistLayout
    public void registBtnClicked(View view)
    {
        Intent i = new Intent (this,RegistMain.class);
        startActivity(i);
    }


    public void loginBtnClicked(View view)
    {
        Intent i = new Intent(this,NormalMain.class);
        Intent j = new Intent(this,ExceptMain.class);

        String usernametext = usernameinput.getText().toString();
        String passwordtext = passwdinput.getText().toString();


        if(dbHandler.CheckLogin(usernametext, passwordtext)) {
            i.putExtra("username",usernametext);
            i.putExtra("password",passwordtext);
            Intent k = new Intent(this,AdminMain.class);
            k.putExtra("username",usernametext);
            k.putExtra("password",passwordtext);
            if(usernametext.equals("aa"))
                startActivity(k);
            else
                startActivity(i);
        }
        else
        {
            if(exp>0) {
                exp--;
                startActivity(j);
            }
            else {
                Toast.makeText(this, "You had tried too much times!!", Toast.LENGTH_LONG).show();
                exp = 3;
            }
        }
    }
}
