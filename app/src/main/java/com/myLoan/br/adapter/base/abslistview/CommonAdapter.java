package com.myLoan.br.adapter.base.abslistview;

import android.content.Context;

import com.myLoan.br.adapter.base.abslistview.base.ItemViewDelegate;

import java.util.List;

/*import happy.adapter.abslistview.base.ItemViewDelegate;*/

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{

    public CommonAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }


    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
