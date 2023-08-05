package fr.mathis.invisiblewidget;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import fr.mathis.invisiblewidget.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private int alphaValue;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ivWallpaper.setImageResource(R.drawable.ic_fake_wallpaper);

        alphaValue = DataManager.GetMemorizedValue(DataManager.KEY_ALPHA, this.getContext());
        binding.sbAlpha.setProgress(alphaValue);

        binding.sbAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alphaValue = progress;
                updateAlpha();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        updateAlpha();
    }

    private void updateAlpha() {
        @SuppressLint("ResourceType")
        Drawable d = new ColorDrawable(Color.parseColor(getString(R.color.appPrimaryVibrant)));
        d.setAlpha(alphaValue);
        binding.llOverlay.setBackground(d);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override public void onResume() {
        super.onResume();
    }

    @Override public void onPause() {
        super.onPause();

        DataManager.MemorizeValue(DataManager.KEY_ALPHA, alphaValue, this.getContext());

        Intent intent = new Intent(this.getContext(), SmallWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int[] ids = { R.xml.small_widget_meta};
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        this.getContext().sendBroadcast(intent);
    }
}