package com.bfs.bfshostmqttprotocol;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by Joe on 2017/11/24.
 */

public class HostOrderContentFragment extends Fragment {

    private View viewContent;
    //private int mType = 0;
    private String mTitle;

    static final String MQTTHOST = "";
    static final String USERNAME = "";
    static final String PASSWORD = "";
    private String subscribeTopic = "order";// 想訂閱的看板
    private MqttAndroidClient client;

    private OrderBean order;
    private String mqttMessageString;
    private String id = ""; //已抓取的顧客id
    private String orderStatus = "";// 訂單狀態
    private int orderSerialNumber = 0;// 訂單序號

    private TextView toBeAcceptedText;
    private TextView toBeCompletedText;
    private Button refuseBtn;
    private Button acceptBtn;
    private Button completeBtn;
    private Button clearBtn;


    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 布局文件中只有一個居中的TextView
        viewContent = inflater.inflate(R.layout.fragment_host_order_content, container, false);
        //TextView textView = (TextView) viewContent.findViewById(R.id.tv_content);
        //textView.setText(this.mTitle);
        initView();
        initClient();

        return viewContent;
    }

    // 視圖的監聽器
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == refuseBtn.getId()) {
                // 拒絕訂單
                publishStatus(order.id, "拒絕", -1);
                toBeAcceptedText.setText("");
                Toast.makeText(getContext(), "拒絕訂單", Toast.LENGTH_SHORT).show();
            }
            else if (v.getId() == acceptBtn.getId()) {
                // 接受訂單
                publishStatus(order.id, "接受", orderSerialNumber);
                //orderSerialNumber++;
                toBeCompletedText.setText(toBeAcceptedText.getText());
                toBeAcceptedText.setText("");
                Toast.makeText(getContext(), "接受訂單", Toast.LENGTH_SHORT).show();
            }
            else if (v.getId() == completeBtn.getId()) {
                // 完成訂單
                publishStatus(order.id, "完成", orderSerialNumber);
                Toast.makeText(getContext(), "完成訂單", Toast.LENGTH_SHORT).show();
            }
            else if (v.getId() == clearBtn.getId()) {
                // 結束訂單
                publishStatus(order.id, "結束", orderSerialNumber);
                orderSerialNumber++;
                toBeCompletedText.setText("");
                Toast.makeText(getContext(), "清除訂單", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initView() {
        toBeAcceptedText = (TextView) viewContent.findViewById(R.id.toBeAcceptedText);
        toBeCompletedText = (TextView) viewContent.findViewById(R.id.toBeCompletedText);
        refuseBtn = (Button) viewContent.findViewById(R.id.refuseBtn);
        acceptBtn = (Button) viewContent.findViewById(R.id.acceptBtn);
        completeBtn = (Button) viewContent.findViewById(R.id.completeBtn);
        clearBtn = (Button) viewContent.findViewById(R.id.clearBtn);

        refuseBtn.setOnClickListener(onClickListener);
        acceptBtn.setOnClickListener(onClickListener);
        completeBtn.setOnClickListener(onClickListener);
        clearBtn.setOnClickListener(onClickListener);
    }// 初始化視圖

    private void initClient() {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(getContext(), MQTTHOST, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getContext(),"連線成功 !",Toast.LENGTH_LONG).show();
                    subscribeOrder();
                }// 連線成功 欲執行的程式

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getContext(),"連線失敗 !",Toast.LENGTH_LONG).show();
                }// Something went wrong e.g. connection timeout or firewall problems
            });
        } catch (MqttException e) {e.printStackTrace();}

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable throwable) { Toast.makeText(getContext()," connection Lost!",Toast.LENGTH_LONG).show();}

            @Override
            public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                mqttMessageString = new String(mqttMessage.getPayload());
                //toBeAcceptedText.setText(mqttMessageString);
                parseOrder(mqttMessageString);
                String orderText = "時間 : " + order.time + "\n" +
                        "總計 : " + order.sum_price + " 元\n" +
                        "餐點 : ";
                for (int i = 0; i < order.order.size(); i++) {
                    orderText += order.order.get(i).getName() + "*" + order.order.get(i).getQuantity() + "\n";
                }
                toBeAcceptedText.setText(orderText);
            }// 收到訂閱之看板的回傳訊息

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {}
        });
    }// 初始化連線

    public void publishStatus(String id, String orderStatus, int orderSerialNumber){
        String topic = id;
        String message = orderRespondToJson(id, orderStatus, orderSerialNumber);;
        try {
            client.publish(topic, message.getBytes(),0,false);
        } catch (MqttException e) {e.printStackTrace();}
    }// 發佈看板、訊息

    private void subscribeOrder(){
        try{
            client.subscribe(subscribeTopic,0);
        }catch(MqttException e){e.printStackTrace();}
    }// 訂閱看板

    public static String orderRespondToJson(String id, String orderStatus, int orderSerialNumber) {
        OrderRespondBean orderRespondBean = new OrderRespondBean();
        orderRespondBean.setId(id);
        orderRespondBean.setOrderStatus(orderStatus);
        orderRespondBean.setOrderSerialNumber(orderSerialNumber);
        Gson gson = new Gson();
        String json = gson.toJson(orderRespondBean);
        Log.d("orderRespondJson", json);
        return json;
    }// 用Gson包要回傳的訂單狀態

    public void parseOrder(String mqttMessageString) {
        Gson gson = new Gson();
        order = gson.fromJson(mqttMessageString, new TypeToken<OrderBean>(){}.getType());
        //Toast.makeText(getContext(), order.id, Toast.LENGTH_LONG).show();
        Log.d("orderBeanID", order.id);
    }

}
