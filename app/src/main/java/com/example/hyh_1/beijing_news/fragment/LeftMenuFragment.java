package com.example.hyh_1.beijing_news.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hyh_1.beijing_news.R;
import com.example.hyh_1.beijing_news.activity.MainActivity;
import com.example.hyh_1.beijing_news.domain.MenuTabBean;

/**
 * Created by hyh_1 on 2015/10/27.
 */
public class LeftMenuFragment extends BaseFragment {

    private MenuTabBean menuTabBean;
    ListView listView;
    public int selectedIndex=0;
    MBaseAdtapter adapter;
    @Override
    public View initChildView() {

        listView = new ListView(mActivity);
        listView.setPadding(40,70,0,0);
        listView.setDividerHeight(0);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setBackgroundColor(Color.TRANSPARENT);
        return listView;
    }

    public void setData(MenuTabBean menuTabBean){

        this.menuTabBean=menuTabBean;

        if(menuTabBean!=null){

            adapter = new MBaseAdtapter();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new MOnItemClickLitener());
        }
    }

    class MOnItemClickLitener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           MainActivity mainActivity=(MainActivity)mActivity;
            mainActivity.getMenu().toggle();

            //根据点击的位置来切换页面
            MainActivity mainActivity1=(MainActivity)mActivity;
            mainActivity.getContent().getPager().switchPager(position);

            selectedIndex=position;
            adapter.notifyDataSetChanged();


        }
    }
    class MBaseAdtapter extends BaseAdapter{

        @Override
        public int getCount() {
            return menuTabBean.data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=View.inflate(mActivity, R.layout.menu__item_layout,null);
            TextView tv= (TextView) convertView.findViewById(R.id.tv_menu_tab_name);
            tv.setText(menuTabBean.data.get(position).title);
            if(position==selectedIndex){
                tv.setSelected(true);
            }else{
                tv.setSelected(false);
            }
            return convertView;
        }
    }
}
