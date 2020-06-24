package com.utn.utnphones.model;

import com.utn.utnphones.model.enums.BillStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "bills")

@NoArgsConstructor
@AllArgsConstructor
@Data

@ApiModel(value = "Bill", description = "The bill of a line")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bill")
    @ApiModelProperty(value = "The id of the bill")
    private Integer idBill;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_line")
    @ApiModelProperty(value = "The phone line afected by the bill")
    private Line line;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    @ApiModelProperty(value = "The owner")
    private User user;

    @Column(name = "qty_calls")
    @ApiModelProperty(value = "Number of calls")
    private Integer qtyCalls;

    @Column(name = "total_production_cost")
    @ApiModelProperty(value = "Production cost for the company")
    private Double totalProductionCost;

    @Column(name = "total_price")
    @ApiModelProperty(value = "Total price")
    private Double totalPrice;

    @Column(name = "issue_date")
    @ApiModelProperty(value = "Issue Date")
    private Timestamp issueDate;

    @Column(name = "expiration_date")
    @ApiModelProperty(value = "Expiration Date")
    private Timestamp expirationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @ApiModelProperty(value = "The bill status")
    private BillStatus status;
}
