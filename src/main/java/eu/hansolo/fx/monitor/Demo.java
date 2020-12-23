 /*
  * Copyright (c) 2020 by Gerrit Grunwald
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */

 package eu.hansolo.fx.monitor;

 import eu.hansolo.fx.monitor.tools.EcgData;
 import eu.hansolo.fx.monitor.tools.Theme;
 import eu.hansolo.fx.monitor.tools.Timespan;
 import javafx.application.Application;
 import javafx.application.Platform;
 import javafx.scene.paint.Color;
 import javafx.stage.Stage;
 import javafx.scene.layout.StackPane;
 import javafx.scene.Scene;

 import java.util.Random;
 import java.util.concurrent.Executors;
 import java.util.concurrent.ScheduledExecutorService;
 import java.util.concurrent.TimeUnit;


 /**
  * User: hansolo
  * Date: 18.12.20
  * Time: 09:54
  */
 public class Demo extends Application {
     private static final Random                   RND = new Random();
     private              ScheduledExecutorService executorService;
     private              Monitor                  monitor;
     private              double                   t;
     private              double                   step;

     @Override public void init() {
         monitor = MonitorBuilder.create()
                                 //.lineColor(Color.BLUE)
                                 //.backgroundColor(Color.rgb(51, 51, 51))
                                 //.rasterColor(Color.RED)
                                 //.textColor(Color.YELLOW)
                                 .lineWidth(2)
                                 .dotSize(4)
                                 .rasterVisible(true)
                                 .textVisible(true)
                                 .glowVisible(false)
                                 .crystalOverlayVisible(true)
                                 .lineFading(false)
                                 .timespan(Timespan.FIVE_SECONDS)
                                 .colorTheme(Theme.GREEN_BLACK)
                                 .speedFactor(1)
                                 .noOfSegments(150)
                                 .scaleFactorY(0.4)
                                 //.data(EcgData.ECG_DATA)
                                 .build();

         //monitor.setRasterColorToLineColor();
         //monitor.setTextColorToLineColor();

         monitor.setOnMouseClicked(e -> {
            if (monitor.isRunning()) {
                monitor.stop();
            } else {
                monitor.start();
            }
        });

         t    = 0;
         step = (Math.PI * 2.0) / 100;
         Runnable sinTask = () -> {
             Platform.runLater(() -> {
                 monitor.addDataPoint(Math.sin(t) * 200);
             });
             t += step;
             if (t >= 2 * Math.PI) { t = 0; }
         };
         executorService = Executors.newScheduledThreadPool(1);
         executorService.scheduleAtFixedRate(sinTask, 0, 5, TimeUnit.MILLISECONDS);
     }

     @Override public void start(Stage stage) {
         StackPane pane = new StackPane(monitor);

         Scene scene = new Scene(pane);

         scene.setOnKeyPressed(e -> monitor.addDataPoint(RND.nextInt(200) - 100));
         scene.setOnKeyReleased(e -> monitor.addDataPoint(0));

         stage.setTitle("JavaFX Monitor");
         stage.setScene(scene);
         stage.show();

     }

     @Override public void stop() {
         System.exit(0);
     }

     public static void main(String[] args) {
         launch(args);
     }
 }
