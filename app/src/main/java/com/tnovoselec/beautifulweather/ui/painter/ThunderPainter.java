package com.tnovoselec.beautifulweather.ui.painter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;

import com.tnovoselec.beautifulweather.ui.Cloud;

public class ThunderPainter implements IconPainter {

  int ctr = 0;
  int strokeColor;
  int bgColor;
  float thHeight;
  PathPoints[] leftPoints;
  boolean check;
  private Paint paintCloud, paintThunder;
  private int screenW;
  private int screenH;
  private float Y;
  private float X;
  private Path thFillPath;
  private Path thPath;

  private double count;
  Cloud cloud;


  @Override
  public void init(int strokeColor, int bgColor) {
    this.strokeColor = strokeColor;
    this.bgColor = bgColor;
    count = 0;

    thHeight = 0;
    thPath = new Path();
    thFillPath = new Path();

    paintCloud = new Paint();
    paintCloud.setColor(strokeColor);
    paintCloud.setStrokeWidth((screenW / 25));
    paintCloud.setAntiAlias(true);
    paintCloud.setStrokeCap(Paint.Cap.ROUND);
    paintCloud.setStrokeJoin(Paint.Join.ROUND);
    paintCloud.setStyle(Paint.Style.STROKE);
    paintCloud.setShadowLayer(0, 0, 0, Color.BLACK);

    paintThunder = new Paint();
    paintThunder.setColor(strokeColor);
    paintThunder.setStrokeWidth(10);
    paintThunder.setAntiAlias(true);
    paintThunder.setStrokeCap(Paint.Cap.ROUND);
    paintThunder.setStrokeJoin(Paint.Join.ROUND);
    paintThunder.setStyle(Paint.Style.STROKE);
    paintThunder.setShadowLayer(0, 0, 0, Color.BLACK);
  }

  @Override
  public void paint(Canvas canvas) {
    canvas.drawColor(bgColor);

    paintCloud.setStrokeWidth((float) (0.02083 * screenW));
    paintThunder.setStrokeWidth((float) (0.02083 * screenW));

    //incrementing counter for rotation
    count = (count + 3.5) % 360;

    PointF P2c1 = cloud.getP2c1(X, Y, screenW, count);

    //Setting up the height of thunder from the cloud
    if (thHeight == 0) {
      thHeight = P2c1.y;
    }
    float startHeight = thHeight - (thHeight * 0.1f);

    //Setting up X coordinates of thunder
    float path2StartX = X + (X * 0.04f);


    //Calculating coordinates of thunder

    thPath.moveTo(path2StartX, startHeight);
    thPath.lineTo(X - (X * 0.1f), startHeight + (startHeight * 0.2f)); //1
    thPath.lineTo(X + (X * 0.03f), startHeight + (startHeight * 0.15f));
    thPath.lineTo(X - (X * 0.08f), startHeight + (startHeight * 0.3f));

    leftPoints = getPoints(thPath);

    if (ctr <= 98) {

      if (!check) {
        thFillPath.moveTo(leftPoints[ctr].getX(), leftPoints[ctr].getY());
        thFillPath.lineTo(leftPoints[ctr + 1].getX(), leftPoints[ctr + 1].getY());
      } else {
        //Once filled, erasing the fill from top to bottom
        thFillPath.reset();
        thFillPath.moveTo(leftPoints[ctr].getX(), leftPoints[ctr].getY());
        for (int i = ctr + 1; i < leftPoints.length - 1; i++) {
          thFillPath.lineTo(leftPoints[i].getX(), leftPoints[i].getY());
        }
      }
      ctr += 2;
    } else {
      ctr = 0;
      check = !check;
    }

    canvas.drawPath(thFillPath, paintThunder);

    paintCloud.setColor(strokeColor);
    paintCloud.setStyle(Paint.Style.STROKE);
    canvas.drawPath(cloud.getCloud(X, Y, screenW, count), paintCloud);
  }

  @Override
  public void onSizeChanged(int w, int h, int oldw, int oldh) {
    screenW = w; //getting Screen Width
    screenH = h; // getting Screen Height

    // center point  of Screen
    X = screenW / 2;
    Y = (screenH / 2);

    cloud = new Cloud();
  }

  private PathPoints[] getPoints(Path path) {
    PathPoints[] pointArray = new PathPoints[100];
    PathMeasure pm = new PathMeasure(path, false);
    float length = pm.getLength();
    float distance = 0f;
    float speed = length / 100;
    int counter = 0;
    float[] aCoordinates = new float[2];

    while ((distance < length) && (counter < 100)) {
      // get point from the pathMoon
      pm.getPosTan(distance, aCoordinates, null);
      pointArray[counter] = new PathPoints(aCoordinates[0], aCoordinates[1]);
      counter++;
      distance = distance + speed;
    }

    return pointArray;
  }

  class PathPoints {

    float x, y;

    public PathPoints(float x, float y) {
      this.x = x;
      this.y = y;
    }

    public float getX() {
      return x;
    }

    public float getY() {
      return y;
    }

  }
}
