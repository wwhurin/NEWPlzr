package com.example.jangyujin.gimjangprojects;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RecyclerAdapter2 extends RecyclerView.Adapter<RecyclerAdapter2.ViewHolder> {
    Context context;
    List<Item> items;
    int item_layout;

    EditText TName;
    DatePicker mDateS, mDateF;
    EditText content;

    SetFood setFood;

    public RecyclerAdapter2(Context context, List<Item> items, int item_layout) {
        this.context = context;
        this.items = items;
        this.item_layout = item_layout;


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview2, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Item item = items.get(position);
       /* Drawable drawable = ContextCompat.getDrawable(context, item.getImage());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.image.setBackground(drawable);
        }*/
        holder.title.setText(item.getTitle());

        TName=item.getTName();
        content=item.getContent();
        mDateS=item.getmDateS();
        mDateF=item.getmDateF();

        //holder.Binsert.setOnClickListener();

        holder.Binsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFood = item.getSetFood();
                TName.setText(setFood.getName());
                content.setText(setFood.getContent());
                String[] indate = setFood.getInputdate().split("-");
                mDateS.init(Integer.parseInt(indate[0]), Integer.parseInt(indate[1]), Integer.parseInt(indate[2]),

                        new DatePicker.OnDateChangedListener() {

                            //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                            @Override

                            public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                                      int dayOfMonth) {
                                // TODO Auto-generated method stub

                                //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                            }
                        });
                String[] outdate = setFood.getOutdate().split("-");
                mDateF.init(Integer.parseInt(outdate[0]), Integer.parseInt(outdate[1]), Integer.parseInt(outdate[2]),

                        new DatePicker.OnDateChangedListener() {

                            //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.

                            @Override

                            public void onDateChanged(DatePicker view, int year, int monthOfYear,

                                                      int dayOfMonth) {
                                // TODO Auto-generated method stub

                                //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                            }
                        });
            }
        });

        holder.BDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //ImageView image;
        TextView title;
        CardView cardview;

        Button Binsert;
        Button BDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            //Log.d(("!!"));
            //image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
            Binsert=(Button) itemView.findViewById(R.id.Bupdate);
            BDelete=(Button) itemView.findViewById(R.id.BDelete);
            cardview = (CardView) itemView.findViewById(R.id.cardview);
        }
    }
}
