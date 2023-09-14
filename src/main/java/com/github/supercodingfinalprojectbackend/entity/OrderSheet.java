package com.github.supercodingfinalprojectbackend.entity;

import com.github.supercodingfinalprojectbackend.dto.PostDto.OrderCodeReviewDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "orderSheetId", callSuper = false)
@Table(name = "order_sheets")
public class OrderSheet extends CommonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_sheet_id")
    private Long orderSheetId;
    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private Mentee mentee;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;
    @Column(name = "total_price")
    private Integer totlaPrice;
    @Column(name = "is_completed")
    private Boolean isCompleted;

    public Payment approvedBy(Mentor mentor) {
        mentor.getUser().getAbstractAccount().chargePaymoney(totlaPrice.longValue());
        isCompleted = true;
        isDeleted = true;

        return Payment.builder()
                .orderSheet(this)
                .consumerAbstarctAccount(mentee.getUser().getAbstractAccount())
                .sellerAbstractAccount(mentor.getUser().getAbstractAccount())
                .build();
    }

    public void beRejected() {
        this.isDeleted = true;
        mentee.getUser().getAbstractAccount().chargePaymoney(totlaPrice.longValue());
    }

    public static OrderSheet of(OrderCodeReviewDto orderCodeReviewDto, Mentee mentee, Posts posts){
        return OrderSheet.builder()
                .mentee(mentee)
                .post(posts)
                .totlaPrice(orderCodeReviewDto.getTotalPrice())
                .isCompleted(false)
                .build();
    }
}
