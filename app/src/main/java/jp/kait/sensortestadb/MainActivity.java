package jp.kait.sensortestadb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;
import java.util.List;
import android.view.WindowManager;
import org.microbridge.server.Server;
import java.io.IOException;
import org.microbridge.server.AbstractServerListener;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager manager;
    private Server server= null;
    TextView dataView;
    MyView myView;
    //String   sendStr;
    byte[] sendData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        dataView = findViewById(R.id.textView1);
        myView = findViewById(R.id.myView1);

        try {
            server = new Server(4567);
            server.start();
            server.addListener(new AbstractServerListener()
            {
                @Override
                public void onReceive(org.microbridge.server.Client client,byte[] data)
                {
                    if (data.length<3)return;
                    myView.setColor(
                        (int)data[0]&0xFF,(int)data[1]&0xFF,(int)data[2]&0xFF
                        );
                }
            });
        }
        catch(IOException e){
            System.exit(-1);
        }
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onResume() {
        super.onResume();

        List<Sensor> sensors =
                manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size() > 0) {
            Sensor s = sensors.get(0);
            manager.registerListener(this, s, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(server != null){
            server.stop();
        }
        manager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // センサの値が変化したときに呼ばれる
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float accX = event.values[0];
            float accY = event.values[1];
            float accZ = event.values[2];
/*
            sendStr = "加速度センサ値:"
                    + "\nX軸:" + accX
                    + "\nY軸:" + accY
                    + "\nZ軸:" + accZ;*/
            //dataView.setText(sendStr.toCharArray(), 0, sendStr.length() );

            sendData = new byte[6];
            short ax = (short)(accX * 100.0);
            sendData[0] = (byte)((ax >> 8) & 0xFF);
            sendData[1] = (byte)(ax & 0xFF);

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try
                    {
                        //server.send(sendStr+"\r\n");
                        server.send(sendData);
                    }
                    catch (IOException e)
                    {
                        String str = "通信エラー発生";
                        dataView.setText(str.toCharArray(), 0, str.length() );
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();

            int posX , posY;
            posX = (int)(accX*100);
            posY = (int)(accY*100);
            myView.setPos( posX, posY);
        }
    }
}