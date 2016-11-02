package com.github.dilyar85.violetdroid;

import android.content.ClipDescription;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

/**
 * Created by joyyan on 11/2/16.
 */

public class ShapeDragListener implements View.OnDragListener{



    @Override
    public boolean onDrag(View v, DragEvent event) {

        // Defines a variable to store the action type for the incoming event
        final int action = event.getAction();

        // Handles each of the expected events
        switch(action) {

            case DragEvent.ACTION_DRAG_STARTED:

                // Determines if this View can accept the dragged data
                if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                    // As an example of what your application might do,
                    // applies a blue color tint to the View to indicate that it can accept
                    // data.
                    //   v.setColorFilter(Color.BLUE);

                    // Invalidate the view to force a redraw in the new tint
                    v.setBackgroundColor(0xFF00FF00);
                    v.invalidate();

                    // returns true to indicate that the View can accept the dragged data.
                    return true;

                }

                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
                return false;

            case DragEvent.ACTION_DRAG_ENTERED:

                // Applies a green tint to the View. Return true; the return value is ignored.

                //   v.setColorFilter(Color.GREEN);

                // Invalidate the view to force a redraw in the new tint
                v.invalidate();

                return true;

            case DragEvent.ACTION_DRAG_LOCATION:

                // Ignore the event
                return true;

            case DragEvent.ACTION_DRAG_EXITED:

                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                //   v.setColorFilter(Color.BLUE);

                // Invalidate the view to force a redraw in the new tint
                v.invalidate();

                return true;

            case DragEvent.ACTION_DROP:

                // Gets the item containing the dragged data
                // ClipData.Item item = event.getClipData().getItemAt(0);
           //     Toast.makeText(v, "Good", Toast.LENGTH_LONG);

                v.invalidate();
                return true;

            case DragEvent.ACTION_DRAG_ENDED:


                v.invalidate();

                // Does a getResult(), and displays what happened.
                if (event.getResult()) {
                   // Toast.makeText(this, "The drop was handled.", Toast.LENGTH_LONG);

                } else {
                   // Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG);
                }

                // returns true; the value is ignored.
                return true;

            // An unknown action type was received.
            default:
                Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                break;
        }

        return false;
    }

}
