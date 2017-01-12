package engineer.echo.eerefreshdemo.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import engineer.echo.eerefreshdemo.R;

/**
 * 青岛芳林信息
 * Created by Plucky on 2017/1/12.
 * 功能描述: Item2ViewHolder
 */

public class Item2ViewHolder extends RecyclerView.ViewHolder {

    public Item2ViewHolder(View itemView) {
        super(itemView);
    }

    public static Item2ViewHolder getHolder(Context mContext) {
        View view = View.inflate(mContext, R.layout.layout_item_2, null);
        return new Item2ViewHolder(view);
    }
}
