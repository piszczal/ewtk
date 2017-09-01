package com.example.szymon.ewtk.Fragment.Question;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.szymon.ewtk.Activity.QuestionActivity;
import com.example.szymon.ewtk.Constants;
import com.example.szymon.ewtk.Methods;
import com.example.szymon.ewtk.Model.Answer;
import com.example.szymon.ewtk.FilePath;
import com.example.szymon.ewtk.Model.FileServer;
import com.example.szymon.ewtk.Model.Question;
import com.example.szymon.ewtk.QuestionsManager;
import com.example.szymon.ewtk.R;
import com.example.szymon.ewtk.RestClient;
import com.example.szymon.ewtk.SessionManagement;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FileFragment extends Fragment implements View.OnClickListener {

    private static final int SELECT_SINGLE_PICTURE = 101;
    private static final int TAKE_PHOTO_CODE = 0;
    private int currentQuestion;
    private HashMap<String, String> user;
    private List<Question> questionsList;
    private List<Answer> answersList;
    private TextView mTitle;
    private TextView mDescription;
    private ImageView circleFile;
    private ImageView fileCamera;
    private ImageView fileAttach;
    private FloatingActionButton buttonNext;
    private CardView takePhoto;
    private CardView chooseFile;
    private TextView selectFileResult;
    private RestClient restClient;
    private SessionManagement sessionManagement;
    private HttpRequestTask mAuthTask = null;
    private MultiValueMap<String, Object> sendFile;
    private String selectedFilePath;
    private File file;
    private Uri selectedFileUri;
    private Uri photoUri;
    ProgressDialog dialog;

    public static FileFragment newInstance(List<Question> questionsList, List<Answer> answersList, int currentQuestion) {
        FileFragment fragment = new FileFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.QUESTIONS_INSTANCE, (Serializable) questionsList);
        args.putSerializable(Constants.ANSWERS_INSTANCE, (Serializable) answersList);
        args.putInt(Constants.QUESTION_CURRENT, currentQuestion);
        fragment.setArguments(args);
        return fragment;
    }

    public List<Question> getQuestionsList() {
        return (List<Question>) getArguments().getSerializable(Constants.QUESTIONS_INSTANCE);
    }

    public List<Answer> getAnswersList() {
        return (List<Answer>) getArguments().getSerializable(Constants.ANSWERS_INSTANCE);
    }

    public int getCurrentQuestion() {
        return getArguments().getInt(Constants.QUESTION_CURRENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        getActivity().setFinishOnTouchOutside(false);
        restClient = new Methods(getActivity().getApplicationContext());
        sessionManagement = new SessionManagement(getActivity().getApplicationContext());
        user = sessionManagement.getUserDetails();
        questionsList = getQuestionsList();
        answersList = getAnswersList();
        currentQuestion = getCurrentQuestion();
        mTitle = (TextView) view.findViewById(R.id.titleFile);
        mDescription = (TextView) view.findViewById(R.id.descriptionFile);
        circleFile = (ImageView) view.findViewById(R.id.circleFile);
        buttonNext = (FloatingActionButton) view.findViewById(R.id.next_fabFile);
        selectFileResult = (TextView) view.findViewById(R.id.selectFileResult);
        fileCamera = (ImageView) view.findViewById(R.id.file_camera);
        fileAttach = (ImageView) view.findViewById(R.id.file_attach);

        buttonNext.setImageResource(R.drawable.ic_arrow_next);
        fileCamera.setImageResource(R.drawable.ic_camera);
        fileAttach.setImageResource(R.drawable.ic_attach_file);
        takePhoto = (CardView) view.findViewById(R.id.takePhotoCard);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((QuestionActivity)getActivity()).startRequest()){

                }else {
                    File file = getOutputMediaFile(1);
                    photoUri = Uri.fromFile(file);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
                }
            }
        });
        chooseFile = (CardView) view.findViewById(R.id.chooseFileCard);
        chooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!((QuestionActivity)getActivity()).startRequest()){

                }else {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);

                    try {
                        startActivityForResult(
                                Intent.createChooser(intent, getResources().getString(R.string.file_select_upload_)),
                                SELECT_SINGLE_PICTURE);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity().getApplicationContext(), getResources().getString(R.string.file_select_manager) , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        mTitle.setText(questionsList.get(currentQuestion).getTitle());
        mDescription.setText(questionsList.get(currentQuestion).getDescription());
        String firstLetter = String.valueOf(questionsList.get(currentQuestion).getTitle().charAt(0));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(currentQuestion);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(firstLetter, color);
        circleFile.setImageDrawable(drawable);
        buttonNext.setOnClickListener(this);
        getActivity().setTitle(getResources().getString(R.string.question) + " " + (currentQuestion + 1) + "/" + questionsList.size());
        return view;
    }

    @Override
    public void onClick(View v) {
        if (file != null) {
            sendFile = new LinkedMultiValueMap<>();
            String uriPath = file.getPath();
            if (uriPath.contains(".")) {
                String extension = uriPath.substring(uriPath.lastIndexOf(".") + 1);
                if(extension.equals("jpg")||extension.equals("jpeg")||extension.equals("png")){
                    float size = Float.parseFloat(String.valueOf(file.length()/1024));
                    if((size/1024)>=1.5){
                        FilePath.resizeImage(file);
                        file = file.getAbsoluteFile();
                    }
                }
            }
            sendFile.add("file", new FileSystemResource(file.getPath()));
            mAuthTask = new HttpRequestTask(user, sendFile);
            mAuthTask.execute((Void) null);
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.no_file), Toast.LENGTH_LONG).show();
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Integer, Boolean> {
        MultiValueMap<String, Object> fileSend;
        String access_token;
        Answer answer;

        HttpRequestTask(HashMap<String, String> user, MultiValueMap<String, Object> file) {
            this.access_token = user.get(SessionManagement.KEY_ACCESS_TOKEN);
            this.fileSend = file;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage(getResources().getString(R.string.sending_file));
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                restClient.setBearerAuth(access_token);
                FileServer fileServer = restClient.sendFile(fileSend);
                Log.d("File serv: ", fileServer.getName()+" r: "+fileServer.getRealName());
                answer = new Answer(questionsList.get(currentQuestion).getDescription(), fileServer.getName(), file.getName(), questionsList.get(currentQuestion).getID(), questionsList.get(currentQuestion).getQuestionTypeID());
                answersList.add(answer);
                return true;
            } catch (HttpClientErrorException e) {
                Log.e("Send answers error: ", e.getLocalizedMessage(), e);
                Log.d("Error: ", e.getResponseBodyAsString());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean status) {
            mAuthTask = null;
            dialog.cancel();
            if (status != false) {
                QuestionsManager questionsManager = new QuestionsManager(questionsList, answersList, currentQuestion + 1);
                Fragment fragment = questionsManager.nextQuestion();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .replace(R.id.activity_question, fragment, "MY FRAGMENT")
                        .addToBackStack(null);
                transaction.commit();
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.wrong_type_file), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    private File getOutputMediaFile(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/ewtk/");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == 1) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == SELECT_SINGLE_PICTURE) {
                    if (data == null) {
                        return;
                    }
                    selectedFileUri = data.getData();
                    selectedFilePath = FilePath.getPath(getContext(), selectedFileUri);
                    if (selectedFilePath != null && !selectedFilePath.equals("")) {
                        file = new File(selectedFilePath);
                        selectFileResult.setText(getResources().getString(R.string.selected_file_) + file.getName());
                    } else {
                        selectFileResult.setText("null");
                    }
                }
                if (requestCode == TAKE_PHOTO_CODE) {
                    Uri uri = photoUri;
                    selectedFilePath = FilePath.getPath(getContext(), uri);
                    file = new File(selectedFilePath);
                    selectFileResult.setText(getResources().getString(R.string.selected_file_) + file.getName());
                }
            }
        }catch(Exception e){

        }
    }
}
