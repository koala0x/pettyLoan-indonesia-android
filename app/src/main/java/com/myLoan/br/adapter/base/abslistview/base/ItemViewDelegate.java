package com.myLoan.br.adapter.base.abslistview.base;


import com.myLoan.br.adapter.base.abslistview.ViewHolder;

public interface ItemViewDelegate<T>
{

    public abstract int getItemViewLayoutId();

    public abstract boolean isForViewType(T item, int position);

    public abstract void convert(ViewHolder holder, T t, int position);



}
