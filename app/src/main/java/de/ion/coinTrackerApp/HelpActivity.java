package de.ion.coinTrackerApp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Objects;

import de.ion.coinTrackerApp.animation.AnimationImageZoomInService;
import de.ion.coinTrackerApp.help.HelpItemFactory;
import de.ion.coinTrackerApp.help.HelpItemForHelpActivityFactory;
import de.ion.coinTrackerApp.help.valueObject.HelpItem;

public class HelpActivity extends AppCompatActivity implements Activity {
    private ImageView toolbarBitcoinImg;
    private ImageButton toolbarBack;
    private TextView toolbarHeading;
    private LinearLayout helpActivityLinearLayout;

    private HelpItemFactory helpItemFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        init();

        HashMap<Integer, HelpItem> helpItems = helpItemFactory.getHelpItems();

        for (int i = 0; i < helpItems.size(); i++) {
            LayoutInflater inflater;
            inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout helpExampleLinearLayout = (LinearLayout) inflater.inflate(R.layout.help_example, null);

            LinearLayout helpExampleLinearLayoutCopy = (LinearLayout) helpExampleLinearLayout.getChildAt(0);
            TextView questionText = (TextView) helpExampleLinearLayoutCopy.getChildAt(0);
            TextView answerText = (TextView) helpExampleLinearLayoutCopy.getChildAt(1);

            questionText.setText(Objects.requireNonNull(helpItems.get(i)).getQuestion());
            answerText.setText(Objects.requireNonNull(helpItems.get(i)).getAnswer());
            helpActivityLinearLayout.addView(helpExampleLinearLayout);
        }
    }

    public void goBackWithToolbar(View view) {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    public void loadViews() {
        this.helpActivityLinearLayout = (LinearLayout) findViewById(R.id.helpActivityLinearLayout);
        this.toolbarBitcoinImg = (ImageView) findViewById(R.id.toolbarBitcoinImg);
        this.toolbarBack = (ImageButton) findViewById(R.id.toolbarBack);
        this.toolbarHeading = (TextView) findViewById(R.id.toolbarHeading);
    }

    public void initComponents() {
        new AnimationImageZoomInService(this, this.toolbarBitcoinImg);
        this.helpItemFactory = new HelpItemForHelpActivityFactory();
    }

    @Override
    public void initDatabase() {
    }

    @Override
    public void initSingleton() {
    }

    public void initToolbar() {
        this.toolbarBack.setVisibility(View.VISIBLE);
        this.toolbarHeading.setVisibility(View.VISIBLE);
        this.toolbarBitcoinImg.setVisibility(View.GONE);

        this.toolbarHeading.setText("Hilfe");
    }

    public void init() {
        loadViews();
        initComponents();
        initDatabase();
        initSingleton();
        initToolbar();
    }
}