package com.android.administrator.childrensittingposture.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.administrator.childrensittingposture.R;
import com.android.administrator.childrensittingposture.bean.CultivateDb;
import com.android.administrator.childrensittingposture.db.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/13.
 */
public class MyAdapter extends BaseAdapter {
    private List<CultivateDb> listContacts;

    public void setData(List<CultivateDb> listContacts) {
        this.listContacts = listContacts;
        notifyDataSetChanged();
    }


    private LayoutInflater mInflater = null;

    public MyAdapter(Context context) {
        //根据context上下文加载布局，这里的是Demo17Activity本身，即this
        this.mInflater = LayoutInflater.from(context);
    }

    //    /**
//     * 当ListView数据发生变化时,调用此方法来更新ListView
//     * @param listContacts
//     */
//    public void updateListView(List<ContactsBean> listContacts){
//        this.listContacts = listContacts;
//        notifyDataSetChanged();
//    }
    @Override
    public int getCount() {
        //How many items are in the data set represented by this Adapter.
        //在此适配器中所代表的数据集中的条目数
        return listContacts.size();
    }

    @Override
    public Object getItem(int position) {
        // Get the data item associated with the specified position in the data set.
        //获取数据集中与指定索引对应的数据项,,给定索引值，得到索引值对应的对象
        return listContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        //Get the row id associated with the specified position in the list.
        //获取在列表中与指定索引对应的行id,,,,, 获取条目id
        return position;
    }

    public void remove(int position) {
        listContacts.remove(position);
    }

    //Get a View that displays the data at the specified position in the data set.
    //获取一个在数据集中指定索引的视图来显示数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        final CultivateDb mContent = listContacts.get(position);

        //如果缓存convertView为空，则需要创建View
        if (convertView == null) {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView = mInflater.inflate(R.layout.item, null);
            holder.tv_thatDaytime = (TextView) convertView.findViewById(R.id.tv_thatDaytime);
            holder.tv_timeCultivate = (TextView) convertView.findViewById(R.id.tv_timeCultivate);
            holder.tv_remindFrequency = (TextView) convertView.findViewById(R.id.tv_remindFrequency);

//            convertView.setOnClickListener(this);

            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        Log.e("title",(String) data.get(position).get("title"));




//        holder.tv_thatDaytime.setText(listContacts.get(position).getThatDayTime());
//        holder.tv_timeCultivate.setText(listContacts.get(position).getCultivateTime());
//        holder.tv_remindFrequency.setText(listContacts.get(position).getRemindFrequency());
        Log.e("ada",listContacts.get(position).getThatDayTime());
        return convertView;
    }
}