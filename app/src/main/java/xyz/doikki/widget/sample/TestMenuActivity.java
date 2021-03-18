package xyz.doikki.widget.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xyz.doikki.widget.circlemenu.CircleMenuLayout;
import xyz.doikki.widget.circlemenu.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class TestMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_menu);

        CircleMenuLayout circleMenuLayout = findViewById(R.id.menu);

        final List<MenuItem> menuItems = new ArrayList<>();

        for(int i = 0; i < 6; i++) {
            MenuItem menuItem = new MenuItem();
            menuItem.logoRes = R.drawable.ic_love;
            menuItem.name = "菜单" + i;
            menuItems.add(menuItem);
        }

        circleMenuLayout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return menuItems.size();
            }

            @Override
            public Object getItem(int position) {
                return menuItems.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
                ImageView logo = view.findViewById(R.id.menu_logo);
                MenuItem menuItem = menuItems.get(position);
                logo.setImageResource(menuItem.logoRes);
                TextView name = view.findViewById(R.id.menu_name);
                name.setText(menuItem.name);
                return view;
            }
        });

        circleMenuLayout.setOnItemClickListener(new CircleMenuLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(view.getContext(), menuItems.get(position).name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
