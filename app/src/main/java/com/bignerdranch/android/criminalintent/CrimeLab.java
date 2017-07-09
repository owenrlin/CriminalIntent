package com.bignerdranch.android.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private LinkedHashMap<UUID, Crime> mCrimes;

    private CrimeLab(Context context) {
        mCrimes = new LinkedHashMap<>();

        //Populate dummy crimes for now...
        for(int i = 0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.put(crime.getId(), crime);
        }
    };

    public static CrimeLab getCrimeLab(Context context) {

        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    /**
     * Know this method contructs a new list each time it is called. Use sparingly.
     */
    public List<Crime> getCrimes() {
        return new ArrayList<>(mCrimes.values());
    }

    public Crime getCrime(UUID id) {
        return mCrimes.get(id);
    }
}
