package com.example.emiratesauctionsweatherapp.ui.adapter.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, V extends ViewDataBinding>
        extends RecyclerView.Adapter<BaseRecyclerViewAdapter<T, V>.BaseViewHolder> {

    public static final int ZERO_POSITION = 0;

    private List<T> mDataList;
    private OnItemClickListener<T> mOnItemClickListener;

    // Constructors

    public BaseRecyclerViewAdapter() {
    }

    public BaseRecyclerViewAdapter(final List<T> dataList) {
        mDataList = new ArrayList<>(dataList);
    }

    public BaseRecyclerViewAdapter(final OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public BaseRecyclerViewAdapter(final OnItemClickListener<T> onItemClickListener, final List<T> dataList) {
        mDataList = new ArrayList<>(dataList);
        mOnItemClickListener = onItemClickListener;
    }

    // Recycler View Adapter Overridden Methods

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        final V viewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new BaseViewHolder(viewDataBinding, getViewBindingVariableId());
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (mDataList != null && position >= ZERO_POSITION && position < mDataList.size()) {
            holder.bind(mDataList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        final int itemLayoutId = getItemLayoutId();
        return itemLayoutId != 0 ? itemLayoutId : super.getItemViewType(position);
    }

    // Abstract methods to be overridden by inheriting children classes

    @LayoutRes
    protected abstract int getItemLayoutId();

    protected abstract int getViewBindingVariableId();

    protected abstract void onViewHolderBinding(V viewDataBinding, T item, int position);

    // Getters and Setters

    public List<T> getDataList() {
        return mDataList;
    }

    public void setDataList(final List<T> dataList) {
        mDataList = dataList != null ? new ArrayList<>(dataList) : null;
        notifyDataSetChanged();
    }

    // Other Helper Methods

    public T getItemAtPosition(final int position) {
        if (mDataList != null && position >= ZERO_POSITION && position < mDataList.size()) {
            return mDataList.get(position);
        }
        return null;
    }

    public int addItem(final T item) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.add(ZERO_POSITION, item);
        notifyItemInserted(ZERO_POSITION);
        return ZERO_POSITION;
    }

    public void addItemList(final List<T> itemList) {
        if (mDataList == null) {
            mDataList = new ArrayList<>();
        }
        mDataList.addAll(itemList);
        final int itemListSize = itemList.size();
        notifyItemRangeInserted(mDataList.size() - itemListSize, itemListSize);
    }

    public void updateItemAtPosition(final T item, final int position) {
        if (mDataList != null && position >= ZERO_POSITION && position < mDataList.size()) {
            mDataList.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void removeItemAtPosition(final int position) {
        if (mDataList != null && position >= ZERO_POSITION && position < mDataList.size()) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeItem(T item) {
        if (mDataList != null) {
            final int index = mDataList.indexOf(item);
            if (index != -1) {
                mDataList.remove(index);
                notifyItemRemoved(index);
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    // Base View Holder Class
    public class BaseViewHolder extends RecyclerView.ViewHolder {

        private V mViewDataBinding;
        private int mViewBindingVariableId;

        public BaseViewHolder(final V viewDataBinding, final int viewBindingVariableId) {
            super(viewDataBinding.getRoot());

            mViewDataBinding = viewDataBinding;
            mViewBindingVariableId = viewBindingVariableId;
        }

        public void bind(final T item, int position) {
            mViewDataBinding.setVariable(mViewBindingVariableId, item);
            onViewHolderBinding(mViewDataBinding, item, position);
            mViewDataBinding.executePendingBindings();

            if (mOnItemClickListener != null) {
                mViewDataBinding.getRoot().setOnClickListener(view -> mOnItemClickListener.onItemClick(item, position));
            }
        }

        public V getViewDataBinding() {
            return mViewDataBinding;
        }
    }

    // On Item Click Listener Interface
    public interface OnItemClickListener<T> {

        void onItemClick(final T dataItem, final int position);
    }
}