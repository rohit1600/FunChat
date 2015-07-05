package com.shwetharohit.funchat;

import android.app.Activity;
import android.database.Cursor;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;


public class MainActivity extends Activity {
DBAdapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openDB();
        TextView ChatBox = (TextView)findViewById(R.id.ChatBox);
        Cursor c=myDB.getAllRows();
        ChatBox.setText(displayRecordSet(c));

    }
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }
    private void closeDB() {
        myDB.close();
    }
    private void openDB() {
        myDB=new DBAdapter(this);
        myDB.open();
    }

   /* public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    */
    public void onSend(View v) {
        // Perform action on click*/
        TextView textEntered = (TextView)findViewById(R.id.EnterMessage);
        TextView ChatBox = (TextView)findViewById(R.id.ChatBox);
        Long rowid= myDB.insertRow(textEntered.getText().toString(), "14086135446", "S", new Date().toString());
        textEntered.setText("");
        textEntered.clearFocus();
        //System.out.println("Rohit: "+myDB.getAllRows().getString(1));
        Cursor c=myDB.getAllRows();
        c.moveToLast();
        ChatBox.append("Rohit [" + c.getString(DBAdapter.COL_Timestamp) + "]:" +c.getString(DBAdapter.COL_Message) + "\n");
        ChatBox.append(displayRecordSet(c));
        c.close();
        //ChatBox.append("Stock: " + textEntered.getText() + "    ");
        //StockModel wm=new StockModel();
        //enableStrictMode();
        //wm.doSearch(textEntered.getText().toString());
        //System.out.println(wm.getCurrentPrice());
        //ChatBox.append("Price: " + wm.getCurrentPrice() + "\n");
        //textEntered.setText("");
    }
    private String displayRecordSet(Cursor c){
        String s="";
        if(c.moveToFirst()){
            do {
                s=s+"Rohit [" + c.getString(DBAdapter.COL_Timestamp) + "]:" +c.getString(DBAdapter.COL_Message) + "\n";
            } while (c.moveToNext());
        }
        return s;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
