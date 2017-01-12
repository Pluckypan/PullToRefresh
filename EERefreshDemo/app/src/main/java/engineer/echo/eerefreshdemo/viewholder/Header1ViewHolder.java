package engineer.echo.eerefreshdemo.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import engineer.echo.eerefreshdemo.R;

/**
 * 青岛芳林信息
 * Created by Plucky on 2017/1/12.
 * 功能描述: 头部视图一ViewHolder
 */

public class Header1ViewHolder extends RecyclerView.ViewHolder {

    public Header1ViewHolder(View itemView) {
        super(itemView);
    }

    public static Header1ViewHolder getHolder(Context mContext) {
        View view = View.inflate(mContext, R.layout.layout_header_1, null);
        return new Header1ViewHolder(view);
    }
}
