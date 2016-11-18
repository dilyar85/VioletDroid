package com.github.dilyar85.violetdroid.graphframework;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

/**
   A class that supplies convenience implementations for 
   a number of methods in the Edge interface type.
*/
public abstract class AbstractEdge implements Edge
{  
   public Object clone()
   {
      try
      {
         return super.clone();
      }
      catch (CloneNotSupportedException exception)
      {
         return null;
      }
   }

   public void connect(Node s, Node e)
   {  
      start = s;
      end = e;
   }

   public Node getStart()
   {
      return start;
   }

   public Node getEnd()
   {
      return end;
   }

   public Rect getBounds(Canvas canvas)
   {
      Line2D conn = getConnectionPoints();      
      Rectangle2D r = new Rectangle2D.Double();
      r.setFrameFromDiagonal(conn.getX1(), conn.getY1(),
            conn.getX2(), conn.getY2());
      return r;
   }

   public Path getConnectionPoints()
   {
      Path p = new Path();
      p.op
      Rectangle2D startBounds = start.getBounds();
      Rectangle2D endBounds = end.getBounds();
      Point2D startCenter = new Point2D.Double(
            startBounds.getCenterX(), startBounds.getCenterY());
      Point2D endCenter = new Point2D.Double(
            endBounds.getCenterX(), endBounds.getCenterY());
      return new Line2D.Double(
            start.getConnectionPoint(endCenter),
            end.getConnectionPoint(startCenter));
   }

   private Node start;
   private Node end;
}
