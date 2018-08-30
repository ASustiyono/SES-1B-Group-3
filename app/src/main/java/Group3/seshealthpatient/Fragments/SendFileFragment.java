package Group3.seshealthpatient.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.OpenableColumns;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

import Group3.seshealthpatient.Activities.MainActivity;
import Group3.seshealthpatient.R;
import Group3.seshealthpatient.UploadListAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendFileFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;
    private Button mSelectButton;
    private RecyclerView mUploadList;

    private List<String> fileNameList;
    private List<String> fileDoneList;

    private UploadListAdapter uploadListAdapter;

    private StorageReference mStorageRef;

    public SendFileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_file, container, false);
        /*
        // Stores it on Firebase
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //Compare files
        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();

        //Select File and display recycler view
        mSelectButton = view.findViewById(R.id.send_select_btn);
        mUploadList = view.findViewById(R.id.send_list_view);

        //Display item in a recyler view using an adapter
        uploadListAdapter = new UploadListAdapter(fileNameList, fileDoneList);

        mUploadList.setLayoutManager(new LinearLayoutManager(getContext()));
        mUploadList.setHasFixedSize(true);
        mUploadList.setAdapter(uploadListAdapter);

        mSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("video/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select video"), RESULT_LOAD_IMAGE);
            }
        });
*/
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == android.app.Activity.RESULT_OK) {

        }

        Uri uri = data.getData();

        Toast.makeText(getActivity(), "File Selected", Toast.LENGTH_SHORT).show();

/*
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == android.app.Activity.RESULT_OK) {

            if(data.getClipData() != null) {

                int totalItemsSelected = data.getClipData().getItemCount();

                for(int i = 0; i < totalItemsSelected; i++) {

                    Uri fileUri = data.getClipData().getItemAt(i).getUri();

                    String fileName = getFileName(fileUri);

                    fileNameList.add(fileName);
                    fileDoneList.add("uploading");
                    uploadListAdapter.notifyDataSetChanged();

                    StorageReference fileToUpload = mStorageRef.child("Videos").child(fileName);

                    final int finalI = i;
                    fileToUpload.putFile(fileUri).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            fileDoneList.remove(finalI);
                            fileDoneList.add(finalI, "done");

                            uploadListAdapter.notifyDataSetChanged();

                        }
                    });
                }
                Toast.makeText(getActivity(), "Selected Multiple Files", Toast.LENGTH_SHORT).show();

            } else if (data.getData() != null) {

                Toast.makeText( getActivity(), "Select Multiple Files", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("Context")) {
            Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
                try {
                    if (cursor != null && cursor.moveToFirst()) {
                        result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            }
            if (result == null) {
                result = uri.getPath();
                int cut = result.lastIndexOf('/');
                if (cut != -1) {
                    result = result.substring(cut + 1);
                }
            }
        return result;
    }
    */
} }
