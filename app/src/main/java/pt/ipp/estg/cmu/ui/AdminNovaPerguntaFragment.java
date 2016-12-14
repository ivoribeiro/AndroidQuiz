package pt.ipp.estg.cmu.ui;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.db.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.models.Nivel;
import pt.ipp.estg.cmu.models.Pergunta;
import pt.ipp.estg.cmu.tasks.DownloadImage;
import pt.ipp.estg.cmu.util.FileOperations;
import pt.ipp.estg.cmu.util.StringsOperations;
import pt.ipp.estg.cmu.util.Util;

import static android.app.Activity.RESULT_OK;

public class AdminNovaPerguntaFragment extends Fragment implements View.OnClickListener {

    private static int RESULT_LOAD_IMAGE = 1;
    private static int CAPTURE_IMAGE_ACTIVITY = 2;

    private Nivel mNivel;
    private PerguntaRepo mRepository;
    private String mImagemPathText;

    //layout
    private EditText mUrlText;
    private EditText mRespostaText;
    private FloatingActionButton mDownloadBt;
    private FloatingActionButton mFab;
    private Button mGaleriaBt;
    private Button mCameraBt;

    //TODO implementar caminho e nome para as imagens
    private String mImageName = "image.jpg";

    public AdminNovaPerguntaFragment() {
        // Required empty public constructor
    }

    public static AdminNovaPerguntaFragment newInstance(Nivel nivel) {
        AdminNovaPerguntaFragment fragment = new AdminNovaPerguntaFragment();
        Bundle args = new Bundle();
        args.putParcelable(Util.ARG_LEVEL, nivel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNivel = getArguments().getParcelable(Util.ARG_LEVEL);
        }
        mRepository = new PerguntaRepo(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_nova_pergunta, container, false);

        mUrlText = (EditText) view.findViewById(R.id.admin_nova_pergunta_url);
        mRespostaText = (EditText) view.findViewById(R.id.admin_nova_pergunta_resposta);

        mDownloadBt = (FloatingActionButton) view.findViewById(R.id.fab_download);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mGaleriaBt = (Button) view.findViewById(R.id.bt_galeria);
        mCameraBt = (Button) view.findViewById(R.id.bt_camera);

        mDownloadBt.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mGaleriaBt.setOnClickListener(this);
        mCameraBt.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE || requestCode == RESULT_LOAD_IMAGE) {
            if (resultCode == RESULT_OK && null != data) {
                try {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();

                    mImagemPathText = picturePath;

                    File sourceFile = new File(mImagemPathText);
                    //File f = FileOperations.compressImageFile(sourceFile);
                    FileOperations.copy(sourceFile, mImageName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_download:

                if (mUrlText.getText().toString() != "") {
                    new DownloadImage(getContext(), mImageName, mUrlText).execute(mUrlText.getText().toString());
                    mImagemPathText = Util.getAppFolderPath() + mImageName;
                } else {
                    //TODO warning edittext vazia
                }
                break;

            case R.id.bt_galeria:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;

            case R.id.bt_camera:
                File dest = new File(Util.getAppFolderPath() + mImageName);

                Uri outputFileUri = Uri.fromFile(dest);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY);
                break;
            case R.id.fab:
                savePergunta();
                break;

        }
    }

    private void savePergunta() {
        String respostaCerta = mRespostaText.getText().toString();
        String urlImagem = "";
        if (mUrlText.getText().toString() != "") {
            urlImagem = mUrlText.getText().toString();
        } else {
            urlImagem = mImagemPathText;
        }

        if (respostaCerta != "" && urlImagem != "") {
            StringsOperations operations = new StringsOperations(respostaCerta.toUpperCase());
            String respostaRandom = operations.generateString();
            Pergunta p = new Pergunta();
            p.setImagem(mImagemPathText);
            p.setNivel(mNivel.getId());
            p.setRespostaCerta(respostaCerta.toUpperCase());
            p.setStringAleatoria(respostaRandom);
            mRepository.insertInto(p);


            Intent intent = new Intent(getContext(), AdminPerguntaActivity.class);
            intent.putExtra(Util.ARG_LEVEL, mNivel);
            startActivity(intent);


        }

    }
}
