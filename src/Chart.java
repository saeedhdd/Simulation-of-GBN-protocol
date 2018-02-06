
        import javafx.application.Application;
        import javafx.scene.Scene;
        import javafx.scene.chart.LineChart;
        import javafx.scene.chart.NumberAxis;
        import javafx.scene.chart.XYChart;
        import javafx.stage.Stage;

        import java.util.ArrayList;
        import java.util.HashMap;


        public class Chart extends Application {
    static ArrayList<HashMap<Integer, Double>> datas = new ArrayList<>();
    static String[] fff = {
            "1,5",
            "4,8",
            "1,8",
            "1,10"
    };
    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("time");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Window Size Monitoring");
        //defining a series

        for (int i = 0; i < datas.size(); i++) {
            HashMap<Integer, Double> a = datas.get(i);
            XYChart.Series series = new XYChart.Series();
            series.setName(fff[i]);
            //populating the series with data
            for (int x :
                    a.keySet()) {
                series.getData().add(new XYChart.Data(((double)x)/10, a.get(x)));

            }
            lineChart.getData().add(series);
        }


        Scene scene  = new Scene(lineChart,1024,960);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}