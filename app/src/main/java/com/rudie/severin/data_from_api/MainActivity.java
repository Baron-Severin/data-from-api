package com.rudie.severin.data_from_api;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.koushikdutta.ion.Ion;
import com.rudie.severin.data_from_api.Model.Giphy;
import com.rudie.severin.data_from_api.Model.GiphyResponse;
import com.rudie.severin.data_from_api.REST.ApiClient;
import com.rudie.severin.data_from_api.REST.ApiInterface;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    String API_KEY = "dc6zaTOxFJmzC";
    Giphy giphy;
    ApiInterface apiInterface;
    ImageView gifView, imageView;
    Button button;
    EditText editText;
    static int rotations = 1;
    ProgressBar progressBar;
    Bitmap bitmap;
    int imageViewHeight, imageViewWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gifView = (ImageView) findViewById(R.id.gifView);
        imageView = (ImageView) findViewById(R.id.imageView);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(View.INVISIBLE);

        Retrofit client = ApiClient.getRetrofit();
        apiInterface = client.create(ApiInterface.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = editText.getText().toString();
                setNewGif(query);
            }
        });


    }

    private void setNewGif(String query) {

        Call<GiphyResponse> call = apiInterface.getGiphy(query, API_KEY);

        call.enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(Call<GiphyResponse> call, Response<GiphyResponse> response) {
                int responseCode = response.code();
                if (responseCode > 199 && responseCode < 300) {

                    giphy = response.body().getGiphies();
                    String gifUrl = giphy.getGiphyImages().getOriginalGif().getUrl();

                    Ion.with(gifView)
                            .placeholder(R.color.colorPrimary)
                            .error(R.color.colorAccent)
                            .load(gifUrl);

                    imageViewHeight = imageView.getHeight();
                    imageViewWidth = imageView.getWidth();

                    // grab from async
                    try {

                        new DownloadAndInvertPicture().execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }




                } else {
                    Log.d("SEVTEST ", "Response code :" + responseCode);
                }
            }

            @Override
            public void onFailure(Call<GiphyResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("SEVTEST RESP", "" + t.getMessage());
            }
        });
    }

    public void setImage(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    private class DownloadAndInvertPicture extends AsyncTask<Void, Integer, Bitmap> {

        Context mContext;
        protected void onPreExecute() {
            // Runs on the UI thread before doInBackground
            // Good for toggling visibility of a progress indicator
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

//        protected Bitmap doInBackground(Context... context) {
//            // Some long-running task like downloading an image.
//            mContext = context[0];
//
//            String imageUrl = giphy.getGiphyImages().getOriginalStill().getUrl();
//
//            try {
//                bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
//                bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            for (int h = 0; h < bitmap.getHeight(); h++) {
//                for (int w = 0; w < bitmap.getWidth(); w++) {
//                    int pixel = bitmap.getPixel(w, h);
//                    int red = 255 - Color.red(pixel);
//                    int green = 255 - Color.green(pixel);
//                    int blue = 255 - Color.blue(pixel);
//                    int color = Color.argb(255, red, green, blue);
//                    bitmap.setPixel(w, h, color);
//                }
//                int progressVal = Math.round((long) (100*(h/(1.0*imageViewHeight))));
//                publishProgress(progressVal);
//            }
//            return bitmap;
//        }

        protected Bitmap doInBackground(Void... voids){
            //You must use this mutable Bitmap in order to modify the pixels
            String imageUrl = giphy.getGiphyImages().getOriginalStill().getUrl();
            try {
                bitmap = BitmapFactory.decodeStream(new URL(imageUrl).openStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
            bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap mutableBitmap = bitmap.copy(bitmap.getConfig(),true);
//      bitmap = mutableBitmap;
            bitmap = Bitmap.createBitmap(mutableBitmap.getWidth(), mutableBitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);

            final int block = 90;
//      mutableBitmap.setHeight(mutableBitmap.getHeight()*2);
//      Bitmap newBitmap = Bitmap.createBitmap(mutableBitmap.getWidth(), mutableBitmap.getHeight()*2,
//              Bitmap.Config.ARGB_8888);

            //Loop through each pixel, and invert the colors
            for (int i = 0; i < mutableBitmap.getWidth(); i+=block) {
                for(int j = 0; j < mutableBitmap.getHeight(); j+=block){
                    //TODO: Get the Red, Green, and Blue values for the current pixel, and reverse them
                    //TODO: Set the current pixel's color to the new, reversed value
                    ArrayList<Integer> colors  = new ArrayList<>();
                    List<Pair<Integer, Integer>> locations = new ArrayList<>();
                    Map<Integer, List<Pair<Integer, Integer>>> colorToLocation = new HashMap<>();

                    for (int a = 0; a < block; a ++) {
                        for (int b = 0; b < block; b++) {
                            if (i+a < mutableBitmap.getWidth() -1 && j+b < mutableBitmap.getHeight() - 1) {
                                int pixel = mutableBitmap.getPixel(i + a, j + b);
                                int red = Color.red(pixel);
                                int green = Color.green(pixel);
                                int blue = Color.blue(pixel);
                                int newColor = Color.argb(255, red, green, blue);
                                colors.add(newColor);
                                locations.add(new Pair<>(i+a, j+b));
                            }
                        }
                    }
                    for (int c = 0; c < colors.size(); c++) {
                        List<Pair<Integer, Integer>> tempLocations = new ArrayList<>();
//            try {
                        if (colorToLocation.get(colors.get(c)) != null) {
                            tempLocations = colorToLocation.get(colors.get(c));
                        }
//            } catch (Exception e) {
//              //its not there yet
//            }
//            tempLocations.add(locations.get(c));
                        tempLocations.add(locations.get(c));
                        colorToLocation.put(colors.get(c), tempLocations);
                    }

                    Collections.sort(colors);

                    for (int d = 0; d < colors.size(); d++) {
                        Pair<Integer, Integer> paintLocation = colorToLocation.get(colors.get(d)).get(0);
                        bitmap.setPixel(paintLocation.first, paintLocation.second, colors.get(colors.size()-1-d));
                        colorToLocation.get(colors.get(d)).remove(0);
                    }

                }
                int progressVal = Math.round((long) (100*(i/(1.0*mutableBitmap.getWidth()))));
                publishProgress(progressVal);
                //TODO: Update the progress bar. progressVal is the current progress value out of 100
            }
            return bitmap;
        }

        protected void onProgressUpdate(Integer... values) {
            // Executes whenever publishProgress is called from doInBackground
            // Used to update the progress indicator
//            progressBar.setProgress(values[0]);
            progressBar.setProgress(values[0]);
        }

        protected void onPostExecute(Bitmap result) {
//            // This method is executed in the UIThread
//            // with access to the result of the long running task
            imageView.setImageBitmap(result);
            setImage(result);
            // Hide the progress bar
            progressBar.setVisibility(ProgressBar.INVISIBLE);
//            MainActivity.setBitmap(result);
        }
    }

}
