package eu.hansolo.fx.monitor;

import eu.hansolo.fx.monitor.tools.ColorTheme;
import eu.hansolo.fx.monitor.tools.FixedSizeQueue;
import eu.hansolo.fx.monitor.tools.Helper;
import eu.hansolo.fx.monitor.tools.Point;
import eu.hansolo.fx.monitor.tools.Timespan;
import javafx.animation.AnimationTimer;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * User: hansolo
 * Date: 20.12.20
 * Time: 04:54
 */
@DefaultProperty("children")
public class Monitor extends Region {
    public  static final int                   DEFAULT_TIMESPAN           = Timespan.FIVE_SECONDS.getSeconds();
    public  static final double                DEFAULT_SPEED              = 1.6732;
    public  static final double                DEFAULT_SPEED_WIDTH_FACTOR = 1.0;
    public  static final double                DEFAULT_SPEED_FACTOR       = 1.0;
    public  static final double                DEFAULT_SCALE_FACTOR_Y     = 1.0;
    public  static final double                DEFAULT_LINE_WIDTH         = 3.0;
    public  static final double                DEFAULT_DOT_SIZE           = 4.0;
    public  static final int                   DEFAULT_NO_OF_SEGMENTS     = 75;
    public  static final int                   MAX_NO_OF_SEGMENTS         = 250;
    private static final double                PREFERRED_WIDTH            = 500;
    private static final double                PREFERRED_HEIGHT           = 200;
    private static final double                MINIMUM_WIDTH              = 50;
    private static final double                MINIMUM_HEIGHT             = 50;
    private static final double                MAXIMUM_WIDTH              = 4096;
    private static final double                MAXIMUM_HEIGHT             = 4096;
    private static final double[]              DASH_ARRAY                 = { 5, 5 };
    private static final Color                 DARK_NOISE_COLOR           = Color.rgb(100, 100, 100, 0.10);
    private static final Color                 BRIGHT_NOISE_COLOR         = Color.rgb(200, 200, 200, 0.05);
    private              double                width;
    private              double                height;
    private              Canvas                bkgCanvas;
    private              GraphicsContext       bkgCtx;
    private              Canvas                lineCanvas;
    private              GraphicsContext       lineCtx;
    private              Canvas                dotCanvas;
    private              GraphicsContext       dotCtx;
    private              Pane                  pane;
    private              int                   timespan;
    private              double                speed;
    private              double                speedWidthFactor;
    private              double                speedFactor;
    private              double                currentSpeed;
    private              double                rasterWidth;
    private              double                rasterSubWidth;
    private              double                lineWidth;
    private              int                   noOfSegments;
    private              int                   index;
    private              List<Number>          data;
    private              Number                dataPoint;
    private              Number                lastDataPoint;
    private              Color                 backgroundColor;
    private              Color                 rasterColor;
    private              Color                 textColor;
    private              Color                 lineColor;
    private              ImageView             crystalOverlay;
    private              Image                 crystalImage;
    private              boolean               rasterVisible;
    private              boolean               textVisible;
    private              boolean               glowVisible;
    private              boolean               lineFading;
    private              boolean               crystalOverlayVisible;
    private              Font                  font;
    private              double                centerY;
    private              double                x;
    private              double                y;
    private              double                delta;
    private              double                scaleFactorY;
    private              double                red;
    private              double                green;
    private              double                blue;
    private              double                dotSize;
    private              double                dotRadius;
    private              RadialGradient        dotGradient;
    private              DropShadow            glow;
    private              boolean               running;
    private              AnimationTimer        timer;
    private              FixedSizeQueue<Point> queue;
    private              double[]              lastP;



