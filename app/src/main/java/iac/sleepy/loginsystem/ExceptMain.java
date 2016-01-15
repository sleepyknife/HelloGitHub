package iac.sleepy.loginsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 21524677 on 2015/11/18.
 */
public class ExceptMain extends Activity
{
    private int testTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exceptlayout);
    }
    public void BackBtnClicked(View view)
    {
        Intent i = new Intent(this,LoginMain.class);
        startActivity(i);
    }
    public boolean gettesttime()
    {
        if(testTime > 0) {
            testTime--;
            return true;
        }
        else
        {
            testTime = 3;
            return false;
        }
    }
    public void setTestTime()
    {
        testTime = 3;
    }
}
