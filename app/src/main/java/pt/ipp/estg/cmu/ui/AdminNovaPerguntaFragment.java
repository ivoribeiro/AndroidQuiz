package pt.ipp.estg.cmu.ui;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.sql.Timestamp;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.dblib.repositories.NivelRepo;
import pt.ipp.estg.dblib.repositories.PerguntaRepo;
import pt.ipp.estg.cmu.interfaces.AdminPerguntaAdapterChangeListener;
import pt.ipp.estg.dblib.models.Nivel;
import pt.ipp.estg.dblib.models.Pergunta;
import pt.ipp.estg.cmu.tasks.DownloadImage;
import pt.ipp.estg.cmu.util.FileOperations;
import pt.ipp.estg.cmu.util.StringsOperations;
import pt.ipp.estg.cmu.util.Util;

import static android.app.Activity.RESULT_OK;

public class AdminNovaPerguntaFragment extends Fragment implements View.OnClickListener {

    private static int RESULT_LOAD_IMAGE = 1;
    private static int CAPTURE_IMAGE_ACTIVITY = 2;

    private AdminPerguntaAdapterChangeListener mListener;

    private Nivel mNivel;
    private Pergunta mPergunta;
    private PerguntaRepo mRepositoryPergunta;
    private NivelRepo mRepositoryNivel;
    private String mImagemPathText;

    private boolean editMode;
    private boolean checkedPreviewImage;

    //layout
    private EditText mRespostaText;
    private FloatingActionButton mFab;
    private Button mDownloadBt;
    private Button mGaleriaBt;
    private Button mCameraBt;
    private ImageView mImagePreview;
    private String mImageName;
    private CoordinatorLayout mRootLayout;


    public AdminNovaPerguntaFragment() {
        // Required empty public constructor
    }

    public static AdminNovaPerguntaFragment newInstance(Nivel nivel, Pergunta pergunta) {
        AdminNovaPerguntaFragment fragment = new AdminNovaPerguntaFragment();
        Bundle args = new Bundle();
        if (null != pergunta) {
            args.putParcelable(Util.ARG_QUESTION, pergunta);
        } else if (null != nivel) {
            args.putParcelable(Util.ARG_LEVEL, nivel);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments().getParcelable(Util.ARG_LEVEL)) {
            mNivel = getArguments().getParcelable(Util.ARG_LEVEL);
            editMode = false;
        } else if (null != getArguments().getParcelable(Util.ARG_QUESTION)) {
            mPergunta = getArguments().getParcelable(Util.ARG_QUESTION);
            editMode = true;
        }
        mRepositoryPergunta = new PerguntaRepo(getContext());
        mRepositoryNivel = new NivelRepo(getContext());

        //image com o nome do timestamp
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        mImageName = timestamp.getTime() + ".jpg";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_nova_pergunta, container, false);

        mRootLayout = (CoordinatorLayout) view.findViewById(R.id.root_layout);
        mRespostaText = (EditText) view.findViewById(R.id.admin_nova_pergunta_resposta);
        mDownloadBt = (Button) view.findViewById(R.id.bt_download);
        mFab = (FloatingActionButton) view.findViewById(R.id.fab);
        mGaleriaBt = (Button) view.findViewById(R.id.bt_galeria);
        mCameraBt = (Button) view.findViewById(R.id.bt_camera);
        mImagePreview = (ImageView) view.findViewById(R.id.user_avatar);

