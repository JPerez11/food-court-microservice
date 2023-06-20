package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderRankingDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.OrderTimeResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.OrderHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.OrderTimeResponseMapper;
import com.pragma.powerup.usermicroservice.domain.api.OrderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderHandlerImpl implements OrderHandler {

    private final OrderServicePort orderServicePort;
    private final OrderTimeResponseMapper orderTimeResponseMapper;

    @Override
    public void createOrder(Long restaurantId) {
        orderServicePort.createOrder(restaurantId);
    }

    @Override
    public void assignEmployee(Long id) {
        orderServicePort.assignEmployee(id);
    }

    @Override
    public void updateOrderStatus(Long id, String status) {
        orderServicePort.updateOrderStatus(id, status);
    }

    @Override
    public void cancelOrder(Long orderId) {
        orderServicePort.cancelOrder(orderId);
    }

    @Override
    public List<OrderTimeResponseDto> showOrderTime() {
        return orderTimeResponseMapper.toResponseList(orderServicePort.showOrderTime());
    }

    @Override
    public List<OrderRankingDto> orderRanking(Long restaurantId) {
        List<Object[]> responseList = orderServicePort.orderRanking(restaurantId);
        List<OrderRankingDto> rankingList = new ArrayList<>();
        for (Object[] obj :
                responseList) {
            String formattedDuration = formatDuration((Double) obj[1]);
            rankingList.add(new OrderRankingDto((Long) obj[0], formattedDuration));
        }

        return rankingList;
    }

    private String formatDuration(double seconds) {
        Duration duration = Duration.ofSeconds((long) seconds);
        long hour = duration.toHours();
        long min = duration.toMinutes() % 60;
        long sec = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hour, min, sec);
    }
}
