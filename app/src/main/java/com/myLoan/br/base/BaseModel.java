package com.myLoan.br.base;

import java.util.ArrayList;

import io.reactivex.disposables.Disposable;

public class BaseModel {
    private ArrayList<Disposable> arrayList = new ArrayList<>();

    public void addDisposable(Disposable disposable) {
        arrayList.add(disposable);
    }

    public void removeDisposable(Disposable disposable) {
        arrayList.remove(disposable);
    }


    public void interruptRequest() {
        for (Disposable disposable : arrayList) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        arrayList = null;
    }
}
