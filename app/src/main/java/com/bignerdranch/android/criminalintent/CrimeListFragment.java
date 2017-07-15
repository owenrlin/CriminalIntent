package com.bignerdranch.android.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private static final int REQUEST_CRIME_POSITION = 1;
    private static final String EXTRA_CRIME_POSITION =
            "com.bignerdranch.android.geoquiz.crime_position";

    private RecyclerView mCrimerRecyclerView;
    private CrimeAdapter mCrimeAdapter;

    private class CrimeViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

        private int mCrimePosition;
        private Crime mCrime;
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;

        public CrimeViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime, parent, false));

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        protected CrimeViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
        }

        @Override
        public void onClick(View view) {
            Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
            intent.putExtra(EXTRA_CRIME_POSITION, getCrimePosition());
            startActivityForResult(intent, REQUEST_CRIME_POSITION);
        }

        public void bind(Crime crime) {
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(DateFormat.format("MMM d, yyyy", crime.getDate()));
            mSolvedImageView.setVisibility(crime.isSolved() ? View.VISIBLE : View.GONE);
        }

        public int getCrimePosition() {
            return mCrimePosition;
        }

        public void setCrimePosition(int crimePosition) {
            mCrimePosition = crimePosition;
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeViewHolder> {

        private final static int VIEW_TYPE_PETTY = 0;
        private final static int VIEW_TYPE_SERIOUS = 1;

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @Override
        public CrimeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CrimeViewHolder(LayoutInflater.from(getActivity()), parent);
        }

        @Override
        public void onBindViewHolder(CrimeViewHolder holder, int position) {
            holder.bind(mCrimes.get(position));
            holder.setCrimePosition(position);
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);

        mCrimerRecyclerView = (RecyclerView) view.findViewById(R.id.crime_recycler_view);
        mCrimerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if(mCrimeAdapter == null) {
            CrimeLab crimeLab = CrimeLab.getCrimeLab(getActivity());
            List<Crime> crimes = crimeLab.getCrimes();
            mCrimeAdapter = new CrimeAdapter(crimes);
            mCrimerRecyclerView.setAdapter(mCrimeAdapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CRIME_POSITION) {
            int position = data.getIntExtra(EXTRA_CRIME_POSITION, -1);

            if(position > -1) {
                mCrimeAdapter.notifyItemChanged(position);
            }

        }
    }

}

