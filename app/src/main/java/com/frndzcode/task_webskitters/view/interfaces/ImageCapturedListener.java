package com.frndzcode.task_webskitters.view.interfaces;

import android.net.Uri;


/**
 * Created by SERVER on 03/03/2018.
 */

public interface ImageCapturedListener {

    public void imageCaptured(Uri fileUri);
    public void imageProcessed(String base64Image);
}
