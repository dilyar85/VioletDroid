package com.github.dilyar85.violetdroid.graphframework;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.io.*;

/**
   A node in a graph.
*/
public interface Node extends Serializable, Cloneable
{
   /**
      Draw the node.
      @param canvas the graphics context
   */
   void draw(Canvas canvas);

   /**
      Translates the node by a given amount.
      @param dx the amount to translate in the x-direction
      @param dy the amount to translate in the y-direction
   */
   void translate(double dx, double dy);

   /**
      Tests whether the node contains a point.
      @param aPoint the point to test
      @return true if this node contains aPoint
   */
   boolean contains(Point aPoint);

   /**
      Get the best connection point to connect this node 
      with another node. This should be a point on the boundary
      of the shape of this node.
      @param aPoint an exterior point that is to be joined
      with this node
      @return the recommended connection point
   */
   Point getConnectionPoint(Point aPoint);

   /**
      Get the bounding rectangle of the shape of this node
      @return the bounding rectangle
   */
   Rect getBounds();

   Object clone();
}
