package com.example.administrator.testall.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 按类型显示item
 */
public abstract class BaseRecyclerTypeAdapter<T extends Object> extends RecyclerView.Adapter<BaseRecyclerTypeAdapter.BaseRecyclerTypeHolder> {
    private List<T> mDatas;
    private Context mContext;
    private OnItemClickListener listener;
    private OnItemLongClickListener longListener;

    public static class BaseRecyclerTypeHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding binding;

        public BaseRecyclerTypeHolder(View itemView) {
            super(itemView);
        }

        public void setDataBinding(ViewDataBinding viewDataBinding) {
            this.binding = viewDataBinding;
        }

        public void setVariable(int variableId, Object value) {
            binding.setVariable(variableId, value);
        }
    }

    public BaseRecyclerTypeAdapter(Context context, Collection<T> datas){
        if (datas == null) {
            mDatas = new ArrayList<>();
        } else if (datas instanceof List) {
            mDatas = (List<T>) datas;
        } else {
            mDatas = new ArrayList<>(datas);
        }

        this.mContext = context;
    }

    /**
     * 动态增加一条数据
     *
     * @param itemDataType 数据实体类对象
     */
    public void append(T itemDataType) {
        if (itemDataType != null) {
            mDatas.add(itemDataType);
            notifyDataSetChanged();
        }
    }


    /**
     * 动态增加一组数据集合
     *
     * @param itemDataTypes 数据实体类集合
     */
    public void append(Collection<T> itemDataTypes) {
        if (itemDataTypes.size() > 0) {
            for (T itemDataType : itemDataTypes) {
                mDatas.add(itemDataType);
            }
            notifyDataSetChanged();
        }
    }

    /**
     * 替换全部数据
     *
     * @param itemDataTypes 数据实体类集合
     */
    public void replace(Collection<T> itemDataTypes) {
        mDatas.clear();
        if (itemDataTypes.size() > 0) {
            mDatas.addAll(itemDataTypes);
            notifyDataSetChanged();
        }
    }

    public List<T> getData(){
        return mDatas;
    }
    /**
     * 移除一条数据集合
     *
     * @param position
     */
    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    /**
     * 移除所有数据
     */
    public void removeAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position){
        if(mDatas != null && mDatas.size() > position) {
            return mDatas.get(position);
        }else{
            return null;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getViewType(mDatas.get(position), position);
    }

    abstract public int getViewType(T value, int position);
    abstract public int getLayoutByType(int viewType);

    @Override
    public BaseRecyclerTypeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = getLayoutByType(viewType);
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), layout, parent, false);
        BaseRecyclerTypeHolder holder = new BaseRecyclerTypeHolder(dataBinding.getRoot());
        holder.setDataBinding(dataBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BaseRecyclerTypeHolder holder, final int position) {
        final T value = mDatas.get(position);
        showData(holder, position, value);
        if(longListener != null){
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    longListener.onLongClick(holder, value, position);
                    return true;
                }
            });
        }

        if(listener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(holder, value, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemClickListener(OnItemClickListener l){
        this.listener = l;

    }

    public void setOnItemLongClickListener(OnItemLongClickListener l){
        this.longListener = l;
    }

    /**
     * 显示数据抽象函数
     *
     * @param viewHolder 基类ViewHolder,需要向下转型为对应的ViewHolder（example:MainRecyclerViewHolder mainRecyclerViewHolder=(MainRecyclerViewHolder) viewHolder;）
     * @param position   位置
     * @param data
     */
    public abstract void showData(BaseRecyclerTypeHolder viewHolder, int position, T data);

    public interface OnItemClickListener<T> {
        void onItemClick(BaseRecyclerTypeHolder viewHolder, T data, int position);
    }

    public interface OnItemLongClickListener<T>{
        boolean onLongClick(BaseRecyclerTypeHolder viewHolder, T data, int position);
    }
}
