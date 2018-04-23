package ie.ul.o.daysaver;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import ie.ul.o.daysaver.dummy.DummyContent;

/**
 * A fragment representing a single Video detail screen.
 * This fragment is either contained in a {@link VideoListActivity}
 * in two-pane mode (on tablets) or a {@link VideoDetailActivity}
 * on handsets.
 */

public class VideoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    private Context context;
    public static final String ARG_ITEM_ID = "item_id";
    private OnFragmentInteractionListener mListener;

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private String TAG;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoDetailFragment() {

    }



    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
           /* CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.video_url);
            }*/
        }
    }

    VideoView videoView;
    TextView mTextview;
    TextView dTextView;
    Button button;
    Uri uri;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_detail, container, false);

        // Show the dummy content as text in a TextView.



        videoView= rootView.findViewById(R.id.video_detail);
        mTextview=rootView.findViewById(R.id.textView48);
        dTextView=rootView.findViewById(R.id.textView53);
        //button=rootView.findViewById(R.id.button15);



        if (mItem != null) {

            uri=Uri.parse(mItem.video_url);//youtubeLink
            videoView.setVideoURI(uri);
           // videoView.setVideoPath(mItem.video_url);
            MediaController mediaController=new MediaController(getActivity());
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()  {
                                                        @Override
                                                        public void onPrepared(MediaPlayer mp) {
                                                            Log.i(TAG, "Duration = " +
                                                                    videoView.getDuration());
                                                        }
                                                    });


            videoView.start();
            mTextview.setText(mItem.video_name);
            dTextView.setText(mItem.video_desc);


        }

        return rootView;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
