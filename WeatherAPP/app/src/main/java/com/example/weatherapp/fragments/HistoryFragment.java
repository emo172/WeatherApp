package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.database.WeatherDao;
import com.example.weatherapp.model.WeatherHistory;
import com.example.weatherapp.utils.WeatherIconMapper;

import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView historyRecyclerView;
    private HistoryAdapter adapter;
    private WeatherDao weatherDao;
    private Button clearButton;
    private TextView emptyView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        historyRecyclerView = view.findViewById(R.id.historyRecyclerView);
        clearButton = view.findViewById(R.id.clearButton);
        emptyView = view.findViewById(R.id.emptyView);

        // 初始化数据库访问对象
        weatherDao = new WeatherDao(requireContext());

        // 设置布局管理器
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 加载历史数据
        loadHistoryData();

        // 设置清空按钮点击事件
        clearButton.setOnClickListener(v -> {
            weatherDao.clearAllHistory();
            loadHistoryData(); // 刷新列表
        });

        return view;
    }

    private void loadHistoryData() {
        List<WeatherHistory> historyList = weatherDao.getAllHistory();

        // 显示/隐藏空视图
        if (historyList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            historyRecyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            historyRecyclerView.setVisibility(View.VISIBLE);

            if (adapter == null) {
                adapter = new HistoryAdapter(historyList);
                historyRecyclerView.setAdapter(adapter);
            } else {
                adapter.updateData(historyList);
            }
        }
    }

    // 历史记录适配器
    private class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

        private List<WeatherHistory> historyList;

        public HistoryAdapter(List<WeatherHistory> historyList) {
            this.historyList = historyList;
        }

        public void updateData(List<WeatherHistory> newList) {
            this.historyList = newList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_weather_history, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            WeatherHistory history = historyList.get(position);

            holder.cityName.setText(history.getCityName());
            holder.temperature.setText(String.format("%s°C", history.getTemperature()));
            holder.weatherCondition.setText(history.getWeatherCondition());
            holder.queryTime.setText(history.getFormattedTime());
            holder.windInfo.setText(String.format("%s风 %s级 | %skm/h",
                    history.getWindDirection(),
                    history.getWindScale(),
                    history.getWindSpeed()));
            holder.humidityInfo.setText(String.format("湿度: %s%%", history.getHumidity()));
            holder.visibilityInfo.setText(String.format("能见度: %s公里", history.getVisibility()));

            // 设置天气图标
            int iconResId = WeatherIconMapper.getWeatherIcon(history.getWeatherCode());
            holder.weatherIcon.setImageResource(iconResId);

            // 设置删除按钮点击事件
            holder.deleteButton.setOnClickListener(v -> {
                // 获取当前适配器位置
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    WeatherHistory historyToDelete = historyList.get(adapterPosition);

                    // 删除数据库中的记录
                    weatherDao.deleteHistory(historyToDelete.getId());

                    // 从列表中移除
                    historyList.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    notifyItemRangeChanged(adapterPosition, historyList.size());

                    // 检查是否为空
                    if (historyList.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        historyRecyclerView.setVisibility(View.GONE);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return historyList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView cityName;
            TextView temperature;
            TextView weatherCondition;
            TextView windInfo;
            TextView humidityInfo;
            TextView visibilityInfo;
            ImageView weatherIcon;
            TextView queryTime;
            ImageView deleteButton; // 删除按钮

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                cityName = itemView.findViewById(R.id.historyCityName);
                temperature = itemView.findViewById(R.id.historyTemperature);
                weatherCondition = itemView.findViewById(R.id.historyWeatherCondition);
                windInfo = itemView.findViewById(R.id.historyWindInfo);
                humidityInfo = itemView.findViewById(R.id.historyHumidityInfo);
                visibilityInfo = itemView.findViewById(R.id.historyVisibilityInfo);
                weatherIcon = itemView.findViewById(R.id.historyWeatherIcon);
                queryTime = itemView.findViewById(R.id.historyTime);
                deleteButton = itemView.findViewById(R.id.deleteButton);
            }
        }
    }
}
