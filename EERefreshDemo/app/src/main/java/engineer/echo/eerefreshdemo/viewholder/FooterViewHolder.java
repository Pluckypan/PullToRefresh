package engineer.echo.eerefreshdemo.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import engineer.echo.eerefreshdemo.R;

/**
 * 青岛芳林信息
 * Created by Plucky on 2017/1/12.
 * 功能描述: FooterViewHolder
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {

    public FooterViewHolder(View itemView) {
        super(itemView);
    }

    public static FooterViewHolder getHolder(Context mContext) {
        View view = View.inflate(mContext, R.layout.layout_footer, null);
        return new FooterViewHolder(view);
    }
}