    // ******************** Constructors **************************************
    public Monitor() {
        timespan              = DEFAULT_TIMESPAN;
        speed                 = DEFAULT_SPEED * DEFAULT_TIMESPAN / timespan;
        speedWidthFactor      = DEFAULT_SPEED_WIDTH_FACTOR;
        speedFactor           = DEFAULT_SPEED_FACTOR;
        currentSpeed          = speed * speedWidthFactor;
        rasterWidth           = PREFERRED_WIDTH / 5;
        rasterSubWidth        = rasterWidth / 5;
        lineWidth             = DEFAULT_LINE_WIDTH;
        noOfSegments          = DEFAULT_NO_OF_SEGMENTS;
        index                 = 0;
        data                  = new ArrayList<>();
        dataPoint             = 0;
        lastDataPoint         = 0;
        backgroundColor       = Color.BLACK;
        lineColor             = Color.CYAN;
        crystalImage          = Helper.createNoiseImage(PREFERRED_WIDTH, PREFERRED_HEIGHT, DARK_NOISE_COLOR, BRIGHT_NOISE_COLOR, 8);
        crystalOverlay        = new ImageView(crystalImage);
        rasterVisible         = true;
        textVisible           = true;
        glowVisible           = true;
        lineFading            = true;
        crystalOverlayVisible = true;
        font                  = Font.font(10);
        red                   = lineColor.getRed();
        green                 = lineColor.getGreen();
        blue                  = lineColor.getBlue();
        rasterColor           = Color.color(red, green, blue, 0.35);
        textColor             = Color.color(red, green, blue, 0.75);
        dotSize               = DEFAULT_DOT_SIZE;
        dotRadius             = dotSize * 0.5;
        dotGradient           = new RadialGradient(0, 0, x, y, 2.5, false, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(0.2, Color.WHITE), new Stop(1.0, lineColor));
        glow                  = new DropShadow(BlurType.TWO_PASS_BOX, lineColor, 10, 0.8, 0, 0);
        centerY               = PREFERRED_HEIGHT / 2.0;
        x                     = 0;
        y                     = 0;
        scaleFactorY          = DEFAULT_SCALE_FACTOR_Y;
        queue                 = new FixedSizeQueue<>((int) (noOfSegments / speed));
        lastP                 = new double[] { 0, centerY };
        running               = false;
        timer                 = new AnimationTimer() {
            @Override public void handle(final long now) {
                drawLine();
                drawDot();
            }
        };

        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 || Double.compare(getWidth(), 0.0) <= 0 ||
            Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        bkgCanvas = new Canvas(getPrefWidth(), getPrefHeight());
        bkgCtx    = bkgCanvas.getGraphicsContext2D();
        bkgCtx.setLineJoin(StrokeLineJoin.ROUND);
        bkgCtx.setLineCap(StrokeLineCap.BUTT);
        bkgCtx.setLineWidth(1);
        bkgCtx.setTextAlign(TextAlignment.RIGHT);

        lineCanvas = new Canvas(getPrefWidth(), getPrefHeight());
        lineCtx    = lineCanvas.getGraphicsContext2D();
        lineCtx.setLineJoin(StrokeLineJoin.ROUND);
        lineCtx.setLineCap(StrokeLineCap.BUTT);
        lineCtx.setLineWidth(lineWidth);

        dotCanvas  = new Canvas(getPrefWidth(), getPrefHeight());
        dotCtx     = dotCanvas.getGraphicsContext2D();

        Helper.enableNode(crystalOverlay, crystalOverlayVisible);

        pane = new Pane(bkgCanvas, lineCanvas, dotCanvas, crystalOverlay);

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    // ******************** Methods *******************************************
    @Override protected double computeMinWidth(final double HEIGHT) { return MINIMUM_WIDTH; }
    @Override protected double computeMinHeight(final double WIDTH) { return MINIMUM_HEIGHT; }
    @Override protected double computePrefWidth(final double HEIGHT) { return super.computePrefWidth(HEIGHT); }
    @Override protected double computePrefHeight(final double WIDTH) { return super.computePrefHeight(WIDTH); }
    @Override protected double computeMaxWidth(final double HEIGHT) { return MAXIMUM_WIDTH; }
    @Override protected double computeMaxHeight(final double WIDTH) { return MAXIMUM_HEIGHT; }

    @Override public ObservableList<Node> getChildren() { return super.getChildren(); }

    public double getLineWidth() { return lineWidth; }
    public void setLineWidth(final double lineWidth) {
        this.lineWidth = Helper.clamp(0.5, 5, lineWidth);
    }

    public double getDotSize() { return dotSize; }
    public void setDotSize(final double dotSize) {
        this.dotSize = Helper.clamp(1, 5, dotSize);
        this.dotRadius = this.dotSize * 0.5;
    }

    public Color getLineColor() { return lineColor; }
    public void setLineColor(final Color lineColor) {
        this.lineColor = lineColor;
        red            = lineColor.getRed();
        green          = lineColor.getGreen();
        blue           = lineColor.getBlue();
        dotGradient    = new RadialGradient(0, 0, x, y, 2.5, false, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(0.2, Color.WHITE), new Stop(1.0, lineColor));
        glow           = new DropShadow(BlurType.TWO_PASS_BOX, lineColor, 10, 0.8, 0, 0);
        redraw();
    }

    public Color getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(final Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        redraw();
    }

    public Color getRasterColor() { return rasterColor; }
    public void setRasterColor(final Color rasterColor) {
        this.rasterColor = rasterColor;
        redraw();
    }

    public void setRasterColorToLineColor() {
        rasterColor = Color.color(red, green, blue, 0.35);
        redraw();
    }

    public Color getTextColor() { return textColor; }
    public void setTextColor(final Color textColor) {
        this.textColor = textColor;
        redraw();
    }

    public void setTextColorToLineColor() {
        textColor = Color.color(red, green, blue, 0.75);
        redraw();
    }

    public boolean isRasterVisible() { return rasterVisible; }
    public void setRasterVisible(final boolean rasterVisible) {
        this.rasterVisible = rasterVisible;
        redraw();
    }

    public boolean isTextVisible() { return textVisible; }
    public void setTextVisible(final boolean textVisible) {
        this.textVisible = textVisible;
        redraw();
    }

    public boolean isGlowVisible() { return glowVisible; }
    public void setGlowVisible(final boolean glowVisible) {
        this.glowVisible = glowVisible;
        redraw();
    }

    public boolean isLineFading() { return lineFading; }
    public void setLineFading(final boolean lineFading) {
        this.lineFading = lineFading;
        redraw();
    }

    public boolean isCrystalOverlayVisible() { return crystalOverlayVisible; }
    public void setCrystalOverlayVisible(final boolean crystalOverlayVisible) {
        this.crystalOverlayVisible = crystalOverlayVisible;
        Helper.enableNode(crystalOverlay, crystalOverlayVisible);
        redraw();
    }

    public void setColorTheme(final ColorTheme colorTheme) {
        this.backgroundColor = colorTheme.getBackgroundColor();
        this.lineColor       = colorTheme.getLineColor();
        this.rasterColor     = colorTheme.getRasterColor();
        this.textColor       = colorTheme.getTextColor();
        this.red             = lineColor.getRed();
        this.green           = lineColor.getGreen();
        this.blue            = lineColor.getBlue();
        this.dotGradient     = new RadialGradient(0, 0, x, y, 2.5, false, CycleMethod.NO_CYCLE, new Stop(0, Color.WHITE), new Stop(0.2, Color.WHITE), new Stop(1.0, lineColor));
        this.glow            = new DropShadow(BlurType.TWO_PASS_BOX, lineColor, 10, 0.8, 0, 0);
        redraw();
    }

    public int getTimespan() { return timespan; }
    public void setTimespan(final Timespan timespan) {
        if (isRunning()) {
            timer.stop();
            this.timespan     = timespan.getSeconds();
            this.speed        = DEFAULT_SPEED * DEFAULT_TIMESPAN / this.timespan;
            this.currentSpeed = speed * speedWidthFactor * speedFactor;
            resize();
            timer.start();
        } else {
            this.timespan     = timespan.getSeconds();
            this.speed        = DEFAULT_SPEED * DEFAULT_TIMESPAN / this.timespan;
            this.currentSpeed = speed * speedWidthFactor * speedFactor;
            resize();
        }
    }

    public void setSpeedFactor(final double speedFactor) {
        this.speedFactor = Helper.clamp(0.1, 10, speedFactor);
        if (running) {
            timer.stop();
            currentSpeed = speed * speedWidthFactor * speedFactor;
            timer.start();
        } else {
            currentSpeed = speed * speedWidthFactor * speedFactor;
        }
    }

    public int getNoOfSegments() { return noOfSegments; }
    public void setNoOfSegments(final int noOfSegments) {
        if (noOfSegments < 1) {
            this.noOfSegments = 1;
        } else if (noOfSegments > MAX_NO_OF_SEGMENTS || (width > 0 && noOfSegments > width)) {
            if (width > 0 && noOfSegments > width) {
                this.noOfSegments = (int) width;
            } else {
                this.noOfSegments = MAX_NO_OF_SEGMENTS;
            }
        } else {
            this.noOfSegments = noOfSegments;
        }
        adjustQueueSize();
    }

    public List<Number> getData() { return data; }
    public void setData(final List<Number> data) {
        if (null == data) { throw new IllegalArgumentException("Data cannot be null"); }
        double maxValue = Math.abs(data.stream().mapToDouble(Number::doubleValue).max().getAsDouble());
        double minValue = Math.abs(data.stream().mapToDouble(Number::doubleValue).min().getAsDouble());
        delta = Math.max(minValue, maxValue);
        if (delta > height) {
            scaleFactorY = height * 0.5 / delta * 0.75;
        }
        queue.clear();
        if (running) {
            stop();
            this.data  = data;
            this.index = 0;
            start();
        } else {
            this.data  = data;
            this.index = 0;
        }
    }

    public void addDataPoint(final Number dataPoint) {
        if (null == dataPoint) { return; }
        this.dataPoint = dataPoint;
    }

    public double getScaleFactorY() { return scaleFactorY; }
    public void setScaleFactorY(final double scaleFactorY) {
        this.scaleFactorY = Helper.clamp(0.05, 10.0, scaleFactorY);
    }

    public void start() {
        timer.start();
        running = true;
    }
    public void stop() {
        timer.stop();
        running = false;
    }
    public boolean isRunning() { return running; }

    private void adjustQueueSize() {
        int queueSize = (int) ((double) noOfSegments / speed);
        if (queueSize < 1) { queueSize = 1; }
        if (queueSize > MAX_NO_OF_SEGMENTS) { queueSize = MAX_NO_OF_SEGMENTS; }
        if (running) {
            timer.stop();
            this.queue = new FixedSizeQueue<>(queueSize);
            timer.start();
        } else {
            this.queue = new FixedSizeQueue<>(queueSize);
        }
    }


    // ******************** Resizing ******************************************
    private void resize() {
        width   = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height  = getHeight() - getInsets().getTop() - getInsets().getBottom();
        centerY = height / 2.0;

        if (width > 0 && height > 0) {
            pane.setMaxSize(width, height);
            pane.setPrefSize(width, height);
            pane.relocate((getWidth() - width) * 0.5, (getHeight() - height) * 0.5);

            bkgCanvas.setWidth(width);
            bkgCanvas.setHeight(height);

            lineCanvas.setWidth(width);
            lineCanvas.setHeight(height);

            dotCanvas.setWidth(width);
            dotCanvas.setHeight(height);

            if (crystalOverlay.isVisible()) {
                crystalOverlay.setCache(false);
                crystalOverlay.setImage(Helper.createNoiseImage(width, height, DARK_NOISE_COLOR, BRIGHT_NOISE_COLOR, 8));
                crystalOverlay.setCache(true);
            }

            if (!data.isEmpty()) { scaleFactorY = height * 0.5 / delta * 0.75; }

            font             = Font.font(height * 0.05);
            rasterWidth      = width / timespan;
            rasterSubWidth   = rasterWidth / 5.0;
            speedWidthFactor = width / PREFERRED_WIDTH;
            currentSpeed     = speed * speedWidthFactor * speedFactor;
            redraw();
        }
    }


    // ******************** Drawing ******************************************
    private void drawBackground() {
        bkgCtx.clearRect(0, 0, width, height);
        bkgCtx.setFill(backgroundColor);
        bkgCtx.fillRect(0, 0, width, height);

        // Draw raster
        if (rasterVisible) {
            bkgCtx.setStroke(rasterColor);
            bkgCtx.setFill(textColor);
            bkgCtx.setLineDashes(null);
            bkgCtx.strokeLine(0, centerY, width, centerY);
            bkgCtx.setFont(font);
            bkgCtx.setLineDashes(DASH_ARRAY);
            double distanceYFromCenter = font.getSize() * 1.0;
            double speedRatio          = DEFAULT_SPEED / speed;
            double timespanRatio       = (double) timespan / (double) DEFAULT_TIMESPAN;
            double textValueFactor     = speedRatio / timespanRatio / speedFactor;
            for (int i = 0; i < timespan; i++) {
                bkgCtx.strokeLine(i * rasterWidth, 0, i * rasterWidth, height);
                bkgCtx.save();
                bkgCtx.setLineDashes(null);
                for (int j = 1 ; j < 5 ; j++) {
                    bkgCtx.strokeLine(i * rasterWidth + j * rasterSubWidth, centerY - 3, i * rasterWidth + j * rasterSubWidth, centerY + 3);
                }
                bkgCtx.restore();
                if (textVisible) {
                    bkgCtx.fillText(String.format(Locale.US, "%.1f", (i + 1) * textValueFactor), (i + 1) * rasterWidth - 5, centerY + distanceYFromCenter);
                }
            }
        }
    }

    private void drawLine() {
        if (!running) { return; }
        lineCtx.setLineWidth(lineWidth);

        // Add data to queue
        x += currentSpeed;

        if (data.isEmpty()) {
            if (null != dataPoint) {
                lastDataPoint = dataPoint;
                y = dataPoint.doubleValue() * scaleFactorY;
                queue.add(new Point(x, centerY + y));
                dataPoint = null;
            } else {
                y = lastDataPoint.doubleValue() * scaleFactorY;
                queue.add(new Point(x, centerY + y));
            }
        } else {
            y = -data.get(++index >= data.size() ? index = 0 : index++).doubleValue() * scaleFactorY;
            queue.add(new Point(x, centerY + y));
        }

        // Draw line elements
        final Point[] points = queue.toArray(new Point[0]);
        final long    length = points.length;
        if (lineFading) {
            lineCtx.clearRect(0, 0, width, height);
            double opacityFactor = 1.0 / (length - 1);
            for (int i = 0; i < length - 1; i += 1) {
                if (points[i].x < points[i + 1].x) {
                    lineCtx.setStroke(Color.color(red, green, blue, i * opacityFactor));
                    lineCtx.strokeLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
                }
            }
        } else {
            lineCtx.setLineWidth(lineWidth);
            lineCtx.setStroke(lineColor);
            double pY = centerY + y;
            if (x > lastP[0]) {
                lineCtx.strokeLine(lastP[0], lastP[1], x, pY);
            } else {
                lineCtx.strokeLine(x, pY, x, pY);
            }
            lineCtx.clearRect(x + 1, 0, 20, height);
            lastP[0] = x;
            lastP[1] = pY;
        }
        if (x > width) { x = (-currentSpeed); }
    }

    private void drawDot() {
        if (!running) { return; }
        dotCtx.clearRect(0, 0, width, height);
        dotCtx.save();
        dotCtx.setEffect(glowVisible ? glow : null);
        dotCtx.setFill(dotGradient);
        dotCtx.fillOval(x - dotRadius, centerY + y - dotRadius, dotSize, dotSize);
        dotCtx.restore();
    }

    private void redraw() {
        drawBackground();
        drawLine();
        drawDot();
    }
}
