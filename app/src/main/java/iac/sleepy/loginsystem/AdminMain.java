package iac.sleepy.loginsystem;

import android.app.Dialog;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;


/**
 * Created by 21524677 on 2015/11/20.
 */
public class AdminMain extends ListActivity  {
    DBHandler dbHandler;
    private myAdapter mListAdapter;
    ArrayList<listformat> list = new ArrayList<listformat>();
    String Getusername;
    String Getpassword;

    String TAG = "sleepy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHandler = new DBHandler(this,null,null,1);
        list = setList();
        mListAdapter = new myAdapter(this);
        setListAdapter(mListAdapter);
        //Because this case use textview to implement list. Hence if setContentView here will cause crash
        //setContentView(R.layout.adminlayout);


        //Get the imformation from LoginMain
        Bundle RevData = getIntent().getExtras();
        Getusername = RevData.getString("username");
        Getpassword = RevData.getString("password");

        // Set up the Item Long Click Listener
        AdapterView.OnItemLongClickListener  listener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Dialog mydialog = new AlertDialog.Builder(AdminMain.this)
                        .setTitle("Are you sure to delete this Profile?")
                        //Delete button function implement
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Initial EditText var
                                final EditText passwordinput = new EditText(AdminMain.this);
                                //Check Adminstrator password if pass delete from db
                                DialogInterface.OnClickListener CheckAdminPass = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(passwordinput.getText().toString().equals(Getpassword))
                                        {
                                            dbHandler.delProfile(list.get(position).listname);
                                            list.remove(list.get(position));
                                            mListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                };
                                //Create Another Dialog window let user enter password&check
                                new AlertDialog.Builder(AdminMain.this)
                                        .setTitle("Please enter Adminstrator password")
                                        .setView(passwordinput)
                                        .setPositiveButton("Enter", CheckAdminPass)
                                        .setNegativeButton("Cancel",null)
                                        .show();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
                return true;
            }
        };
        getListView().setOnItemLongClickListener(listener);
    }










    @Override
    public void onBackPressed() {
        //Disable BackButton
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_main,menu);
        //menu.findItem(R.id.logout).setVisible(true);
        menu.findItem(R.id.logout).setVisible(true);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                Intent i = new Intent (this,LoginMain.class);
                startActivity(i);
                break;
        }
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);
        final View item = LayoutInflater.from(AdminMain.this).inflate(R.layout.dialog_content,null);
        final TextView usrnameshown = (TextView) item.findViewById(R.id.usrnameshown);
        TextView usrpasswordshown = (TextView) item.findViewById(R.id.usrpasswordshown);
        EditText input = (EditText) item.findViewById(R.id.edtfield);

        usrnameshown.setText(list.get(position).listname);
        usrpasswordshown.setText(list.get(position).listpass);
        input.setVisibility(View.INVISIBLE);

        DialogInterface.OnClickListener deleteBtnclicked = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog childlog = new AlertDialog.Builder(AdminMain.this)
                        .setTitle("Are you sure to delete this Profile?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Initial EditText var
                                final EditText passwordinput = new EditText(AdminMain.this);
                                //Check Adminstrator password if pass delete from db
                                DialogInterface.OnClickListener CheckAdminPass = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (passwordinput.getText().toString().equals(Getpassword)) {
                                            dbHandler.delProfile(list.get(position).listname);
                                            list.remove(list.get(position));
                                            mListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                };
                                //Create Another Dialog window let user enter password&check
                                new AlertDialog.Builder(AdminMain.this)
                                        .setTitle("Please enter Adminstrator password")
                                        .setView(passwordinput)
                                        .setPositiveButton("Enter", CheckAdminPass)
                                        .setNegativeButton("Cancel", null)
                                        .show();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        };

        Dialog mydialog = new AlertDialog.Builder(AdminMain.this)
                .setTitle("Profile")
                .setView(item)
                .setPositiveButton("Delete", deleteBtnclicked)
                .setNegativeButton("OK", null)
                .setNeutralButton("Modify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final EditText passwordinput = new EditText(AdminMain.this);
                        new AlertDialog.Builder(AdminMain.this)
                                .setTitle("Please enter the password")
                                .setView(passwordinput)
                                .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        listformat  updatedata = new listformat();
                                        updatedata.listname = usrnameshown.getText().toString();
                                        updatedata.listpass = passwordinput.getText().toString();
                                        dbHandler.modProfile(usrnameshown.getText().toString(), passwordinput.getText().toString());
                                        list.set(position,updatedata);
                                        mListAdapter.notifyDataSetChanged();
                                    }
                                })
                                .setNegativeButton("Cancel",null)
                                .show();
                    }
                })
                .show();

/*
        l.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,final  int position, long id) {
                Dialog mydialog = new AlertDialog.Builder(AdminMain.this)
                        .setTitle("Are you sure to delete this Profile?")
                                //Delete button function implement
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Initial EditText var
                                final EditText passwordinput = new EditText(AdminMain.this);
                                //Check Adminstrator password if pass delete from db
                                DialogInterface.OnClickListener CheckAdminPass = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(passwordinput.getText().toString().equals(Getpassword))
                                        {
                                            dbHandler.delProfile(list.get(position).listname);
                                            list.remove(list.get(position));
                                            mListAdapter.notifyDataSetChanged();
                                        }
                                    }
                                };
                                //Create Another Dialog window let user enter password&check
                                new AlertDialog.Builder(AdminMain.this)
                                        .setTitle("Please enter Adminstrator password")
                                        .setView(passwordinput)
                                        .setPositiveButton("Enter", CheckAdminPass)
                                        .setNegativeButton("Cancel",null)
                                        .show();
                            }
                        })
                        .setNegativeButton("Cancel",null)
                        .show();
                return true;
            }
        });

        */
    }


    private class myAdapter extends BaseAdapter{
        private LayoutInflater mInflator;
        public myAdapter(Context context){
            mInflator = LayoutInflater.from(context);
        }


        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int position) {
            return list.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AccountHolder accountHolder;
            if(convertView == null){
                convertView = mInflator.inflate(R.layout.adminlayout,null);
                accountHolder = new AccountHolder();
                accountHolder.username = (TextView) convertView.findViewById(R.id.dbusername);
                accountHolder.password = (TextView) convertView.findViewById(R.id.dbpassword);
                convertView.setTag(accountHolder);
            }
            else
                accountHolder = (AccountHolder) convertView.getTag();
                accountHolder.username.setText(list.get(position).listname);
                accountHolder.password.setText(list.get(position).listpass);

            return convertView;
        }
    }
    static class AccountHolder{
        TextView username;
        TextView password;
    }

    public ArrayList<listformat> setList()
    {
        int position = 1;
        ArrayList<listformat> returnlist = new ArrayList<listformat>();
        while(dbHandler.GetdbData(position,0) != null)
        {
            //TODO: Note Every data input through template need to Re-assign when a new data coming  (Otherwise the data information will same as the last data
            listformat dbpackage = new listformat();
            String username = dbHandler.GetdbData(position,0);
            String password = dbHandler.GetdbData(position,1);
            dbpackage.listname = username;
            dbpackage.listpass = password;
            returnlist.add(dbpackage);
            position++;
        }
        return  returnlist;
    }
    public class listformat
    {
        String listname;
        String listpass;
    }
}
