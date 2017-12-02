package com.bfs.bfshostmqttprotocol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.List;

public class MainActivity extends AppCompatActivity {
/*
    static final String MQTTHOST = "";
    static final String USERNAME = "";
    static final String PASSWORD = "";
    private String subscribeTopic = "orders";// 想訂閱的看板
    private MqttAndroidClient client;

    private OrderBean order;
    private String mqttMessageString;
    private String id = ""; //已抓取的顧客id
    private String orderStatus = "";// 訂單狀態

    private TextView toBeAcceptedText;
    private TextView toBeCompletedText;
    private Button refuseBtn;
    private Button acceptBtn;
    private Button completeBtn;
    private Button clearBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main);
        //initViews();
        //initClient();
    }

    private void initViews() {
        toBeAcceptedText = (TextView) findViewById(R.id.toBeAcceptedText);
        toBeCompletedText = (TextView) findViewById(R.id.toBeCompletedText);
        refuseBtn = (Button) findViewById(R.id.refuseBtn);
        acceptBtn = (Button) findViewById(R.id.acceptBtn);
        completeBtn = (Button) findViewById(R.id.completeBtn);
        clearBtn = (Button) findViewById(R.id.clearBtn);
    }

    private void initClient() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(this.getApplicationContext(), MQTTHOST, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) { // 連線成功 欲執行的程式
                    Toast.makeText(MainActivity.this,"connected!",Toast.LENGTH_LONG).show();
                    setSubscription();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(MainActivity.this,"connected failed!",Toast.LENGTH_LONG).show();
                }
            });
        } catch (MqttException e) {e.printStackTrace();}

        client.setCallback(new MqttCallback() { // 收到訂閱之看板的回傳訊息
            @Override
            public void connectionLost(Throwable throwable) { Toast.makeText(MainActivity.this," connection Lost!",Toast.LENGTH_LONG).show();}

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                mqttMessageString = new String(mqttMessage.getPayload());
                toBeAcceptedText.setText(mqttMessageString);
                parseOrder(mqttMessageString);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}
        });
    }

    public void setPublish(String id, String orderStatus){
        String topic = id;
        String message = orderRespondToJson(id, orderStatus);;
        try {
            client.publish(topic, message.getBytes(),0,false);
        } catch (MqttException e) {e.printStackTrace();}
    }// 發佈看板、訊息

    private void setSubscription(){
        try{
            client.subscribe(subscribeTopic,0);
        }catch(MqttException e){e.printStackTrace();}
    }// 訂閱看板

    public static String sendOrderToHost(String id, String time, int sumPrice, List list) {
        OrderBean bean = new OrderBean();
        bean.setId(id);
        bean.setTime(time);
        bean.setSumprice(sumPrice);
        bean.setList(list);
        Gson gson = new Gson();
        String printer = gson.toJson(bean);
        System.out.println(printer);
        return printer;
    }// 將選取的餐點資訊丟進來會用Gson包好

    public static String orderRespondToJson(String id, String orderStatus) {
        OrderRespondBean orderRespondBean = new OrderRespondBean();
        orderRespondBean.setId(id);
        orderRespondBean.setOrderStatus(orderStatus);
        Gson gson = new Gson();
        String json = gson.toJson(orderRespondBean);
        Log.d("orderRespondJson", json);
        return json;
    }// 用Gson包要回傳的訂單狀態

    public void parseOrder(String mqttMessageString) {
        Gson gson = new Gson();
        order = gson.fromJson(mqttMessageString, new TypeToken<OrderBean>(){}.getType());
        Toast.makeText(MainActivity.this, order.id, Toast.LENGTH_LONG).show();
        Log.d("orderBeanID", order.id);
    }

    public void refuseOrder(View v) {
        setPublish(order.id, "拒絕");
        toBeAcceptedText.setText("");
        Toast.makeText(MainActivity.this, "拒絕訂單", Toast.LENGTH_SHORT).show();
    }// 拒絕訂單

    public void acceptOrder(View v) {
        setPublish(order.id, "接受");
        toBeCompletedText.setText(toBeAcceptedText.getText());
        toBeAcceptedText.setText("");
        Toast.makeText(MainActivity.this, "接受訂單", Toast.LENGTH_SHORT).show();
    }// 接受訂單

    public void completeOrder(View v) {
        setPublish(order.id, "完成");
        Toast.makeText(MainActivity.this, "完成訂單", Toast.LENGTH_SHORT).show();
    }// 完成訂單

    public void clearOrder(View v) {
        setPublish(order.id, "結束");
        toBeCompletedText.setText("");
        Toast.makeText(MainActivity.this, "清除訂單", Toast.LENGTH_SHORT).show();
    }// 清除訂單
    */
}
