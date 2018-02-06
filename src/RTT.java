import cern.jet.random.Poisson;
import cern.jet.random.engine.DRand;
import cern.jet.random.engine.RandomEngine;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/**
 * Created by hd on 2017/7/7 AD.
 */
public class RTT {


     int getRTT(int t){
        Poisson poisson = new Poisson(3*dropProbability(t), engine);
        int poissonObs = 2*poisson.nextInt()+2;

        return poissonObs;
    }
     int getTimeout(){
        EstimatedRTT =(1-alpha)*EstimatedRTT + sampleRTT*alpha;
        DevRTT = ((1-beta)*DevRTT + Math.abs(sampleRTT-EstimatedRTT)*beta);
        return (int)(EstimatedRTT+ DevRTT*4);
    }
     boolean isSent(int t1){
        int i = new Random().nextInt(100);
        return i>=dropProbability(t1)*100 ;
    }

     double dropProbability(int time){
        double t = ((double)time )/10;
        double n = 0.7*t*Math.exp(-0.3*t);
        n = t - 15 <= 0 ? n : n+ 0.7*(t-15)*(Math.exp(-0.3*(t-15)));
        return n;
    }



    public static void main(String[] args) {
        RTT[] rtt1 = {
                new RTT(1, 5),
                 new RTT(4, 8),
                 new RTT(1, 8),
                new RTT(1, 10),
        };
        for (RTT rt:rtt1) {
            rt.calc();
        }

        Chart.main(args);
    }

    public void calc(){
        int time= 0;
        System.out.println(currentWS + " time: "+ time);
        HashMap<Integer,Double > datas = new HashMap<>();
        for (int i = 0; i <500 ; i++) {
            datas.put(i,dropProbability(i));
        }
        Chart.datas.add(datas);
//        while (time<500){
//            int RTT = getRTT(time);
//            int timeOut = getTimeout();
//            if (isSent(time) && timeOut>= RTT){
//                time += RTT ;
//                setPtilda(0);
//            }else {
//                System.out.println("salam");
//                time += timeOut;
//                setPtilda(1);
//            }
//            resetW(time);
//            sampleRTT = RTT;
//        }
//            Chart.datas.add(datas);
    }

    private  void setPtilda(int isRecieved) {
        prevPtilda = currentPtilda;
        currentPtilda = prevPtilda*alphaP+ (1-alphaP)*isRecieved;
    }

     RandomEngine engine = new DRand();


      int MAXWS = 8;

    public RTT( int MINWS, int MAXWS) {
        this.MAXWS = MAXWS;
        this.MINWS = MINWS;
    }

    int MINWS= 4;
     final double alphaP = 0.4;
     final double alpha = 0.25;
     final double beta = 0.125;
     int currentWS = 1 ;
     double prevPtilda = 0;
     double currentPtilda = 0;
     int sampleRTT = new Random().nextInt(2)+1;
     double EstimatedRTT = sampleRTT;
     double DevRTT = 0;
     HashMap<Integer, Integer> datas= new HashMap<>();
    private  void resetW(int time){
        if (currentPtilda-prevPtilda <= 0){
            if (currentWS==1) currentWS =2;
            else currentWS = (int) Math.min(currentWS * 1.5, MAXWS);
        }else {
            currentWS = (int) Math.max(currentWS * 0.6777, MINWS);
        }
        datas.put(time, currentWS);
        System.out.println(currentWS);
    }


}
