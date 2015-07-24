package com.example.example.evernote.ui.activity;

import android.app.FragmentManager;
import android.os.Bundle;

import com.example.example.R;
import com.example.example.evernote.ui.fragment.SettingFragment;


public class SettingActivity extends ToolbarActivity {

    public static final String PREFERENCE_FILE_NAME = "note.settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSettingFragment();
    }

    @Override
    protected String getToolbarTitle() {
        return getResources().getString(R.string.notes_menu_settings);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_setting;
    }

    private void initSettingFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.setting_fragment, new SettingFragment(), null).commit();
    }

}
