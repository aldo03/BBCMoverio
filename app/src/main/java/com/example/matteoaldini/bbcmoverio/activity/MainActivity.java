package com.example.matteoaldini.bbcmoverio.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matteoaldini.bbcmoverio.R;
import com.example.matteoaldini.bbcmoverio.TreasureAdapter;
import com.example.matteoaldini.bbcmoverio.bluetooth.AcceptThread;
import com.example.matteoaldini.bbcmoverio.model.Match;
import com.example.matteoaldini.bbcmoverio.model.Position;
import com.example.matteoaldini.bbcmoverio.model.TreasureChest;

import org.json.JSONException;

import java.io.IOException;


public class MainActivity extends Activity {
    private final static int REQUEST_ENABLE_BT = 10;
    private TextView latText;
    private TextView longText;
    private TextView totalText;
    private TextView pointsText;
    private Handler handler;
    private Match match;
    private Button button;
    private Button alertButton;
    private ListView listView;
    private ScrollView scrollView;
    private boolean show;
    private TextView nearTextView;
    private AcceptThread btThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.i("entrato", "non attivo");
        this.show = false;
        this.latText = (TextView)findViewById(R.id.latText);
        this.longText = (TextView)findViewById(R.id.longText);
        this.totalText = (TextView) findViewById(R.id.totalAmount);
        this.pointsText = (TextView) findViewById(R.id.points);
        this.button = (Button)findViewById(R.id.treasuresButton);
        this.alertButton = (Button) findViewById(R.id.alertButton);
        this.listView = (ListView)findViewById(R.id.listView);
        this.scrollView = (ScrollView)findViewById(R.id.scrollView);
        this.scrollView.setVisibility(View.INVISIBLE);
        this.nearTextView = (TextView) findViewById(R.id.nearTextView);
        this.nearTextView.setVisibility(View.INVISIBLE);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show){
                    show = true;
                    listView.setAdapter(new TreasureAdapter(match.getTreasures(), getApplicationContext()));
                    scrollView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    show = false;
                    scrollView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                }
            }
        });

        this.alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btThread.getConnection().sendAlertToSmartphone("Danger Zone!!!");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        this.handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 0:
                        matchReceived((Match) msg.obj);
                        break;
                    case 1:
                        treasureReceived((TreasureChest) msg.obj);
                        break;
                    case 2:
                        confirmOrRefuseMsgReceived((Boolean) msg.obj);
                        break;
                    case 3:
                        moneyTheftReceived((Integer) msg.obj);
                        break;
                    case 4:
                        newAmountReceived((Integer) msg.obj);
                        break;
                    case 5:
                        positionReceived((Position) msg.obj);
                        break;
                    case 6:
                        treasureReceivedNotPresent((TreasureChest) msg.obj);
                        break;
                    case 7:
                        alertReceived((String) msg.obj);
                        break;
                    case 8:
                        moneyTheftReceivedNotMe((Integer) msg.obj);
                        break;
                }
            }
        };

        if (!mBluetoothAdapter.isEnabled()) {
            Log.i("entrato", "non attivo");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else {
            this.btThread = new AcceptThread(handler);
            btThread.start();
        }
    }

    private void moneyTheftReceivedNotMe(Integer obj) {
        Toast.makeText(this, "Your friend were robbed!!!",Toast.LENGTH_LONG).show();
        this.match.setPoints(obj);
        this.pointsText.setText(""+this.match.getPoints());
    }

    private void alertReceived(String obj) {
        Toast.makeText(this, "ALERT:"+obj,Toast.LENGTH_LONG).show();
    }

    private void treasureReceivedNotPresent(TreasureChest treasureChest) {
        String s = this.match.updateTreasureChestNotPresent(treasureChest);
        if(s.equals("COOPERATION")){
            new AlertDialog.Builder(getApplicationContext())
                    .setTitle("Cooperation request for treasure chest "+treasureChest.getNumber())
                    .setPositiveButton("Accept",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                btThread.getConnection().sendResponseToSmartphone("OK");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .setNegativeButton("Refuse", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                btThread.getConnection().sendResponseToSmartphone("KO");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .show();
        }
        this.totalText.setText(""+this.match.getMaxPoints());
        this.pointsText.setText(""+this.match.getPoints());
        Toast.makeText(this, s,Toast.LENGTH_LONG).show();
    }

    private void positionReceived(Position obj) {
        this.latText.setText(""+obj.getLatitude());
        this.longText.setText(""+obj.getLongitude());
        boolean found = false;
        for(TreasureChest t: this.match.getTreasures()){
            Position p = new Position(t.getLatitude(), t.getLongitude());
            if(p.getDistance(obj)<0.02){
                found = true;
                this.nearTextView.setText("You are near a Treasure Chest");
                this.nearTextView.setVisibility(View.VISIBLE);
            }
        }
        if(!found){
            this.nearTextView.setVisibility(View.INVISIBLE);
        }
        Log.i("",""+obj.getLatitude());
    }

    private void matchReceived(Match match){
        this.match = match;
        this.totalText.setText(""+this.match.getMaxPoints());
        this.pointsText.setText("" + this.match.getPoints());
        Toast.makeText(this, "Match Received!!!",Toast.LENGTH_LONG).show();
    }

    private void treasureReceived(TreasureChest treasureChest){
        String s = this.match.updateTreasureChest(treasureChest);
        this.totalText.setText(""+this.match.getMaxPoints());
        this.pointsText.setText(""+this.match.getPoints());
        Toast.makeText(this, s,Toast.LENGTH_LONG).show();
    }

    private void confirmOrRefuseMsgReceived(boolean confirm){
        if(confirm){
            Toast.makeText(this, "Cooperation confirmed",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Cooperation refused",Toast.LENGTH_LONG).show();
        }
    }

    private void moneyTheftReceived(int amount) {
        Toast.makeText(this, "You were robbed!!!",Toast.LENGTH_LONG).show();
        this.match.setPoints(amount);
        this.pointsText.setText(""+this.match.getPoints());
    }

    private void newAmountReceived(int amount) {
        Toast.makeText(this, "Amount updated!!!",Toast.LENGTH_LONG).show();
        this.match.setMaxPoints(amount);
        this.totalText.setText("" + this.match.getMaxPoints());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*AcceptThread thread = new AcceptThread(handler);
        thread.start();*/
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
