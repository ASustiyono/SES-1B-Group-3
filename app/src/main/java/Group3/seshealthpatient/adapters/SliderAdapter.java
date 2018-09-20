package group3.seshealthpatient.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout;

import group3.seshealthpatient.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slide_images = {
            R.drawable.tutorial_slide0,
            R.drawable.tutorial_slide1,
            R.drawable.tutorial_slide2,
            R.drawable.tutorial_slide3,
            R.drawable.tutorial_slide4,
            R.drawable.tutorial_slide5,
            R.drawable.tutorial_slide6
    };

    public String[] slide_headings = {
            "LETS GET STARTED",
            "YOUR INFORMATION",
            "YOUR MESSAGES",
            "YOUR HEART",
            "YOUR VIDEOS",
            "YOUR FILES",
            "YOUR CLINICS"
    };

    public String[] slide_descs = {
            "FIRSTLY, WE TAKE SECURITY SERIOUSLY.\n" +
                    "\n" +
                    "ALL OF YOUR INFORMATION IS SAFELY\n" +
                    "SECURED USING OUR STATE-OF-THE-ART\n" +
                    "PATENTED ANTI-HACKING TECHNOLOGY.",
            "HOMEPAGE DISPLAYS YOUR LATEST\n" +
                    "CHECKUP AND CURRENT INFORMATION.\n" +
                    "\n" +
                    "DAILY CHECK UP ALLOWS YOU TO KEEP\n" +
                    "PROGRESS AND RECORD OF YOUR HEALTH.",
            "OUR MESSENGER ALLOWS YOU TO\n" +
                    "INSTANT TALK WITH YOUR DOCTOR.\n" +
                    "\n" +
                    "YOU CAN ATTACHED FILES LIKE MEDICAL\n" +
                    "REPORTS TO HELP YOUR DOCTOR.",
            "THE HEART MONITOR ALLOWS PATIENTS\n" +
                    "TO RECORD THEIR HEART RATE.\n" +
                    "\n" +
                    "HOLDING YOUR FINGER OVER YOUR\n" +
                    "CAMERA FLASHLIGHT FOR 3SECS WILL\n" +
                    "ALLOW US TO DETERMINE YOUR RATE.*",
            "RECORD YOUR VIDEO SNIPPETS AND\n" +
                    "SEND THEM TO YOUR DOCTOR.*\n" +
                    "\n" +
                    "RECORD A NEW VIDEO OR SELECT\n" +
                    "A VIDEO FROM YOUR PHONE.",
            "SEND ANY MEDICAL FILES\n" +
                    "TO YOUR DOCTOR.\n" +
                    "\n" +
                    "SELECT A VIDEO FROM YOUR PHONE\n" +
                    "OR THROUGH CLOUD STORAGE APPS.",
            "LOCATE THE NEAREST CLINICS\n" +
                    "WITH ONE SIMPLE CLICK.\n" +
                    "\n" +
                    "PINCH TO ZOOM.\n" +
                    "\n" +
                    "CLICK ON CLINICS FOR MORE INFO."
    };

    public String[] slide_warnings = {
            "",
            "",
            "",
            "*APPLIES TO ANDROID DEVICES THAT HAS A FLASHLIGHT.",
            "*A SNIPPET SHOULD BE 10-30 SECONDS LONG",
            "*RECOMMENDED FILE SIZE SHOULD BE UNDER 5MB",
            "*APPLIES TO ANDROID DEVICES THAT HAS A FLASHLIGHT."
    };

    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService( context.LAYOUT_INFLATER_SERVICE );
        View view = layoutInflater.inflate( R.layout.slide_layout, container, false );

        ImageView slideImageView = (ImageView) view.findViewById( R.id.icon_slide );
        TextView slideHeading = (TextView) view.findViewById( R.id.heading_slide );
        TextView slideDescription = (TextView) view.findViewById( R.id.desc_slide );
        TextView slideWarning = (TextView) view.findViewById( R.id.warning_slide );

        slideImageView.setImageResource( slide_images[position] );
        slideHeading.setText( slide_headings[position] );
        slideDescription.setText( slide_descs[position] );
        slideWarning.setText( slide_warnings[position] );

        container.addView( view );

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView( (RelativeLayout) object );
    }

}