        mDownloadBt.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mGaleriaBt.setOnClickListener(this);
        mCameraBt.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (editMode) {
            setPreviewImage(mPergunta.getImagem());
            mRespostaText.setText(mPergunta.getRespostaCerta());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (AdminPerguntaAdapterChangeListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE || requestCode == CAPTURE_IMAGE_ACTIVITY) {
            if (resultCode == RESULT_OK && null != data) {
                try {

                    if (requestCode == RESULT_LOAD_IMAGE) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};

                        Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                        if (cursor != null) {
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();

                            mImagemPathText = picturePath;

                            File sourceFile = new File(mImagemPathText);
                            //File f = FileOperations.compressImageFile(sourceFile);
                            FileOperations.copy(sourceFile, mImageName);
                            setPreviewImage(sourceFile.getPath());
                        }
                    }

                    if (requestCode == CAPTURE_IMAGE_ACTIVITY) {
                        Uri mCameraTempUri = (Uri) data.getExtras().get(MediaStore.EXTRA_OUTPUT);
                        //Bitmap photo = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
                        //mImageView.setImageBitmap(mImageBitmap);
                        //mImagePreview.setImageBitmap(photo);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_download:
                dialogPermission(Util.PERMISSIONS_REQUEST_WRITE_DOWNLOAD);
                break;

            case R.id.bt_galeria:
                dialogPermission(Util.PERMISSIONS_REQUEST_WRITE_GALERIA);
                break;

            case R.id.bt_camera:
                //TODO permissao camera
/*                File dest = new File(Util.getAppFolderPath() + mImageName);
                Uri outputFileUri = Uri.fromFile(dest);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY);*/


/*                ContentValues values = new ContentValues(1);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");
                Uri mCameraTempUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                if (mCameraTempUri != null) {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraTempUri);
                }
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY);*/

                break;

            case R.id.fab:
                savePergunta();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Util.PERMISSIONS_REQUEST_WRITE_DOWNLOAD: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showDownloadDialog();
                } else {
                    Snackbar.make(mRootLayout, getContext().getResources().getString(R.string.permission_denied), Snackbar.LENGTH_SHORT).show();
                }
                return;
            }
            case Util.PERMISSIONS_REQUEST_WRITE_GALERIA: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                } else {
                    Snackbar.make(mRootLayout, getContext().getResources().getString(R.string.permission_denied), Snackbar.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void dialogPermission(int requestcode) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestcode);
            }
        } else {
            switch (requestcode) {
                case Util.PERMISSIONS_REQUEST_WRITE_DOWNLOAD:
                    showDownloadDialog();
                    break;
                case Util.PERMISSIONS_REQUEST_WRITE_GALERIA:
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    break;
            }
        }
    }

    private void setPreviewImage(String imagePath) {
        if (null != imagePath) {
            File imgFile = new File(imagePath);
            if (imgFile.exists()) {
                Bitmap bmImg = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                mImagePreview.setBackground(null);
                mImagePreview.setImageBitmap(bmImg);
            }
        }
    }

    private void showDownloadDialog() {
        final EditText input = new EditText(getContext());
        input.setHint(R.string.admin_download_dialog_message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(input);
        builder.setTitle(R.string.admin_download_dialog_title)
                .setPositiveButton(R.string.admin_download_dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new DownloadImage(getContext(), mImagePreview, mImageName).execute(input.getText().toString());
                        mImagemPathText = Util.getAppFolderPath() + mImageName;
                    }
                })
                .setNegativeButton(R.string.admin_download_dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.create();
        builder.show();
    }

    private void savePergunta() {
        String respostaCerta = mRespostaText.getText().toString();
        //imagem de galeria ou camera
        if ((!respostaCerta.equals("") && ((!editMode && mImagemPathText != null) || (editMode)))) {
            StringsOperations operations = new StringsOperations(respostaCerta.toUpperCase());
            String respostaRandom = operations.generateString();
            Pergunta p = new Pergunta();
            p.setRespostaCerta(respostaCerta.toUpperCase());
            p.setStringAleatoria(respostaRandom);
            if (!editMode) {
                p.setImagem(mImagemPathText);
                p.setNivel(mNivel.getId());
                p.setRespostaActual("");
                mRepositoryPergunta.insertInto(p);
            } else {
                p.setId(mPergunta.getId());
                mImagemPathText = mImagemPathText == null ? mPergunta.getImagem() : mImagemPathText;
                p.setImagem(mImagemPathText);
                p.setnRespostasErradas(mPergunta.getnRespostasErradas());
                p.setAcertou(mPergunta.isAcertou());
                p.setNivel(mPergunta.getNivel());
                p.setnAjudasUsadas(mPergunta.getnAjudasUsadas());
                p.setRespostaActual(mPergunta.getRespostaActual());
                mRepositoryPergunta.update(p);
            }
            getActivity().getSupportFragmentManager().popBackStack(Util.STACK_ADMIN, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            mListener.onPerguntaSave();
        } else {
            Toast.makeText(getContext(), getContext().getResources().getString(R.string.admin_toast_campos_erro), Toast.LENGTH_SHORT).show();
        }
    }
}
