package co.com.mirecarga.vendedor.notification;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.com.mirecarga.core.util.SwipeButton.SwipeRevealLayout;
import co.com.mirecarga.core.util.SwipeButton.ViewBinderHelper;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.business.NotificacionDato;

public class NotificacionAdapter extends RecyclerView.Adapter {
    private List<NotificacionDato> mDataSet;
    private LayoutInflater mInflater;
    private Context mContext;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    NotificacionAdapter(final Context context, List<NotificacionDato> dataSet) {
        mContext = context;
        mDataSet = dataSet;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final View view = mInflater.inflate(R.layout.row_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder h, final int position) {
        final ViewHolder holder = (ViewHolder) h;

        if (mDataSet != null && 0 <= position && position < mDataSet.size()) {
            final String data = mDataSet.get(position).getTitle();
            holder.titleTextView.setText(data);
            holder.bodyTextView.setText(mDataSet.get(position).getBody());
            int op = 4;
            if (mDataSet.get(position).getData() != null) {
                op = Integer.parseInt(mDataSet.get(position).getData());
            }
            switch (op) {
                case 1:
                    holder.iconImageView.setBackgroundResource(R.drawable.ic_link_off_white_48dp);
                    break;
                case 2:
                    holder.iconImageView.setBackgroundResource(R.drawable.ic_emoji_transportation_white_48dp);
                    break;
                case 3:
                    holder.iconImageView.setBackgroundResource(R.drawable.ic_add_a_photo_white_48dp);
                    break;
                default:
                    holder.iconImageView.setBackgroundResource(R.drawable.ic_notifications_none_white_24dp);
            }

            binderHelper.bind(holder.swipeLayout, data);
            holder.bind(data);
        }
    }

    void addItem(final NotificacionDato item) {
        try {
            mDataSet.add(0, item);
            notifyItemInserted(0);
        } catch (final Exception e) {
            Log.e("MainActivity", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        if (mDataSet == null)
            return 0;
        return mDataSet.size();
    }

    void saveStates(final Bundle outState) {
        binderHelper.saveStates(outState);
    }

    void restoreStates(final Bundle inState) {
        binderHelper.restoreStates(inState);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        private TextView titleTextView;
        private TextView bodyTextView;
        private ImageView iconImageView;
        private CardView frontLayout;
        private ConstraintLayout archiveLayout;
        private ConstraintLayout deleteLayout;

        ViewHolder(final View itemView) {
            super(itemView);
            swipeLayout = itemView.findViewById(R.id.swipe_layout);
            titleTextView = itemView.findViewById(R.id.text1);
            bodyTextView = itemView.findViewById(R.id.text2);
            iconImageView = itemView.findViewById(R.id.symbol);
            frontLayout = itemView.findViewById(R.id.front_layout);
            archiveLayout = itemView.findViewById(R.id.ButtonAchive);
            deleteLayout = itemView.findViewById(R.id.ButtonDelete);
        }

        void bind(final String data) {
            archiveLayout.setOnClickListener(v -> {
                mDataSet.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), mDataSet.size());
                Toast.makeText(mContext, data + " archivado", Toast.LENGTH_SHORT).show();
            });

            deleteLayout.setOnClickListener(v -> {
                mDataSet.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                notifyItemRangeChanged(getAdapterPosition(), mDataSet.size());
                Toast.makeText(mContext, data + " eliminado", Toast.LENGTH_SHORT).show();
            });

            frontLayout.setOnClickListener(v -> {
                mDataSet.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
                Toast.makeText(mContext, data + " clicked", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
