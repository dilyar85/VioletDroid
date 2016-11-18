package com.github.dilyar85.violetdroid.graphframework;

import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

import java.io.*;

/**
   An edge in a graph.
*/
public interface Edge extends Serializable, Cloneable
{
   /**
      Draw the edge.
      @param canvas the graphics context
   */
   void draw(Canvas canvas);

   /**
      Tests whether the edge contains a point.
      @param aPoint the point to test
      @return true if this edge contains aPoint
   */
   boolean contains(Point aPoint);

   /**
      Connects this edge to two nodes.
      @param aStart the starting node
      @param anEnd the ending node
   */
   void connect(Node aStart, Node anEnd);

   /**
      Gets the starting node.
      @return the starting node
   */
   Node getStart();

   /**
      Gets the ending node.
      @return the ending node
   */
   Node getEnd();

//   /**
//      Gets the points at which this edge is connected to
//      its nodes.
//      @return a line joining the two connection points
//   */
//   Line getConnectionPoints();

   /**
      Gets the smallest rectangle that bounds this edge.
      The bounding rectangle contains all labels.
      @return the bounding rectangle
   */
   Rect getBounds(Canvas canvas);

   Object clone();
}

