package com.example.sauld.appconocemipais.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.sauld.appconocemipais.Activities.Login_activity;
import com.example.sauld.appconocemipais.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.sauld.appconocemipais.Util.Util.goTo;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsuariosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsuariosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuariosFragment extends Fragment {
    private FirebaseDatabase mDatabase;
    private static UsuariosFragment mUsuariosFragment;
    private FirebaseAuth myAuth;
    private DatabaseReference myDatabaseReference;
    private FirebaseAuth.AuthStateListener mStateListener;


    @Override
    public void onStart() {
        super.onStart();
        myAuth.addAuthStateListener(mStateListener);
    }


    private OnFragmentInteractionListener mListener;

    public UsuariosFragment() {
        // Required empty public constructor
    }

    public static UsuariosFragment newInstance() {
        UsuariosFragment fragment = new UsuariosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.logout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mDatabase = FirebaseDatabase.getInstance();
        myDatabaseReference = mDatabase.getReference();
        myAuth = FirebaseAuth.getInstance();

        mStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(goTo(getContext(), Login_activity.class));
                }
            }
        };

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();

        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);
        view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Inflate the layout for this fragment
        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar) view.findViewById(R.id.toolbarUser);
        mToolbar.setTitle(getResources().getText(R.string.perfil));
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public static UsuariosFragment getInstance() {
        if (mUsuariosFragment == null) {
            mUsuariosFragment = new UsuariosFragment();
        }
        return mUsuariosFragment;
    }
}
