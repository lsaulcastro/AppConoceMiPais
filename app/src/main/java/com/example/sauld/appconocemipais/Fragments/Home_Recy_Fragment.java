package com.example.sauld.appconocemipais.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sauld.appconocemipais.Adapters.MyAdapterDesti;
import com.example.sauld.appconocemipais.Modelos.Destino;
import com.example.sauld.appconocemipais.R;
import com.example.sauld.appconocemipais.Util.Util;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home_Recy_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home_Recy_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home_Recy_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static Home_Recy_Fragment mHome_recy_fragment;
    private FirebaseDatabase mDatabase;
    private ProgressDialog mProgressBar;
    private DatabaseReference myDatabaseReference;
    public ArrayList<Destino> mDestinos;
    public ArrayList<String> mDestinoKeys;
    private OnFragmentInteractionListener mListener;
    private MyAdapterDesti myAdapterDesti;
    private View view;

    public Home_Recy_Fragment() {
        // Required empty public constructor
    }


    public static Home_Recy_Fragment newInstance(String param1, String param2) {
        Home_Recy_Fragment fragment = new Home_Recy_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        mDestinos = new ArrayList<>();
        mProgressBar = new ProgressDialog(getActivity());
        mDestinoKeys = new ArrayList<>();
        mProgressBar.setMessage(getResources().getText(R.string.cargando));
        mProgressBar.setCancelable(false);
        myAdapterDesti = new MyAdapterDesti(getContext());
        mDatabase = FirebaseDatabase.getInstance();
        myDatabaseReference = mDatabase.getReference();

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home__recy_, container, false);
        Toolbar toolbar = view.findViewById(R.id.toolbarMain);
        toolbar.setTitle("Destinos ");
        llenarRecycler();
        mDestinos = myAdapterDesti.getArrayList();
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerviewDestino);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(myAdapterDesti);
        myAdapterDesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), " Posicion " +
                        mDestinos.get(recyclerView.getChildAdapterPosition(v)).getTitulo(), Toast.LENGTH_SHORT).show();

            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    private void llenarRecycler() {

        DatabaseReference mreference = myDatabaseReference.child(Util.REFERENCE_CONOCER);
        mProgressBar.show();
        myAdapterDesti.clearArray();
        if (!Util.compruebaConexion(getContext())) {
            mProgressBar.dismiss();
//            Snackbar.make(view,"No existe conexion a internet", Snackbar.LENGTH_LONG).show();
            Toast.makeText(getContext(), "No existe conexion a internet", Toast.LENGTH_SHORT).show();
        }
        mreference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Destino destino = dataSnapshot.getValue(Destino.class);
                myAdapterDesti.add(destino);
                mProgressBar.dismiss();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Destino destino = dataSnapshot.getValue(Destino.class);
                myAdapterDesti.updateItem(destino);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Destino destino = dataSnapshot.getValue(Destino.class);
                myAdapterDesti.removeItem(destino);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mProgressBar.dismiss();
                Snackbar.make(getView(), "asd", Snackbar.LENGTH_LONG).show();
            }

        });

    }

//    private void llenarLista() {
//        mDestinos.clear();
//        for (int x = 0; x <= 3; x++) {
//            mDestinos.add(new Destino("http://www.losmejoresdestinos.com/destinos/republica_dominicana/playa_rep_dom1.jpg", "Playa 3", "Playa 3", "Playa 3"));
//        }
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
            //     llenarRecycler();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void mandarBundel(Bundle bundle);
    }

    public static Home_Recy_Fragment getInstance() {

        if (mHome_recy_fragment == null) {
            mHome_recy_fragment = new Home_Recy_Fragment();
        }
        return mHome_recy_fragment;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
