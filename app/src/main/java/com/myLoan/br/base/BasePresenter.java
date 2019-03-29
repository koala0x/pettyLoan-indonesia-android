package com.myLoan.br.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public abstract class BasePresenter<T> implements PresenterInterace<T> {
    private Reference<T> mviewRef;
    public ArrayList<BaseModel> models = new ArrayList<>();

    @Override
    public void attachView(T view) {
        mviewRef = new WeakReference<T>(view);
        initModle();
    }

    public T getAttach() {
        return mviewRef.get();
    }

    @Override
    public boolean isViewAttach() {
        if (mviewRef == null) {
            return false;
        }
        return mviewRef.get() != null;
    }

    @Override
    public void detachView(T view) {
        if (models != null) {
            for (BaseModel model : models) {
                model.interruptRequest();
            }
        }
        if (mviewRef != null) {
            mviewRef.clear();
            mviewRef = null;
        }
    }



    public abstract void initModle();
    protected void regitModel(BaseModel baseModel){
        models.add(baseModel);
    }
}
