package com.github.supercodingfinalprojectbackend.repository;

import com.github.supercodingfinalprojectbackend.entity.Mentee;
import com.github.supercodingfinalprojectbackend.entity.Mentor;
import com.github.supercodingfinalprojectbackend.entity.OrderSheet;
import com.github.supercodingfinalprojectbackend.entity.SelectedClassTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectedClassTimeRepository extends JpaRepository<SelectedClassTime, Long> {

    List<SelectedClassTime> findAllByMentorAndOrderSheetAndIsDeletedIsFalse(Mentor mentor, OrderSheet orderSheet);
    List<SelectedClassTime> findAllByMentorAndDayAndHourAndIsDeletedIsFalse(Mentor mentor, Integer day, Integer hour);
    List<SelectedClassTime> findAllByMenteeAndDayAndHourAndIsDeletedIsFalse(Mentee mentee, Integer day, Integer hour);
    List<SelectedClassTime> findAllByOrderSheet(OrderSheet orderSheet);

    void deleteAllByMentorUserUserIdAndOrderSheet(Long userId, OrderSheet orderSheet);

    void deleteAllByMenteeUserUserIdAndOrderSheetIsIn(Long userId, List<OrderSheet> orderSheets);
}
