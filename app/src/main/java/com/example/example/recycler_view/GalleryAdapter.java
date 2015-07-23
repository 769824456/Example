package com.example.example.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.example.R;

import java.util.ArrayList;

import butterknife.Bind;

/*
 * PROJECT_NAME :Example
 * VERSION :[V 1.0.0]
 * AUTHOR :  yulongsun
 * CREATE AT : 7/23/2015 2:36 PM
 * COPYRIGHT : InSigma HengTian Software Ltd.
 * NOTE :
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private final ArrayList<Integer> mDatas;
    private final LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    /**构造
     * @param context
     * @param mDatas
     */
    public GalleryAdapter(Context context, ArrayList<Integer> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    /**
     * 创建ViewHolder
     *
     * @param viewGroup
     * @param i
     * @return viewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,   int i) {
        //1.
        View view = mInflater.inflate(R.layout.item_recycler_view, null);
        //2.
        final ViewHolder viewHolder = new ViewHolder(view);
        //3
        viewHolder.ivItemRecyclerView= (ImageView) view.findViewById(R.id.iv_item_recycler_view);
        viewHolder.tvItemRecyclerView= (TextView) view.findViewById(R.id.tv_item_recycler_view);

        return viewHolder;
    }

    /**
     * 绑定
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        viewHolder.ivItemRecyclerView.setImageResource(mDatas.get(i));
        //4.接口回调
        if(mOnItemClickListener!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mOnItemClickListener.onItemClick(viewHolder.itemView,i);
                }
            });
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivItemRecyclerView;
        TextView tvItemRecyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


    //=====================定义接口============================+
    //让Activity去实现接口
    interface OnItemClickListener {
        void onItemClick(View view,int position);
    };


    /**
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.mOnItemClickListener= itemClickListener;
    }

}
