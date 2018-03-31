package com.a.myapplication;

import android.app.Activity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class FileListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_PATH = "/";
    private ListView mFileListView;
    private VideoAdapter mAdapter;
    private String mPath;

    public static FileListFragment newInstance(String path) {
        FileListFragment f = new FileListFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putString(ARG_PATH, path);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_file_list, container, false);
        mFileListView = (ListView) viewGroup.findViewById(R.id.file_list_view);
        return viewGroup;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();

        Bundle bundle = getArguments();
        if (bundle != null) {
            mPath = bundle.getString(ARG_PATH);
            mPath = new File(mPath).getAbsolutePath();
            //mPathView.setText(mPath);
        }

        mAdapter = new VideoAdapter(activity);
        mFileListView.setAdapter(mAdapter);
        mFileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {
                String path = mAdapter.getFilePath(position);
                if (TextUtils.isEmpty(path))
                    return;
                //FileExplorerEvents.getBus().post(new FileExplorerEvents.OnClickFile(path));
                EventBus.getDefault().post(new message(path));
            }
        });

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (TextUtils.isEmpty(mPath))
            return null;
        return new PathCursorLoader(getActivity(), mPath);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
