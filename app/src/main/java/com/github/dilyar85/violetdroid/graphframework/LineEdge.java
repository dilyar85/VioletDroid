package com.github.dilyar85.violetdroid.graphframework;

import android.graphics.Canvas;

import java.awt.*;
import java.awt.geom.*;

/**
   An edge that is shaped like a straight line.
*/
public class LineEdge extends AbstractEdge
{
   public void draw(Canvas canvas)
   {
      canvas.draw(getConnectionPoints());
   }

   public boolean contains(Point2D aPoint)
   {
      final double MAX_DIST = 2;
      return getConnectionPoints().ptSegDist(aPoint) 
         < MAX_DIST;
   }
}
