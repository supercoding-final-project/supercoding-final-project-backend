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
    @Version
    private Integer version;    // 낙관적 락

    public Payment approvedBy(Mentor mentor) {
        mentor.getUser().getAbstractAccount().chargePaymoney(totlaPrice.longValue());
        isCompleted = true;

        return Payment.of(this, mentee, mentor);
    }

    public void beRejected() {
        mentee.getUser().getAbstractAccount().chargePaymoney(totlaPrice.longValue());
        this.isCompleted = false;
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public void canceled() {
        mentee.getUser().getAbstractAccount().chargePaymoney(totlaPrice.longValue());
        this.isCompleted = false;
        softDelete();
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
