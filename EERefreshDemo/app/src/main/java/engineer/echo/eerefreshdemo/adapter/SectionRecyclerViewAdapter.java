package engineer.echo.eerefreshdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import engineer.echo.eerefreshdemo.viewholder.FooterViewHolder;
import engineer.echo.eerefreshdemo.viewholder.Header1ViewHolder;
import engineer.echo.eerefreshdemo.viewholder.Header2ViewHolder;
import engineer.echo.eerefreshdemo.viewholder.Item1ViewHolder;
import engineer.echo.eerefreshdemo.viewholder.Item2ViewHolder;

/**
 * Created by Plucky on 2017/1/12.
 * 功能描述: RecyclerView分区
 */

public class SectionRecyclerViewAdapter extends SectionedRecyclerViewAdapter<RecyclerView.ViewHolder, RecyclerView.ViewHolder, FooterViewHolder> {

    public static final int MTYPE_HEADER_1 = 1;
    public static final int MTYPE_HEADER_2 = 2;
    public static final int MTYPE_ITEM_1 = 3;
    public static final int MTYPE_ITEM_2 = 4;
    public static final int MTYPE_FOOTER = 5;


    private Context mContext;

    public SectionRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected int getSectionCount() {
        return 2;
    }

    @Override
    protected int getItemCountForSection(int section) {
        return section == 0 ? 5 : 6;
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return section > 0;
    }

    @Override
    protected boolean isSectionHeaderViewType(int viewType) {
        return viewType == MTYPE_HEADER_1 || viewType == MTYPE_HEADER_2;
    }

    @Override
    protected boolean isSectionFooterViewType(int viewType) {
        return viewType == MTYPE_FOOTER;
    }

    @Override
    protected int getSectionItemViewType(int section, int position) {
        if (section == 0) {
            return MTYPE_ITEM_1;
        } else {
            return MTYPE_ITEM_2;
        }
    }

    @Override
    protected int getSectionHeaderViewType(int section) {
        if (section == 0) {
            return MTYPE_HEADER_1;
        } else {
            return MTYPE_HEADER_2;
        }
    }

    @Override
    protected int getSectionFooterViewType(int section) {
        return MTYPE_FOOTER;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MTYPE_HEADER_1) {
            return Header1ViewHolder.getHolder(mContext);
        } else {
            return Header2ViewHolder.getHolder(mContext);
        }
    }

    @Override
    protected FooterViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return FooterViewHolder.getHolder(mContext);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MTYPE_ITEM_1) {
            return Item1ViewHolder.getHolder(mContext);
        } else {
            return Item2ViewHolder.getHolder(mContext);
        }
    }

    @Override
    protected void onBindSectionHeaderViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindSectionFooterViewHolder(FooterViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int section, int position) {
        if (holder instanceof Item1ViewHolder) {

        }
    }

    public int getSpanSize(int position) {
        int viewType = getItemViewType(position);
        if (viewType == MTYPE_ITEM_1) {
            return 1;
        } else {
            return 3;
        }
    }
}
