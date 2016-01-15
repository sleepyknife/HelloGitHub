package iac.sleepy.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 21524677 on 2015/11/18.
 */
public class NormalMain extends Activity
{
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normallayout);
        Bundle revData = getIntent().getExtras();
        username = (TextView) findViewById(R.id.normalID);
        username.setText(revData.getString("username"));
    }

    public void regcancelBtnClicked(View view)
    {
        Intent i = new Intent (this,LoginMain.class);
        startActivity(i);
    }
}
